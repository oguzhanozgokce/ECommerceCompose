package com.hoy.ecommercecompose.domain.usecase.product

import com.hoy.ecommercecompose.domain.repository.ProductRepository

class GetProductDetailUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: Int) = productRepository.getProductDetail(productId)
}