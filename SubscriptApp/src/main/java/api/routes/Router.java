package api.routes;

import api.classes.Response;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Router implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        String path = exchange.getRequestURI().getPath();

        String body = new String(exchange.getRequestBody().readAllBytes());

        if (path.equals("/favicon.ico")) {
            sendResponse(exchange, responseEmpty());
            return;
        }

        Route route = RouteFactory.getRoute(path);

        path = path.substring(path.indexOf("/", 1));

        if (route instanceof NotFound) {
            sendResponse(exchange, new Response("Route not found", 404));
            return;
        }

        Response response = null;
        try {
            response = route.execute(requestMethod, path, body);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, new Response("Internal server error", 500));
            return;
        }

        sendResponse(exchange, response);
    }

    private Response responseEmpty() {
        return new Response("", 200);
    }

    private void sendResponse(HttpExchange exchange, Response response) throws IOException {
        String responseString = response.getMessage();
        byte[] bytes = responseString.getBytes();
        exchange.sendResponseHeaders(response.getStatus(), bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}