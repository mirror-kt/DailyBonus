package dev.mirror.kt.minecraft.dailybonus

import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration

class MessageConfig(val config: YamlConfiguration) {
    val SET_ITEM_SUCCESS: String
        get() = config.getString("set-item-success")?.colored() ?: "Set daily bonuses succeed:"
    val DAILY_BONUSES_SUCCESS: String
        get() = config.getString("daily-bonuses-success")?.colored() ?: "Today's daily bonuses given!"
    val DAILY_BONUS_DROPPED: String
        get() = config.getString("daily-bonuses-drop-message")?.colored()
            ?: "Your inventory is full, so we dropped daily bonus to the ground!"
    val GIVE_DAILYBONUS_ADMIN: String
        get() = config.getString("daily-bonuses-admin-success")?.colored() ?: "Daily bonus items are given to you!"
    val RELOAD_SUCCESS: String
        get() = config.getString("reload-success") ?: "Reload succeed."
}

fun String.colored(): String = ChatColor.translateAlternateColorCodes('&', this)