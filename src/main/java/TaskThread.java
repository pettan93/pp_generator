/**
 * Created by admin on 12.06.2017.
 */
public class TaskThread extends Thread {
    private Thread thread;
    private String threadName;
    TaskExecuter taskExecuter;

    TaskThread(String name, TaskExecuter taskExecuter) {
        threadName = name;
        this.taskExecuter = taskExecuter;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        synchronized (taskExecuter) {
            System.out.print("Thread " + threadName);
            taskExecuter.pickTask();
        }

        System.out.println("Thread " +  threadName + " exiting.");

    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
