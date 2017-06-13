import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.parallel.InputStreamSupplier;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.compress.archivers.zip.ZipArchiveEntryRequest.createZipArchiveEntryRequest;

/**
 * Created by Petr on 13.06.2017.
 */
public class ATest {


    public static void main(String[] args) throws IOException {


        File target = null;

        File scatterFile = null;

        UzasnyVlakno vlakno = new UzasnyVlakno("Vlakno1");
        UzasnyVlakno vlakno2 = new UzasnyVlakno("Vlakno2");

        vlakno.start();
        vlakno2.start();

        try {
            vlakno.join();
            vlakno2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        target = new File("scattertest.zip");
        ZipArchiveOutputStream outputStream = new ZipArchiveOutputStream(target);
        vlakno.getScatterZipOutputStream().writeTo( outputStream);
        vlakno2.getScatterZipOutputStream().writeTo( outputStream);
        outputStream.close();
        vlakno.getScatterZipOutputStream().close();
        vlakno2.getScatterZipOutputStream().close();

        ZipFile zf = new ZipFile(target);
//        final ZipArchiveEntry b_entry = zf.getEntries("vlakno.txt").iterator().next();
//        final ZipArchiveEntry b_entry2 = zf.getEntries("vlakno2.txt").iterator().next();
        zf.close();



    }

    private static InputStreamSupplier createPayloadSupplier(final ByteArrayInputStream payload) {
        return new InputStreamSupplier() {
            public InputStream get() {
                return payload;
            }
        };
    }
}
