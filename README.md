# Instagram Like Counter - Spring Boot + Redis

A scalable backend service that simulates Instagram-style post likes using Spring Boot, Redis, and PostgreSQL.  
Built to handle high-concurrency scenarios using asynchronous Redis counters and batch aggregation.

## Features

- Record post likes in Redis instantly
- Periodically sync like counts to PostgreSQL
- Data persistence with JPA + PostgreSQL
- High-performance design to handle massive concurrent likes
- Clears Redis counters after flush to maintain accuracy

## Tech Stack

- **Java 8**
- **Spring Boot**
- **Spring Data JPA**
- **Redis**
- **PostgreSQL**
- **Spring Scheduler**

## Architecture

```

User Clicks "Like" on Post
             |
             V
Like count stored in Redis (post:like:{postId})
             |
             V      
Batch Aggregator (runs every 30s)
             |
             V
Fetch like counts from Redis
             |
             V
Update `likeCount` in PostgreSQL
             |
             V
Delete Redis key for fresh count

```

## Project Structure

```

src/
├── controller/
│   └── PostController.java
├── entity/
│   └── Post.java
├── repository/
│   └── PostRepository.java
├── service/
│   └── LikeService.java
├── scheduler/
│   └── LikeAggregator.java
└── InstagramLikeCounterApplication.java

```

## API Endpoints

### Like a Post

```
POST /posts/{postId}/like
```

### Example

```bash
curl -X POST http://localhost:8080/posts/101/like
```

## Sample Redis Keys

```
post:like:101 -> 15
post:like:102 -> 8
```

These are incremented in real time and flushed to the database every 30 seconds.

## Future Improvements

* Prevent duplicate likes per user using Redis Sets
* Use Kafka for event-driven like processing
* Track like timestamps for analytics
* Add unlike/dislike support

## 💡 Author

**Dev Portal**
Follow for more backend-focused tutorials and projects.
* LinkedIn: [LinkedIn Profile](https://www.linkedin.com/in/nakul-mitra-microservices-spring-boot-java-postgresql/)
* YouTube: [Dev Portal](https://www.youtube.com/@DevPortal2114)