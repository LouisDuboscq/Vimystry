package com.lduboscq.vimystry.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorScreen(error: String) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Red),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "Ooops",
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center),
            style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
            textAlign = TextAlign.Center
        )

        Text(
            "Error\n$error",
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center),
            style = LocalTextStyle.current.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}
