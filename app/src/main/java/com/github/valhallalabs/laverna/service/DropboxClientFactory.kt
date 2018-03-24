package com.github.valhallalabs.laverna.service

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.http.OkHttp3Requestor
import com.dropbox.core.v2.DbxClientV2

/**
 *
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
object DropboxClientFactory {

    private var sDbxClient: DbxClientV2? = null

    @JvmStatic
    fun getClient() = sDbxClient ?: throw IllegalStateException("Dropbox client not initialized.")

    @JvmStatic
    fun init(accessToken: String): DbxClientV2 =
            sDbxClient ?: buildClient(accessToken).also { sDbxClient = it }

    private fun buildClient(accessToken: String): DbxClientV2 {
        val requestConfig = DbxRequestConfig.newBuilder("Laverna Android")
                .withHttpRequestor(OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
                .build()
        return DbxClientV2(requestConfig, accessToken)
    }



}
