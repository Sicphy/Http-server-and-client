package http.protocol;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.PropertyResourceBundle;

import static http.protocol.StringConstants.notFoundResponse;
import static http.protocol.StringConstants.okResponse;

public class HeadMethod implements HttpMethod {
    @Override
    public void executeMethod(String fileName, OutputStream os) {
        try {
            os.write(arrangeResponse(fileName).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String arrangeResponse(String fileName) {
        Folder folder = new Folder("src/repository");
        PropertyResourceBundle contentTypePR = (PropertyResourceBundle)
                PropertyResourceBundle.getBundle("http.protocol.contentType");
        String expansion;
        File file;
        Response response = new Response();

        if(fileName == null) {
            response.setState(notFoundResponse);
            response.setType("text/html");
            file = new File("src/repository/Error404.html");
        } else if(fileName.contains(".")) {
            expansion = fileName.substring(fileName.lastIndexOf('.'));
            if (folder.isExist(fileName)) {
                response.setState(okResponse);
                response.setType(contentTypePR.getString(expansion));
                file = new File("src/repository/" + fileName);
            } else {
                response.setState(notFoundResponse);
                response.setType("text/html");
                file = new File("src/repository/Error404.html");
            }
        } else {
            response.setState(notFoundResponse);
            response.setType("text/html");
            file = new File("src/repository/Error404.html");
        }

        response.setLength(file.length());
        return response.getResponse();
    }
}