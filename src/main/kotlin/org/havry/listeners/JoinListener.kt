package org.havry.listeners

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.havry.SkinHelper

class JoinListener : Listener {
    private val plugin = SkinHelper.instance
    private val blockClient = SkinHelper.blockClient
    private val skinApplier = SkinHelper.skinApplier

    @EventHandler(priority = EventPriority.LOWEST)
    fun onJoin(e: PlayerJoinEvent) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin) {
            val skin = blockClient.getSkin(e.player.uniqueId).get()

            skinApplier.applyTexture(
                e.player,
                skin.value,
                skin.signature
            )
        }
    }
}
