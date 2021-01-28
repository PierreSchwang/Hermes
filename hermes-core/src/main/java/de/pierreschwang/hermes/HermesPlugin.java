package de.pierreschwang.hermes;

import de.pierreschwang.hermes.listener.AutoRegisterListener;
import de.pierreschwang.hermes.player.HermesPlayer;
import de.pierreschwang.hermes.player.HermesPlayerFactory;
import de.pierreschwang.hermes.player.HermesPlayerStorage;
import de.pierreschwang.hermes.service.ServiceRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class HermesPlugin<P extends HermesPlayer> extends JavaPlugin {

    private ServiceRegistry serviceRegistry;
    private final HermesPlayerStorage<P> playerStorage = new HermesPlayerStorage<>();

    @Override
    public void onLoad() {
        serviceRegistry = new ServiceRegistry(this);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            playerStorage.registerPlayer(onlinePlayer, getPlayerFactory().constructPlayer(onlinePlayer));
        }
    }

    @Override
    public void onEnable() {
        new AutoRegisterListener().registerListener(this);
        onStartup();
    }

    @Override
    public void onDisable() {
        onShutdown();
    }

    public abstract void onStartup();

    public abstract void onShutdown();

    public abstract HermesPlayerFactory<P> getPlayerFactory();

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

}