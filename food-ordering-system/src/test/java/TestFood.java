import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fos.item.Food;

public class TestFood {

    @Test
    public void TestCookWhenFoodNoStock() {
        Food food = new Food("Hotdog", 0, 0);
        food.use();
        Assertions.assertEquals("Hotdog", food.getName());
        Assertions.assertEquals(0, food.getCookingTime());
        Assertions.assertEquals(0, food.getStock());
        Assertions.assertEquals(Boolean.FALSE, food.isInStock());
        Assertions.assertEquals("Food(name=Hotdog, cookingTime=0, stock=0)", food.toString());
    }

    @Test
    public void TestCookWhenFoodWithCook() {
        Food food = new Food("Hotdog", 0, 1);
        food.cook();
        Assertions.assertEquals("Hotdog", food.getName());
        Assertions.assertEquals(0, food.getCookingTime());
        Assertions.assertEquals(1, food.getStock());
        Assertions.assertEquals(Boolean.TRUE, food.isInStock());
        Assertions.assertEquals("Food(name=Hotdog, cookingTime=0, stock=1)", food.toString());
    }

    @Test
    void testMixHandlesInterruptedException() throws InterruptedException {
        Food food = new Food("Hotdog", 1, 0);

        Thread thread = new Thread(() -> {
            food.cook();
        });

        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        thread.join();
        assertTrue(thread.isInterrupted(), "Thread should be interrupted");
    }

}
