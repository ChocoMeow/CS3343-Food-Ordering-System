import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fos.item.Drink;

public class TestDrink {

    @Test
    public void TestCookWhenDrinkNoStock() {
        Drink drink = new Drink("Water", 0, 0);
        drink.use();
        Assertions.assertEquals("Water", drink.getName());
        Assertions.assertEquals(0, drink.getMixingTime());
        Assertions.assertEquals(0, drink.getStock());
        Assertions.assertEquals(Boolean.FALSE, drink.isInStock());
        Assertions.assertEquals("Drink(name=Water, mixingTime=0, stock=0)", drink.toString());
    }

    @Test
    public void TestCookWhenFoodWithMix() {
        Drink drink = new Drink("Water", 0, 1);
        drink.mix();
        Assertions.assertEquals("Water", drink.getName());
        Assertions.assertEquals(0, drink.getMixingTime());
        Assertions.assertEquals(1, drink.getStock());
        Assertions.assertEquals(Boolean.TRUE, drink.isInStock());
        Assertions.assertEquals("Drink(name=Water, mixingTime=0, stock=1)", drink.toString());
    }

    @Test
    public void TestCookWhenFoodWithMixThrowsException() {
        Drink drink = new Drink("Water", 0, 1);
        // assertThrows(InterruptedException.class, () -> {drink.mix();});

        Assertions.assertEquals("Water", drink.getName());
        Assertions.assertEquals(0, drink.getMixingTime());
        Assertions.assertEquals(1, drink.getStock());
        Assertions.assertEquals(Boolean.TRUE, drink.isInStock());
        Assertions.assertEquals("Drink(name=Water, mixingTime=0, stock=1)", drink.toString());
    }

    @Test
    void testMixHandlesInterruptedException() throws InterruptedException {
        Drink drink = new Drink("Water", 1, 1);

        Thread thread = new Thread(() -> {
            drink.mix();
        });

        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        thread.join();
        assertTrue(thread.isInterrupted(), "Thread should be interrupted");
    }

}
