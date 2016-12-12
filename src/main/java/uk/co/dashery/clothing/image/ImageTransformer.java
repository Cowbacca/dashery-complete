package uk.co.dashery.clothing.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.google.common.collect.ImmutableMap;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@Log
public class ImageTransformer {

    private final Cloudinary cloudinary;

    @Inject
    public ImageTransformer(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Optional<String> transformedUrl(String id, String url) {
        try {
            Map<String, String> uploadResult = upload(id, url);
            return Optional.ofNullable(uploadResult.get("url"));
        } catch (Exception e) {
            log.warning("Failed to upload image with url of " + url + " for transformation: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Map<String, String> upload(String id, String url) throws IOException {
            log.info("Uploading " + url + " for transformation.");
            return cloudinary.uploader().upload(url, metadata(id));
    }

    private Map metadata(String id) {
        return ImmutableMap.builder()
                .put("public_id", id)
                .put("transformation", new Transformation().width(1000).height(1000).crop("limit").quality(80))
                .build();
    }
}
