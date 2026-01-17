package com.example.taskmanagerapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val PrimaryColor = Color(0xFF6200EE)
val OnPrimaryColor = Color(0xFFFFFFFF)
val SecondaryColor = Color(0xFF03DAC5)
val OnSecondaryColor = Color(0xFF000000)
val Background = Color(0xFFF2F2F2)

private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    background = Background
)

@Composable
fun TaskManagerAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
