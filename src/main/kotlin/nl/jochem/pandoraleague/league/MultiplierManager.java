package nl.jochem.pandoraleague.league;

import com.gufli.kingdomcraft.api.domain.Kingdom;

import java.util.HashMap;
import java.util.UUID;

public class MultiplierManager {

    public static HashMap<UUID, Integer> players = new HashMap<>();
    public static void addPoints(Kingdom kingdom, Integer points, Integer multiplier) {
        LeagueManager.INSTANCE.addPoints(kingdom, points, multiplier);
    }

}
