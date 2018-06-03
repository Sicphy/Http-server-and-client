package http.protocol;

import java.io.*;

import static http.protocol.StringConstants.createResponse;
import static http.protocol.StringConstants.internalErrorResponse;

public class PutMethod extends HttpMethod {
    @Override
    public void executeMethod(String fileName, OutputStream os) {
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