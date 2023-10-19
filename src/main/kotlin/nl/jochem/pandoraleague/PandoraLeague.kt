package nl.jochem.pandoraleague

import hazae41.minecraft.kutils.bukkit.info
import nl.jochem.pandoraleague.commands.PandoraLeagueCommand
import nl.jochem.pandoraleague.commands.PandoraLeagueTab
import nl.jochem.pandoraleague.data.LeagueData
import nl.jochem.pandoraleague.data.MessagesData
import nl.jochem.pandoraleague.data.SettingsData
import nl.jochem.pandoraleague.events.DeathEvents
import nl.jochem.pandoraleague.league.LeagueManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PandoraLeague : JavaPlugin() {

    override fun onEnable() {
        registerConfig()
        LeagueManager.onEnable()

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Placeholders().register();
        }

        Bukkit.getPluginManager().registerEvents(DeathEvents(), this)

        getCommand("pandoraleague").executor = PandoraLeagueCommand()
        getCommand("pandoraleague").tabCompleter = PandoraLeagueTab()

        info("==========================")
        info("=                        =")
        info("= PandoraLeague enabled! =")
        info("=                        =")
        info("==========================")
    }

    override fun onDisable() {
        LeagueManager.onDisable()

        info("===========================")
        info("=                         =")
        info("= PandoraLeague disabled! =")
        info("=                         =")
        info("===========================")
    }

    fun registerConfig() {
        SettingsData.register()
        MessagesData.register()
        LeagueData.register()
    }
}
