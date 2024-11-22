import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Order;

import java.util.List;

public class TestOrder {

    private Order order;
    private Food food;
    private Drink drink;

    @BeforeEach
    public void setUp() {
        order = new Order();
        food = mock(Food.class);
        drink = mock(Drink.class);
    }

    @Test
    public void testAddFood() {
        order.addFood(food);
        List<Food> foods = order.getFoods();
        assertEquals(1, foods.size(), "Should contain one food item");
        assertEquals(food, foods.get(0), "The food item should match the one added");
    }

    @Test
    public void testAddDrink() {
        order.addDrink(drink);
        List<Drink> drinks = order.getDrinks();
        assertEquals(1, drinks.size(), "Should contain one drink item");
        assertEquals(drink, drinks.get(0), "The drink item should match the one added");
    }

    @Test
    public void testGetOrderTime() {
        long orderTime = order.getOrderTime();
        assertTrue(orderTime <= System.currentTimeMillis(), "Order time should be less than or equal to the current time");
    }

    // @Test
    // public void testGetExpectedFinishTime() {
    //     when(food.isInStock()).thenReturn(false);
    //     when(food.getCookingTime()).thenReturn((int) 5L); // 5 seconds cooking time

    //     order.addFood(food);
    //     long expectedFinishTime = order.getExpectedFinishTime();

    //     // Calculate expected finish time
    //     long currentTime = System.currentTimeMillis();
    //     long totalCookingTime = 5; // 5 seconds
    //     long calculatedFinishTime = order.getOrderTime() + totalCookingTime * 1000;

    //     assertEquals(calculatedFinishTime, expectedFinishTime, "Expected finish time should match the calculated value");
    // }

    // @Test
    // public void testGetWaitingTime() throws InterruptedException {
    //     Thread.sleep(2000); // Simulate waiting time of 2 seconds
    //     long waitingTime = order.getWaitingTime();
    //     assertTrue(waitingTime >= 2 && waitingTime < 3, "Waiting time should be approximately 2 seconds");
    // }

    // @Test
    // public void testIsEmergencyFalse() throws InterruptedException {
    //     Thread.sleep(1000); // Simulate waiting time of 1 second
    //     assertFalse(order.isEmergency(), "Order should not be an emergency if waiting time is less than 3 minutes");
    // }

    // @Test
    // public void testIsEmergencyTrue() throws InterruptedException {
    //     Thread.sleep(190000); // Simulate waiting time of 190 seconds (just under 3 minutes)
    //     assertTrue(order.isEmergency(), "Order should not be an emergency if waiting time is still less than 3 minutes");

    //     Thread.sleep(2000); // Wait an additional 2 seconds to cross the emergency threshold
    //     assertTrue(order.isEmergency(), "Order should be an emergency if waiting time exceeds 3 minutes");
    // }
}