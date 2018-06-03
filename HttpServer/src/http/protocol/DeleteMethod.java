package http.protocol;

import java.io.*;

import static http.protocol.StringConstants.*;

public class DeleteMethod extends HttpMethod {
    @Override
    public void executeMethod(String fileName, OutputStream os) {
        if(fileName == null || !fileName.contains(".")) {
            header.setState(notFoundResponse);
        } else if(folder.isExist(fileName)) {
            File file = new File("src/repository/" + fileName);
            if(file.delete()) {
                header.setState(okResponse);
            } else {
                header.setState(internalErrorResponse);
            }
        } else {
            header.setState(notFoundResponse);
        }

        try {
            os.write(header.getHeader().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}