package http.protocol;

import java.io.OutputStream;

public interface HttpMethod {
    void executeMethod(String url, OutputStream os);
}