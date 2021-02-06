package dev.mirror.kt.minecraft.dailybonus.listener

import dev.mirror.kt.minecraft.dailybonus.DailyBonus
import dev.mirror.kt.minecraft.dailybonus.formatted
import dev.mirror.kt.minecraft.dailybonus.giveItems
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.time.ZonedDateTime
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists
import kotlin.io.path.reader

class GiveDailyBonusListener : Listener {
    @OptIn(ExperimentalPathApi::class)
    private val playersDir = DailyBonus.getPath("players")
        .also {
            if (it.notExists()) it.createDirectory()
        }

    @EventHandler
    @OptIn(ExperimentalPathApi::class)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerFilePath = playersDir.resolve("${player.uniqueId}")

        val config = runCatching {
            YamlConfiguration.loadConfiguration(
                playerFilePath.reader()
            )
        }.getOrDefault(YamlConfiguration())

        val items = DailyBonus.bonusItems

        val lastLogin = config.getString("lastLogin")?.let { ZonedDateTime.parse(it).toLocalDate() }
        if (lastLogin == null || lastLogin < ZonedDateTime.now().toLocalDate()) {
            player.giveItems(
                items,
                """
                    ${DailyBonus.messages.DAILY_BONUSES_SUCCESS}
                    ${items.joinToString("\n") { it.formatted() }}
                """.trimIndent(),
                DailyBonus.messages.DAILY_BONUS_DROPPED,
            )
        }

        config["lastLogin"] = "${ZonedDateTime.now()}"
        config.save(playerFilePath.toFile())
    }
}