import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.apache.commons.compress.archivers.zip.ZipArchiveEntryRequest.createZipArchiveEntryRequest;

/**
 * Created by Petr on 09.05.2017.
 */
public class Zipper {

    static ZipArchiveOutputStream zipArchiveOutputStream = null;

    /**
     * Creates zip tempFile with the given name
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

    /**
     * Adds a new image to the zip file. Each letter has its own folder.
     * @param scatterZipOutputStream
     * @param task
     */
    public static void addZipEntry(ScatterZipOutputStream scatterZipOutputStream, Task task) {

        try {
            final byte[] data = convertImageToArrayOfBytes(task.data);
            ZipArchiveEntry zab = new ZipArchiveEntry(task.letter + "/" + task.number + ".png");
            zab.setMethod(ZipArchiveEntry.DEFLATED);
            final ByteArrayInputStream payload = new ByteArrayInputStream(data);
            scatterZipOutputStream.addArchiveEntry(createZipArchiveEntryRequest(zab, createPayloadSupplier(payload)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start zipping with the given fileName
     * @param fileName
     */
    public static void startZipping(String fileName) {

        File newZip = new File(fileName);
        System.out.println("Start zipping");
        try {
            zipArchiveOutputStream = new ZipArchiveOutputStream(newZip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close created zipping
     */
    public static void closeZipping() {
        System.out.println("Close zipping");

        try {
            zipArchiveOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InputStreamSupplier createPayloadSupplier(final ByteArrayInputStream payload) {
        return new InputStreamSupplier() {
            public InputStream get() {
                return payload;
            }
        };
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
