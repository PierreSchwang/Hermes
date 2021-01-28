package de.pierreschwang.hermes.listener;

import de.pierreschwang.hermes.HermesPlugin;
import de.pierreschwang.hermes.annotation.HermesConfiguration;
import de.pierreschwang.hermes.exception.ListenerRegistrationException;
import de.pierreschwang.hermes.util.ConstructorResolver;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public class AutoRegisterListener {

    public void registerListener(HermesPlugin<?> plugin) {
        for (Class<? extends Listener> listenerClass : new Reflections(getPackagePath(plugin)).getSubTypesOf(Listener.class)) {
            if (listenerClass.getConstructors().length > 1)
                throw new ListenerRegistrationException(listenerClass.getSimpleName() + " exceeds maximum constructor amount of one!");

            try {
                Listener listener;
                if (listenerClass.getConstructors().length == 0) {
                    listener = listenerClass.getConstructor().newInstance();
                } else {
                    listener = (Listener) ConstructorResolver.resolveConstructor(plugin, plugin.getServiceRegistry(),
                            listenerClass.getConstructors()[0]);
                }

                Bukkit.getPluginManager().registerEvents(listener, plugin);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new ListenerRegistrationException("Failed to auto register listener " +
                        listenerClass.getSimpleName(), e);
            }
        }
    }

    private String getPackagePath(HermesPlugin<?> plugin) {
        String path = plugin.getClass().getPackage().getName();
        if (plugin.getClass().isAnnotationPresent(HermesConfiguration.class)) {
            HermesConfiguration configuration = plugin.getClass().getAnnotation(HermesConfiguration.class);
            if (!configuration.listenerSearchPackage().isEmpty())
                path = configuration.listenerSearchPackage();
        }
        return path;
    }

}
