package de.pierreschwang.hermes.player;

import org.bukkit.entity.Player;

public class HermesPlayer {

    private final Player player;

    public HermesPlayer(Player player) {
        this.player = player;
    }

    public Player getBukkitPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "HermesPlayer{" +
                "uuid=" + player.getUniqueId() + "," +
                "name=" + player.getName() +
                '}';
    }
}
