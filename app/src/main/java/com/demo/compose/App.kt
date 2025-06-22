package com.demo.compose

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.crossfade
import coil3.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .logger(DebugLogger())
            .crossfade(true)
            .build()
}