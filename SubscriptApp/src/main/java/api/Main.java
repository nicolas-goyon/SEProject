package api;

import com.sun.net.httpserver.HttpServer;
import api.routes.Router;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        // Define the port on which the server will listen
        int port = 8889;

        // Create a new HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);

        // Create a context for the root path and set a handler
        server.createContext("/", new Router());

        // Set the executor to null (default)
        server.setExecutor(null);

        // Start the server
        server.start();

        System.out.println("Server is listening on port " + port);
    }

}