package source.ailin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Synchronization is the mechanism that prevents thread interference and memory consistency errors.
 * <p>
 * Problems of multithreading environment are:
 * <ul>
 * <li>Thread interference is a problem that happens when two or more threads have some shared resources.
 * When one thread modifies data and writes it to the shared variable and at the same time another thread does the same
 * The result of execution of the first thread could be overridden by the second thread and vice versa.</li>
 * <li>Memory consistency errors occur when different threads have inconsistent views of what should be the same data.
 * The key to avoiding this errors is understanding happens-before relationship.
 * This relationship guarantees that memory writes in one statement of the one thread are visible for statements in another thread</li>
 * </ul>
 * <p>
 * Java has two basic synchronization idioms: synchronized methods and synchronized statements.
 *
 * @see #synchronizedMethodExample() for more information about synchronized methods
 * @see #synchronizedStatementExample() for more information about syncronized statements
 * <p>
 * Every time we use synchronized statement or method we acquire a lock for object we synchronized to.
 * @see #synchronizedMethodExample() for information about intrinsic locks in synchronized methods
 * @see #synchronizedStatementExample() for information about intrinsic locks in synchronized statements
 * Thread also can acquire a lock that it already owns, this called Reentrant synchronization.
 * <p>
 * There are atomic action, ones that can be either happen completely or doesn't happen at all.
 * Some of the atomic actions are:
 * <ul>
 * <li>Reads and writes are atomic for reference variables and for most primitives, except long and double.</li>
 * <li>Reads and writes are atomic for variables declared volatile, including long and double variables.</li>
 * </ul>
 * Changes to volatile variables are always visible for all other threads.
 * Using simple atomic variable access is more efficient than accessing these variables through synchronized code but requires more care by programmer
 * to avoid memory consistency error.
 * <p>
 * Popular problematic situations of multithreading enviromnent:
 * <p>
 * Deadlock - situation when two threads are forever blocked by each other because try to call resources of each other while having lock of each other
 * Starvation - situation when one thread (greedy thread) frequently calls the same synchronized method that takes some time, so it never releases the object's lock
 * and other threads that need this object starving
 * Livelock - situation when one thread acts in response to the action of another thread. If the other thread's action is a also response to the action of
 * another thread, then livelock may result
 */
public class ThreadSynchronizationExample {

    private static Thread t1;
    private static Thread t2;

    private static class SynchronizedCounter {

        private int counter = 0;

        synchronized void inc() {
            counter++;
        }

        synchronized void dec() {
            counter--;
        }

        synchronized int value() {
            return counter;
        }
    }

    private static class Named {
        private String name;

        /*
        Invoking other objects' methods from synchronized code can create problems
        That's why synchronized block is preffered here
        */
        private void setName(String name, Collection<String> names) {
            synchronized (this) {
                this.name = name;
            }
            names.add(name);
        }
    }

    /**
     * To make method synchronized add a synchronized keyword in method declaration.
     * Synchronized methods guarantee that any invocation of this method in one thread will block other threads invocations of this method on this object until
     * method returns and also happens-before relationship is kept - result of method execution is visible for all other invocation of this method in other
     * threads.
     * <p>
     * Intrinsic lock is the inner lock of an object.
     * Every time synchronized method is called it acquires a lock for current object (this)
     * Until this lock is released object cannot be accessed by other threads. Lock is released even if return was caused by exception.
     * <p>
     * If static synchronized method is called it acquires an intrinsic lock for the Class object.
     */
    private static void synchronizedMethodExample() {
        SynchronizedCounter counter = new SynchronizedCounter();

        t1 = new Thread(() -> {
            try {
                System.out.println("[Thread - 1 ThreadSynchronizationExample#synchronizedMethodExample] Thread started");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // does nothing
            }
            counter.inc();
            System.out.println("[Thread - 1 ThreadSynchronizationExample#synchronizedMethodExample] Sync counter incremented");
        });

        t2 = new Thread(() -> {
            try {
                System.out.println("[Thread - 2 ThreadSynchronizationExample#synchronizedMethodExample] Thread started");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // does nothing
            }
            counter.dec();
            System.out.println("[Thread - 2 ThreadSynchronizationExample#synchronizedMethodExample] Sync counter decremented");
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // do nothing
        }
        System.out.println("[Main Thread ThreadSynchronizationExample#synchronizedMethodExample] Sync counter value: " + counter.value());
    }

    /**
     * Synchronized statement can be used to specify object to lock on.
     * It's flexible because if we need to change only some fields of an object that doesn't
     * affects other state then we can create object to lock on only for this field modification.
     * Also it's useful when in some instance method we need to call other object's methods.
     */
    private static void synchronizedStatementExample() {
        List<String> names = new ArrayList<>();

        Named named = new Named();
        t1 = new Thread(() -> {
            System.out.println("[Thread - 1 ThreadSynchronizationExample#synchronizedStatementExample] Thread started");
            named.setName("John", names);
        });
        t2 = new Thread(() -> {
            System.out.println("[Thread - 2 ThreadSynchronizationExample#synchronizedStatementExample] Thread started");
            named.setName("Kevin", names);
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // do nothing
        }

        System.out.println("[Main Thread ThreadSynchronizationExample#synchronizedStatementExample] Result: " + names);
    }

    public static void main(String[] args) {
        try {
            run(new Thread(ThreadSynchronizationExample::synchronizedMethodExample));
            run(new Thread(ThreadSynchronizationExample::synchronizedStatementExample));
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    private static void run(Thread thread) throws InterruptedException {
        thread.start();
        thread.join();
    }
}
