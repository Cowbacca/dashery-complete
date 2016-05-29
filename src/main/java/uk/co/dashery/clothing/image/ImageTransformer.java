package uk.co.dashery.clothing.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.google.common.collect.ImmutableMap;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

@Component
@Log
public class ImageTransformer {

    private final Cloudinary cloudinary;

    @Inject
    public ImageTransformer(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String transformedUrl(String id, String url) {
        Map<String, String> uploadResult = upload(id, url);
        return uploadResult.get("url");
    }

    private Map<String, String> upload(String id, String url) {
        try {
            log.info("Uploading " + url + " for transformation.");
            return cloudinary.uploader().upload(url, metadata(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map metadata(String id) {
        return ImmutableMap.builder()
                .put("public_id", id)
                .put("transformation", new Transformation().width(1000).height(1000).crop("limit").quality(80))
                .build();
    }
}
