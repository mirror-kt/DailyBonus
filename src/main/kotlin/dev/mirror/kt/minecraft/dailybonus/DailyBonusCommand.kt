package dev.mirror.kt.minecraft.dailybonus

import org.bukkit.command.CommandSender

interface DailyBonusCommand {
    val commandName: String

    fun dispatch(
        sender: CommandSender,
        args: List<String>,
    )
}