import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskThread extends Thread {

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
        System.out.println("Running " + threadName);

        Task pickedTask;

        /* VYBER ULOHY - NEJDE PARALELNE */
        synchronized (taskPicker) {
            System.out.print("Thread " + threadName);
            pickedTask = taskPicker.pickTask();

            taskPicker.notifyAll();
        }

        if (pickedTask != null) {
            Map<String, Object> results = TaskExecuter.executeTask(pickedTask);





        /* ZAPIS VYSLEDKU ULOHY - NEJDE PARALELNE */
            synchronized (taskResultWriter) {
                taskResultWriter.writeResult(results);

//                taskPicker.notifyAll();
            }
        }


        System.out.println(threadName + " exiting ");

    }


//    public void start() {
//        System.out.println("Starting " + threadName);
//        if (thread == null) {
//            thread = new Thread(this, threadName);
//            thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
