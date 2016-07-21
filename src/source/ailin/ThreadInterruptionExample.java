package source.ailin;

/**
 * Thread interruption example
 * <p>
 * {@link Thread#sleep(long)} when interrupted throws {@link InterruptedException}
 */
public class ThreadInterruptionExample {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("[Thread - 1 ThreadInterruptionExample] Thread was interrupted");
            }
        });
        thread.start();
        thread.interrupt();
    }
}
