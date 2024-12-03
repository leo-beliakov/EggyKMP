package com.leoapps.eggy.base.storage

import android.content.Context

class ApplicationDirectoryProviderAndroidImpl(
    context: Context
) : ApplicationDirectoryProvider {

    override val appDirectory: String = context.filesDir.absolutePath
}