package gg.amy;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;

/**
 * @author amy
 * @since 9/3/18.
 */
class Client {
    private final Vertx vertx = Vertx.vertx();
    
    void run() {
        final HttpClient client = vertx.createHttpClient();
        
        client.websocketAbs("ws://localhost:7777/socket", null, null, null,
                socket -> {
                    socket.exceptionHandler(Throwable::printStackTrace)
                            .closeHandler(__ -> System.err.println("Socket closing!"))
                            .frameHandler(frame -> {
                                if(frame.isClose()) {
                                    System.err.println("Got close frame! code = " + frame.closeStatusCode() + ", reason = "
                                            + frame.closeReason());
                                } else if(frame.isText()) {
                                    System.out.println("Recv. text frame of size " + frame.textData().length());
                                } else if(frame.isBinary()) {
                                    System.out.println("Recv. binary frame of size " + frame.binaryData().length());
                                } else {
                                    System.out.println("Recv. frame: text = " + frame.isText() + ", close = "
                                            + frame.isClose() + ", binary = " + frame.isBinary() + ", final = "
                                            + frame.isFinal() + ", continuation = " + frame.isContinuation());
                                }
                            });
                }, Throwable::printStackTrace);
    }
}
