package ru.aasmc.jetreddit.routing

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

private val localBackPressedDispatcher = staticCompositionLocalOf<OnBackPressedDispatcher?> { null }

@Composable
fun BackButtonHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {

}

@Composable
fun BackButtonAction(onBackPressed: () -> Unit) {

}