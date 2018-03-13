package concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * CallablesAndFutureExample
 */
public class CallablesAndFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

        System.out.println("1future done? " + future.isDone());

        Integer result = future.get();

        System.out.println("2future done? " + future.isDone());
        System.out.print("result: " + result);

        //--------------------------------------------------------
        ExecutorService executorService = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");

        executorService.invokeAll(callables)
                .stream()
                .map(future1 -> {
                    try {
                        return future1.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
        // --------------------------------------------------------


        ExecutorService executor1 = Executors.newWorkStealingPool();

        List<Callable<String>> callables1 = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));

        String resultado = executor1.invokeAny(callables1);
        System.out.println("resultado " + resultado);
    }
    static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }
}
