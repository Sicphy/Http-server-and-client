package http.protocol;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private InputStream is;
    private String request;
    private String method;
    private String version;
    private String url;
    private String fileName;
    private HashMap<String, String> attributes;

    HttpRequest(InputStream is) {
        this.is = is;
        request = new String();
        fileName = null;
    }

    public void readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));


        while(true) {
            String s = br.readLine();
            if(s == null || s.trim().length() == 0) {
                break;
            }
            this.request += s;
            this.request += "\n";
        }

        setHttpHeader(request);

        //System.out.println(request);

        if(method.equals("PUT")) {
            long s;
            s = Integer.parseInt(attributes.get("content-length"));
            byte[] byteArray = new byte[1024];
            File f = new File("src/repository/" + fileName);
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            BufferedInputStream bis = new BufferedInputStream(is);
            while (s > 0) {
                int i = bis.read(byteArray);
                fos.write(byteArray, 0, i);
                s -= i;
            }
            fos.close();
        }

        System.out.println(request);
        /*System.out.println(method);
        System.out.println(url);
        System.out.println(version);
        System.out.println(attributes);
        System.out.println("File name: " + fileName);*/
    }

    private void setHttpHeader(String header) throws Throwable {
        attributes = new HashMap<String, String>();
        String [] headerLines = header.split("\r?\n");
        String [] firstLineFields = headerLines[0].split(" ");
        System.out.println("First line length: " + firstLineFields.length);
        if(firstLineFields.length != 3) throw new BadRequestException();
        version = firstLineFields[2].trim();
        method = firstLineFields[0].trim();
        Pattern p = Pattern.compile("^(GET|DELETE|HEAD|PUT)$");
        Matcher m = p.matcher(method);

        if(!m.matches()) {
            throw new BadMethodException();
        }

        if(!version.equals("HTTP/1.1")) {
            throw new BadHttpVersionException();
        }

        url = firstLineFields[1].trim();
        fileName = url.substring(1);

        for(int i = 1; i < headerLines.length; ++i)
        {
            String []tokns = headerLines[i].split(": ");
            if(tokns.length != 2) continue;
            attributes.put(tokns[0].trim().toLowerCase(), tokns[1].trim());
        }
    }

    public String getUrl() { return this.url; }
    public String getFileName() { return this.fileName; }
    public String  getMethod() { return this.method; }
}
