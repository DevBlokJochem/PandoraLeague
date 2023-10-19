package nl.jochem.pandoraleague.events

import com.gufli.kingdomcraft.api.KingdomCraftProvider
import com.gufli.kingdomcraft.api.domain.User
import nl.jochem.pandoraleague.data.SettingsData
import nl.jochem.pandoraleague.league.LeagueManager
import nl.jochem.pandoraleague.league.MultiplierManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent


private val kdc = KingdomCraftProvider.get()

class DeathEvents: Listener {

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        if(SettingsData.getConfig().deathPoints != 0) {
            val target: User = kdc.getUser(event.entity.uniqueId).get()
            LeagueManager.removePoints(target.kingdom, SettingsData.getConfig().deathPoints)
        }

        if(SettingsData.getConfig().killPoints != 0 && event.entity.killer != null) {
            val killer: User = kdc.getUser(event.entity.killer.uniqueId).get()
            LeagueManager.addPoints(killer.kingdom, SettingsData.getConfig().killPoints, MultiplierManager.players[killer.uniqueId] ?: 1)
        }
    }

}