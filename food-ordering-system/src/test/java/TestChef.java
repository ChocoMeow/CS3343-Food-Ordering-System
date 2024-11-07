import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fos.item.Food;
import com.fos.worker.Chef;


public class TestChef  {

    private Chef chef;
    private Food mockFood;

    @BeforeEach
    public void setUp() {
        chef = new Chef("Gordon");
        mockFood = mock(Food.class);
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources if necessary (not needed in this case)
    }

    @Test
    public void testChefConstructor() {
        assertEquals("Gordon", chef.getName());
    }

    @Test
    public void testCookMethod() throws InterruptedException {
        when(mockFood.getCookingTime()).thenReturn((int) 2L); // Set cooking time to 2 seconds

        chef.cook(mockFood);
        
        assertTrue(chef.isBusy(), "Chef should be busy after starting to cook");
        assertEquals(mockFood, chef.getCurrentFood(), "Current food should be the one being cooked");

        // Wait for the cooking to complete
        Thread.sleep(2500); // Wait longer than the cooking time
        assertFalse(chef.isBusy(), "Chef should not be busy after cooking completes");
        assertNull(chef.getCurrentFood(), "Current food should be null after cooking completes");
    }

    @Test
    public void testIsBusy() {
        assertFalse(chef.isBusy(), "Chef should not be busy initially");
        
        chef.cook(mockFood);
        assertTrue(chef.isBusy(), "Chef should be busy after starting to cook");
    }

    @Test
    public void testGetCurrentFood() {
        assertNull(chef.getCurrentFood(), "Current food should be null initially");

        chef.cook(mockFood);
        assertEquals(mockFood, chef.getCurrentFood(), "Current food should be the one being cooked");
    }

    @Test
    public void testGetRemainingCookingTime() throws InterruptedException {
        when(mockFood.getCookingTime()).thenReturn((int) 4L); // Set cooking time to 4 seconds

        chef.cook(mockFood);
        Thread.sleep(2000); // Wait for 2 seconds

        long remainingTime = chef.getRemainingCookingTime();
        assertTrue(remainingTime <= 2 && remainingTime >= 0, "Remaining time should be around 2 seconds");

        // Wait for additional time to complete cooking
        Thread.sleep(3000); // Wait for additional time to complete cooking
        assertEquals(0, chef.getRemainingCookingTime(), "Remaining time should be 0 after cooking completes");
    }

    @Test
    public void testGetRemainingCookingTimeWhenNoFood() {
        assertEquals(0, chef.getRemainingCookingTime(), "Remaining time should be 0 when no food is being cooked");
    }
}