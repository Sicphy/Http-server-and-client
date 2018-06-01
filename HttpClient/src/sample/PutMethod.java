package sample;

import java.io.*;
import java.net.Socket;
import java.util.PropertyResourceBundle;

public class PutMethod {
    private String filePath;

    PutMethod() {
        this.filePath = null;
    }

    PutMethod(String filePath) {
        this.filePath = filePath;
    }

    public Integer sendRequest() throws BadFileNameException {
        try {
            File f = new File(filePath);
            int port = 8080;
            Socket socket;
            socket = new Socket("localhost", port);
            OutputStream socketOut = socket.getOutputStream();
            PropertyResourceBundle contentTypePR = (PropertyResourceBundle)
                    PropertyResourceBundle.getBundle("sample.contentType");
            ServerResponse serverResponse = new ServerResponse(socket.getInputStream());
            String expansion;
            String type = null;
            if(filePath.contains(".")) {
                expansion = filePath.substring(filePath.lastIndexOf('.'));
                if(contentTypePR.containsKey(expansion)) {
                    type = contentTypePR.getString(expansion);
                }
            }

            if(type == null) {
                throw new BadFileNameException();
            }

            socketOut.write(("PUT /" + f.getName() + " HTTP/1.1\r\n").getBytes());
            socketOut.write("Host: localhost:8080\r\n".getBytes());
            socketOut.write(("Content-Type: " + type + "\r\n").getBytes());
            socketOut.write(("Content-Length: " + f.length() + "\r\n").getBytes());
            socketOut.write("Connection: close\r\n".getBytes());
            socketOut.write("\r\n".getBytes());
            socketOut.flush();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                byte[] byteArray = new byte[1024];
                FileInputStream fis = new FileInputStream(f.getPath());
                long s;
                s = f.length();
                BufferedOutputStream bos = new BufferedOutputStream(socketOut);
                while (s > 0) {
                    int i = fis.read(byteArray);
                    bos.write(byteArray, 0, i);
                    s -= i;
                }
                bos.flush();

                serverResponse.readResponse();

                fis.close();
                socketOut.close();

                socket.close();

                return  serverResponse.getCode();
            } catch (FileNotFoundException e) {
                throw new BadFileNameException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

