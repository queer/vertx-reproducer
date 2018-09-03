# vertx-reproducer

Repro's an issue with the vert.x websocket client

## Usage

```Bash
mvn clean package
# start server
java -jar target/vertx-reproducer-1.0.jar -server
# start client and watch the explosions!
java -jar target/vertx-reproducer-1.0.jar -client
```