import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Petr on 09.05.2017.
 */
public class Zipper {

    /**
     * Creates zip file with the given name
     *
     * @param fileName
     */
    public static void zipFile(String fileName, HashMap<Character, ArrayList> generatedMap) {

        System.out.println("Zipuji..");
        try {
            FileOutputStream f = new FileOutputStream(fileName);
            ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(f));

            for (Character character : generatedMap.keySet()) {
                List<BufferedImage> bufferedImageList = generatedMap.get(character);
                int k = 0;
                for (BufferedImage bufferedImage : bufferedImageList) {
                    zip.putNextEntry(new ZipEntry(character + "/" + k + ".png"));
                    byte[] arrayOfBytes = convertImageToArrayOfBytes(bufferedImage);
                    zip.write(arrayOfBytes, 0, arrayOfBytes.length);
                    zip.closeEntry();
                    k++;
                }
            }

//            int j = 0;
//            for (String captcha : captchas) {
//                BufferedImage bufferedImage;
//                if (upperCase)
//                    bufferedImage = generate(captcha);
//                else
//                    bufferedImage = generate(captcha);
//                byte[] arrayOfBytes = convertImageToArrayOfBytes(bufferedImage);
//                zip.putNextEntry(new ZipEntry("captcha" + j + ".png"));
//                zip.write(arrayOfBytes, 0, arrayOfBytes.length);
//                zip.closeEntry();
//                j++;
//            }

            zip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addZipEntry(ZipOutputStream zip, Task task) {
        zip.putNextEntry(new ZipEntry(task.letter + "/" + k + ".png"));
        byte[] arrayOfBytes = convertImageToArrayOfBytes(bufferedImage);
        zip.write(arrayOfBytes, 0, arrayOfBytes.length);
        zip.closeEntry();
    }

    public static ZipOutputStream startZipping(String fileName) {
        ZipOutputStream zip = null;
        System.out.println("Start zipping");
        try {
            FileOutputStream f = new FileOutputStream(fileName);
            zip = new ZipOutputStream(new BufferedOutputStream(f));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return zip;
    }

    public static void closeZipping(ZipOutputStream zipOutputStream) {
        try {
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectories(String fileName) {

    }

    /**
     * Converts the given bufferedImage to array of bytes
     *
     * @param bufferedImage
     * @return array of bytes
     * @throws IOException
     */
    private static byte[] convertImageToArrayOfBytes(BufferedImage bufferedImage) throws IOException {
        byte[] imageInByte = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return imageInByte;

    }


}
