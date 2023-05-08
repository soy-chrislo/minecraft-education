package com.soychristian.minecraftpoo.commands;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.views.MenuView;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {
    MinecraftPOO plugin;
    public MenuCommand(MinecraftPOO plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            MenuView menuView = new MenuView(plugin);
            player.openInventory(menuView.getInventory());
        }
        return true;
    }
}
