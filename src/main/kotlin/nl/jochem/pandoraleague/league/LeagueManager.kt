package nl.jochem.pandoraleague.league

import com.gufli.kingdomcraft.api.domain.Kingdom
import nl.jochem.pandoraleague.data.LeagueData
import nl.jochem.pandoraleague.data.LeaguePoints
import java.util.PriorityQueue

object LeagueManager {

    private val points: HashMap<Int, Int> = HashMap()

    fun onEnable() {
        points.clear()
        LeagueData.getConfig().points.forEach {
            points[it.key] = it.value
        }
    }

    fun onDisable() {
        LeagueData.update(LeaguePoints(points))
    }

    fun addPoints(kingdom: Kingdom, amount: Int, multiplier: Int) {
        points[kingdom.id] = if(points.containsKey(kingdom.id)) {
            points[kingdom.id]!! + amount * multiplier
        }else {
            amount
        }
        LeagueData.update(LeaguePoints(points))
    }

    fun removePoints(kingdom: Kingdom, amount: Int) {
        if(!points.containsKey(kingdom.id)) return
        if(points[kingdom.id]!! <= amount) {
            points[kingdom.id] = 0
            return
        }
        points[kingdom.id] = points[kingdom.id]!! - amount
        LeagueData.update(LeaguePoints(points))
    }

    fun getPoints(kingdom: Kingdom): Int {
        if(!points.containsKey(kingdom.id)) return 0
        return points[kingdom.id]!!
    }

    fun getPosition(kingdom: Kingdom): Int {

        val positions = sortListOfPairDesc(points.toList())
        val pair = positions.singleOrNull { it.first == kingdom.id } ?: return 999
        return positions.indexOf(pair) + 1
    }

}

fun sortListOfPairDesc(list: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    return list.sortedWith(compareBy({ it.second }, { it.first })).asReversed()
}