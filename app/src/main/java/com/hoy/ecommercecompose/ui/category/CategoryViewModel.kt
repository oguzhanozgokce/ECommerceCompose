package com.hoy.ecommercecompose.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.model.ProductUi
import com.hoy.ecommercecompose.domain.usecase.category.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _uiState: MutableStateFlow<CategoryContract.UiState> =
        MutableStateFlow(CategoryContract.UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<CategoryContract.UiEffect>() }
    val uiEffect: Flow<CategoryContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private val categoryList = mutableListOf<ProductUi>()

    init {
        savedStateHandle.get<String>("category")?.let {
            getProductsByCategory(it)
        }
    }

    fun onAction(action: CategoryContract.UiAction) {
        when (action) {
            is CategoryContract.UiAction.ClearError -> updateUiState { copy(errorMessage = "") }
            is CategoryContract.UiAction.SearchProducts -> searchProducts(action.query)
            is CategoryContract.UiAction.ChangeQuery -> changeQuery(action.query)
            is CategoryContract.UiAction.LoadProducts -> updateUiState {
                copy()
            }
        }
    }

    fun getCategory(): String {
        return savedStateHandle.get<String>("category").orEmpty()
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            getProductsByCategoryUseCase(category)
                .onStart { updateUiState { copy(isLoading = true) } }
                .onCompletion { updateUiState { copy(isLoading = false) } }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            categoryList.clear()
                            categoryList.addAll(result.data)
                            _uiState.update {
                                it.copy(
                                    categoryList = categoryList
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiEffect.send(CategoryContract.UiEffect.ShowError(result.message))
                        }
                    }
                }
        }
    }

    private fun changeQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun searchProducts(query: String) {
        val filteredList = if (query.isEmpty()) {
            categoryList
        } else {
            categoryList.filter { it.title.contains(query, ignoreCase = true) }
        }
        _uiState.update {
            it.copy(
                categoryList = filteredList
            )
        }
    }

    private fun updateUiState(block: CategoryContract.UiState.() -> CategoryContract.UiState) {
        _uiState.update(block)
    }
}
