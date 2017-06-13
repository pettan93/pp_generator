import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskThread extends Thread {

    private Thread thread;
    private String threadName;
    final TaskPicker taskPicker;
    final TaskResultWriter taskResultWriter;


    TaskThread(String name, TaskPicker taskPicker, TaskResultWriter taskResultWriter) {
        threadName = name;
        this.taskPicker = taskPicker;
        this.taskResultWriter = taskResultWriter;

        System.out.println("Creating " + threadName);
    }

    public void run() {
//        System.out.println("Running " + threadName);

        Task pickedTask;

        while (true) {
            if (taskPicker.getTasks().size() == 0) {
                System.out.println(threadName + " exiting ");
                break;
            }


            /* VYBER ULOHY - NEJDE PARALELNE */
            synchronized (taskPicker) {
                System.out.print("Thread " + threadName);
                pickedTask = taskPicker.pickTask();
                taskPicker.notifyAll();
            }


            if (pickedTask != null) {

                Task task = TaskExecuter.executeTask(pickedTask);

               /* ZAPIS VYSLEDKU ULOHY - NEJDE PARALELNE */
                synchronized (taskResultWriter) {
                    taskResultWriter.writeResult(task);
                    taskResultWriter.notifyAll();
                }

            }


        }

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
