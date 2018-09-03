package gg.amy;

import io.vertx.core.json.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static spark.Spark.*;

/**
 * @author amy
 * @since 9/3/18.
 */
class Server {
    //private final Vertx vertx = Vertx.vertx();
    private static String big = "";
    
    private Collection<String> readFile() {
        final List<String> list = new ArrayList<>();
        
        final InputStream in = getClass().getResourceAsStream("/big.txt");
        final BufferedReader input = new BufferedReader(new InputStreamReader(in));
        input.lines().forEach(list::add);
        try {
            input.close();
        } catch(final IOException e) {
            throw new RuntimeException(e);
        }
        
        return list;
    }
    
    
    void run() {
        big = String.join("\n", readFile());
        /*
        final HttpServer server = vertx.createHttpServer();
        System.out.println("Loaded big.txt");
        server.websocketHandler(socket -> {
            System.out.println("Socket connected, sending oversized payload!");
            socket.writeBinaryMessage(Buffer.buffer(new JsonObject().put("big", big).encode()));
        });
        server.listen(7777);
        System.out.println("Server ready!");
        */
        port(7777);
        webSocket("/socket", SocketServer.class);
        init();
    }
    
    @WebSocket
    public static final class SocketServer {
        @OnWebSocketConnect
        public void connected(final Session session) {
            try {
                session.getRemote().sendString(new JsonObject().put("big", big).encode());
            } catch(final IOException e) {
                e.printStackTrace();
            }
        }
    }
}
