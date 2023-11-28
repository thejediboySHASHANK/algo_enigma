# Comparative Shortest Path Algorithms for Real-time Navigation on Google Maps

<p align="center">
  <img src="https://community.sw.siemens.com/servlet/rtaImage?eid=ka64O000000bqkN&feoid=00N4O000006Yxpf&refid=0EM4O00000113ss" width="500" alt="BullMQ Logo">
</p>

In the realm of navigation applications utilizing Google Maps, there exists a need to design and implement bespoke shortest path finder algorithms, steering clear of Google's proprietary closed-source methods. The primary objectives of this project are as follows:

#### Algorithmic Diversity:
Develop a solution that incorporates a minimum of three distinct shortest path finder algorithms.
Facilitate a comprehensive real-time performance analysis to discern the strengths and weaknesses of each algorithm.
Independence from Native Google Algorithms:
Devise algorithms that operate independently of Google's native closed-source routing mechanisms.
Ensure a high degree of customization and adaptability to diverse real-world scenarios.
Graphical Visualization:
Implement a visually intuitive graphical user interface to depict the dynamic functioning of the algorithms on Google Maps.
Enhance understanding of fundamental data structures and algorithms through real-time graphical representations.

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



