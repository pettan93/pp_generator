import org.apache.commons.compress.archivers.zip.ScatterZipOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.parallel.InputStreamSupplier;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.compress.archivers.zip.ZipArchiveEntryRequest.createZipArchiveEntryRequest;

/**
 * Created by admin on 13.06.2017.
 */
public class UzasnyVlakno extends Thread {
    private String threadName;
    File scatterFile = null;
    ScatterZipOutputStream scatterZipOutputStream;

    UzasnyVlakno(String threadName) {
        this.threadName = threadName;
    }

    public void run(){
        try {
            scatterFile = File.createTempFile("scattertest", ".notzip");
            scatterZipOutputStream = ScatterZipOutputStream.fileBased(scatterFile);
            final byte[] B_PAYLOAD = threadName.getBytes();

            ZipArchiveEntry zab = new ZipArchiveEntry(threadName + ".txt");
            zab.setMethod(ZipArchiveEntry.DEFLATED);
            final ByteArrayInputStream payload = new ByteArrayInputStream(B_PAYLOAD);
            scatterZipOutputStream.addArchiveEntry(createZipArchiveEntryRequest(zab, createPayloadSupplier(payload)));
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

    public ScatterZipOutputStream getScatterZipOutputStream() {
        return scatterZipOutputStream;
    }

    public void setScatterZipOutputStream(ScatterZipOutputStream scatterZipOutputStream) {
        this.scatterZipOutputStream = scatterZipOutputStream;
    }
}
