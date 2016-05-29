package uk.co.dashery.ingestor.productfeed;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipInputStream;

@Data
@NoArgsConstructor
class ProductFeedForm {

    private boolean usingUrl;
    private String url;

    private MultipartFile file;

    private boolean isAffiliateWindowFormat;

    ProductFeedForm(String url) {
        this.usingUrl = true;
        this.url = url;
    }

    ProductFeedForm(MultipartFile file) {
        this.usingUrl = false;
        this.file = file;
    }

    ProductFeedForm(MultipartFile file, boolean isAffiliateWindowFormat) {
        this.file = file;
        this.isAffiliateWindowFormat = isAffiliateWindowFormat;
    }

    Reader generateReader() throws IOException {
        InputStream csvInputStream;
        if (usingUrl) {
            csvInputStream = getInputStreamRegardlessOfWhetherInZip(new URL(url).openStream());
        } else {
            csvInputStream = getInputStreamRegardlessOfWhetherInZip(file.getInputStream());
        }
        return new BufferedReader(new InputStreamReader(csvInputStream));
    }

    private InputStream getInputStreamRegardlessOfWhetherInZip(InputStream inputStream) throws IOException {
        byte[] buffer = IOUtils.toByteArray(inputStream);
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(buffer));
        if (zipInputStream.getNextEntry() != null) {
            return zipInputStream;
        } else {
            return new ByteArrayInputStream(buffer);
        }
    }
}
