package http.protocol;

import java.util.Scanner;

public class ConsoleThread extends Thread {
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