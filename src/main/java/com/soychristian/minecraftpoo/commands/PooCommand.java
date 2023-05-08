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
            String playerArg, propertyArg, valueArg;
            PlayerFileFactory playerFileFactory;
            switch (args.length) {
                case 0:
                    player.sendMessage("Argumentos insuficientes: ");
                    player.sendMessage("- /poo getdataplayer <jugador> <propiedad> | Obtiene el valor de una propiedad del jugador.");
                    player.sendMessage("- /poo setdataplayer <jugador> <propiedad> <valor> | Establece el valor de una propiedad del jugador.");
                    player.sendMessage("- /poo showdataplayer <jugador> | Todas las propiedades del usuario.");
                    player.sendMessage("- /poo removedataplayer <jugador> <propiedad> | Elimina una propiedad del jugador.");
                    player.sendMessage("- /poo incrementdataplayer <jugador> <propiedad> <incremento> | Incrementa el valor de una propiedad numerica del jugador.");
                    break;
                case 1:
                    argument = args[0];
                    switch (argument) {
                        case "getdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /getdataplayer <jugador> <propiedad>");
                            break;
                        case "setdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /setdataplayer <jugador> <propiedad> <valor>");
                            break;
                        case "showdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /showdataplayer <jugador>");
                            break;
                        case "removedataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /removedataplayer <jugador> <propiedad>");
                            break;
                        case "incrementdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /incrementdataplayer <jugador> <propiedad> <incremento>");
                            break;
                    }
                    break;
                case 2:
                    argument = args[0];
                    switch (argument) {
                        case "getdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /getdataplayer <jugador> <propiedad>");
                            break;
                        case "setdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /setdataplayer <jugador> <propiedad> <valor>");
                            break;
                        case "removedataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /removedataplayer <jugador> <propiedad>");
                            break;
                        case "showdataplayer":
                            playerArg = args[1];
                            playerFileFactory = new PlayerFileFactory(plugin, playerArg);
                            if (playerFileFactory.getPlayer() == null) {
                                player.sendMessage("[Información] El jugador no se encuentra conectado.");
                            }
                            player.sendMessage("Propiedades del jugador " + playerArg + ":");
                            String[] playerData = playerFileFactory.getAllPlayerData();
                            for (String data : playerData) {
                                player.sendMessage(data);
                            }
                            break;
                        case "incrementdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /incrementdataplayer <jugador> <propiedad> <incremento>");
                            break;
                    }
                    break;
                case 3:
                    argument = args[0];
                    switch (argument) {
                        case "getdataplayer":
                            playerArg = args[1];
                            propertyArg = args[2];
                            playerFileFactory = new PlayerFileFactory(plugin, playerArg);
                            if (playerFileFactory.getPlayer() == null) {
                                player.sendMessage("[Información] El jugador no se encuentra conectado.");
                            }
                            String value = playerFileFactory.getPlayerData(propertyArg);
                            if (value.equalsIgnoreCase("null")) {
                                player.sendMessage("[Advertencia] La propiedad a la que trata de acceder no existe o su valor es null");
                            }
                            player.sendMessage("El valor de la propiedad " + propertyArg + " es: " + value);
                            break;
                        case "setdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /setdataplayer <jugador> <propiedad> <valor>");
                            break;
                        case "removedataplayer":
                            playerArg = args[1];
                            propertyArg = args[2];
                            playerFileFactory = new PlayerFileFactory(plugin, playerArg);
                            if (playerFileFactory.getPlayer() == null) {
                                player.sendMessage("[Información] El jugador no se encuentra conectado.");
                            }
                            playerFileFactory.removePlayerData(propertyArg);
                            break;
                        case "incrementdataplayer":
                            player.sendMessage("[Comandos] Argumentos requeridos: /incrementdataplayer <jugador> <propiedad> <incremento>");
                            break;
                    }
                    break;
                case 4:
                    argument = args[0];
                    switch (argument){
                        case "setdataplayer":
                            playerArg = args[1];
                            propertyArg = args[2];
                            valueArg = args[3];
                            playerFileFactory = new PlayerFileFactory(plugin, playerArg);
                            if (playerFileFactory.getPlayer() == null) {
                                player.sendMessage("[Información] El jugador no se encuentra conectado.");
                            }
                            playerFileFactory.setPlayerData(propertyArg, valueArg);
                            break;
                        case "incrementdataplayer":
                            playerArg = args[1];
                            propertyArg = args[2];
                            valueArg = args[3];
                            playerFileFactory = new PlayerFileFactory(plugin, playerArg);
                            if (playerFileFactory.getPlayer() == null) {
                                player.sendMessage("[Información] El jugador no se encuentra conectado.");
                            }
                            playerFileFactory.incrementProperty(propertyArg, Integer.parseInt(valueArg));
                            break;
                    }
                    break;
            }
        }
        return true;
    }
}
