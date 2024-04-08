package org.havry.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

class TabCompletion : TabCompleter {
    private val subcommands = arrayListOf("restart")

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        alias: String,
        args: Array<out String>
    ): List<String>? {
        if (args.size == 1) {
            return if (args[0] != "") {
                subcommands.filter {
                    it.lowercase(Locale.getDefault()).startsWith(args[0].lowercase(Locale.getDefault()))
                }.sorted()
            } else {
                subcommands.sorted()
            }
        } else if (args.size == 2) {
            if (args[0] == "restart") {
                return arrayListOf("1", "2", "5")
            }
        }

        return null
    }
}
