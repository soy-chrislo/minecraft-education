package com.soychristian.minecraftpoo.views;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MainView implements InventoryHolder {
    private Inventory inventory;

    public MainView() {
        this.inventory = Bukkit.createInventory(this, 9, "Main Menu");
        this.inventory.setItem(0, new ItemStack(Material.DIRT, 2));
    }
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
