package com.soychristian.minecraftpoo;

import com.soychristian.minecraftpoo.commands.*;
import com.soychristian.minecraftpoo.listeners.OnJoinListener;
import com.soychristian.minecraftpoo.utils.EvaluateUtils;
import com.soychristian.minecraftpoo.utils.PlayerFileFactory;
import com.soychristian.minecraftpoo.views.EvaluateView;
import com.soychristian.minecraftpoo.views.MainView;
import com.soychristian.minecraftpoo.views.MenuView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPOO extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("MinecraftPOO has been enabled!");
        registerCommands();
        registerEvents();
        registerUtils();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        this.getCommand("poo").setExecutor(new PooCommand(this));
        this.getCommand("evaluate").setExecutor(new EvaluateCommand(this));
        this.getCommand("registro").setExecutor(new RegisterCommand());
        this.getCommand("manageitems").setExecutor(new ManageItemsCommand(this));
        this.getCommand("menu").setExecutor(new MenuCommand(this));
    }

    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnJoinListener(this), this);
        pm.registerEvents(new MenuView(this), this);
        //getServer().getPluginManager().registerEvents(new EvaluateView(), this);
    }

    public void registerUtils(){
        new EvaluateUtils(this);
    }
}
