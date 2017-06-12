import java.util.*;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskThread extends Thread {
    private Thread t;
    private String threadName;
    private ArrayList<Task> tasks;
    private volatile static Set<Task> sharedTasks = new HashSet<>();

    TaskThread(String name, ArrayList<Task> taskList) {
        threadName = name;
        tasks = taskList;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        System.out.println("Thread " + threadName + " exiting.");

        while (tasks.size() > 0) {
            System.out.println("Choosing task ");
            Task pickedTask = pickTask(tasks);
            if (!sharedTasks.contains(pickedTask))
                sharedTasks.add(pickedTask);
            else System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! vy debilove !!!!!!!!!!!!");
        }
        System.out.println("Done. Tasks are empty. Size of task set " + sharedTasks.size());

    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public synchronized Task pickTask(ArrayList<Task> ar) {
        Random rn = new Random();
        int random = rn.nextInt(ar.size());
        Task picked = ar.get(random);
        ar.remove(random);
        System.out.println("Choosen: " + picked.toString());
        return picked;
    }


}
