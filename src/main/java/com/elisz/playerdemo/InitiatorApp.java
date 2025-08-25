package com.elisz.playerdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client process in multi-process mode. 
 * Responsibilities:
 * - Establishes socket connection to ResponderApp (port 5000)
 * - Sends the initial "Hello" message
 * - Reads responses and echoes them back with incrementing counters
 * - Terminates after 10 exchanged messages
 */

public class InitiatorApp {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    private static final int MESSAGE_LIMIT = 10;

    public static void main(String[] args) {
        int sentCount = 0;
        int receivedCount = 0;

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("[Initiator] Connected to Responder.");

            // İlk mesajı gönder
            String initialMessage = "Hello";
            out.println(initialMessage);
            sentCount++;
            System.out.printf("[Initiator] Sent (#%d): %s%n", sentCount, initialMessage);

            while (receivedCount < MESSAGE_LIMIT) {
                String response = in.readLine();
                if (response == null) break;

                receivedCount++;
                System.out.printf("[Initiator] Received (#%d): %s%n", receivedCount, response);

                if (sentCount >= MESSAGE_LIMIT) break;

                out.println(response);
                sentCount++;
                System.out.printf("[Initiator] Sent (#%d): %s%n", sentCount, response);
            }

            System.out.println("[Initiator] Conversation completed.");

        } catch (IOException e) {
            System.err.println("[Initiator] Error: " + e.getMessage());
        }
    }
}


