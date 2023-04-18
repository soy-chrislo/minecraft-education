package com.soychristian.minecraftpoo.views;

import com.soychristian.minecraftpoo.MinecraftPOO;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class EvaluateView implements Listener {
    private Inventory mainInventory;
    private ItemStack questionItem, option1, option2, option3, option4;
    private String staticAnswer;


    public void buildGui(Player player, String question, ArrayList<String> options, String answer) {
        if (options.size() != 4) {
            throw new IllegalArgumentException("[buildGui() de EvaluateView] options must be an array of length 4");
        }
        if (!options.contains(answer)) {
            throw new IllegalArgumentException("[buildGui() de EvaluateView] answer must be one of the options");
        }
        staticAnswer = answer;

        mainInventory = Bukkit.createInventory(null, 9 * 3, "TEST EN PROGRESO");

        questionItem = new ItemStack(Material.BOOK, 1);
        ItemMeta questionItemMeta = questionItem.getItemMeta();
        questionItemMeta.setDisplayName(question);
        questionItem.setItemMeta(questionItemMeta);
        mainInventory.setItem(4, questionItem);

        option1 = new ItemStack(Material.PAPER, 1);
        ItemMeta option1Meta = option1.getItemMeta();
        option1Meta.setDisplayName(options.get(0));
        option1.setItemMeta(option1Meta);
        mainInventory.setItem(10, option1);

        option2 = new ItemStack(Material.PAPER, 1);
        ItemMeta option2Meta = option2.getItemMeta();
        option2Meta.setDisplayName(options.get(1));
        option2.setItemMeta(option2Meta);
        mainInventory.setItem(12, option2);

        option3 = new ItemStack(Material.PAPER, 1);
        ItemMeta option3Meta = option3.getItemMeta();
        option3Meta.setDisplayName(options.get(2));
        option3.setItemMeta(option3Meta);
        mainInventory.setItem(14, option3);

        option4 = new ItemStack(Material.PAPER, 1);
        ItemMeta option4Meta = option4.getItemMeta();
        option4Meta.setDisplayName(options.get(3));
        option4.setItemMeta(option4Meta);
        mainInventory.setItem(16, option4);

        Bukkit.getPluginManager().registerEvents(this, Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("MinecraftPOO")));
        player.openInventory(mainInventory);
    }

    @EventHandler
    public void onSelectOption(InventoryClickEvent event){
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
}
