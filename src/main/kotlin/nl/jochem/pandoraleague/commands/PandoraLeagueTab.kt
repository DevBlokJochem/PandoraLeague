package nl.jochem.pandoraleague.commands

import com.gufli.kingdomcraft.api.KingdomCraftProvider
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil


private val kdc = KingdomCraftProvider.get()

class PandoraLeagueTab: TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?,
        command: Command?,
        alias: String?,
        args: Array<out String>?
    ): List<String> {
        if(args?.size == 1) {
            return StringUtil.copyPartialMatches(args[0], TabCompletion.values().map { it.name }, ArrayList())
        }else if(args?.size == 2 && TabCompletion.findByName(args[0]) != null) {
            return when(TabCompletion.findByName(args[0])) {
                TabCompletion.give, TabCompletion.remove -> StringUtil.copyPartialMatches(args[1], kdc.kingdoms.map { it.name }, ArrayList())
                else -> ArrayList()
            }
        }
        return ArrayList()
    }
}