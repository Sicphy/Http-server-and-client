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
        Header header = new Header();

        if(fileName == null) {
            header.setState(notFoundResponse);
            header.setType("text/html");
            file = new File("src/repository/Error404.html");
        } else if(fileName.contains(".")) {
            expansion = fileName.substring(fileName.lastIndexOf('.'));
            if (folder.isExist(fileName)) {
                header.setState(okResponse);
                header.setType(contentTypePR.getString(expansion));
                file = new File("src/repository/" + fileName);
            } else {
                header.setState(notFoundResponse);
                header.setType("text/html");
                file = new File("src/repository/Error404.html");
            }
        } else {
            header.setState(notFoundResponse);
            header.setType("text/html");
            file = new File("src/repository/Error404.html");
        }

        header.setLength(file.length());
        return header.getHeader();
    }
}