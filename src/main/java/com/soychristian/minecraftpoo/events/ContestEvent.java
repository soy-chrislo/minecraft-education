package com.soychristian.minecraftpoo.events;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.utils.DelayUtils;
import com.soychristian.minecraftpoo.utils.EvaluateUtils;
import com.soychristian.minecraftpoo.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ContestEvent {
    private ArrayList<Player> players;
    private String contestName;
    private EvaluateUtils quizManager;
    private MinecraftPOO plugin;
    private Location contestLocation;

    public ContestEvent(MinecraftPOO plugin) throws RuntimeException  {
        this.contestName = "Example Contest Name";
        this.players = PlayerUtils.getOnlinePlayers();
        this.quizManager = new EvaluateUtils(plugin);
        this.plugin = plugin;

        if (quizManager.getPreguntas().isEmpty()){
            throw new RuntimeException("No hay preguntas en el fichero " + quizManager.getFile().getAbsolutePath() + " para comenzar con el quiz.");
        }
        Bukkit.broadcastMessage("El juego se ha establecido con " + players.size() + " y " + quizManager.getPreguntas().size() + " preguntas.");

    }

    public void init(String ubicacion){
        /*
        Primera Fase.
        1. Teletransportar a todos a un punto.
        */
        startGame(ubicacion);

    }

    public void updatePlayers(){
        this.players = PlayerUtils.getOnlinePlayers();
    }



    public void startGame(String ubicacion){
        // Se establece la ubicacion del concurso.
        setContestLocation(ubicacion);
        // Se teletransportan a todos los jugadores al punto de inicio del concurso.
        teleportUsers();

    }
    public void teleportUsers(){
        /*
        Segunda Fase.
        1. Teletransportar a todos a un punto.
        2. Iniciar el quiz.
        */
        Bukkit.broadcastMessage("El concurso " + contestName + " ha comenzado. ¡Buena suerte!");
        Bukkit.broadcastMessage("Teletransportando a todos los jugadores al punto de inicio del concurso...");
        Bukkit.broadcastMessage("El concurso comenzará en 5 segundos.");
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (Player player : players){
                    if (!player.isOnline()) continue;
                    player.teleport(contestLocation);
                    player.sendMessage("Te has teletransportado al punto de inicio del concurso.");
                }

            }
        };
        DelayUtils.executeDelayed(task, 5000);
    }

    public void setContestLocation(String ubicacion){
        String[] coords = ubicacion.split(",");
        double x, y, z;
        try {
            y = Double.parseDouble(coords[1]);
            x = Double.parseDouble(coords[0]);
            z = Double.parseDouble(coords[2]);
        } catch (NumberFormatException e){
            plugin.getLogger().warning("La ubicación " + ubicacion + " no es válida. Debe ser un string con las coordenadas separadas por comas. Ejemplo: 0,0,0");
            return;
        }
        // Buscar la forma de instanciar un mundo en overworld
        World world = plugin.getServer().getWorld("world");
        contestLocation = new Location(world, x, y, z);
        plugin.getLogger().info("Se ha establecido la ubicación del concurso en " + contestLocation.toString());
    }
}
