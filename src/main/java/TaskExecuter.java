import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskExecuter {

    ArrayList<Task> tasks;


    public TaskExecuter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task pickTask() {
        Task pickedTask = null;
        while (tasks.size() > 0) {
            Random rn = new Random();
            int random = rn.nextInt(tasks.size());
            pickedTask = tasks.get(random);

            tasks.remove(random);
            System.out.println(".. chosen: " + pickedTask.toString());
        }

        System.out.println("Pocet ukolu po provedeni: " + tasks.size());

        return pickedTask;
    }
}
