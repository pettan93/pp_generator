import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Petr on 12.06.2017.
 */
public class MainTest {

    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        int samples = 100;
        int countsThreads = 50;

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        ArrayList<Task> tasks = new ArrayList<>();


        for (Character c : alphabet) {
            for (int i = 0; i < samples; i++) {
                tasks.add(new Task(c, i, null));
            }
        }

        TaskPicker taskPicker = new TaskPicker(tasks);
        TaskResultWriter taskResultWriter = new TaskResultWriter();

        Set<TaskThread> mnozina = new HashSet<TaskThread>();

        for (int i = 0; i < countsThreads; i++) {
            TaskThread thread = new TaskThread("Thread " + i, taskPicker, taskResultWriter);
            thread.start();
            mnozina.add(thread);
        }

        for (TaskThread thread : mnozina) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        taskPicker.setTasks(new ArrayList<>(TaskResultWriter.generatedTasks));
        TaskResultWriter.generatedTasks = new ArrayList<>();

        Zipper.startZipping("test.zip");

        Set<TaskThread> mnozina2 = new HashSet<TaskThread>();

        for (int i = 0; i < countsThreads; i++) {
            TaskThread thread = new TaskThread("Thread " + i, taskPicker, taskResultWriter);
            thread.start();
            mnozina2.add(thread);
        }


        for (TaskThread thread : mnozina2) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (TaskThread taskThread : mnozina2) {
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


    }



}
