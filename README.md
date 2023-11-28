# Message Queue using BullMQ

<p align="center">
  <img src="https://www.gitbook.com/cdn-cgi/image/width=120,dpr=2,height=120,fit=contain,format=auto/https%3A%2F%2F876297641-files.gitbook.io%2F~%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252F-LUuDmt_xXMfG66Rn1GA%252Ficon%252FHOq80FSJicAlE4bVptC9%252Fbull.png%3Falt%3Dmedia%26token%3D10a2ba71-db1f-4d5c-8787-3dbedc8dd3ce" width="200" alt="BullMQ Logo">
</p>

This repository demonstrates the implementation of a message queue using BullMQ. The project consists of a worker file and a producer file, showcasing how to use the message queue to send emails asynchronously.

## Getting Started

To get started with this project, follow the instructions below.

### Prerequisites

- Node.js
- Redis

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/thejediboySHASHANK/Message_Queue_Backend.git

2. Install the dependencies:

   ```sh
   npm install

### Usage

1. Start the Redis server on your local machine using docker:

   ```sh
   docker run -itd -p 6379:6379 redis

2. Run the worker file: 
   
   ```sh
   node worker.js

2. Run the producer file: 
   
   ```sh
   node producer.js

The producer will add a job to the queue, which will be processed by the worker.


### Explanation

The worker file (worker.js) contains the logic for processing the messages from the queue. It simulates sending an email by using a delay.

The producer file (producer.js) demonstrates how to add a job to the queue. In this, it adds an email job with sample details.



