import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fos.commands.CreateOrderCommand;
import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Kitchen;
import com.fos.main.Order;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class TestCreateOrderCommand  {

    @InjectMocks
    private CreateOrderCommand createOrderCommand;

    @Mock
    private Kitchen kitchen;

    @Mock
    private Food food1;

    @Mock
    private Food food2;

    @Mock
    private Drink drink1;

    @Mock
    private Drink drink2;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Mock food and drink behavior
        when(food1.getName()).thenReturn("Pizza");
        when(food1.getCookingTime()).thenReturn(15);
        when(food1.isInStock()).thenReturn(true);

        when(food2.getName()).thenReturn("Pasta");
        when(food2.getCookingTime()).thenReturn(10);
        when(food2.isInStock()).thenReturn(true);

        when(drink1.getName()).thenReturn("Coke");
        when(drink1.isInStock()).thenReturn(true);

        when(drink2.getName()).thenReturn("Water");
        when(drink2.isInStock()).thenReturn(true);

        // Mock kitchen's available foods and drinks
        when(kitchen.getAvailableFoods()).thenReturn(Arrays.asList(food1, food2));
        when(kitchen.getAvailableDrinks()).thenReturn(Arrays.asList(drink1, drink2));
    }

    @Test
    public void testExecute() {
        // Simulate user input for food and drink selection
        Scanner scanner = new Scanner("1\nd\n1\nd\n"); // Select food 1, finish food selection, select drink 1, finish drink selection

        // Execute the command
        createOrderCommand.execute(scanner, kitchen);
        
        // Use ArgumentCaptor to capture the Order passed to placeOrder
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(kitchen).placeOrder(orderCaptor.capture());
        
        // Get the captured order
        Order capturedOrder = orderCaptor.getValue();
        
        // Check the contents of the captured order
        assertNotNull(capturedOrder);
        assertEquals(1, capturedOrder.getFoods().size());
        assertEquals("Pizza", capturedOrder.getFoods().get(0).getName());
        assertEquals(0, capturedOrder.getDrinks().size());
        // assertEquals("Coke", capturedOrder.getDrinks().get(0).getName());
        
        // Capture the output
        String output = outputStream.toString();
        assertTrue(output.contains("Pizza added to the order."), "Output should indicate that Pizza was added.");
        assertFalse(output.contains("Coke added to the order."), "Output should indicate that Coke was added.");
        
        // Clean up
        scanner.close();
    }

    @Test
    public void testExecuteNoItems() {
        // Simulate user input to skip adding any items
        Scanner scanner = new Scanner("d\nd\n"); // Directly finish food and drink selection

        // Execute the command
        createOrderCommand.execute(scanner, kitchen);

        // Verify that no order was placed in the kitchen
        verify(kitchen, never()).placeOrder(any(Order.class));

        // Capture the output
        String output = outputStream.toString();
        assertTrue(output.contains("No items in the order. Order cancelled."), "Output should indicate that order was cancelled.");

        // Clean up
        scanner.close();
    }
}