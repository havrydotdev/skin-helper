package org.havry.commands.subcommands

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.havry.SkinHelper
import org.havry.entities.Skin

class Set {
    private val plugin = SkinHelper.instance
    private val mineSkinClient = SkinHelper.mineSkinClient
    private val blockClient = SkinHelper.blockClient
    private val skinApplier = SkinHelper.skinApplier

    fun run(sender: CommandSender, args: Array<out String>): Boolean {
        Bukkit.getScheduler().runTaskAsynchronously(plugin) {
            val url = args[1]
            val skin = mineSkinClient.generateUrl(url).get()
            val texture = skin.data.texture
            val player = (sender as Player)

            skinApplier.applyTexture(
                player,
                texture.value,
                texture.signature
            )

            blockClient.createSkin(
                Skin(
                    texture.url.split("/").last(),
                    player.uniqueId,
                    texture.value,
                    texture.signature
                )
            ).join()
        }

        return true
    }
}