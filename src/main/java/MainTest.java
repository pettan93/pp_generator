import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Petr on 12.06.2017.
 */
public class MainTest {

    public static void main(String[] args) {

        int samples = 1;
        int countsThreads = 5;

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        ArrayList<Task> tasks = new ArrayList<>();


        for (Character c : alphabet) {
            for(int i = 0;i<samples;i++){
                tasks.add(new Task(c,i));
            }
        }

        System.out.println("Count of tasks " + tasks.size());

        for (int i = 0; i<countsThreads;i++) {
            TaskThread thread = new TaskThread( "Thread " + i, tasks);
            thread.start();
            thread.pickTask(tasks);
        }


    }




}
