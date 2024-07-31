package com.hoy.ecommercecompose.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column {
        Text(text = "Home Screen", modifier = modifier)
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen()
}