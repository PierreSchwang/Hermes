package de.pierreschwang.hermes.config.converters;

import de.pierreschwang.hermes.config.converter.ConfigConverter;

import java.util.Map;

public class MapConfigConverter implements ConfigConverter {

    @Override
    public Object fromConfigSection(Object section, Class<?> type) {
        return null;
    }

    @Override
    public Object toConfigSection(Object data, Class<?> type) {
        return null;
    }

    @Override
    public boolean supportsType(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

}