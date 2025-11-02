package ca.ulaval.glo2003.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppConfigEnd2EndTest {

    @Test
    public void givenAppContext_whenTestAppIsLaunched_thenLocalMongoDbIsUsed() {
        String mongoDBmode = "mongo";
        assertEquals(mongoDBmode,System.getProperty("persistence"), "End2EndTest should be configured to use mongoDB");
    }
}
