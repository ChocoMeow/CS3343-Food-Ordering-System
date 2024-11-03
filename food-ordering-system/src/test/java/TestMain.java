import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Kitchen;
import com.fos.main.Main;
import com.fos.member.Bartender;
import com.fos.member.Chef;


public class TestMain {

    public long MockMile = 1000;

    @Mock
    Main main;
    // @Mock
    // Kitchen kitchen;

    @Test
    public void TestCook(){

        Food f = new Food("Hotdog", 0, 0);

        f.cook();
        
        Assertions.assertEquals(0,f.getStock());
    }

    @Test

    public void TestFormatTimeLeft(){

        Assertions.assertEquals("00:01",main.formatTimeLeft(MockMile));
    }

    @Test

    public void TestKitchen(){
 
        Chef c = new Chef("Test");
        List<Chef> clist = Arrays.asList(c);

        Bartender b = new Bartender("Test");
        List< Bartender> blist = Arrays.asList(b);

        Kitchen kitchen = new Kitchen(clist, blist);
        com.fos.main.Order order = new com.fos.main.Order();
        Food f = new Food("Hotdog", 0, 0);
        Drink d = new Drink("Milk",0,0);
        order.addDrink(d);
        order.addFood(f);
        kitchen.placeOrder(order);
        Assertions.assertEquals(kitchen.getOrders().poll(), order);
    }

    // @ParameterizedTest
    // @CsvSource({"f,1", "d,1"})
    // public void TestCook(String type,int stock){

    //     Food f = new Food("Hotdog", 0, 0);

    //     f.cook();
        
    //     Assertions.assertEquals(1,f.getStock());
    // }
}