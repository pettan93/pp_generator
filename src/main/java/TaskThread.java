import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskThread extends Thread {

    private String threadName;
    final TaskPicker taskPicker;
    private final TaskResultWriter taskResultWriter;
    private File tempFile = null;
    private ScatterZipOutputStream scatterZipOutputStream;

    private final CyclicBarrier barrier;


    TaskThread(String name, TaskPicker taskPicker, TaskResultWriter taskResultWriter,CyclicBarrier barrier) {
        threadName = name;
        this.taskPicker = taskPicker;
        this.taskResultWriter = taskResultWriter;
        this.barrier = barrier;

        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);

        Task pickedTask;

        while (true) {

            if (taskPicker.getTasks().size() == 0) {
                System.out.println(threadName + " exiting ");
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                break;
            }


            /* VYBER ULOHY - NEJDE PARALELNE */
            synchronized (taskPicker) {
                System.out.print("Thread " + threadName);
                pickedTask = taskPicker.pickTask();
                taskPicker.notifyAll();
            }


            if (pickedTask != null) {
                Task task = null;

//                 TaskExecuter.zip = this.scatterZipOutputStream;
                 task = TaskExecuter.executeTask(pickedTask);

                 if(pickedTask.data != null)
                    Zipper.addZipEntry(this.scatterZipOutputStream,pickedTask);


               /* ZAPIS VYSLEDKU ULOHY - NEJDE PARALELNE */
                synchronized (taskResultWriter) {
                    taskResultWriter.writeResult(task);
                    taskResultWriter.notifyAll();
                }

            }
        }


    }

    public void start(){
        System.out.println("Starting " + threadName);
        try {
            tempFile = File.createTempFile(threadName + "test", ".nozip");
            scatterZipOutputStream = ScatterZipOutputStream.fileBased(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.start();
    }

    public ScatterZipOutputStream getScatterZipOutputStream() {
        return scatterZipOutputStream;
    }

}
