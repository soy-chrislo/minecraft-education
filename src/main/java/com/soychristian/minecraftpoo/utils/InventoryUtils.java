package com.soychristian.minecraftpoo.utils;

import com.soychristian.minecraftpoo.exceptions.InvalidEncodedInventoryFormat;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class InventoryUtils {
    public static String encodeInventory(Inventory inventory){
        ItemStack[] inventoryContents = new ItemStack[36];
        int inventorySize = inventory.getSize();
        if (inventorySize != 36){
            // Para 1.19.2 41 slots.
            for (int i = 0; i < 36; i++){
                inventoryContents[i] = inventory.getItem(i);
            }
            inventorySize = 36;
        } else {
            // Para 1.8.8
            inventoryContents = inventory.getContents();
        }
        String encodedInventory = "";

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(inventoryContents);
            dataOutput.flush();

            byte[] serializedObject = outputStream.toByteArray();

            encodedInventory = new String(Base64.getEncoder().encode(serializedObject));

            outputStream.close();
            dataOutput.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return encodedInventory + ":" + inventorySize;
    }

    public static Inventory decodeInventory(String encodedInventory) throws InvalidEncodedInventoryFormat {
        ItemStack[] inventoryContents = null;
        if (encodedInventory == null) return Bukkit.createInventory(null, 36);
        if (!(encodedInventory.contains(":"))){
            throw new InvalidEncodedInventoryFormat("Encoded inventory does not contain a size");
        }
        String encodedInventoryContents = encodedInventory.split(":")[0];
        int inventorySize = Integer.parseInt(encodedInventory.split(":")[1]);
        Inventory inventory = Bukkit.createInventory(null, inventorySize);

        try {
            byte[] serializedObject = Base64.getDecoder().decode(encodedInventoryContents.getBytes());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            inventoryContents = (ItemStack[]) dataInput.readObject();
            inventory.setContents(inventoryContents);

            inputStream.close();
            dataInput.close();
        } catch (IOException | ClassNotFoundException | NullPointerException e){
            e.printStackTrace();
        }
        return inventory;
    }
}
