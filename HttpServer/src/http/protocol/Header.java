package http.protocol;

import java.util.Date;

public class Header {
    private String header;
    private String length = null;
    private String type = null;
    private String state = null;
    private String server = null;
    private String date = null;
    private String connection = null;
    private String location = null;

    Header() {
        header = new String();
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

    public String getHeader() {
        Date date = new Date();
        this.date = "Date: " + date.toString() + "\r\n";
        connection = "Connection: close\r\n";
        server = "Server: some-test-server\r\n";
        header = this.state + this.date + server;
        if(length != null) {
            header += length;
        }
        if(type != null) {
            header += type;
        }
        if(location != null) {
            header += location;
        }
        header += connection + "\r\n";
        System.out.println(header);
        return header;
    }

}