package org.havry

import org.bukkit.plugin.java.JavaPlugin
import org.havry.listeners.JoinListener
import org.mineskin.MineskinClient
import java.net.http.HttpClient

class SkinHelper : JavaPlugin() {
    companion object {
        lateinit var instance: SkinHelper private set
        lateinit var mineSkinClient: MineskinClient private set
        lateinit var blockClient: BlockClient private set
        lateinit var skinApplier: SkinApplier private set
    }

    override fun onEnable() {
        instance = this
        mineSkinClient = MineskinClient("SkinHelper")
        blockClient = BlockClient(
            HttpClient.newHttpClient(),
            instance.config.getString("api.url"),
            instance.config.getString("api.api_key")
        )
        skinApplier = SkinApplier()

        instance.server.pluginManager.registerEvents(JoinListener(), this)

        saveDefaultConfig()
        config.options().copyDefaults(true)
        saveConfig()
    }
}