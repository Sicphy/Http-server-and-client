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
        Header header = new Header();
        header.setState(this.response);
        try {
            os.write(header.getHeader().getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
