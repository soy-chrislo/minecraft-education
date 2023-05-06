package com.soychristian.minecraftpoo.commands;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.utils.PlayerFileFactory;
import com.soychristian.minecraftpoo.views.MainView;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PooCommand implements CommandExecutor {
    private MinecraftPOO plugin;
    public PooCommand(MinecraftPOO plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            player.openInventory(new MainView().getInventory());
            String argument;
            switch (args.length) {
                case 0 -> {
                    player.sendMessage("Argumentos insuficientes: ");
                    player.sendMessage("- /getdataplayer <jugador> <propiedad>");
                }
                case 1 -> {
                    argument = args[0];
                    switch (argument){
                        case "getdataplayer" -> {
                            player.sendMessage("[Comandos] Argumentos requeridos: /getdataplayer <jugador> <propiedad>");
                        }
                    }
                }
                case 3 -> {
                    argument = args[0];
                    switch (argument){
                        case "getdataplayer" -> {
                            String playerName = args[1];
                            String dataProperty = args[2];

                            PlayerFileFactory playerFileFactory = new PlayerFileFactory(plugin, playerName);
                            if (playerFileFactory.getPlayer() == null){
                                player.sendMessage("[Información] El jugador no se encuentra conectado.");
                            }
                            String value = playerFileFactory.getPlayerData(dataProperty);
                            if (value.equalsIgnoreCase("null")){
                                player.sendMessage("[Advertencia] La propiedad a la que trata de acceder no existe o su valor es null");
                            }
                            player.sendMessage("El valor de la propiedad " + dataProperty + " es: " + value);

                        }
                    }
                }

            }
        }
        return true;
    }
}
