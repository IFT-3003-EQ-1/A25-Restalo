package ca.ulaval.glo2003;

public class AppConfig {

    private String host;
    private String port;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public AppConfig(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public String getURI() {
        return String.format("http://%s:%s/", host, port);
    }
}
