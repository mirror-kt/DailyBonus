package dev.mirror.kt.minecraft.dailybonus.command

import dev.mirror.kt.minecraft.dailybonus.DailyBonus
import dev.mirror.kt.minecraft.dailybonus.DailyBonusCommand
import dev.mirror.kt.minecraft.dailybonus.formatted
import dev.mirror.kt.minecraft.dailybonus.giveItems
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GiveCommand : DailyBonusCommand {
    override val commandName: String = "give"

    override fun dispatch(sender: CommandSender, args: List<String>) {
        if (sender !is Player) {
            sender.sendMessage("This command only execute in game.")
            return
        }

        val items = DailyBonus.bonusItems

        sender.giveItems(
            items,
            """
                ${DailyBonus.messages.GIVE_DAILYBONUS_ADMIN}
                ${items.joinToString("\n") { it.formatted() }}
            """.trimIndent(),
            DailyBonus.messages.DAILY_BONUS_DROPPED,
        )
    }
}