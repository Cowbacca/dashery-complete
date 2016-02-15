package uk.co.dashery.ingestor.productfeed;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipInputStream;

@Data
@NoArgsConstructor
public class ProductFeedForm {

    private boolean usingUrl;
    private String url;

    private MultipartFile file;

    private boolean isAffiliateWindowFormat;

    public ProductFeedForm(String url) {
        this.usingUrl = true;
        this.url = url;
    }

    public ProductFeedForm(MultipartFile file) {
        this.usingUrl = false;
        this.file = file;
    }

    public ProductFeedForm(MultipartFile file, boolean isAffiliateWindowFormat) {
        this.file = file;
        this.isAffiliateWindowFormat = isAffiliateWindowFormat;
    }

    public Reader generateReader() throws IOException {
        InputStream csvInputStream;
        if (usingUrl) {
            csvInputStream = getInputStreamRegardlessOfWhetherInZip(new URL(url).openStream());
        } else {
            csvInputStream = getInputStreamRegardlessOfWhetherInZip(file.getInputStream());
        }
        return new BufferedReader(new InputStreamReader(csvInputStream));
    }

    private InputStream getInputStreamRegardlessOfWhetherInZip(InputStream inputStream) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        if (zipInputStream.getNextEntry() != null) {
            return zipInputStream;
        } else {
            return inputStream;
        }
    }
}
