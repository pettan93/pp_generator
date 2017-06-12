import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskPicker {

    ArrayList<Task> tasks;


    public TaskPicker(ArrayList<Task> tasks) {
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

        return pickedTask;

    }

}
