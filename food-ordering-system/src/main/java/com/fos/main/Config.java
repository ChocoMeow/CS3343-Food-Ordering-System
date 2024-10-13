package com.fos.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Config {
    public static final String configPath = "./food-ordering-system/src/main/resources/Configuration.json";

    @SerializedName("CHEF_NUM")
    private int chefNum;

    @SerializedName("BARTENDER_NUM")
    private int bartenderNum;

    @SerializedName("BATCH_JOB_PER_TIME")
    private double batchJobPerTime;

    @SerializedName("ORDER_DEQUEUE_THRESHOLD")
    private double orderDequeueThreshold;

    @SerializedName("ORDER_INTERVAL_CONFIG")
    private double orderIntervalConfig;

    public static Config loadConfig() {
        Gson gson = new Gson();
        Config config = null;

        try (BufferedReader br = new BufferedReader(new FileReader(configPath))) {
            config = gson.fromJson(br, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }

    public void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // For pretty printing
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(configPath))) {
            gson.toJson(this, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}