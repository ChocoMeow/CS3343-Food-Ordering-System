package com.fos.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fos.worker.Chef;
import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.worker.Bartender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
public class Config {
    private static final String configFileName = "Configuration.json";

    @SerializedName("CHEFS")
    public ArrayList<Chef> chefs;

    @SerializedName("BARTENDERS")
    private ArrayList<Bartender> bartenders;

    @SerializedName("ITEMS")
    private Items items;

    public static Config loadConfig() {
        Gson gson = new Gson();
        Config config = null;

        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(configFileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Config file not found: " + configFileName);
            }
            config = gson.fromJson(br, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }

    public void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // For pretty printing
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                Files.newOutputStream(Paths.get(getClass().getClassLoader().getResource(configFileName).getPath()))))) {
            gson.toJson(this, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chef> getChefs() {
        return chefs;
    }

    public ArrayList<Bartender> getBartenders() {
        return bartenders;
    }

    public Items getItems() {
        return items;
    }

    public static class Items {
        @SerializedName("FOOD")
        private ArrayList<Food> food;

        @SerializedName("DRINK")
        private ArrayList<Drink> drink;

        public ArrayList<Food> getFoods() {
            return food;
        }

        public ArrayList<Drink> getDrinks() {
            return drink;
        }
    }
}