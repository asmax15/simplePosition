package me.asmax.simpleposition

import me.asmax.simpleposition.commands.PositionCommandExecutor
import me.asmax.simpleposition.utils.Config
import org.bukkit.plugin.java.JavaPlugin

class SimplePosition : JavaPlugin() {

    val prefix = "§8[§9SimplePosition§8]§7"

    companion object {
        lateinit var instance: SimplePosition
        private set
    }

    override fun onLoad() {
        Config.Config()
        instance = this
    }

    override fun onEnable() {
        registerCommands()
    }

    override fun onDisable() {
        Config.save()
    }

    private fun registerCommands() {
        val positionCommand = getCommand("position") ?: error("Couldn't get info command! This should not happen!")
        positionCommand.setExecutor(PositionCommandExecutor())
    }

}