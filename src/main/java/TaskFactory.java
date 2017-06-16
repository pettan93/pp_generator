import java.util.ArrayList;
import java.util.List;

/**
 * Created by ON on 16.06.2017.
 */
public class TaskFactory {
    private static ArrayList<Task> tasks = new ArrayList<>();
    static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static ArrayList<Task> createTasks(int countOfSamples) {
        for (Character c : alphabet) {
            for (int i = 0; i < countOfSamples; i++) {
                tasks.add(new Task(c, i, null));
            }
        }

        return tasks;

    }
}

