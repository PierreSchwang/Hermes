package de.pierreschwang.hermes.util;

import org.bukkit.Bukkit;

public class HermesThreading {

    public static boolean isMainThread() {
        return Bukkit.getServer().isPrimaryThread();
    }

    public static void ensureMainThread() {
        if (!isMainThread())
            throw new IllegalThreadStateException("Execution must be performed on main thread!");
    }

    public static void ensureNotMainThread() {
        if (isMainThread())
            throw new IllegalThreadStateException("Execution should not be performed on main thread!");
    }

}
