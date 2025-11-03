package com.kishibashi.androidapp.tutorial.architecture.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}
