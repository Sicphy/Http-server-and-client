package http.protocol;

import java.io.*;

import static http.protocol.StringConstants.createResponse;
import static http.protocol.StringConstants.internalErrorResponse;

public class PutMethod implements HttpMethod {
    @Override
    public void executeMethod(String fileName, OutputStream os) {

        Header header = new Header();
        Folder folder = new Folder("src/repository");


        if(folder.isExist(fileName)) {
            header.setState(createResponse);
            header.setContentLocation(fileName);
        } else {
            header.setState(internalErrorResponse);
        }

        try {
            os.write(header.getHeader().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}