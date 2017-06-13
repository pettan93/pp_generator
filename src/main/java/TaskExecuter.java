import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskExecuter {



    public static Task executeTask(Task pickedTask) {

        if(pickedTask.data == null)
            return new Task(pickedTask.letter,pickedTask.number,ImageGenerator.generateFromTask(pickedTask));

//        if(pickedTask.data != null)
//            return null;

        return null;
    }



}
