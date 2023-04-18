package com.soychristian.minecraftpoo.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class OnJoinListener implements Listener {
    @EventHandler
    public void onJoinListener(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ArrayList<String> message = new ArrayList<String>();
        message.add("-------------------------");
        message.add("&lBienvenid@ al servidor de la Lúdica de Programación");
        message.add("Este es un mensaje para anunciar cambios, &cleélo siempre que entres");
        message.add("");
        message.add("Recuerda seguir las &6normas de convivencia&f para evitar sanciones");
        message.add("Este servidor es &cexclusivo&f para los alumnos de la &6Lúdica de Programación&f.");
        message.add("-------------------------");

        for (String line : message){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }
}
