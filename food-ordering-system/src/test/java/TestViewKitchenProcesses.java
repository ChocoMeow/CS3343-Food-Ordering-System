import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fos.commands.ViewKitchenProcesses;
import com.fos.main.Kitchen;
import com.fos.main.Order;

import java.util.ArrayList;
import java.util.Scanner;

// @ExtendWith(MockitoExtension.class) // Use MockitoExtension to enable Mockito
public class TestViewKitchenProcesses {

    @InjectMocks
    private ViewKitchenProcesses viewKitchenProcesses; // Inject mocks into this instance

    @Mock
    private Kitchen kitchen; // Mock the Kitchen

    @Mock
    private Scanner scanner; // Mock the Scanner

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testDisplayCurrentOrderDetails() {
        Order currentOrder = mock(Order.class);
        when(currentOrder.getFoods()).thenReturn(new ArrayList<>());
        when(currentOrder.getDrinks()).thenReturn(new ArrayList<>());
        
        viewKitchenProcesses.displayCurrentOrderDetails(currentOrder);
        
        // Verify that the correct methods were called on the currentOrder mock
        verify(currentOrder, times(1)).getFoods();
        verify(currentOrder, times(1)).getDrinks();
    }

    // Additional tests for display methods can be added here, e.g., for Chef and Bartender activities
}