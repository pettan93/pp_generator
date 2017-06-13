import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskExecuter {



    public static Task  executeTask(Task pickedTask) {
        return new Task(pickedTask.letter,ImageGenerator.generateFromTask(pickedTask));
    }



}
