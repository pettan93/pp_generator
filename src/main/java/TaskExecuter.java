import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskExecuter {



    public static Map<String,Object>  executeTask(Task pickedTask) {

        HashMap<String,Object> results = new HashMap<>();
        results.put("letter",pickedTask.letter);
        results.put("image",ImageGenerator.generateFromTask(pickedTask));


        return results;

    }

}
