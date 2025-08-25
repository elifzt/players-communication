
# Player Demo Project

## Overview
The **Player Demo Project** is a Java-based application that simulates communication between two `Player` instances in both single-process and multi-process modes. The goal of the project is to demonstrate message exchanges between two players (Alice and Bob), where messages are passed back and forth, with each player incrementing a counter with each message sent and received. After a predefined number of messages, the communication stops.

The project can run in two modes:
1. **Single-process mode**: Both players are run within the same Java process.
2. **Multi-process mode**: Players run as separate Java processes, communicating over a network socket.

## Features
- **Message Exchange**: Players send and receive messages, appending a message counter to each message.
- **Termination Condition**: The conversation stops after 10 messages have been exchanged by the initiator (Alice) and the responder (Bob).
- **Multi-process Communication**: Players can run in separate processes and communicate via sockets.
- **Statistics**: After the exchange, the system outputs statistics such as the number of messages sent and received by each player.
- **Graceful Shutdown**: The system monitors the conversation's completion and exits gracefully when the message limit is reached.

## Architecture
### Components:
- **`App.java`**: The main class that handles the single-process mode. It initializes the players and handles the message flow between them.
- **`InitiatorApp.java`**: The initiator player in multi-process mode. It connects to the responder over a socket, sends and receives messages, and maintains the message count.
- **`ResponderApp.java`**: The responder player in multi-process mode. It waits for messages from the initiator and sends back modified messages.
- **`Player.java`**: Represents a player in the conversation, handling the logic for message sending, receiving, and counting.
- **`pom.xml`**: Maven configuration file that manages dependencies and project settings.

### Flow of Communication:
1. **Single-Process Mode**: Both players (Alice and Bob) run within the same process. Alice (the initiator) sends the first message, and Bob responds. This continues until the message limit is reached.
2. **Multi-Process Mode**: Alice (initiator) and Bob (responder) run in separate processes. Alice connects to Bob via a socket, sends a message, receives a response, and continues the exchange.

## Prerequisites
- **Java 21** or later.
- **Maven 3.8** or later.
- A terminal or command-line interface (e.g., Git Bash on Windows).
- Basic knowledge of Java programming and terminal commands.

## Setup
1. Clone or download the repository:

   ```bash
   cd player-demo
   ```

2. Install dependencies and compile the project:

   ```bash
   mvn clean install
   ```

## Running the Application

### Single-Process Mode
In single-process mode, both players (Alice and Bob) communicate in the same process. To run the application:

1. Start the application by running the following command:

   ```bash
   ./run.sh
   ```

This will start both players within the same process, exchange messages, and display the final statistics.

### Multi-Process Mode
In multi-process mode, players run as separate processes. To simulate this:

## How to Run

Make sure you have [Maven](https://maven.apache.org/) installed.


1. **Start the Responder** (Bob):

   ```bash
   ./run_responder.sh
   ```

2. **Start the Initiator** (Alice) in another terminal:

   ```bash
   ./run_initiator.sh
   ```

The initiator will connect to the responder via a socket, and the message exchange will begin.

**Note**: If you're on Linux/macOS and face a permission issue, you may need to make the scripts executable:

```bash
chmod +x run.sh run_initiator.sh run_responder.sh


## Code Structure

player-demo/
├── src/                         
│   └── main/java/com/elisz/playerdemo/
│       ├── App.java # Single-process entry point
│       ├── InitiatorApp.java # Multi-process client
│       ├── ResponderApp.java # Multi-process server
│       └── Player.java # Core player logic
├── run.sh # Single-process runner                        
├── run_initiator.sh # Multi-process client starter
├── run_responder.sh # Multi-process server starter
├── pom.xml # Maven configuration                    
└── README.md # Project documentation                    

- **`App.java`**: The main entry point for single-process mode. It initializes both players and monitors the message exchange.
- **`InitiatorApp.java`**: The client-side process for multi-process mode. It connects to the `ResponderApp` via socket and exchanges messages.
- **`ResponderApp.java`**: The server-side process for multi-process mode. It listens for incoming messages from the initiator and responds with modified messages.
- **`Player.java`**: The `Player` class that encapsulates the logic for sending and receiving messages. It tracks the message count and ensures the correct behavior of each player based on whether they are the initiator or responder.
- **`run.sh`**: A script to start the application in single-process mode.
- **`run_initiator.sh`**: A script to start the `InitiatorApp` in multi-process mode.
- **`run_responder.sh`**: A script to start the `ResponderApp` in multi-process mode.

## Shell Scripts
### `run.sh`
This script cleans the previous build, compiles the project, and starts the application in single-process mode. It can be extended for different environments (e.g., debug, production).

### `run_initiator.sh` & `run_responder.sh`
These scripts start the initiator and responder processes in multi-process mode. The initiator connects to the responder via a socket, and they exchange messages until the message limit is reached.

## Sample Output
When running the application, the console will display messages showing the message exchange. Here’s an example of what you might see:

```
=== Starting Message Exchange ===
[Alice] Sending to [Bob] (#1): Hello
[Bob] Received from [Alice] (#1): Hello
[Bob] Sending: Hello|1
[Alice] Received from [Bob] (#1): Hello|1
[Alice] Sending to [Bob] (#2): Hello|1
...
=== Conversation Completed ===
[Alice] Sent: 10, Received: 10
[Bob] Sent: 10, Received: 10
```
