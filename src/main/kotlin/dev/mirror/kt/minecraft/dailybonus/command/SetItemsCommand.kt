package dev.mirror.kt.minecraft.dailybonus.command

import de.tr7zw.changeme.nbtapi.NBTFile
import dev.mirror.kt.minecraft.dailybonus.DailyBonus
import dev.mirror.kt.minecraft.dailybonus.DailyBonusCommand
import dev.mirror.kt.minecraft.dailybonus.formatted
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectory
import kotlin.io.path.deleteIfExists
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.notExists

object SetItemsCommand : DailyBonusCommand {
    override val commandName: String = "setitems"

    @OptIn(ExperimentalPathApi::class)
    override fun dispatch(sender: CommandSender, args: List<String>) {
        if (sender !is Player) {
            sender.sendMessage("This command only execute in game.")
            return
        }

        val itemsDir = DailyBonus.getPath("items")
            .also {
                if (it.notExists()) {
                    it.createDirectory()
                }
                it.listDirectoryEntries()
                    .forEach { file -> file.deleteIfExists() }
            }

        sender.inventory.storageContents
            .filterNotNull()
            .filterNot { it.type == Material.AIR }
            .forEach { item ->
                val file = itemsDir.resolve("${UUID.randomUUID()}.nbt")
                with(NBTFile(file.toFile())) {
                    setItemStack("item", item)
                    save()
                }
            }

        DailyBonus.reloadItems()

        sender.sendMessage(
            """
            ${DailyBonus.messages.SET_ITEM_SUCCESS}
            ${DailyBonus.bonusItems.joinToString("\n") { it.formatted() }}
            """.trimIndent()
        )
    }
}