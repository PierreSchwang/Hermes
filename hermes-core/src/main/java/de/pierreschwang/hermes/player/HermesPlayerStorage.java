package de.pierreschwang.hermes.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HermesPlayerStorage<P extends HermesPlayer> {

    private final Map<UUID, P> players = new HashMap<>();

    public P getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public P getPlayer(Player bukkitPlayer) {
        return getPlayer(bukkitPlayer.getUniqueId());
    }

    public boolean registerPlayer(Player bukkitPlayer, P hermesPlayer) {
        if (getPlayer(bukkitPlayer) != null)
            return false;
        players.put(bukkitPlayer.getUniqueId(), hermesPlayer);
        return true;
    }

}
