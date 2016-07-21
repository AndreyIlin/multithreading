package source.ailin;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        run(new Thread(() -> ThreadCreateExample.main(args)));
        run(new Thread(() -> ThreadSleepExample.main(args)));
        run(new Thread(() -> ThreadInterruptionExample.main(args)));
        run(new Thread(() -> ThreadJoinExample.main(args)));
        run(new Thread(() -> ThreadSynchronizationExample.main(args)));
        run(new Thread(() -> ThreadGuardedBlockExample.main(args)));
        run(new Thread(() -> ThreadDeadlockExample.main(args)));
        System.out.println("Finished");
    }

    private static void run(Thread thread) throws InterruptedException {
        thread.start();
        thread.join();
    }
}
