package com.github.ljmartin94.zealotry

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import di.initKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin()

        setContent {
            App()
        }
    }
}

@Preview
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppAndroidPreview() {
    App()
}
