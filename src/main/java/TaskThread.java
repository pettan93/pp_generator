import java.util.*;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskThread extends Thread {
    private Thread t;
    private String threadName;
    private ArrayList<Task> tasks;
    private volatile static Set<Task> sharedTasks = new HashSet<>();
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
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
//
//    public synchronized Task pickTask(ArrayList<Task> ar) {
//        Random rn = new Random();
//        int random = rn.nextInt(ar.size());
//        Task picked = ar.get(random);
//        ar.remove(random);
//        System.out.println("Chosen: " + picked.toString());
//        return picked;
//    }


}
