package com.example.ai.language;

public abstract class Config {
    private String message;
    private String model;
    private double top_P;
    private double temperature;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getTop_P() {
        return top_P;
    }

    public void setTop_P(double top_P) {
        this.top_P = top_P;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
