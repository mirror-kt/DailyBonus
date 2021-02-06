package dev.mirror.kt.minecraft.dailybonus

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun ItemStack.formatted() =
    "${type}${if (itemMeta?.hasDisplayName() == true) "(${itemMeta!!.displayName})" else ""} x $amount"

fun Player.giveItems(items: List<ItemStack>, successMessage: String, dropMessage: String) {
    if (inventory.storageContents.count { it == null || it.type == Material.AIR } > items.size) {
        inventory.addItem(*items.toTypedArray())
        sendMessage(successMessage)
    } else {
        items.forEach { item ->
            world.dropItem(location, item)
        }
        sendMessage(dropMessage)
    }
}