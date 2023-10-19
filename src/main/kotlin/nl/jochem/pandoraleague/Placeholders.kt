package nl.jochem.pandoraleague

import com.gufli.kingdomcraft.api.KingdomCraftProvider
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import nl.jochem.pandoraleague.league.LeagueManager
import org.bukkit.OfflinePlayer

private val kdc = KingdomCraftProvider.get()

class Placeholders: PlaceholderExpansion() {

    override fun getAuthor(): String {
        return "BlokJochem"
    }

    override fun getIdentifier(): String {
        return "pandoraleague"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        kdc.kingdoms.forEach {
            if(params == "{${it.name}}_points") return LeagueManager.getPoints(it).toString()
            if(params == "{${it.name}}_position") return LeagueManager.getPosition(it).toString()
        }
        return "pandora league"
    }

}