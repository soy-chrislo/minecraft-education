package com.soychristian.minecraftpoo.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static ItemStack getItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        List<String> loreList = new ArrayList<>();
        for (String s : lore) {
            loreList.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        meta.setLore(loreList);
        item.setItemMeta(meta);
        return item;
    }
}
