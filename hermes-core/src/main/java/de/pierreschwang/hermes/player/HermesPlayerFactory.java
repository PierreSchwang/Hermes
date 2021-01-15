package de.pierreschwang.hermes.player;

import org.bukkit.entity.Player;

public interface HermesPlayerFactory<P extends HermesPlayer> {

    P constructPlayer(Player player);

}
