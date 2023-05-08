package com.soychristian.minecraftpoo.commands;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.minecraftpoo.utils.InventoryUtils;
import com.soychristian.minecraftpoo.utils.ItemUtils;
import com.soychristian.minecraftpoo.utils.PlayerFileFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ManageItemsCommand implements CommandExecutor {
    MinecraftPOO plugin;

    public ManageItemsCommand(MinecraftPOO plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player playerSender = (Player) sender;
            if (args.length == 0){
                playerSender.sendMessage("Argumentos insuficientes.");
                return true;
            }
            String argument = args[0];
            switch (argument){
                case "encodeitem":
                    ItemStack itemStack = playerSender.getInventory().getItemInHand();
                    playerSender.sendMessage(ItemUtils.encodeItemStack(itemStack));
                    Bukkit.getLogger().info(ItemUtils.encodeItemStack(itemStack));
                    break;
                case "decodeitem":
                    String encodedItem = "rO0ABXNyABpvcmcuYnVra2l0LnV0aWwuaW8uV3JhcHBlcvJQR+zxEm8FAgABTAADbWFwdAAPTGphdmEvdXRpbC9NYXA7eHBzcgA1Y29tLmdvb2dsZS5jb21tb24uY29sbGVjdC5JbW11dGFibGVNYXAkU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAlsABGtleXN0ABNbTGphdmEvbGFuZy9PYmplY3Q7WwAGdmFsdWVzcQB+AAR4cHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAJ0AAI9PXQABHR5cGV1cQB+AAYAAAACdAAeb3JnLmJ1a2tpdC5pbnZlbnRvcnkuSXRlbVN0YWNrdAANRElBTU9ORF9TV09SRA==";
                    playerSender.getInventory().addItem(ItemUtils.decodeItemStack(encodedItem));
                    break;
                case "saveinventory":
                    PlayerInventory inventory = playerSender.getInventory();
                    String encodedInventory = InventoryUtils.encodeInventory(inventory);
                    PlayerFileFactory playerFileFactory = new PlayerFileFactory(plugin, playerSender);
                    playerFileFactory.setPlayerData("inventory", encodedInventory);
                    break;
                case "getinventory":
                    PlayerFileFactory playerFileFactory1 = new PlayerFileFactory(plugin, playerSender);
                    String encodedInventory1 = playerFileFactory1.getPlayerData("inventory");
                    try {
                        Inventory inventory1 = InventoryUtils.decodeInventory(encodedInventory1);
                        playerSender.openInventory(inventory1);
                    } catch (InvalidEncodedInventoryFormat e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
        return true;
    }
}
