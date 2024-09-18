package com.fos.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonAddJson {

    public static void main(String[] args) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();

        try (InputStream inputStream = GsonAddJson.class.getClassLoader().getResourceAsStream("Configuration.json");
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            Map<String, Object> data = gson.fromJson(reader, type);

            // Modify the data
            data.put("CHEF_NUM", 2);

            // Write the updated data back to the file
            try (FileWriter writer = new FileWriter("src/main/resources/Configuration.json")) {
                gson.toJson(data, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

// import com.google.gson.Gson;
// import com.google.gson.reflect.TypeToken;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.lang.reflect.Type;
// import java.util.Map;

// public class JsonUpdater {
//     public static void main(String[] args) {
//         Gson gson = new Gson();
//         Type type = new TypeToken<Map<String, Object>>() {}.getType();

//         try (FileReader reader = new FileReader("data.json")) {
//             Map<String, Object> data = gson.fromJson(reader, type);

//             // Modify the data
//             data.put("keyToUpdate", "newValue");

//             // Write the updated data back to the file
//             try (FileWriter writer = new FileWriter("data.json")) {
//                 gson.toJson(data, writer);
//             }

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }
