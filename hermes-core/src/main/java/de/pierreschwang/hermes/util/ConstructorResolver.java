package de.pierreschwang.hermes.util;

import de.pierreschwang.hermes.HermesPlugin;
import de.pierreschwang.hermes.service.ServiceRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorResolver {

    public static <T> T resolveConstructor(HermesPlugin<?> plugin, ServiceRegistry registry,
                                           Constructor<T> constructor) throws InstantiationException {
        try {
            Object[] params = new Object[constructor.getParameterCount()];
            for (int i = 0; i < constructor.getParameters().length; i++) {
                Class<?> type = constructor.getParameters()[i].getType();

                if (HermesPlugin.class.isAssignableFrom(type)) {
                    params[i] = plugin;
                    continue;
                }

                if (Server.class.isAssignableFrom(type)) {
                    params[i] = Bukkit.getServer();
                    continue;
                }

                Object service;
                if((service = registry.getService(type)) != null) {
                    params[i] = service;
                    continue;
                }

                throw new InstantiationException("Could not instantiate " + constructor.toString() + ": " +
                        "Could not resolve parameter " + constructor.getParameters()[i].toString());
            }
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
