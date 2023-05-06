package com.soychristian.minecraftpoo.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.models.Pregunta;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EvaluateUtils {
    private MinecraftPOO plugin;
    private File file;
    private Gson gson;
    private FileWriter fileWriter;
    private BufferedReader bufferedReader;
    private ArrayList<Pregunta> preguntas;

    public EvaluateUtils(MinecraftPOO plugin){
        this.plugin = plugin;
        this.file = new File("plugins/MinecraftPOO/preguntas.json");
        this.gson = new Gson();

        if (!file.exists()){
            file.getParentFile().mkdirs();
            try {
                fileWriter = new FileWriter(this.file);
                fileWriter.write("[]");
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.preguntas = getPreguntas();
        if (preguntas.size() == 0){
            plugin.getLogger().info("No hay preguntas en el fichero de preguntas.json");
        } else {
            plugin.getLogger().info("Se han cargado " + preguntas.size() + " preguntas.");
        }
    }

    public void addPregunta(Pregunta pregunta){
        this.preguntas.add(pregunta);
        String preguntasJson = this.gson.toJson(preguntas);

        try {
            fileWriter = new FileWriter(this.file);
            fileWriter.write(preguntasJson);
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<Pregunta> getPreguntas(){
        ArrayList<Pregunta> preguntasFichero = new ArrayList<Pregunta>();
        try {
            bufferedReader = new BufferedReader(new FileReader(this.file));
            Type listType = new TypeToken<ArrayList<Pregunta>>(){}.getType();
            // ! Returns an object of type T from the Reader. Returns null if json is at EOF.
            preguntasFichero = this.gson.fromJson(bufferedReader, listType);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preguntasFichero;
    }

    public MinecraftPOO getPlugin() {
        return plugin;
    }

    public void setPlugin(MinecraftPOO plugin) {
        this.plugin = plugin;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }

    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void setPreguntas(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
