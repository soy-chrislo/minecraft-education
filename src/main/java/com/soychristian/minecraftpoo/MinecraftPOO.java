package com.soychristian.minecraftpoo;

import com.soychristian.minecraftpoo.commands.EvaluateCommand;
import com.soychristian.minecraftpoo.commands.PooCommand;
import com.soychristian.minecraftpoo.commands.RegisterCommand;
import com.soychristian.minecraftpoo.listeners.OnJoinListener;
import com.soychristian.minecraftpoo.views.EvaluateView;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPOO extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("MinecraftPOO has been enabled!");
        this.getCommand("poo").setExecutor(new PooCommand());
        this.getCommand("evaluate").setExecutor(new EvaluateCommand());
        this.getCommand("registro").setExecutor(new RegisterCommand());
        getServer().getPluginManager().registerEvents(new OnJoinListener(), this);
        //getServer().getPluginManager().registerEvents(new EvaluateView(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
