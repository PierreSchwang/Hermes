package de.pierreschwang.hermes.test;

import static org.junit.jupiter.api.Assertions.*;

import de.pierreschwang.hermes.util.TopologicalSorter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class HermesServicesTest {

    @Test
    public void shouldDetectCyclicDependency() {
        TopologicalSorter<String, RuntimeException> sorter = new TopologicalSorter<>(
                Arrays.asList("ServiceOne", "ServiceTwo", "ServiceThree"),
                s -> {
                    if(s.equals("ServiceOne"))
                        return Arrays.asList("ServiceTwo", "ServiceThree");
                    return Arrays.asList("ServiceOne", "ServiceTwo", "ServiceThree");
                }, s -> new RuntimeException("Cyclic dependency found")
        );
        assertThrows(RuntimeException.class, sorter::sort, "Cyclic dependency found");
    }

}