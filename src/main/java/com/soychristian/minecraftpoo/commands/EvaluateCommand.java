package com.soychristian.minecraftpoo.commands;

import com.soychristian.minecraftpoo.utils.DelayUtils;
import com.soychristian.minecraftpoo.utils.PlayerUtils;
import com.soychristian.minecraftpoo.views.EvaluateView;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class EvaluateCommand implements CommandExecutor {
    String pluginPrefix = "&6[&cMinecraftPOO&6]&r ";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("minecraftpoo.evaluate")) {
                ArrayList<Player> players = PlayerUtils.getOnlinePlayers();
                for (Player p : players) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "El test esta a punto de comenzar"));
                    Runnable task = new Runnable(){
                        @Override
                        public void run(){
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginPrefix + "El test ha comenzado"));
                            String question = "¿Qué es una clase en POO?";
                            ArrayList<String> options = new ArrayList<String>();
                            options.add("Una clase es un conjunto de objetos");
                            options.add("Una clase es un conjunto de atributos");
                            options.add("Una clase es un conjunto de métodos");
                            options.add("Una clase es un conjunto de atributos y métodos");
                            String answer = "Una clase es un conjunto de atributos y métodos";
                            new EvaluateView().buildGui(p, question, options, answer);
                        }
                    };

                    DelayUtils.executeDelayed(task, 5000);

                }
            }
        }
        return true;
    }
}
