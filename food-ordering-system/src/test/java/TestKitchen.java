import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Order;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TestKitchen {

    private Kitchen kitchen;
    private Chef chef;
    private Bartender bartender;
    private Config config;

    @BeforeEach
    public void setUp() {
        chef = mock(Chef.class);
        bartender = mock(Bartender.class);
        config = mock(Config.class);

        // Set up chefs and bartenders
        ArrayList<Chef> chefs = new ArrayList<>();
        chefs.add(chef);
        ArrayList<Bartender> bartenders = new ArrayList<>();
        bartenders.add(bartender);

        when(config.getChefs()).thenReturn(chefs);
        when(config.getBartenders()).thenReturn(bartenders);
        when(config.getItems()).thenReturn(mock(Config.Items.class));
        
        kitchen = new Kitchen(config);
    }

    @Test
    public void testPlaceOrder() {
        Order order = new Order(); // Assume Order has a default constructor
        kitchen.placeOrder(order);
        
        Queue<Order> orders = kitchen.getOrders();
        assertEquals(1, orders.size(), "Order should be placed in the queue");
        assertEquals(order, orders.poll(), "The order retrieved should be the same as placed");
    }

    @Test
    public void testProcessOrders() throws Exception {
        // Set up a mock order with foods and drinks
        Food food = mock(Food.class);
        Drink drink = mock(Drink.class);
        when(food.isInStock()).thenReturn(false);
        when(drink.isInStock()).thenReturn(false);
        when(food.getPrice()).thenReturn(10.0f);
        when(drink.getPrice()).thenReturn(15.0f);
        
        Order order = mock(Order.class);
        when(order.getFoods()).thenReturn(List.of(food));
        when(order.getDrinks()).thenReturn(List.of(drink));
        when(order.getWaitingTime()).thenReturn(1000L); // Mock waiting time
        
        kitchen.placeOrder(order);

        // Simulate processing in a separate thread
        Thread processingThread = new Thread(kitchen::processOrders);
        processingThread.start();
        processingThread.join(); // Wait for processing to complete

        // Verify that the chef was called to cook food and the bartender was called to mix drinks
        verify(chef).cook(food);
        verify(bartender).mix(drink);
        
        // Check total profit
        // assertEquals(25.0, kitchen.getTotalProfit(), "Total profit should equal the sum of food and drink prices");
        assertEquals(1, kitchen.getTotalHandledOrder(), "One order should have been handled");
        assertTrue(kitchen.getTotalWaitingTime() > 0, "Total waiting time should be updated");
    }

    @Test
    public void testFindAvailableChef() {
        when(chef.isBusy()).thenReturn(false); // Chef is available
        assertEquals(chef, kitchen.findAvailableChef(), "Should find an available chef");
    }

    @Test
    public void testFindAvailableBartender() {
        when(bartender.isBusy()).thenReturn(false); // Bartender is available
        assertEquals(bartender, kitchen.findAvailableBartender(), "Should find an available bartender");
    }
}