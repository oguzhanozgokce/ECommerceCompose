package com.hoy.ecommercecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.noRippleClickable
import com.hoy.ecommercecompose.data.source.remote.model.Category
import com.hoy.ecommercecompose.ui.home.HomeContract
import com.hoy.ecommercecompose.ui.theme.ECTheme
import com.hoy.ecommercecompose.ui.theme.displayFontFamily

@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier,
    onCategoryListClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .noRippleClickable { onCategoryListClick(category.name) }
            .size(ECTheme.dimensions.oneHundred, ECTheme.dimensions.oneHundredTwenty),
        colors = CardDefaults.cardColors(
            containerColor = ECTheme.colors.white
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = ECTheme.dimensions.two)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(category.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.category_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(ECTheme.dimensions.oneHundred, ECTheme.dimensions.sixty)
                    .background(ECTheme.colors.white)
                    .clip(RoundedCornerShape(ECTheme.dimensions.four))
            )
            Text(
                text = category.name,
                color = ECTheme.colors.black,
                fontFamily = displayFontFamily,
                modifier = Modifier
                    .padding(top = ECTheme.dimensions.four)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CategoryList(
    uiState: HomeContract.UiState,
    modifier: Modifier = Modifier,
    onCategoryListClick: (String) -> Unit
) {
    LazyRow(modifier = modifier) {
        items(uiState.categoryList) { category ->
            CategoryCard(
                onCategoryListClick = onCategoryListClick,
                category = category,
                modifier = Modifier.padding(ECTheme.dimensions.eight)
            )
        }
    }
}
