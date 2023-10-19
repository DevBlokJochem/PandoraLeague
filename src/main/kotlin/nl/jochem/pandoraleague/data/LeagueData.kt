package nl.jochem.pandoraleague.data

import com.google.gson.GsonBuilder
import nl.jochem.pandoraleague.league.LeagueManager
import java.io.File

object LeagueData {

    private var config: LeaguePoints? = null

    fun register() {
        if (!File(getPath()).exists()) File(getPath()).mkdirs()
        if(!File("${getPath()}/${getFileName()}").exists()) {
            File("${getPath()}/${getFileName()}").createNewFile()
            File("${getPath()}/${getFileName()}").writeText(
                GsonBuilder().setPrettyPrinting().create()!!.toJson(LeaguePoints(mapOf()))
            )
        }
        reload()
    }

    private fun reload() {
        config = GsonBuilder().setPrettyPrinting().create()!!.fromJson(File("${getPath()}/${getFileName()}").readText(), LeaguePoints::class.java)!!
    }

    fun reload(boolean: Boolean) {
        reload()
        LeagueManager.onEnable()
    }

    fun getConfig(): LeaguePoints {
        return config!!
    }

    fun update(data: LeaguePoints) {
        File("${getPath()}/${getFileName()}").writeText(
            GsonBuilder().setPrettyPrinting().create()!!.toJson(data)
        )
        reload()
    }

    fun getPath(): String = "plugins/pandoraleague"
    fun getFileName(): String = "league.json"

}

data class LeaguePoints(
    val points: Map<Int, Int>
)
