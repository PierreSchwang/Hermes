package de.pierreschwang.hermes;

import de.pierreschwang.hermes.player.HermesPlayer;
import de.pierreschwang.hermes.player.HermesPlayerFactory;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class HermesPlugin<P extends HermesPlayer> extends JavaPlugin {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public abstract HermesPlayerFactory<P> getPlayerFactory();

}