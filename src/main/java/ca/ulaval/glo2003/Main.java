package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.RestaurantRessource;
import ca.ulaval.glo2003.api.response.exceptions.AccessInterditExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.NotFoundExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.ParametreInvalideExceptionMapper;
import ca.ulaval.glo2003.api.response.exceptions.ParametreManquantExceptionMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    private static HttpServer server;

    public static HttpServer startServer() {
        AppContext app = new AppContext();
        final ResourceConfig rc = new ResourceConfig()
                .register(app.getRessource())
                .register(AccessInterditExceptionMapper.class)
                .register(ParametreInvalideExceptionMapper.class)
                .register(ParametreManquantExceptionMapper.class)
                .register(NotFoundExceptionMapper.class);
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        return server;
    }

    public static void main(String[] args) {
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
    }

    public static void stopServer() {
        if (server != null) {
            server.shutdownNow();
        }
    }
}
