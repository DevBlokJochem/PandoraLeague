package nl.jochem.pandoraleague.commands

import com.gufli.kingdomcraft.api.KingdomCraftProvider
import com.gufli.kingdomcraft.api.domain.Kingdom
import nl.jochem.pandoraleague.data.LeagueData
import nl.jochem.pandoraleague.data.MessagesData
import nl.jochem.pandoraleague.data.SettingsData
import nl.jochem.pandoraleague.league.LeagueManager
import nl.jochem.pandoraleague.league.MultiplierManager
import nl.jochem.pandoraleague.msg
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

private val kdc = KingdomCraftProvider.get()

class PandoraLeagueCommand: CommandExecutor {

    private val commandTip = "${MessagesData.getConfig().prefix}${ChatColor.RED}/pandoraleague <give/remove/info/reload>"

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        val prefix = MessagesData.getConfig().prefix

        if(sender !is Player) {
            println(MessagesData.getConfig().onlyPlayer)
            return false
        }

        if (args.isNullOrEmpty()) {
            sender.msg(commandTip)
            return false
        }

        val pltc: TabCompletion? = TabCompletion.findByName(args[0])
        if (pltc == null) {
            sender.msg(commandTip)
            return false
        }

        if(!sender.hasPermission(pltc.permission)) {
            sender.msg(prefix + MessagesData.getConfig().noPermission.replace("%permissions%", pltc.permission))
            return false
        }

        when(pltc) {
            TabCompletion.info -> MessagesData.getConfig().pointsMessages.forEach {
                sender.msg(prefix + it)
            }
            TabCompletion.give -> {
                if(args.size != 3) {
                    sender.msg(commandTip)
                    return false
                }
                val kd = getKingdom(args[1])
                if(kd == null) {
                    sender.msg(prefix + MessagesData.getConfig().invalidKingdom)
                    return false
                }
                val amount = args[2].toIntOrNull()
                if(amount == null) {
                    sender.msg(prefix + MessagesData.getConfig().invalidInteger)
                    return false
                }

                LeagueManager.addPoints(kd, amount, 1)

                sender.msg(prefix + MessagesData.getConfig().givePoints.replace("%punten%", amount.toString()))
            }
            TabCompletion.remove -> {
                if(args.size != 3) {
                    sender.msg(commandTip)
                    return false
                }
                val kd = getKingdom(args[1])
                if(kd == null) {
                    sender.msg(prefix + MessagesData.getConfig().invalidKingdom)
                    return false
                }
                val amount = args[2].toIntOrNull()
                if(amount == null) {
                    sender.msg(prefix + MessagesData.getConfig().invalidInteger)
                    return false
                }
                LeagueManager.removePoints(kd, amount)
                sender.msg(prefix + MessagesData.getConfig().removePoints.replace("%punten%", amount.toString()))
            }
            TabCompletion.reload -> {
                LeagueData.hardReload()
                MessagesData.reload()
                SettingsData.reload()
                sender.msg(prefix + MessagesData.getConfig().reload)
            }
        }
        return true
    }

    private fun getKingdom(name: String): Kingdom? {
        return kdc.getKingdom(name)
    }

}