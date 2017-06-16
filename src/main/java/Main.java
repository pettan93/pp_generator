import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Petr on 12.06.2017.
 */
public class Main {


    public static void main(String[] args) {


        Long start = System.currentTimeMillis();
        int samples = 100;
        int countsThreads = 5;
        ArrayList<Task> tasks = new ArrayList<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Set<TaskThread> zipThreadTasks = new HashSet<>();

        for (Character c : alphabet) {
            for (int i = 0; i < samples; i++) {
                tasks.add(new Task(c, i, null));
            }
        }

        TaskPicker taskPicker = new TaskPicker(tasks);
        TaskResultWriter taskResultWriter = new TaskResultWriter();

        // responsible for starting the zip phase
        Runnable endActionBarrier = () -> {
            for (TaskThread taskThread : zipThreadTasks) {
                try {
                    taskThread.getScatterZipOutputStream().writeTo(Zipper.zipArchiveOutputStream);
                    taskThread.getScatterZipOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Zipper.closeZipping();
            Long end = System.currentTimeMillis();
            System.out.println("Hotovo za [" + (end - start) / 1000 + " sekund]");
        };

        CyclicBarrier endBarrier = new CyclicBarrier(countsThreads, endActionBarrier);
        // barrier for changing tasks after the generating phase
        Runnable changeActionBarrier = () -> {
            taskPicker.setTasks(new ArrayList<>(TaskResultWriter.generatedTasks));
            TaskResultWriter.generatedTasks = new ArrayList<>();

            Zipper.startZipping("test.zip");
            for (int i = 0; i < countsThreads; i++) {
                TaskThread thread = new TaskThread("Thread " + i, taskPicker, taskResultWriter, endBarrier);
                thread.start();
                zipThreadTasks.add(thread);
            }
        };
        CyclicBarrier changeBarrier = new CyclicBarrier(countsThreads, changeActionBarrier);


        for (int i = 0; i < countsThreads; i++) {
            TaskThread thread = new TaskThread("Thread " + i, taskPicker, taskResultWriter, changeBarrier);
            thread.start();
        }

    }


}
