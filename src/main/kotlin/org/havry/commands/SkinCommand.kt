package org.havry.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.havry.commands.subcommands.Set

class SkinCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}Specify command to be executed")
            return true
        }

        when (args[0]) {
            "set" -> return Set().run(sender, args)
            else -> {
                sender.sendMessage("${ChatColor.RED}Unknown command")
                return true
            }
        }
    }
}