package me.asmax.simpleposition.commands

import me.asmax.simpleposition.SimplePosition
import me.asmax.simpleposition.utils.Config
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PositionCommandExecutor: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        var player: Player = sender

        if (!player.hasPermission("position.position")) {
            player.sendMessage("${SimplePosition.instance.prefix} §cDafür hats du keine Rechte.")
            return true
        }

        if (args.isEmpty()) {
            player.sendMessage("${SimplePosition.instance.prefix} §7Bitte verwende: §9/position <del | list | [Position]>")
            return true
        }

        when (args[0]) {
            "list" -> player.sendMessage("${SimplePosition.instance.prefix} §7Available §9positions: §b${Config.getConfig().getConfigurationSection(".Positions")?.getKeys(false)}")
            "del" -> {
                if (Config.getConfig().getString("Positions.") != null) {

                    Config.getConfig().set("Positions.${args[1]}", null)
                    Config.save()

                    player.sendMessage("${SimplePosition.instance.prefix} §7The Position §9${args[1]} §7has been §9§osuccessfully deleted.")
                } else {
                    player.sendMessage("${SimplePosition.instance.prefix} §7Diese Position existiert nicht.")
                }

            }
            else -> {

                if (Config.getConfig().get("Positions.${args[0]}") == null) {

                    Config.getConfig().set("Positions.${args[0]}.World", player.world.name)
                    Config.getConfig().set("Positions.${args[0]}.X", player.location.blockX)
                    Config.getConfig().set("Positions.${args[0]}.Y", player.location.blockY)
                    Config.getConfig().set("Positions.${args[0]}.Z", player.location.blockZ)

                    Config.save()
                    player.sendMessage("${SimplePosition.instance.prefix} §7Deine Position wurde gesichert unter §9${args[0]}")

                    return true
                }

                if (Config.getConfig().get("Positions.${args[0]}") == null) {
                    player.sendMessage("${SimplePosition.instance.prefix} §7Diese Position §9§oexisitiert nicht")
                    return true
                }

                player.sendMessage("${SimplePosition.instance.prefix} §7${Config.getConfig().getInt("Positions.${args[0]}.X")}, Y: ${Config.getConfig().getInt("Positions.${args[0]}.Y")}, " +
                        "Z: ${Config.getConfig().getInt("Positions.${args[0]}.Z")}")

            }
        }
        return true
    }
}