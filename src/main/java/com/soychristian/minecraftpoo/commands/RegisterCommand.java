package com.soychristian.minecraftpoo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        File file;
        YamlConfiguration yamlConfiguration;
        if (sender instanceof Player){
            if (args.length == 0 || args.length > 3){
                sender.sendMessage("/registro <nickname> <nombre> <grado>");
                return true;
            }
            player = (Player) sender;
            file = new File("plugins/MinecraftPOO/registros.yml");
            if (!file.exists()){
                file.getParentFile().mkdirs();
            }
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            yamlConfiguration.set(args[0], args[1] + "," + args[2]);
            try {
                yamlConfiguration.save(file);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
