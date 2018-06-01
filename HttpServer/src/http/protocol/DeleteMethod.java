package http.protocol;

import java.io.*;

import static http.protocol.StringConstants.*;

public class DeleteMethod implements HttpMethod {
    @Override
    public void executeMethod(String fileName, OutputStream os) {
        System.out.println(fileName);
        Folder folder = new Folder("src/repository");
        Response response = new Response();

        if(fileName == null || !fileName.contains(".")) {
            response.setState(notFoundResponse);
        } else if(folder.isExist(fileName)) {
            File file = new File("src/repository/" + fileName);
            if(file.delete()) {
                response.setState(okResponse);
            } else {
                response.setState(internalErrorResponse);
            }
        } else {
            response.setState(notFoundResponse);
        }

        try {
            os.write(response.getResponse().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}