import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fos.item.Drink;
import com.fos.worker.Bartender;

public class TestBartender  {

    private Bartender bartender;
    private Drink mockDrink,drink;

    @BeforeEach
    public void setUp() {
        bartender = new Bartender("John");
        mockDrink = mock(Drink.class);
        drink = new Drink("Mojito", 5.0f, 5,10);
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources if necessary (not needed in this case)
    }

    @Test
    public void testBartenderConstructor() {
        assertEquals("John", bartender.getName());
    }

    @Test
    public void testMixMethod() throws InterruptedException {
        when(mockDrink.getMixingTime()).thenReturn((int) 2L); // Set mixing time to 2 seconds

        bartender.mix(mockDrink);
        
        assertTrue(bartender.isBusy(), "Bartender should be busy after mixing starts");
        assertEquals(mockDrink, bartender.getCurrentDrink(), "Current drink should be the one being mixed");

        // Wait for the mixing to complete
        Thread.sleep(2500); // Wait longer than the mixing time
        assertFalse(bartender.isBusy(), "Bartender should not be busy after mixing completes");
        assertNull(bartender.getCurrentDrink(), "Current drink should be null after mixing completes");
    }

    @Test
    public void testIsBusy() {
        assertFalse(bartender.isBusy(), "Bartender should not be busy initially");
        
        bartender.mix(mockDrink);
        assertTrue(bartender.isBusy(), "Bartender should be busy after starting to mix");
    }

    @Test
    public void testGetCurrentDrink() {
        assertNull(bartender.getCurrentDrink(), "Current drink should be null initially");

        bartender.mix(mockDrink);
        assertEquals(mockDrink, bartender.getCurrentDrink(), "Current drink should be the one being mixed");
    }

    @Test
    public void testGetRemainingMixingTime() throws InterruptedException {
        when(mockDrink.getMixingTime()).thenReturn((int) 4L); // Set mixing time to 4 seconds

        bartender.mix(mockDrink);
        Thread.sleep(1000); // Wait for 1 second

        long remainingTime = bartender.getRemainingMixingTime();
        assertTrue(remainingTime <= 3 && remainingTime >= 0, "Remaining time should be around 3 seconds");

        // Wait for additional time to complete mixing
        Thread.sleep(3000); // Wait for additional time to complete mixing
        assertEquals(0, bartender.getRemainingMixingTime(), "Remaining time should be 0 after mixing completes");
    }

    @Test
    public void testGetRemainingMixingTimeWhenNoDrink() {
        assertEquals(0, bartender.getRemainingMixingTime(), "Remaining time should be 0 when no drink is being mixed");
    }
    @Test
    public void testGetRemainingMixingTime_initial() throws InterruptedException {
        bartender.mix(drink);
        
        // Immediately after starting mixing, remaining time should be close to mixing time
        long remainingTime = bartender.getRemainingMixingTime();
        assertTrue(remainingTime <= 5 && remainingTime >= 3.5, "Remaining mixing time should be close to initial mixing time");
    }

    @Test
    public void testGetRemainingMixingTime_after3Seconds() throws InterruptedException {
        bartender.mix(drink);
        
        // Wait for 3 seconds to let some of the mixing time pass
        Thread.sleep(3000);
        
        long remainingTime = bartender.getRemainingMixingTime();
        assertTrue(remainingTime <= 2 && remainingTime >= 0.5, "Remaining mixing time should be around 2 seconds after 3 seconds have passed");
    }

    @Test
    public void testGetRemainingMixingTime_afterMixingCompleted() throws InterruptedException {
        bartender.mix(drink);
        
        // Wait for the entire mixing time to pass
        Thread.sleep(6000); // Adding a buffer to ensure mixing is completed
        
        long remainingTime = bartender.getRemainingMixingTime();
        assertEquals(0, remainingTime, "Remaining mixing time should be 0 after mixing is completed");
    }
    
    
}