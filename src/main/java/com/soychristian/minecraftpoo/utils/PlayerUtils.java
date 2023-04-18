package com.soychristian.minecraftpoo.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerUtils {
    public static ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player);
        }
        return players;
    }
}
