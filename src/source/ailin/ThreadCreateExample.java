package source.ailin;

/**
 * Example of how to create and run separate threads
 * <p>
 * To create a new thread we create a new instance of {@link Thread} class, pass a
 * {@link Runnable} object to its constructor that describes the code that will be performed
 * asynchronously in this thread.
 * <p>
 * Another way to run code in the separate thread is to put asynchronous code in the class
 * that extends Thread class and override its {@link Thread#run()} method.
 * <p>
 * First approach gives us an ability to extend our class from other class than {@link Thread} and applicable
 * to the high-level thread management API
 */
public class ThreadCreateExample {
    public static void main(String[] args) {
        // 1
        (new Thread() {
            @Override
            public void run() {
                System.out.println("[Thread - 1 ThreadCreateExample] Current Time: " + System.currentTimeMillis());
            }
        }).start();

        // 2
        (new Thread(() -> System.out.println("[Thread - 2 ThreadCreateExample] Current Time: " + System.currentTimeMillis()))).start();

        // Main Thread
        System.out.println("[Main Thread ThreadCreateExample] Current Time: " + System.currentTimeMillis());
    }
}
