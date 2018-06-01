package http.protocol;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static http.protocol.StringConstants.badRequestResponse;
import static http.protocol.StringConstants.notImplementedResponse;
import static http.protocol.StringConstants.notSupportedHttpVersionResponse;

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
            Thread thread = threadPool.getThread(new SocketProcessor(s));
            while(thread == null) {
                thread = threadPool.getThread(new SocketProcessor(s));
            }
            thread.start();


            //new Thread(new SocketProcessor(s)).start();
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
            this. timer = new Timer();
        }

        public void run() {
            try {
                //   (new TimerThread(Thread.currentThread())).run();
                request.readInputHeaders();
                response.setRequest(request);
                response.writeResponse();
                //timer.schedule(new MyTimerTask(Thread.currentThread()), 10000);
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
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
            ThreadPool.freeThread();
        }
    }

    private static class MyTimerTask extends TimerTask {
        Thread thread;

        MyTimerTask(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            this.thread.interrupt();
            System.err.println("Client processing finished");
        }
    }

    private static class TimerThread extends Thread {
        private Thread thread;

        TimerThread(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            Timer timer = new Timer();
            timer.schedule(new MyTimerTask(this.thread), 4000);
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