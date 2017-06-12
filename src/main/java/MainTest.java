import java.util.*;

/**
 * Created by Petr on 12.06.2017.
 */
public class MainTest {

    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        int samples = 100;
        int countsThreads = 5;

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        ArrayList<Task> tasks = new ArrayList<>();


        for (Character c : alphabet) {
            for (int i = 0; i < samples; i++) {
                tasks.add(new Task(c, i));
            }
        }

        System.out.println("Count of tasks " + tasks.size());

        TaskExecuter taskExecuter = new TaskExecuter(tasks);

        for (int i = 0; i < countsThreads; i++) {
            TaskThread thread = new TaskThread("Thread " + i, taskExecuter);
            thread.start();
        }


        System.out.println("juch");
        Zipper.zipFile("test.zip", TaskExecuter.getGeneratedMap());
        Long end = System.currentTimeMillis();
        System.out.println("Hotovo za [" + (end - start) / 1000 + " sekund]");

    }


}
