package com.example.jetpackcomposeproducts.products.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeproducts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen() {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(R.string.title_product)) })
    }) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)) {
            Text(text = stringResource(R.string.title_product))
        }
    }
}