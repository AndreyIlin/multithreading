package source.ailin;

public class ThreadDeadlockExample {

    private static class User {
        private String name;

        private User(String name) {
            this.name = name;
        }

        private synchronized void send(User other, String message) {
            System.out.println("Sending message: " + message + "\n to: " + other.name);
            other.notifyReceived(this);
        }

        private synchronized void notifyReceived(User notify) {
            System.out.println("Message from: " + notify.name + " has been received");
        }
    }

    public static void main(String[] args) {
        User u1 = new User("John");
        User u2 = new User("Kevin");

        Thread t1 = new Thread(() -> {
            System.out.println("[Thread - 1 ThreadDeadlockExample] Thread started");
            u1.send(u2, "Hi!" + u2.name);
        });
        Thread t2 = new Thread(() -> {
            System.out.println("[Thread - 2 ThreadDeadlockExample] Thread started");
            u2.send(u1, "Hi!" + u1.name);
        });

        t1.start();
        t2.start();

        try {
            t1.join(4000);
            t2.join(4000);
        } catch (InterruptedException e) {
            //do nothing
        }
        System.out.println("[Main Thread ThreadDeadlockExample] Probably deadlock, killing threads...");
        System.exit(1);
    }
}
