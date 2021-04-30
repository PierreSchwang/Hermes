package de.pierreschwang.hermes.annotation.processor;

import com.google.common.io.CharSink;
import de.pierreschwang.hermes.annotation.HermesConfiguration;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;

public class HermesConfigurationMapper {

    private final Map<String, Function<HermesConfiguration, String>> mapping = new HashMap<>();

    public HermesConfigurationMapper(CharSequence mainClassPath) {
        register("name", HermesConfiguration::pluginName);
        register("version", HermesConfiguration::version);
        register("api-version", HermesConfiguration::apiVersion);
        register("main", configuration -> String.valueOf(mainClassPath));
        register("authors", configuration -> arrayToStringOrNull(configuration.authors()));
        register("author", configuration -> configuration.authors().length > 0 ? configuration.authors()[0] : null);
        register("depend", configuration -> arrayToStringOrNull(configuration.dependencies()));
        register("softdepend", configuration -> arrayToStringOrNull(configuration.softDependencies()));
    }

    public void generate(FileObject target, HermesConfiguration configuration) throws IOException {
        Set<String> lines = new HashSet<>();
        mapping.forEach((s, hermesConfigurationStringFunction) -> {
            String result = hermesConfigurationStringFunction.apply(configuration);
            if(result != null && !result.isEmpty()) {
                lines.add(s + ": " + result);
            }
        });
        writeLines(target, lines);
    }

    private void register(String pluginYmlKey, Function<HermesConfiguration, String> resolver) {
        this.mapping.put(pluginYmlKey, resolver);
    }

    private void writeLines(FileObject target, Iterable<String> lines) throws IOException {
        new CharSink() {
            @Override
            public Writer openStream() throws IOException {
                return target.openWriter();
            }
        }.writeLines(lines, "\n");
    }

    private String arrayToStringOrNull(String[] array) {
        return array == null || array.length == 0 ? null : Arrays.toString(array);
    }

}
