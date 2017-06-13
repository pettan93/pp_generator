import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;

import java.io.File;
import java.io.IOException;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskThread extends Thread {

    private String threadName;
    final TaskPicker taskPicker;
    final TaskResultWriter taskResultWriter;
    File tempFile = null;
    ScatterZipOutputStream scatterZipOutputStream;

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
                Task task = null;
                synchronized (taskResultWriter) {
                    TaskExecuter.zip = this.scatterZipOutputStream;
                 task = TaskExecuter.executeTask(pickedTask);
                }

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

    public void setScatterZipOutputStream(ScatterZipOutputStream scatterZipOutputStream) {
        this.scatterZipOutputStream = scatterZipOutputStream;
    }
}
