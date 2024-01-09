package api.routes;

import api.classes.Response;
import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.logic.Subscription.SubscriptionFacade;

import java.sql.SQLException;

public class MemberAccess extends Route {

    public Response handleGet(String path) throws Exception {
        // check if path is like : /{member_id}/{access_id}
        // if not, return 400
        if (!isValidPathFormat(path)) {
            System.err.println("Invalid path format : " + path);
            return new Response("Bad Request", 400);
        }

        // get member_id and access_id from path
        String[] pathParts = path.split("/");
        int member_id = Integer.parseInt(pathParts[1]);
        int access_id = Integer.parseInt(pathParts[2]);

        // check if member_id is valid
        // if not, return 404
        if (member_id < 0) {
            throw new Exception("Member id is invalid, must be positive : " + member_id);
        }

        // check if access_id is valid
        // if not, return 404
        if (access_id < 0) {
            throw new Exception("Access id is invalid, must be positive : " + access_id);
        }

        boolean hasAccess = SubscriptionFacade.getInstance().checkAccess(member_id, access_id);

        if(!hasAccess) {
            return new Response("Unauthorized", 401);
        }

        return new Response("OK", 200);

    }
    private boolean isValidPathFormat(String path) {
        String[] pathSegments = path.split("/");
        return pathSegments.length == 3 && !pathSegments[1].isEmpty() && !pathSegments[2].isEmpty();
    }

    /**
     * @param path
     * @param body
     * @return
     * @throws Exception
     */
    @Override
    public Response handlePost(String path, String body) throws Exception {
        throw new Exception("Not implemented");
    }

    /**
     * @param path
     * @param body
     * @return
     * @throws Exception
     */
    @Override
    public Response handlePut(String path, String body) throws Exception {
        throw new Exception("Not implemented");
    }

    /**
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public Response handleDelete(String path) throws Exception {
        throw new Exception("Not implemented");
    }
}
