import java.util.*;

/**
 * Created by Petr on 12.06.2017.
 */
public class MainTest {

    public static void main(String[] args) {

        int samples = 20;
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


    }


}
