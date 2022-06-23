package com.lduboscq.vimystry.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorScreen(error: String) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Red)
    ) {
        Text(
            "Error\n$error",
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            style = LocalTextStyle.current.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}
