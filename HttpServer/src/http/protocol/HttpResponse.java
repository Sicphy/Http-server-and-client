package http.protocol;

import java.io.OutputStream;

public class HttpResponse {
    private OutputStream os;
    private HttpRequest request;

    HttpResponse(OutputStream os) {
        this.os = os;
        this.request = null;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public void writeResponse() throws Throwable {
        ChooseMethod chooseMethod = new ChooseMethod();
        chooseMethod.chooseMethodClass(this.request.getMethod());
        chooseMethod.executeMethod(this.request.getFileName(), this.os);
        os.flush();
    }
}