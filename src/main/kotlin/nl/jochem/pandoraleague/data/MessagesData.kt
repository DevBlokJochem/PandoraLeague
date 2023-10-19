package nl.jochem.pandoraleague.data

import com.google.gson.GsonBuilder
import org.bukkit.ChatColor
import java.io.File

object MessagesData {

    private var config: Messages? = null

    fun register() {
        if (!File(getPath()).exists()) File(getPath()).mkdirs()
        if(!File("${getPath()}/${getFileName()}").exists()) {
            File("${getPath()}/${getFileName()}").createNewFile()
            File("${getPath()}/${getFileName()}").writeText(
                GsonBuilder().setPrettyPrinting().create()!!.toJson(
                    Messages(
                        "${ChatColor.RED}Sorry, maar je moet een speler zijn om dit commando te kunnen uitvoeren.",
                        "${ChatColor.RED}Sorry, maar je mist de permissie %permission%.",
                        "${ChatColor.RED}Sorry, maar je moet een geldig kingdom opgeven.",
                        "${ChatColor.RED}Sorry, maar dit is geen geldig getal.",
                        "${ChatColor.DARK_RED}[PandoraLeague] ",
                        "${ChatColor.GRAY}Je hebt de league config gereload.",
                        "${ChatColor.GRAY}Je hebt %punten% toegevoegd.",
                        "${ChatColor.GRAY}Je hebt %punten% ervanaf gehaald.",
                        listOf("${ChatColor.GRAY}%kingdom_{test1}_points% (%kingdom_{test1}_position%)",
                            "${ChatColor.GRAY}%kingdom_{test1}_points% (%kingdom_{test1}_position%)",
                            "${ChatColor.GRAY}%kingdom_{test1}_points% (%kingdom_{test1}_position%)",
                            "${ChatColor.GRAY}%kingdom_{test1}_points% (%kingdom_{test1}_position%)")
                    )
                )
            )
        }
        reload()
    }

    fun reload() {
        config = GsonBuilder().setPrettyPrinting().create()!!.fromJson(File("${getPath()}/${getFileName()}").readText(), Messages::class.java)!!
    }

    fun getConfig(): Messages = config!!

    fun getPath(): String = "plugins/pandoraleague"
    fun getFileName(): String = "messages.json"

}

data class Messages(
    val onlyPlayer: String,
    val noPermission: String,
    val invalidKingdom: String,
    val invalidInteger: String,
    val prefix: String,
    val reload: String,
    val givePoints: String,
    val removePoints: String,
    val pointsMessages: List<String>
)