package ca.ulaval.glo2003.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppConfigEnd2EndTest {

    @Test
    public void useInMemoryDb_forEnd2EndTests() {
        String mongoDBmode = "inmemory";
        assertEquals(mongoDBmode,System.getProperty("persistence", "inmemory"), "End2EndTest should be configured to use the inmemory DB");
    }
}
