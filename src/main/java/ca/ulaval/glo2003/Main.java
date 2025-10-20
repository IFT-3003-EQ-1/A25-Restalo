package ca.ulaval.glo2003;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    private static HttpServer server;

    public static HttpServer startServer() {
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), AppContext.getRessources());
        return server;
    }

    public static void main(String[] args) {
        startServer();
        System.out.printf("Jersey app started with endpoints available at %s%n", BASE_URI);
    }

}
