package nl.jochem.pandoraleague

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

fun Player.msg(message: String) {
    sendMessage(PlaceholderAPI.setPlaceholders(this, message.replace("&", "§")))
}