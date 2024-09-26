package com.fos.Tasks;

import com.fos.main.Main;
import com.fos.main.Order;

public class MainTask extends Task {
    
    public MainTask(int initialDelay, int period) {
        super(initialDelay, period);
    }

    @Override
    public void execute() {
        // Sort order

        // Get the first order from the order list
        if (!Main.orders.isEmpty()) {
            Order order = Main.orders.get(0); // Proceed with operations on the order
            
        }
        
        
        // Assign Food and Drink to the chef and Bartender (if available)

        // chef and bartender will start working

    }
}
