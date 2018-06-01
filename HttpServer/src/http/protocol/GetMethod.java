package http.protocol;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.PropertyResourceBundle;

import static http.protocol.StringConstants.*;

public class GetMethod implements HttpMethod {
     private File file;

     public void executeMethod(String fileName, OutputStream os) {
          try {
               os.write(arrangeResponse(fileName).getBytes());
               os.write(getBodyResponse(fileName));
          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     private String arrangeResponse(String fileName) {
          Folder folder = new Folder("src/repository");
          PropertyResourceBundle contentTypePR = (PropertyResourceBundle)
                  PropertyResourceBundle.getBundle("http.protocol.contentType");
          String expansion;
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

     private byte[] getBodyResponse(String fileName) {
          try {
               return Files.readAllBytes(file.toPath());
          } catch (IOException e) {
               e.printStackTrace();
          }
          return null;
     }
}