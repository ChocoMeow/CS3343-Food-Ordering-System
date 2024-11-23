package TestCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import com.fos.commands.CreateReport;
import com.fos.main.Config;
import com.fos.main.Kitchen;


class TestCreateReport {

    private CreateReport createReport;
    private Kitchen kitchen;
    private Config config;

    @BeforeEach
    void setUp() {
        // Initialize the CreateReport command
        createReport = new CreateReport();

        // Initialize Config and load mock data
        config = new Config().loadConfig();

        // Initialize Kitchen and populate it with mock data
        kitchen = new Kitchen(config);

    }

    @Test
    void testCreateReportExecution() {
        // Simulate user pressing "ENTER" after reviewing the report
        String input = "\n";
        Scanner scanner = new Scanner(input);

        // Execute the CreateReport command
        createReport.execute(scanner, kitchen, config);

       
    }
}
