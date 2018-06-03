package http.protocol;

import java.io.OutputStream;

public abstract class HttpMethod {
    protected Header header;
    protected Folder folder;

    HttpMethod() {
        header = new Header();
        folder = new Folder("src/repository");
    }

    public abstract void executeMethod(String fileName, OutputStream os);
}