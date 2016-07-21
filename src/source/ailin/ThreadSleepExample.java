package source.ailin;

/**
 * Example of how to suspend thread with {@link Thread#sleep(long)}
 * <p>
 * To pause thread task execution for some time static method {@link Thread#sleep(long)} is used.
 * It doesn't give guarantee that thread will be paused for the given amount of time
 * because of limitations of the facilities provided by the underlying OS
 */
public class ThreadSleepExample {

    public static void main(String[] args) {
        //1
        Thread thread = new Thread(() -> {
            try {
                long start = System.currentTimeMillis();
                Thread.sleep(4000);
                long end = System.currentTimeMillis();
                System.out.println("[Thread - 1 ThreadSleepExample] Task execution time: " + (end - start));
            } catch (InterruptedException e) {
                // do nothing
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}
