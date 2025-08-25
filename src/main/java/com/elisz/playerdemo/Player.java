package com.elisz.playerdemo;

import java.util.concurrent.atomic.AtomicInteger;

/*/**
 * Represents a participant in the message exchange protocol.
 * 
 * Responsibilities:
 * - Maintains thread-safe counters for sent/received messages (AtomicInteger)
 * - Formats messages by appending the current count (e.g., "Hello|1")
 * - Enforces stop conditions based on the initiator role
 * - Handles direct message passing between Player instances (single-process mode)
 * - Provides getters for conversation metrics (sentCount, receivedCount)
 */

public class Player {
    private final String name;
    private final boolean isInitiator;
    private final AtomicInteger sentCount = new AtomicInteger(0);
    private final AtomicInteger receivedCount = new AtomicInteger(0);
    private final int messageLimit;

    /**
     * Creates a new Player instance
     * @param name Unique identifier for the player
     * @param isInitiator Whether this player starts the conversation
     * @param limit Maximum number of messages to exchange
     */
    public Player(String name, boolean isInitiator, int limit) {
        this.name = name;
        this.isInitiator = isInitiator;
        this.messageLimit = limit;
    }

    /**
     * Sends a message to another player
     * @param receiver Target player to receive the message
     * @param message Content to send
     */
    public void sendMessage(Player receiver, String message) {
        if (shouldStopSending()) return;
        
        int currentCount = sentCount.incrementAndGet();
        System.out.printf("[%s] Sending to [%s] (#%d): %s%n", 
                         name, receiver.name, currentCount, message);
        
        receiver.receiveMessage(this, message);
    }

    /**
     * Processes received message and sends response
     * @param sender Player who sent the message
     * @param message Received content
     */
    public void receiveMessage(Player sender, String message) {
        int currentCount = receivedCount.incrementAndGet();
        System.out.printf("[%s] Received from [%s] (#%d): %s%n", 
                         name, sender.name, currentCount, message);

        if (shouldStopReceiving()) {
            System.out.printf("[%s] Reached limit of %d messages%n", 
                            name, messageLimit);
            return;
        }

        // Create response: original message + current count
        String response = message.split("\\|")[0] + "|" + currentCount;
        sendMessage(sender, response);
    }

    private boolean shouldStopSending() {
        return isInitiator && sentCount.get() >= messageLimit;
    }

    private boolean shouldStopReceiving() {
        return isInitiator && receivedCount.get() >= messageLimit;
    }

    public String getName() {
        return name;
    }

    public int getSentCount() {
        return sentCount.get();
    }

    public int getReceivedCount() {
        return receivedCount.get();
    }
}

