import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

import java.io.*;
import java.util.ArrayList;

public class ConfigTest {

    @InjectMocks
    private Config config;

    @Mock
    private Chef chef;
    
    @Mock
    private Bartender bartender;
    
    @Mock
    private Food food;

    @Mock
    private Drink drink;

    private static final String CONFIG_PATH = "./src/main/resources/Configuration.json";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        config = new Config();
        config.chefs = new ArrayList<>();
        config.bartenders = new ArrayList<>();
        config.items = new Config.Items();
        config.items.food = new ArrayList<>();
        config.items.drink = new ArrayList<>();
    }

    // @Test
    // public void testLoadConfig() throws IOException {
    //     String json = "{ \"CHEFS\": [], \"BARTENDERS\": [], \"ITEMS\": { \"FOOD\": [], \"DRINK\": [] } }";
        
    //     // Mock FileReader and BufferedReader
    //     InputStream inputStream = new ByteArrayInputStream(json.getBytes());
    //     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        
    //     // Use reflection to set the private configPath variable temporarily
    //     System.setProperty("configPath", CONFIG_PATH);
        
    //     // Mocking the file reading process
    //     try (MockedStatic<FileReader> mockedFileReader = mockStatic(FileReader.class);
    //          MockedStatic<BufferedReader> mockedBufferedReader = mockStatic(BufferedReader.class)) {
            
    //         mockedFileReader.when(() -> new FileReader(CONFIG_PATH)).thenReturn(new FileReader(CONFIG_PATH));
    //         mockedBufferedReader.when(() -> new BufferedReader(any(FileReader.class))).thenReturn(br);

    //         Config loadedConfig = Config.loadConfig();
    //         assertNotNull(loadedConfig, "Loaded config should not be null");
    //         assertTrue(loadedConfig.getChefs().isEmpty(), "Chefs list should be empty");
    //         assertTrue(loadedConfig.getBartenders().isEmpty(), "Bartenders list should be empty");
    //         assertNotNull(loadedConfig.getItems(), "Items should not be null");
    //         assertTrue(loadedConfig.getItems().getFoods().isEmpty(), "Foods list should be empty");
    //         assertTrue(loadedConfig.getItems().getDrinks().isEmpty(), "Drinks list should be empty");
    //     }
    // }

    // @Test
    // public void testSaveConfig() throws IOException {
    //     config.chefs.add(chef);
    //     config.bartenders.add(bartender);
    //     config.items.food.add(food);
    //     config.items.drink.add(drink);

    //     // Mock FileWriter and BufferedWriter
    //     BufferedWriter writer = mock(BufferedWriter.class);
    //     FileWriter fileWriter = mock(FileWriter.class);
        
    //     // Mocking the file writing process
    //     try (MockedStatic<FileWriter> mockedFileWriter = mockStatic(FileWriter.class);
    //          MockedStatic<BufferedWriter> mockedBufferedWriter = mockStatic(BufferedWriter.class)) {
            
    //         mockedFileWriter.when(() -> new FileWriter(CONFIG_PATH)).thenReturn(fileWriter);
    //         mockedBufferedWriter.when(() -> new BufferedWriter(fileWriter)).thenReturn(writer);

    //         config.saveConfig();

    //         // Verify that the write method has been called
    //         verify(writer, times(1)).write(anyString());
    //         verify(writer, times(1)).close();
    //     }
    // }

    @Test
    public void testGetChefs() {
        assertNotNull(config.getChefs(), "Chefs list should not be null");
    }

    @Test
    public void testGetBartenders() {
        assertNotNull(config.getBartenders(), "Bartenders list should not be null");
    }

    @Test
    public void testGetItems() {
        assertNotNull(config.getItems(), "Items should not be null");
    }
}