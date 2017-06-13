import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * Created by Petr on 12.06.2017.
 */
public class MainTest {

    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        int samples = 10;
        int countsThreads = 5;

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        ArrayList<Task> tasks = new ArrayList<>();


        for (Character c : alphabet) {
            for (int i = 0; i < samples; i++) {
                tasks.add(new Task(c, i,null));
            }
        }

        System.out.println("Count of tasks " + tasks.size());

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



        System.out.println("juch");
        taskPicker.setTasks(new ArrayList<>(TaskResultWriter.generatedTasks));
        TaskResultWriter.generatedTasks = new ArrayList<>();


        for (TaskThread thread : mnozina) {
            thread.start();
        }

        ZipOutputStream zipOutputStream = Zipper.startZipping("test.zip");


        for (TaskThread thread : mnozina) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("zazipovano");
        System.out.println(TaskResultWriter.getGeneratedTasks().size());




//        Zipper.zipFile("test.zip", TaskResultWriter.getGeneratedMap());
        Long end = System.currentTimeMillis();
        System.out.println("Hotovo za [" + (end - start) / 1000 + " sekund]");

    }


}
