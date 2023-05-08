package com.soychristian.minecraftpoo.views;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.minecraftpoo.utils.InventoryUtils;
import com.soychristian.minecraftpoo.utils.PlayerFileFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuView implements InventoryHolder, Listener {
    MinecraftPOO plugin;

    private Inventory inventory;

    public MenuView(MinecraftPOO plugin) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(this, 9, "Main Menu");

        ItemStack saveInventory = new ItemStack(Material.CHEST, 1);
        ItemMeta saveInventoryMeta = saveInventory.getItemMeta();
        saveInventoryMeta.setDisplayName("Save Inventory");
        saveInventory.setItemMeta(saveInventoryMeta);

        ItemStack restoreInventory = new ItemStack(Material.CHEST, 1);
        ItemMeta restoreInventoryMeta = restoreInventory.getItemMeta();
        restoreInventoryMeta.setDisplayName("Restore Inventory");
        restoreInventory.setItemMeta(restoreInventoryMeta);

        this.inventory.setItem(0, saveInventory);
        this.inventory.setItem(1, restoreInventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().equalsIgnoreCase("Main Menu")) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        Player player;
        PlayerFileFactory playerFileFactory;
        PlayerInventory playerInventory;
        String encodedInventory;
        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "Save Inventory":
                player = (Player) event.getWhoClicked();
                playerInventory = player.getInventory();
                encodedInventory = InventoryUtils.encodeInventory(playerInventory);
                playerFileFactory = new PlayerFileFactory(plugin, player);
                playerFileFactory.setPlayerData("inventory", encodedInventory);
                player.getInventory().clear();

                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage("Inventory saved!");
                break;
            case "Restore Inventory":
                player = (Player) event.getWhoClicked();
                playerFileFactory = new PlayerFileFactory(plugin, player);
                encodedInventory = playerFileFactory.getPlayerData("inventory");
                Inventory inventory1;
                // java.lang.ClassCastException: org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom cannot be cast to org.bukkit.inventory.PlayerInventory
                try {
                    inventory1 = InventoryUtils.decodeInventory(encodedInventory);
                } catch (Exception e) {
                    player.sendMessage("You don't have a saved inventory!");
                    e.printStackTrace();
                    break;
                }
                player.getInventory().setContents(inventory1.getContents());
                playerFileFactory.setPlayerData("inventory", null);

                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage("Inventory restored!");
                break;
        }
    }
}
