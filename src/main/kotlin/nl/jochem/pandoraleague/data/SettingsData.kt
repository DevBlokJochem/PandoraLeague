package nl.jochem.pandoraleague.data

import com.google.gson.GsonBuilder
import java.io.File

object SettingsData {

    private var config: Settings? = null

    fun register() {
        if (!File(getPath()).exists()) File(getPath()).mkdirs()
        if(!File("${getPath()}/${getFileName()}").exists()) {
            File("${getPath()}/${getFileName()}").createNewFile()
            File("${getPath()}/${getFileName()}").writeText(
                GsonBuilder().setPrettyPrinting().create()!!.toJson(
                    Settings(5, 0)
                )
            )
        }
        reload()
    }

    fun reload() {
        config = GsonBuilder().setPrettyPrinting().create()!!.fromJson(File("${getPath()}/${getFileName()}").readText(), Settings::class.java)!!
    }

    fun getConfig(): Settings = config!!

    fun getPath(): String = "plugins/pandoraleague"
    fun getFileName(): String = "settings.json"
}

data class Settings(
    val killPoints: Int,
    val deathPoints: Int
)