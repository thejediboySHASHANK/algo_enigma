# Comparative Shortest Path Algorithms for Real-time Navigation on Google Maps

<p align="center">
  <img src="https://community.sw.siemens.com/servlet/rtaImage?eid=ka64O000000bqkN&feoid=00N4O000006Yxpf&refid=0EM4O00000113ss" width="500" alt="BullMQ Logo">
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



