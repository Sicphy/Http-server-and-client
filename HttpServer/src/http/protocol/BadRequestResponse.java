package http.protocol;

import java.io.IOException;
import java.io.OutputStream;

public class BadRequestResponse {
    private String response;
    private OutputStream os;

    BadRequestResponse(OutputStream os) {
        this.response = null;
        this.os = os;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void sendResponse() {
        Response response = new Response();
        response.setState(this.response);
        try {
            os.write(response.getResponse().getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
