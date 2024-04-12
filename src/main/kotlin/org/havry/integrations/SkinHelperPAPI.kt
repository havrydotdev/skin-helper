package org.havry.integrations

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.havry.SkinHelper
import org.jetbrains.annotations.NotNull
import java.util.*

class SkinHelperPAPI : PlaceholderExpansion() {
    private val plugin = SkinHelper.instance
    private val blockClient = SkinHelper.blockClient
    private val steveTextureId = "6d3b06c38504ffc0229b9492147c69fcf59fd2ed7885f78502152f77b4d50de1"

    override fun getIdentifier(): String {
        return "skinhelper"
    }

    override fun getAuthor(): String {
        return "github.com/gavrylenkoIvan"
    }

    override fun getVersion(): String {
        return "1.3.0"
    }

    override fun persist(): Boolean {
        return true
    }

    override fun onRequest(player: OfflinePlayer?, @NotNull params: String): String? {
        val formatted = params.lowercase(Locale.getDefault())

        return when(formatted) {
            "texture_id" -> getTextureId(player)
            "texture_url" -> "https://mc-heads.net/avatar/${getTextureId(player)}.png"
            "world_prefix" ->
                when(player!!.player.world.environment) {
                    World.Environment.THE_END -> plugin.config.getString("world_tab_prefixes.end")
                    World.Environment.NORMAL -> plugin.config.getString("world_tab_prefixes.normal")
                    World.Environment.NETHER -> plugin.config.getString("world_tab_prefixes.nnether")
                    else -> null
                }

            else -> null
        }
    }

    private fun getTextureId(player: OfflinePlayer?): String {
        if (player == null) {
            return steveTextureId
        }

        val skin = blockClient.getSkin(player.uniqueId).get()
        return skin?.textureId ?: steveTextureId
    }
}