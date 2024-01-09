package api.routes;

import api.classes.Response;

public abstract class Route {

    public abstract Response handleGet(String path) throws Exception;

    public abstract Response handlePost(String path, String body) throws Exception;

    public abstract Response handlePut(String path, String body) throws Exception;

    public abstract Response handleDelete(String path) throws Exception;

    public Response execute(String method, String path, String body) throws Exception {
        return switch (method) {
            case "GET" -> handleGet(path);
            case "POST" -> handlePost(path, body);
            case "PUT" -> handlePut(path, body);
            case "DELETE" -> handleDelete(path);
            default -> throw new Exception("Method not supported");
        };
    }
}
