package dev.mirror.kt.minecraft.dailybonus

import de.tr7zw.changeme.nbtapi.NBTFile
import dev.mirror.kt.minecraft.dailybonus.command.GiveCommand
import dev.mirror.kt.minecraft.dailybonus.command.HelpCommand
import dev.mirror.kt.minecraft.dailybonus.command.ReloadCommand
import dev.mirror.kt.minecraft.dailybonus.command.SetItemsCommand
import dev.mirror.kt.minecraft.dailybonus.listener.GiveDailyBonusListener
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectories
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.notExists
import kotlin.io.path.outputStream

class DailyBonus : JavaPlugin() {
    private val subCommands: List<DailyBonusCommand> = listOf(
        HelpCommand,
        SetItemsCommand,
        GiveCommand,
        ReloadCommand,
    )

    override fun onEnable() {
        instance = this
        reloadItems()
        reloadMessages()
        server.pluginManager.registerEvents(
            GiveDailyBonusListener(),
            this
        )
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val executor = if (args.isEmpty()) {
            HelpCommand
        } else {
            subCommands.find { it.commandName.equals(args[0], true) } ?: HelpCommand
        }
        executor.dispatch(sender, args.toList())

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String> = subCommands.map {
        it.commandName
    }

    companion object {
        lateinit var instance: DailyBonus

        fun getPath(path: String): Path =
            instance.dataFolder
                .toPath()
                .resolve(path)

        lateinit var bonusItems: List<ItemStack>
            private set

        lateinit var messages: MessageConfig
            private set

        @OptIn(ExperimentalPathApi::class)
        fun reloadItems() {
            bonusItems = getPath("items")
                .also {
                    if (it.notExists()) it.createDirectories()
                }
                .listDirectoryEntries("*.nbt")
                .map {
                    NBTFile(it.toFile()).getItemStack("item")
                }
        }

        @OptIn(ExperimentalPathApi::class)
        fun reloadMessages() {
            val messagesFile = getPath("messages.yml")
            if (messagesFile.notExists()) {
                DailyBonus::class.java.classLoader.getResourceAsStream("messages.yml")
                    ?.copyTo(messagesFile.outputStream())
            }

            messages = MessageConfig(YamlConfiguration.loadConfiguration(messagesFile.toFile()))
        }
    }
}