package ca.ulaval.glo2003;
import io.sentry.Sentry;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;
import java.util.Objects;

public class Main {

    private static HttpServer server;

    public static HttpServer startServer() {
        AppContext context = new AppContext();
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(getConfig().getURI()), context.getRessources());
        return server;
    }

    public static AppConfig getConfig() {
        String host = "0.0.0.0";
        String port = Objects.toString(System.getenv("PORT"), "8080");
        return new AppConfig(host, port);
    }
    private static void initializeSentry() {
        String sentryDsn = System.getenv("SENTRY_DSN");

        if (sentryDsn != null && !sentryDsn.isEmpty()) {
            Sentry.init(options -> {
                options.setDsn(sentryDsn);
                options.setTracesSampleRate(1.0);
            });
            System.out.println("Sentry initialized");
        }
    }

    public static void main(String[] args) {
        initializeSentry();
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", getConfig().getURI());
    }

}
