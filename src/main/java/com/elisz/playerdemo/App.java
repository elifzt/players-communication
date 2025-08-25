package com.elisz.playerdemo;

/**
 * Main entry point for single-process mode.
 * 
 * Responsibilities:
 * - Initializes Player instances (Alice as initiator, Bob as responder)
 * - Triggers the first message to start the conversation
 * - Monitors completion by polling the initiator's counters
 * - Prints final statistics after 10 messages
 * - Manages application lifecycle gracefully
 */

public class App {
    private static final int MESSAGE_LIMIT = 10;
    private static final int POLL_INTERVAL_MS = 50;

    public static void main(String[] args) {
        Player initiator = new Player("Alice", true, MESSAGE_LIMIT);
        Player responder = new Player("Bob", false, MESSAGE_LIMIT);

        System.out.println("=== Starting Message Exchange ===");
        initiator.sendMessage(responder, "Hello");

        monitorCompletion(initiator);
        printFinalStats(initiator, responder);
    }

    private static void monitorCompletion(Player initiator) {
        while (!isComplete(initiator)) {
            sleep();
        }
    }

    private static boolean isComplete(Player initiator) {
        return initiator.getSentCount() >= MESSAGE_LIMIT 
            && initiator.getReceivedCount() >= MESSAGE_LIMIT;
    }

    private static void sleep() {
        try {
            Thread.sleep(POLL_INTERVAL_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void printFinalStats(Player initiator, Player responder) {
        System.out.println("\n=== Conversation Completed ===");
        System.out.printf("[%s] Sent: %d, Received: %d%n",
                initiator.getName(), initiator.getSentCount(), initiator.getReceivedCount());
        System.out.printf("[%s] Sent: %d, Received: %d%n",
                responder.getName(), responder.getSentCount(), responder.getReceivedCount());
    }
}