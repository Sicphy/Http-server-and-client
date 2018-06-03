package http.protocol;

import java.net.Socket;

import static http.protocol.StringConstants.badRequestResponse;
import static http.protocol.StringConstants.notImplementedResponse;
import static http.protocol.StringConstants.notSupportedHttpVersionResponse;

public class SocketProcessor implements Runnable {

    private Socket socket;
    private HttpRequest request;
    private HttpResponse response;
    private BadRequestResponse badRequest;

    SocketProcessor(Socket s) throws Throwable {
        this.socket = s;
        this.request = new HttpRequest(s.getInputStream());
        this.response = new HttpResponse(s.getOutputStream());
        this.badRequest = new BadRequestResponse(s.getOutputStream());
    }

    public void run() {
        try {
            request.readInputHeaders();
            response.setRequest(request);
            response.writeResponse();
        }  catch (BadRequestException e) {
            badRequest.setResponse(badRequestResponse);
            badRequest.sendResponse();
        } catch (BadHttpVersionException e) {
            badRequest.setResponse(notSupportedHttpVersionResponse);
            badRequest.sendResponse();
        } catch (BadMethodException e) {
            badRequest.setResponse(notImplementedResponse);
            badRequest.sendResponse();
        } catch (Throwable e) {

        } finally {
            try {
                socket.close();
                ThreadPool.freeThread();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        System.err.println("Client processing finished");
    }
}