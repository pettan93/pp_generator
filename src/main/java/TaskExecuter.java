import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;

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

    public static ScatterZipOutputStream zip;


    public static Task executeTask(Task pickedTask) {

        if (pickedTask.data == null)
            return new Task(pickedTask.letter, pickedTask.number, ImageGenerator.generateFromTask(pickedTask));

        if (pickedTask.data != null)
            Zipper.addZipEntry(, pickedTask);

        return null;
    }

    public static ScatterZipOutputStream getZip() {
        return zip;
    }

}
