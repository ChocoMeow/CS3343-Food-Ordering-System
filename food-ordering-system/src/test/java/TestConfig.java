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

public class TestConfig {

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

    //     Config loadedConfig = Config.loadConfig(); // Load config using the BufferedReader
    //     assertNotNull(loadedConfig, "Loaded config should not be null");

    //     // Validate chefs
    //     assertEquals(2, loadedConfig.getChefs().size(), "Should load 2 chefs");
    //     assertEquals("Gordon", loadedConfig.getChefs().get(0).getName(), "First chef should be Gordon");
    //     assertEquals("Jamie", loadedConfig.getChefs().get(1).getName(), "Second chef should be Jamie");

    //     // Validate bartenders
    //     assertEquals(1, loadedConfig.getBartenders().size(), "Should load 1 bartender");
    //     assertEquals("Tom", loadedConfig.getBartenders().get(0).getName(), "Bartender should be Tom");

    //     // Validate items
    //     assertNotNull(loadedConfig.getItems(), "Items should not be null");
    //     assertEquals(4, loadedConfig.getItems().getFoods().size(), "Should load 4 food items");
    //     assertEquals(2, loadedConfig.getItems().getDrinks().size(), "Should load 2 drink items");

    //     // Validate food items
    //     assertEquals("Pasta", loadedConfig.getItems().getFoods().get(0).getName());
    //     assertEquals(25, loadedConfig.getItems().getFoods().get(0).getPrice());
    //     assertEquals("Hotdog", loadedConfig.getItems().getFoods().get(1).getName());

    //     // Validate drink items
    //     assertEquals("Cocktail", loadedConfig.getItems().getDrinks().get(0).getName());
    //     assertEquals(20, loadedConfig.getItems().getDrinks().get(0).getPrice());
    // }


    // @Test
    // public void testSaveConfig() throws IOException {
    // config.chefs.add(chef);
    // config.bartenders.add(bartender);
    // config.items.food.add(food);
    // config.items.drink.add(drink);

    // // Mock FileWriter and BufferedWriter
    // BufferedWriter writer = mock(BufferedWriter.class);
    // FileWriter fileWriter = mock(FileWriter.class);

    // // Mocking the file writing process
    // try (MockedStatic<FileWriter> mockedFileWriter =
    // mockStatic(FileWriter.class);
    // MockedStatic<BufferedWriter> mockedBufferedWriter =
    // mockStatic(BufferedWriter.class)) {

    // mockedFileWriter.when(() -> new
    // FileWriter(CONFIG_PATH)).thenReturn(fileWriter);
    // mockedBufferedWriter.when(() -> new
    // BufferedWriter(fileWriter)).thenReturn(writer);

    // config.saveConfig();

    // // Verify that the write method has been called
    // verify(writer, times(1)).write(anyString());
    // verify(writer, times(1)).close();
    // }
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