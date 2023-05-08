package com.soychristian.minecraftpoo.utils;

import com.soychristian.minecraftpoo.MinecraftPOO;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerFileFactory {
    private String playerName;
    private Player player;
    private File playerFile;
    private Inventory playerInventory;
    private YamlConfiguration playerYaml;
    private MinecraftPOO plugin;

    // playerName siempre debe ser Player.getName(), no Player.getDisplayName().
    public PlayerFileFactory(MinecraftPOO plugin, Player player){
        this.plugin = plugin;
        this.player = player;
        this.playerName = player.getName();
        init();
    }

    public PlayerFileFactory(MinecraftPOO plugin, String playerName){
        this.plugin = plugin;
        this.playerName = playerName;
        this.player = Bukkit.getPlayerExact(playerName);
        if (player != null){
            plugin.getLogger().info("Se ha encontrado el jugador " + playerName + " en el servidor.");
        } else {
            plugin.getLogger().info("No se ha encontrado el jugador " + playerName + " en el servidor.");
        }
        init();
    }
    private void init(){
        this.playerFile = new File("plugins/MinecraftPOO/players/" + playerName + ".yml");

        if (!playerFile.exists()){
            boolean result = playerFile.getParentFile().mkdirs();
            if (result){
                plugin.getLogger().info("Se ha creado el directorio " + playerFile.getParentFile().getAbsolutePath());
            } else {
                plugin.getLogger().info("El directorio " + playerFile.getParentFile().getAbsolutePath() + " ya existe. Se omite la creación.");
            }
        }
        this.playerYaml = YamlConfiguration.loadConfiguration(playerFile);
    }


    public void addPlayerData(String key, String value){
        try {
            playerYaml.load(playerFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        playerYaml.set(key, value);
        savePlayerData();
    }
    public void setPlayerData(String key, String value){
        addPlayerData(key, value);
    }
    public void removePlayerData(String key){
        try {
            playerYaml.load(playerFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        playerYaml.set(key, null);
        savePlayerData();
    }

    public String getPlayerData(String key){
        try {
            playerYaml.load(playerFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        String value = playerYaml.getString(key);
        if (value == null){
            plugin.getLogger().info("No se ha encontrado el valor " + key + " en el fichero de datos del jugador " + playerName + " en " + this.playerFile.getAbsolutePath());
            return "null";
        }
        return value;
    }

    public String[] getAllPlayerData(){
        // Obtener todas las propiedades del yml, incluyendo clave - valor.
        try {
            playerYaml.load(playerFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        List<String> result = new ArrayList<>();
        for (String key : playerYaml.getKeys(false)){
            result.add(key + ": " + playerYaml.getString(key));
        }
        return result.toArray(new String[0]);
    }

    public void incrementProperty(String key, int increment){
        try {
            playerYaml.load(playerFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        int actualValue = 0;
        try {
            String stringValue = playerYaml.getString(key);
            actualValue = Integer.parseInt(stringValue);
        } catch (NumberFormatException e){
            plugin.getLogger().info("No se ha podido convertir el valor " + playerYaml.getString(key) + " a entero.");
            return;
        }
        playerYaml.set(key, String.valueOf(actualValue + increment));
        savePlayerData();
    }

    public void savePlayerData(){
        try {
            playerYaml.save(playerFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public File getPlayerFile() {
        return playerFile;
    }

    public void setPlayerFile(File playerFile) {
        this.playerFile = playerFile;
    }

    public YamlConfiguration getPlayerYaml() {
        return playerYaml;
    }

    public void setPlayerYaml(YamlConfiguration playerYaml) {
        this.playerYaml = playerYaml;
    }

    public MinecraftPOO getPlugin() {
        return plugin;
    }

    public void setPlugin(MinecraftPOO plugin) {
        this.plugin = plugin;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public void setPlayerInventory(Inventory playerInventory) {
        this.playerInventory = playerInventory;
    }
}
