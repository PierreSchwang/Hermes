package de.pierreschwang.hermes.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class HermesInventory implements Inventory {

    HermesInventory(String title) {

    }

    public void setItem(int i, ItemStack itemStack, Object listener) {
        setItem(i, itemStack);
    }
}