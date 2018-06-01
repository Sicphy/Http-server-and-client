package sample;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.PropertyResourceBundle;

public class DeleteMethod {
    private String fileName;

    DeleteMethod() {
        this.fileName = null;
    }

    DeleteMethod(String fileName) {
        this.fileName = fileName;
    }

    public Integer sendRequest() throws BadFileNameException {
        try {
            int port = 8080;
            Socket socket;
            socket = new Socket("localhost", port);
            OutputStream socketOut = socket.getOutputStream();
            ServerResponse serverResponse = new ServerResponse(socket.getInputStream());
            PropertyResourceBundle contentTypePR = (PropertyResourceBundle)
                    PropertyResourceBundle.getBundle("sample.contentType");
            String expansion = null;
            String type = null;

            if(fileName.contains(".")) {
                expansion = fileName.substring(fileName.lastIndexOf('.'));
                if (contentTypePR.containsKey(expansion)) {
                    type = contentTypePR.getString(expansion);
                }
            }

            if(type == null) {
                throw new BadFileNameException();
            }

            socketOut.write(("DELETE /" + fileName + " HTTP/1.1\r\n").getBytes());
            socketOut.write("Host: localhost:8080\r\n".getBytes());
            socketOut.write(("Content-Type: " + type + "\r\n").getBytes());
            socketOut.write("Connection: close\r\n".getBytes());
            socketOut.write("\r\n".getBytes());
            socketOut.flush();
            System.out.println(fileName);

            serverResponse.readResponse();

            socketOut.close();

            socket.close();

            return serverResponse.getCode();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

