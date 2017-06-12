import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskResultWriter {


    static HashMap<Character, ArrayList> generatedMap = new HashMap<>();



    public void writeResult(Map<String,Object> results) {

        generatedMap.computeIfAbsent((Character) results.get("letter"), k -> new ArrayList<BufferedImage>());

        generatedMap.get((Character) results.get("letter")).add(results.get("image"));


    }

    public static HashMap<Character, ArrayList> getGeneratedMap() {
        return generatedMap;
    }
}
