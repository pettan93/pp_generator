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

        scatterFile = File.createTempFile("scattertest", ".notzip");
        ScatterZipOutputStream scatterZipOutputStream = ScatterZipOutputStream.fileBased(scatterFile);
        final byte[] B_PAYLOAD = "RBBBBBBS".getBytes();
        
        ZipArchiveEntry zab = new ZipArchiveEntry("b.txt");
        zab.setMethod(ZipArchiveEntry.DEFLATED);
        final ByteArrayInputStream payload = new ByteArrayInputStream(B_PAYLOAD);
        scatterZipOutputStream.addArchiveEntry(createZipArchiveEntryRequest(zab, createPayloadSupplier(payload)));

        target = new File("scattertest.zip");
        ZipArchiveOutputStream outputStream = new ZipArchiveOutputStream(target);
        scatterZipOutputStream.writeTo( outputStream);
        outputStream.close();
        scatterZipOutputStream.close();

        ZipFile zf = new ZipFile(target);
        final ZipArchiveEntry b_entry = zf.getEntries("b.txt").iterator().next();
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
