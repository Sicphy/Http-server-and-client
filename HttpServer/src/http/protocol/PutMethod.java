package http.protocol;

import java.io.*;

import static http.protocol.StringConstants.createResponse;
import static http.protocol.StringConstants.internalErrorResponse;

public class PutMethod implements HttpMethod {
    @Override
    public void executeMethod(String fileName, OutputStream os) {

        Response response = new Response();
        Folder folder = new Folder("src/repository");


        if(folder.isExist(fileName)) {
            response.setState(createResponse);
            response.setContentLocation(fileName);
        } else {
            response.setState(internalErrorResponse);
        }

        try {
            os.write(response.getResponse().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}