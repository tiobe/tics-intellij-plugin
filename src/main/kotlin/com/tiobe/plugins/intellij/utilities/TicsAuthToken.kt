package com.tiobe.plugins.intellij.utilities

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.intellij.openapi.util.SystemInfo
import com.tiobe.model.AlertMessages
import com.tiobe.utility.TicsHttpClient
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.hc.core5.util.Timeout
import java.io.FileNotFoundException
import java.io.FileReader

object TicsAuthToken {
    private var authToken: String? = null

    fun getAuthToken(): String? {
        return when (authToken) {
            null -> getFromSystem()
            else -> authToken
        }
    }

    /**
     * Retrieves the TICS Auth token from the file and validates it using the given url.
     * @param ticsConfigUrl the TICS Viewer configuration url.
     */
    fun validateAndGet(ticsConfigUrl: String): ValidAuthTokenInfo {
        val client = TicsHttpClient()
        val tempAuthToken = getFromSystem()?.let {
            client.setBasicAuthorizationHeader(it)
            it
        }

        val httpGet = HttpGet(ticsConfigUrl)

        client.setTimeout(Timeout.ofSeconds(5))
        client.setRequestedWithTics()
        val response = client.createHttpClient().use { http ->
            try {
                http.execute(httpGet) {
                    val entity = EntityUtils.toString(it.entity)
                    val alertMessages = Gson().fromJson(entity, AlertMessages::class.java)
                    if (alertMessages.alertMessages != null) {
                        Response(it.code, alertMessages.alertMessages[0].header)
                    } else {
                        Response(it.code, entity)
                    }
                }
            } catch (_: Exception) {
                Response(408, "The following viewer could not be reached: ")
            }
        }

        if (response.code == 200) {
            authToken = tempAuthToken
        }

        return ValidAuthTokenInfo(response.code, response.message, tempAuthToken)
    }

    /**
     * Gets the authentication token for the TICS Viewer.
     */
    private fun getFromSystem(): String? {
        val ticsAuthToken: String? = System.getenv("TICSAUTHTOKEN")
        if (ticsAuthToken == null) {
            var tokenPath = ""
            if (SystemInfo.isLinux) {
                tokenPath = System.getProperty("user.home") + "/.tics/tics_client.token"
            } else if (SystemInfo.isWindows) {
                tokenPath = System.getenv("APPDATA") + "\\TICS\\tics_client.token"
            }
            return try {
                println(tokenPath)
                val reader = JsonReader(FileReader(tokenPath))
                Gson().fromJson<AuthToken>(reader, AuthToken::class.java).token
            } catch (_: FileNotFoundException) {
                null
            }
        }
        return ticsAuthToken
    }

    data class AuthToken(
        val formatVersion: Int,
        val fileType: String,
        val token: String,
        val description: String,
        val role: String
    )

    data class ValidAuthTokenInfo(
        val code: Int,
        val message: String,
        val authToken: String?
    )

    data class Response(
        val code: Int,
        val message: String
    )
}