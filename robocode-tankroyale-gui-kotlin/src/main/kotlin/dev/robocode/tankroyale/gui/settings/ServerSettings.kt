package dev.robocode.tankroyale.gui.settings

import dev.robocode.tankroyale.gui.ui.new_server.WsUrl
import dev.robocode.tankroyale.gui.util.RegisterWsProtocol
import java.net.URI


object ServerSettings : PropertiesStore("Robocode Server Config", "server.properties") {

    const val DEFAULT_PORT = 80
    const val DEFAULT_URL = "ws://localhost"

    private const val SERVER_URL_PROPERTY = "server.url"
    private const val USER_URLS_PROPERTY = "user.urls"

    init {
        RegisterWsProtocol // work-around for ws:// with URI class
    }

    var serverUrl: String
        get() {
            val url = properties.getProperty(SERVER_URL_PROPERTY, DEFAULT_URL)
            return WsUrl(url).origin
        }
        set(value) {
            properties.setProperty(SERVER_URL_PROPERTY, value)
        }

    val serverPort: Int get() = URI(serverUrl).port

    var userUrls: List<String>
        get() {
            val urls = properties.getProperty(USER_URLS_PROPERTY, "")
            return if (urls.isBlank()) {
                listOf(serverUrl)
            } else {
                urls.split(",")
            }
        }
        set(value) {
            val list = ArrayList(value)
            list.remove(DEFAULT_URL)
            properties.setProperty(USER_URLS_PROPERTY, list.joinToString(separator = ","))
        }

    init {
        resetToDefault()
        load()
    }

    private fun resetToDefault() {
        serverUrl = DEFAULT_URL
        userUrls = emptyList()
    }
}

fun main() {
    with(ServerSettings) {
        println("serverUrl: $serverUrl")
        println("port: $serverPort")
        println("userUrls: $userUrls")

        userUrls = listOf("ws://1.2.3.4:90", "wss://localhost:900")
    }
}