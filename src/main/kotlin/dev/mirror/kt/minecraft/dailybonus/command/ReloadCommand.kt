package dev.mirror.kt.minecraft.dailybonus.command

import dev.mirror.kt.minecraft.dailybonus.DailyBonus
import dev.mirror.kt.minecraft.dailybonus.DailyBonusCommand
import org.bukkit.command.CommandSender

object ReloadCommand : DailyBonusCommand {
    override val commandName: String = "reload"

    override fun dispatch(sender: CommandSender, args: List<String>) {
        DailyBonus.reloadItems()
        DailyBonus.reloadMessages()
        sender.sendMessage(DailyBonus.messages.RELOAD_SUCCESS)
    }
}