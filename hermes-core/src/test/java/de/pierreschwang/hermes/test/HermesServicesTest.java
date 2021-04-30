package de.pierreschwang.hermes.test;

import static org.junit.jupiter.api.Assertions.*;

import de.pierreschwang.hermes.util.TopologicalSorter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class HermesServicesTest {

    @Test
    public void shouldDetectCyclicDependency() {
        TopologicalSorter<String, RuntimeException> sorter = new TopologicalSorter<>(
                Arrays.asList("ServiceOne", "ServiceTwo", "ServiceThree"),
                s -> {
                    if (s.equals("ServiceOne"))
                        return Arrays.asList("ServiceTwo", "ServiceThree");
                    return Arrays.asList("ServiceOne", "ServiceTwo", "ServiceThree");
                }, s -> new RuntimeException("Cyclic dependency found")
        );
        assertThrows(RuntimeException.class, sorter::sort, "Cyclic dependency found");
    }

    @Test
    public void testCyclic() {
        TopologicalSorter<String, RuntimeException> sorter = new TopologicalSorter<>(
                Arrays.asList("a", "b", "c"),
                s -> {
                    if (s.equals("a"))
                        return Arrays.asList("b", "c");
                    if (s.equals("b"))
                        return Collections.singletonList("c");
                    return Collections.emptyList();
                }, s -> new RuntimeException("Cyclic dependency found")
        );
        assertDoesNotThrow(sorter::sort, "Should not throw exception");
    }

}