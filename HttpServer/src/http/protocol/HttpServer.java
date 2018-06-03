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
}