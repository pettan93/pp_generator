import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipOutputStream;

/**
 * Created by admin on 12.06.2017.
 */
public class TaskExecuter {

    private static ZipOutputStream zip;


    public static Task executeTask(Task pickedTask) {

        if(pickedTask.data == null)
            return new Task(pickedTask.letter,pickedTask.number,ImageGenerator.generateFromTask(pickedTask));

        if(pickedTask.data != null)
            Zipper.addZipEntry(zip, pickedTask);

        return null;
    }

    public static ZipOutputStream getZip() {
        return zip;
    }

    public static void setZip(ZipOutputStream zip) {
        zip = zip;
    }
}
