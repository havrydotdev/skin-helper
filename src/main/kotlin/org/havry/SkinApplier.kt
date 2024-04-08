package org.havry

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SkinApplier {
    private val plugin = SkinHelper.instance

    fun applyTexture(player: Player, value: String, signature: String) {
        val profile = (player.playerProfile as PlayerProfile)

        profile.properties.removeIf {
            it.name == "textures"
        }

        profile.properties.add(ProfileProperty("textures", value, signature))

        Bukkit.getScheduler().runTask(plugin) {
            player.playerProfile = profile
        }
    }
}