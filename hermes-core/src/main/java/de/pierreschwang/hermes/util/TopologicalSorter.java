package de.pierreschwang.hermes.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Sort data for dependency trees and more
 * @param <T>
 */
public class TopologicalSorter<T, E extends RuntimeException> {

    private final List<T> sorted = new ArrayList<>();
    private final Set<T> visited = new HashSet<>();
    private final List<T> toSort;
    private final Function<T, List<T>> dependencyResolver;
    private final Function<T, E> exceptionResolver;

    public TopologicalSorter(List<T> toSort, Function<T, List<T>> dependencyResolver,
                             Function<T, E> exceptionResolver) {
        this.toSort = toSort;
        this.dependencyResolver = dependencyResolver;
        this.exceptionResolver = exceptionResolver;
    }

    public List<T> sort() {
        for (T item : toSort) {
            visit(item);
        }
        return sorted;
    }

    private void visit(T item) {
        if (visited.contains(item)) {
            if (sorted.contains(item))
                return;
            throw exceptionResolver.apply(item);
        }
        visited.add(item);
        for (T dependency : dependencyResolver.apply(item)) {
            visit(dependency);
        }
        sorted.add(item);
    }

}

