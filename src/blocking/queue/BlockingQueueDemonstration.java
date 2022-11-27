package blocking.queue;

import com.sun.xml.internal.ws.spi.db.BindingContextFactory;

import java.util.logging.Level;

public class BlockingQueueDemonstration {


    public static void main(String[] args) throws Exception {
        final BlockingQueue<Integer> q = new BlockingQueue<>(5);

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    q.enqueue(i);
                    BindingContextFactory.LOGGER.log(Level.WARNING, "enqueued is : {0} ", i);  // String formatting only applied if needed
                }
            } catch (InterruptedException ie) {
                BindingContextFactory.LOGGER.log(Level.WARNING, "Interrupted !", ie);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 25; i++) {
                    BindingContextFactory.LOGGER.log(Level.WARNING, "Thread 2 dequeued:  {0} ", q.dequeue());
                }
            } catch (InterruptedException ie) {
                BindingContextFactory.LOGGER.log(Level.WARNING, "Interrupted!", ie);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                for (int i = 0; i < 25; i++) {
                    BindingContextFactory.LOGGER.log(Level.WARNING, "Thread 3 dequeued:  {0} ", q.dequeue());
                }
            } catch (InterruptedException ie) {
                BindingContextFactory.LOGGER.log(Level.WARNING, "Interrupted  !", ie);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        });

        t1.start();
        Thread.sleep(4000);
        t2.start();

        t2.join();

        t3.start();
        t1.join();
        t3.join();
    }
}

