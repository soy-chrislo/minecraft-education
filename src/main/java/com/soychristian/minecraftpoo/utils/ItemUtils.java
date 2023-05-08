package com.soychristian.minecraftpoo.utils;

import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.util.ArrayList;
import java.util.List;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
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

    public static String encodeItemStack(ItemStack itemStack){
        String encodedItemStack = "";

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(itemStack);
            dataOutput.flush();

            byte[] serializedObject = outputStream.toByteArray();

            encodedItemStack = new String(Base64.getEncoder().encode(serializedObject));

            outputStream.close();
            dataOutput.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return encodedItemStack;
    }

    public static ItemStack decodeItemStack(String encodedItemStack){
        ItemStack itemStack = null;
        if(encodedItemStack == null) return null;

        try {
            byte[] serializedObject = Base64.getDecoder().decode(encodedItemStack.getBytes());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            itemStack = (ItemStack) dataInput.readObject();

            inputStream.close();
            dataInput.close();
        } catch (IOException | ClassNotFoundException | NullPointerException e){
            e.printStackTrace();
        }
        return itemStack;
    }
}
