import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskResultWriter {


    static ArrayList<Task> generatedTasks = new ArrayList<>();

    void writeResult(Task task) {
        generatedTasks.add(task);
    }

}
