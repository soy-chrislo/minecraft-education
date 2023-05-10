package com.soychristian.minecraftpoo.commands;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.helpers.AdminInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AdminInventoryCommand implements CommandExecutor {
    private MinecraftPOO plugin;
    public AdminInventoryCommand(MinecraftPOO plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("minecraftpoo.admin.inventory")) return true;
        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length == 0){
                player.sendMessage("Argumentos insuficientes.");
                return true;
            }
            String argument = args[0];
            switch (argument){
                case "setinventory":
                    String playerArg = args[1];
                    AdminInventory adminInventory = new AdminInventory(plugin);
                    player.openInventory(adminInventory.getInventory(playerArg));
                    break;
            }
        }
        return true;
    }
}
