import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fos.main.Utils;

public class TestUtils {

    @Test
    public void testFormatDate() {
        long timestamp = 1633036800000L; // Example timestamp (Oct 1, 2021)
        String expectedDate = "10 01 05:20:00"; // Expected format based on timestamp
        String formattedDate = Utils.formatDate(timestamp);
        assertEquals(expectedDate, formattedDate, "Formatted date should match the expected format");
    }

    @Test
    public void testFormatTime() {
        long totalSeconds = 125; // 2 minutes and 5 seconds
        String expectedTime = "02:05";
        String formattedTime = Utils.formatTime(totalSeconds);
        assertEquals(expectedTime, formattedTime, "Formatted time should match the expected format");
    }

    @Test
    public void testFormatTimeLeft() {
        long millis = 125000; // 125 seconds
        String expectedTimeLeft = "02:05";
        String formattedTimeLeft = Utils.formatTimeLeft(millis);
        assertEquals(expectedTimeLeft, formattedTimeLeft, "Formatted time left should match the expected format");
    }

    @Test
    public void testFormatTimeLeftWithNegativeMillis() {
        long millis = -5000; // Negative milliseconds
        String expectedTimeLeft = "00:00"; // Should return 00:00 for negative values
        String formattedTimeLeft = Utils.formatTimeLeft(millis);
        assertEquals(expectedTimeLeft, formattedTimeLeft, "Formatted time left for negative millis should be 00:00");
    }

    @Test
    public void testClearConsole() {
        // We cannot effectively test the actual console clearing, but we can verify that no exceptions are thrown
        // and that the method can be called successfully.
        assertDoesNotThrow(() -> Utils.clearConsole(), "clearConsole should not throw any exceptions");
    }
}