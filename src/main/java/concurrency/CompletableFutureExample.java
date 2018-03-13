package concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFutureExample ex = new CompletableFutureExample();
        //ex.simpleCompletableFuture();
        ex.multiplesCallbacksCompletableFuture();
    }

    public void simpleCompletableFuture() {
        CompletableFuture.supplyAsync(this::sendMsg)
                .thenAccept(this::notify);
        System.out.println("simpleCompletableFuture " + Thread.currentThread().getName());
        waitSeconds(2);
    }

    public void multiplesCallbacksCompletableFuture() {
        CompletableFuture.supplyAsync(this::sendMsg)
                .thenApply(this::sendAnotherMsg)
                .thenAccept(this::notify);
        System.out.println("simpleCompletableFuture " + Thread.currentThread().getName());
        waitSeconds(2);
    }

    private String sendAnotherMsg(String s) {
        System.out.println("sendAnotherMsg " + s);
        return s;
    }

    private void notify(String s) {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Notify" + s);
    }

    private String sendMsg() {
        waitSeconds(1);
        System.out.println("sendMsg ");
        return Thread.currentThread().getName();
    }

    public static void waitSeconds(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        };
    }
}
