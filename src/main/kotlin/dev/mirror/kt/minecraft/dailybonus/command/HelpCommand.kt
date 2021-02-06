package dev.mirror.kt.minecraft.dailybonus.command

import dev.mirror.kt.minecraft.dailybonus.DailyBonusCommand
import org.bukkit.command.CommandSender

object HelpCommand : DailyBonusCommand {
    override val commandName: String = "help"
    override fun dispatch(sender: CommandSender, args: List<String>) {
        sender.sendMessage(
            """
            DailyBonus
            /dailybonus setitems
                Set items in inventory for daily bonus.
            /dailybonus give (player)
                Give items for player(default: self).
            /dailybonus reload
                Reload config.
            /dailybonus help
                Show this help.
        """.trimIndent()
        )
    }
}