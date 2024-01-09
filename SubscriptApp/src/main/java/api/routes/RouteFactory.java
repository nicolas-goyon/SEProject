package api.routes;

public class RouteFactory {

    public static Route getRoute(String path) {
        // get first part of path
        String[] pathParts = path.split("/");
        String routeName = pathParts[1];

        return switch (routeName) {
            case "access" -> new MemberAccess();
            default -> new NotFound();
        };
    }
}
