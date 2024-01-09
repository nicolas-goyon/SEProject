package api.classes;

public class Response {
private String message;
    private int status;

    public Response(String message, int status) {
        this.message = message;
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
