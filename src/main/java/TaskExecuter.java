import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskExecuter {

    ArrayList<Task> tasks;

    static HashMap<Character, ArrayList> generatedMap = new HashMap<>();



    public TaskExecuter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void pickTask() {
        Task pickedTask = null;
        while (tasks.size() > 0) {
            Random rn = new Random();
            int random = rn.nextInt(tasks.size());
            pickedTask = tasks.get(random);

            if(tasks != null){

                BufferedImage bi = ImageGenerator.generateFromTask(pickedTask);

                generatedMap.computeIfAbsent(pickedTask.letter, k -> new ArrayList<BufferedImage>());

                generatedMap.get(pickedTask.letter).add(bi);


            }

            tasks.remove(random);
            System.out.println(".. chosen: " + pickedTask.toString());
        }

        System.out.println("Pocet ukolu po provedeni: " + tasks.size());

    }

    public static HashMap<Character, ArrayList> getGeneratedMap() {
        return generatedMap;
    }
}
