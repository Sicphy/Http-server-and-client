package http.protocol;

import java.io.OutputStream;

public class HttpResponse {
    private OutputStream os;
    private HttpRequest request;
    private ChooseMethod chooseMethod;

    HttpResponse(OutputStream os) {
        this.os = os;
        this.request = null;
        chooseMethod = new ChooseMethod();
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public void writeResponse() throws Throwable {
        chooseMethod.chooseMethodClass(this.request.getMethod());
        chooseMethod.executeMethod(this.request.getFileName(), this.os);
        os.flush();
    }
}