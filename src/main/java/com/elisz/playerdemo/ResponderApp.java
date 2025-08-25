package com.elisz.playerdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server-side player that waits for incoming messages and replies with message + counter.
 */
public class ResponderApp {
    private static final int PORT = 5000;
    private static final int MESSAGE_LIMIT = 10;

    public static void main(String[] args) {
        int messageCount = 0;

        System.out.println("[Responder] Waiting for connection...");

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket clientSocket = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("[Responder] Connected to Initiator.");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                messageCount++;
                System.out.printf("[Responder] Received: %s%n", inputLine);

                // Mesajı düzenle: eski mesajın başına gelen + mesaj sayısı
                String original = inputLine.split("\\|")[0];
                String response = original + "|" + messageCount;

                System.out.printf("[Responder] Sending: %s%n", response);
                out.println(response);

                if (messageCount >= MESSAGE_LIMIT) {
                    System.out.println("[Responder] Reached message limit. Closing...");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("[Responder] Error: " + e.getMessage());
        }
    }
}
