package com.soychristian.minecraftpoo.views;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.minecraftpoo.helpers.AdminInventory;
import com.soychristian.minecraftpoo.utils.InventoryUtils;
import com.soychristian.minecraftpoo.utils.ItemUtils;
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

        ItemStack saveInventory = ItemUtils.getItem(Material.CHEST, "Save Inventory", "Guarda tu inventario actual");
        ItemStack restoreInventory = ItemUtils.getItem(Material.CHEST, "Restore Inventory", "Restaura tu inventario guardado");
        ItemStack adminInventory = ItemUtils.getItem(Material.DIAMOND, "Admin Inventory", "Inventario compartido con la administracion");

        this.inventory.setItem(0, saveInventory);
        this.inventory.setItem(1, restoreInventory);
        this.inventory.setItem(2, adminInventory);
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
        Inventory inventory;
        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "Save Inventory":
                player = (Player) event.getWhoClicked();
                playerInventory = player.getInventory();
                encodedInventory = InventoryUtils.encodeInventory(playerInventory);
                playerFileFactory = new PlayerFileFactory(plugin, player);
                if (playerFileFactory.getPlayerData("inventory") != null) {
                    player.sendMessage("You already have a saved inventory!");
                    break;
                }
                playerFileFactory.setPlayerData("inventory", encodedInventory);
                player.getInventory().clear();

                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage("Inventory saved!");
                break;
            case "Restore Inventory":
                player = (Player) event.getWhoClicked();
                playerFileFactory = new PlayerFileFactory(plugin, player);
                encodedInventory = playerFileFactory.getPlayerData("inventory");
                // java.lang.ClassCastException: org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom cannot be cast to org.bukkit.inventory.PlayerInventory
                try {
                    inventory = InventoryUtils.decodeInventory(encodedInventory);
                } catch (Exception e) {
                    player.sendMessage("You don't have a saved inventory!");
                    e.printStackTrace();
                    break;
                }
                for (ItemStack item : inventory.getContents()){
                    if (item == null) continue;
                    player.getInventory().addItem(item);
                }
                playerFileFactory.setPlayerData("inventory", null);

                player.closeInventory();
                player.sendMessage("Inventory restored!");
                break;
            case "Admin Inventory":
                // TODO: Hacer la logica del inventario, que al cerrar se actualice y solo guarde los items que quedaron dentro.
                // TODO: No permitir agregar items al inventario, solo retirarlos.
                // TODO: Preparar desde la vista del administrador para que pueda acceder a los admin inventories de los usuarios para dejar cosas.
                player = (Player) event.getWhoClicked();
                playerFileFactory = new PlayerFileFactory(plugin, player);
                encodedInventory = playerFileFactory.getPlayerData("adminInventory");
                if (encodedInventory == null) {
                    player.sendMessage("You don't have pending items from the admin!");
                    break;
                }
                try {
                    inventory = InventoryUtils.decodeInventory(encodedInventory);
                } catch (Exception e) {
                    player.sendMessage("You don't have pending items from the admin!");
                    e.printStackTrace();
                    break;
                }
                AdminInventory adminInventory = new AdminInventory(plugin);
                Inventory inventoryView = adminInventory.getInventory(player.getName());
                player.openInventory(inventoryView);
                break;
        }
    }
}
