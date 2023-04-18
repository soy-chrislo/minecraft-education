package com.soychristian.minecraftpoo.models;

public class Pregunta {
    private String pregunta;
    private String[] opciones;
    private int correcta;

    public Pregunta(String pregunta, String[] opciones, int correcta) {
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.correcta = correcta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public void setOpciones(String[] respuestas) {
        this.opciones = respuestas;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }
}