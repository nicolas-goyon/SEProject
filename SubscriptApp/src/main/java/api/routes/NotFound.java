package api.routes;

import api.classes.Response;

public class NotFound extends Route {

        @Override
        public Response handleGet(String path) throws Exception {
            return new Response("Not found", 404);
        }

        @Override
        public Response handlePost(String path, String body) throws Exception {
            return new Response("Not found", 404);
        }

        @Override
        public Response handlePut(String path, String body) throws Exception {
            return new Response("Not found", 404);
        }

        @Override
        public Response handleDelete(String path) throws Exception {
            return new Response("Not found", 404);
        }
}
