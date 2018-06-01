package http.protocol;

import java.util.Date;

public class Response {
    private String response;
    private String length = null;
    private String type = null;
    private String state = null;
    private String server = null;
    private String date = null;
    private String connection = null;
    private String location = null;

    Response() {
        response = new String();
    }

    public void setState(String state) {
        this.state = "HTTP/1.1 " + state + "\r\n";
    }

    public void setLength(long length) {
        this.length = "Content-Length: " + length + "\r\n";
    }

    public void setContentLocation(String location) {
        this.location = "Content-Location: /" + location + "\r\n";
    }

    public void setType(String type) {
        this.type = "Content-Type: " + type + "\r\n";
    }

    public String getResponse() {
        Date date = new Date();
        this.date = "Date: " + date.toString() + "\r\n";
        connection = "Connection: keep-alive\r\n";
        server = "Server: some-test-server\r\n";
        response = this.state + this.date + server;
        if(length != null) {
            response += length;
        }
        if(type != null) {
            response += type;
        }
        if(location != null) {
            response += location;
        }
        response += connection + "\r\n";
        System.out.println(response);
        return response;
    }

}