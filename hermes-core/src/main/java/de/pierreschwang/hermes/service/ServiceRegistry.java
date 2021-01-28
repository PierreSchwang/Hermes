package de.pierreschwang.hermes.service;

import de.pierreschwang.hermes.HermesPlugin;
import de.pierreschwang.hermes.annotation.HermesConfiguration;
import de.pierreschwang.hermes.annotation.Service;
import de.pierreschwang.hermes.exception.IllegalServiceException;
import de.pierreschwang.hermes.util.TopologicalSorter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class ServiceRegistry {

    private final Map<Class<?>, Object> registeredServices = new HashMap<>();
    private final HermesPlugin<?> plugin;

    public ServiceRegistry(HermesPlugin<?> plugin) {
        this.plugin = plugin;
        registerServices();
    }

    public <T> T getService(Class<T> serviceClass) {
        return (T) registeredServices.get(serviceClass);
    }

    void registerServices() {
        final Map<Class<?>, Set<Class<?>>> servicesWithDependencies = new HashMap<>();
        recursiveRegisterServices(resolveBaseServicePath(plugin), servicesWithDependencies);

        final List<Class<?>> servicesToLoad = new ArrayList<>();
        detectCyclicDependencies(servicesWithDependencies, servicesToLoad);

        servicesToLoad.forEach(this::registerService);
    }

    private void registerService(Class<?> service) {
        Constructor<?> constructor = service.getConstructors()[0];

        Object[] parameters = new Object[constructor.getParameterCount()];
        for (int i = 0; i < constructor.getParameters().length; i++) {
            try {
                parameters[i] = resolveParameter(constructor.getParameters()[i]);
            } catch (IllegalServiceException exception) {
                System.err.println("Could not resolve " + i + " parameter for " + service.getSimpleName() + " with type "
                        + constructor.getParameters()[i].getType().toString());
                return;
            }
        }
        try {
            registeredServices.put(service, constructor.newInstance(parameters));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Object resolveParameter(Parameter parameter) throws IllegalServiceException {
        Class<?> type = parameter.getType();
        if (HermesPlugin.class.isAssignableFrom(type))
            return plugin;

        if (Server.class.isAssignableFrom(type))
            return Bukkit.getServer();

        Object data;
        if ((data = getService(type)) == null)
            throw new IllegalServiceException();
        return data;
    }

    private void detectCyclicDependencies(Map<Class<?>, Set<Class<?>>> loader, List<Class<?>> servicesToLoad) {
        TopologicalSorter<Class<?>, IllegalServiceException> sorter = new TopologicalSorter<>(
                new ArrayList<>(loader.keySet()),
                service -> new ArrayList<>(loader.getOrDefault(service, new HashSet<>())),
                service -> new IllegalServiceException("Detected cyclic dependency in " + service.getSimpleName())
        );
        sorter.sort().stream().filter(loader.keySet()::contains).forEach(servicesToLoad::add);
    }

    private void recursiveRegisterServices(String packageName, Map<Class<?>, Set<Class<?>>> loader) {
        new Reflections(packageName).getTypesAnnotatedWith(Service.class).forEach(aClass -> {
            registerService(aClass, loader);
        });
    }

    /**
     * Resolves a class with the matching constructor to determine the dependencies
     *
     * @param serviceClass The service class which should be registered.
     * @param loader       The storage where loaded services are located.
     */
    private void registerService(Class<?> serviceClass, Map<Class<?>, Set<Class<?>>> loader) {
        if (!serviceClass.isAnnotationPresent(Service.class))
            throw new IllegalArgumentException(serviceClass.getSimpleName() + " does not have @Service annotation!");

        if (serviceClass.getConstructors().length == 0) {
            loader.put(serviceClass, new HashSet<>());
            return;
        }

        if (serviceClass.getConstructors().length > 1) {
            throw new IllegalServiceException(serviceClass.getSimpleName() + " exceeds maximum constructor amount of one!");
        }

        Constructor<?> constructor = serviceClass.getConstructors()[0];
        final Set<Class<?>> dependencies = new HashSet<>();
        for (Parameter parameter : constructor.getParameters()) {
            dependencies.add(parameter.getType());
        }
        loader.put(serviceClass, dependencies);
    }

    private String resolveBaseServicePath(HermesPlugin<?> hermesPlugin) {
        String path = hermesPlugin.getClass().getPackage().getName();
        if (hermesPlugin.getClass().isAnnotationPresent(HermesConfiguration.class)) {
            HermesConfiguration configuration = hermesPlugin.getClass().getAnnotation(HermesConfiguration.class);
            if (!configuration.serviceSearchPackage().isEmpty())
                path = configuration.serviceSearchPackage();
        }
        return path;
    }

}
