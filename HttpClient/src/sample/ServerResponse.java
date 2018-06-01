package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ServerResponse {
    private InputStream is;
    private String response;
    private HashMap<String, String> attributes;
    private int code;
    private String message;
    private String version;


    ServerResponse(InputStream is) {
        this.is = is;
        response = new String();
    }

    public void readResponse() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while (true) {
                String s = br.readLine();
                if (s == null || s.trim().length() == 0) {
                    break;
                }
                this.response += s;
                this.response += "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            setHttpHeader(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.response);
        System.out.println(version);
        System.out.println(message);
        System.out.println(code);
        System.out.println(attributes);
    }

    private void setHttpHeader(String respones) throws IOException {
        attributes = new HashMap<String, String>();
        String [] headerLines = respones.split("\r?\n");
        String [] firstLineFields = headerLines[0].split(" ");
        //if(firstLineFields.length != 3) throw new IOException();
        for(int i = 2; i < firstLineFields.length; i++)
            message += firstLineFields[i].trim() + " ";
        message.trim();
        version = firstLineFields[0].trim();
        code = Integer.parseInt(firstLineFields[1].trim());

        for(int i = 1; i < headerLines.length; ++i)
        {
            String []tokns = headerLines[i].split(": ");
            if(tokns.length != 2) continue;
            attributes.put(tokns[0].trim().toLowerCase(), tokns[1].trim());
        }
    }

    public int getCode() {
        return this.code;
    }
}
