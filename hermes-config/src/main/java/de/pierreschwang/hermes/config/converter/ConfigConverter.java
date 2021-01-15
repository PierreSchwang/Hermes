package de.pierreschwang.hermes.config.converter;

public interface ConfigConverter {

    Object fromConfigSection(Object section, Class<?> type);

    Object toConfigSection(Object data, Class<?> type);

    boolean supportsType(Class<?> clazz);

}
