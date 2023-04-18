package com.soychristian.minecraftpoo.views;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EvaluateView implements Listener, InventoryHolder {
    private Inventory mainInventory;
    private ItemStack questionItem, option1, option2, option3, option4;
    private String staticAnswer;
    private MinecraftPOO plugin;

    public EvaluateView(MinecraftPOO plugin){
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public void buildGui(Player player, String question, ArrayList<String> options, String answer) {
        if (options.size() != 4) {
            throw new IllegalArgumentException("[buildGui() de EvaluateView] options must be an array of length 4");
        }
        if (!options.contains(answer)) {
            throw new IllegalArgumentException("[buildGui() de EvaluateView] answer must be one of the options");
        }
        staticAnswer = answer;

        mainInventory = Bukkit.createInventory(null, 9 * 3, "TEST EN PROGRESO");

//        questionItem = new ItemStack(Material.BOOK, 1);
//        ItemMeta questionItemMeta = questionItem.getItemMeta();
//        questionItemMeta.setDisplayName(question);
//        questionItem.setItemMeta(questionItemMeta);
        ItemStack questionItem = ItemUtils.getItem(Material.BOOK, question, "Pregunta");
        mainInventory.setItem(4, questionItem);

        option1 = ItemUtils.getItem(Material.PAPER, options.get(0), "Opción 1");
        mainInventory.setItem(10, option1);

        option2 = ItemUtils.getItem(Material.PAPER, options.get(1), "Opción 2");
        mainInventory.setItem(12, option2);

        option3 = ItemUtils.getItem(Material.PAPER, options.get(2), "Opción 3");
        mainInventory.setItem(14, option3);

        option4 = ItemUtils.getItem(Material.PAPER, options.get(3), "Opción 4");
        mainInventory.setItem(16, option4);
    }



    @EventHandler
    public void onSelectOption(InventoryClickEvent event){
        // Funciona pero el nombre del item ya no es igual al de la pregunta, resolver staticAwnser
        if (!event.getInventory().equals(mainInventory)) return;
        event.setCancelled(true);
//        if (event.getInventory() == null) return;
//        if (option1 == null || option2 == null || option3 == null || option4 == null) return;
//        if (event.getCurrentItem() == null) return;
        Inventory inventoryEvent = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        for (ItemStack option : new ItemStack[]{option1, option2, option3, option4}) {
            if (item.equals(option)) {
                if (option.getItemMeta().getDisplayName().equals(staticAnswer)) {
                    player.sendMessage("Correcto");
                } else {
                    player.sendMessage("Incorrecto");
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
                }
            }
        }

    }

    @Override
    public Inventory getInventory() {
        return this.mainInventory;
    }
}
