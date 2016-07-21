package source.ailin;

import java.util.Random;

public class ThreadGuardedBlockExample {
    private static class Drop {

        private String message;
        private boolean empty = true;

        synchronized String take() {
            while (this.empty) {
                try {
                    System.out.println("[Thread - 2 ThreadGuardedBlockExample] Waiting for new message to be published");
                    wait();
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
            System.out.println("[Thread - 2 ThreadGuardedBlockExample] Taking new message");
            this.empty = true;
            notifyAll();
            return this.message;
        }

        synchronized void put(String message) {
            while (!this.empty) {
                try {
                    System.out.println("[Thread - 1 ThreadGuardedBlockExample] Waiting for message to be retrieved");
                    wait();
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
            System.out.println("[Thread - 1 ThreadGuardedBlockExample] Putting new message");
            this.empty = false;
            this.message = message;
            notifyAll();
        }
    }

    private static class Producer implements Runnable {
        private Drop drop;

        Producer(Drop drop) {
            this.drop = drop;
        }

        @Override
        public void run() {
            String importantInfo[] = {
                    "Mares eat oats",
                    "Does eat oats",
                    "Little lambs eat ivy",
                    "A kid will eat ivy too"
            };
            Random random = new Random();

            for (String anImportantInfo : importantInfo) {
                System.out.println("[Thread - 1 ThreadGuardedBlockExample] Going to put message into drop");
                drop.put(anImportantInfo);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
            drop.put("DONE");
        }
    }

    private static class Consumer implements Runnable {
        private Drop drop;

        Consumer(Drop drop) {
            this.drop = drop;
        }

        public void run() {
            Random random = new Random();
            for (String message = drop.take();
                 !message.equals("DONE");
                 message = drop.take()) {
                System.out.println("[Thread - 2 ThreadGuardedBlockExample] Took message \"" + message + "\" from the drop");
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }
    }

    public static void main(String[] args) {
        Drop drop = new Drop();
        Thread t1 = new Thread(new Producer(drop));
        Thread t2 = new Thread(new Consumer(drop));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
