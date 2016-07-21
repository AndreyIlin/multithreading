package source.ailin;

/**
 * Thread join example
 * <p>
 * To force thread to wait until another thread is completed {@link Thread#join()} method is used.
 * Waiting period could be also specified, but as with {@link Thread#sleep(long)} you can't rely on the time set,
 * because of the same reasons.
 */
public class ThreadJoinExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("[Thread - 1 ThreadJoinExample] Sleeping for 2 seconds");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // do nothing
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            // do nothing
        }
        System.out.println("[Main Thread] Waiting for [Thread - 1] to perform it's actions");
    }
}
