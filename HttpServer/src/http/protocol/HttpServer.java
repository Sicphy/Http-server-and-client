package http.protocol;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static http.protocol.StringConstants.*;

public class HttpServer {

    public static void main(String[] args) throws Throwable {
        int portNumber = 8080;
        ServerSocket ss = new ServerSocket(portNumber);
        ThreadPool threadPool = new ThreadPool();
        ConsoleThread consoleThread = new ConsoleThread();
        consoleThread.start();
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            if(ThreadPool.check()) {
                Thread thread = threadPool.getThread(new SocketProcessor(s));
                thread.start();
            } else {
                BadRequestResponse badRequestResponse = new BadRequestResponse(s.getOutputStream());
                badRequestResponse.setResponse(unavailableResponse);
                badRequestResponse.sendResponse();
            }
        }
    }

    public static class SocketProcessor implements Runnable {
        private Socket s;
        private HttpRequest request;
        private HttpResponse response;
        private BadRequestResponse badRequest;
        private Timer timer;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
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
                    s.close();
                    ThreadPool.freeThread();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            System.err.println("Client processing finished");
        }
    }

    private static class ConsoleThread extends Thread {
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String string;
            do {
                string = scanner.next();
                if (string.equals("close")) {
                    System.exit(0);
                }
            } while(!string.equals("close"));
        }
    }
}