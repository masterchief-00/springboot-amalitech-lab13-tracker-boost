# LAB: Tracker Boost
## Overview
Optimizing the previous project (Secured Project Tracker) using monitoring tools such as JProfiler and JMeter to indentify bottlenecks. This project demonstrates different optimization moves including caching with Redis.


## Monitoring tools
- JMeter
- JProfile
- Grafana

## Monitoring Activities
### JMeter readings (sample)
```
JMETER TESTS - 100 CONCURRENT USERS (GET /projects - NO CACHING)
avg response time - 40ms
min time - 17ms
max time - 393ms
90% requests completed - 79ms
95% requests completed - 143ms
99% requests completed - 286ms
error % - 0
throughput - 40.0/sec
```
```
JMETER TESTS - 500 CONCURRENT USERS (GET /projects - NO CACHING)
avg response time - 30ms
min time - 15ms
max time - 328ms
90% requests completed - 28ms
95% requests completed - 45ms
99% requests completed - 294ms
error % - 0
throughput - 189.5/sec
```
```
GET /users - NO CACHING
JMETER TESTS - 100 CONCURRENT USERS (GET /users - NO CACHING)
avg response time - 24ms
min time - 18ms
max time - 49ms
90% requests completed - 28ms
95% requests completed - 30ms
99% requests completed - 41ms
error % - 0
throughput - 39.9/sec
```
```
JMETER TESTS - 500 CONCURRENT USERS (GET /users - NO CACHING)
avg response time - 24ms
min time - 18ms
max time - 50ms
90% requests completed - 29ms
95% requests completed - 33ms
99% requests completed - 41ms
error % - 0
throughput - 197.7/sec
```

## 💻 Requirements
- Java 21
- Maven
- (Optional) Docker

## Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/masterchief-00/springboot-amalitech-lab13-tracker-boost
   cd springboot-amalitech-lab13-tracker-booster
   ```

2. Run the application:
   ```sh
   docker-compose up   
   ```

The app will start on - [http://localhost:8080](http://localhost:8080).

## Demonstration video
- [Video link](https://drive.google.com/file/d/1fd0QO_wSHk572xd-olRFTwvMzx85_Sl3/view?usp=sharing)
