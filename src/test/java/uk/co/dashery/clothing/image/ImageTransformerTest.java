package uk.co.dashery.clothing.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.Uploader;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ImageTransformerTest {

    public static final String IMAGE_URL = "url";
    public static final String TRANSFORMED_URL = "transformedUrl";
    public static final String ID = "id";
    @Mock
    private Cloudinary cloudinary;
    @Mock
    private Uploader uploader;
    @InjectMocks
    private ImageTransformer imageTransformer;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    public void testReturnsUrlToTransformedImage() throws IOException {
        givenAResultsMapWithAUrlValueOf(TRANSFORMED_URL);

        String transformedUrl = imageTransformer.transformedUrl(ID, IMAGE_URL).get();

        assertThat(transformedUrl, is(TRANSFORMED_URL));
    }

    private void givenAResultsMapWithAUrlValueOf(String transformedUrl) throws IOException {
        ImmutableMap<Object, Object> resultsMap = ImmutableMap.builder().put("url", transformedUrl).build();
        when(uploader.upload(eq(IMAGE_URL), any())).thenReturn(resultsMap);
    }

    @Test
    public void testUsesIdAsPublicId() throws IOException {
        givenAResultsMapWithAUrlValueOf(TRANSFORMED_URL);
        imageTransformer.transformedUrl(ID, TRANSFORMED_URL);

        Object publicId = captureMetadataMapValueWithKeyOf("public_id");

        assertThat(publicId, is(ID));
    }

    private Object captureMetadataMapValueWithKeyOf(String key) throws IOException {
        ArgumentCaptor<Map> metadataCaptor = ArgumentCaptor.forClass(Map.class);
        verify(uploader).upload(any(String.class), metadataCaptor.capture());
        return metadataCaptor.getValue().get(key);
    }

    @Test
    public void testTransformsImagesTo1000Width() throws IOException {
        givenAResultsMapWithAUrlValueOf(TRANSFORMED_URL);

        imageTransformer.transformedUrl(ID, TRANSFORMED_URL);

        assertThatTransformationContains("w_1000");
    }

    @Test
    public void testTransformsImagesTo1000Height() throws IOException {
        givenAResultsMapWithAUrlValueOf(TRANSFORMED_URL);

        imageTransformer.transformedUrl(ID, TRANSFORMED_URL);

        assertThatTransformationContains("h_1000");
    }

    @Test
    public void testCropsImagesToLimit() throws IOException {
        givenAResultsMapWithAUrlValueOf(TRANSFORMED_URL);

        imageTransformer.transformedUrl(ID, TRANSFORMED_URL);

        assertThatTransformationContains("c_limit");
    }

    @Test
    public void testUses80PercentQualityForImageTransformations() throws IOException {
        imageTransformer.transformedUrl(ID, TRANSFORMED_URL);

        assertThatTransformationContains("q_80");
    }

    private void assertThatTransformationContains(String value) throws IOException {
        Transformation transformation = (Transformation) captureMetadataMapValueWithKeyOf("transformation");
        assertThat(transformation.generate(), containsString(value));
    }

    @Test
    public void testReturnsEmptyOptionalOnException() throws IOException {
        when(uploader.upload(eq(IMAGE_URL), any())).thenThrow(new RuntimeException());

        Optional<String> transformedUrl = imageTransformer.transformedUrl(ID, TRANSFORMED_URL);

        assertFalse(transformedUrl.isPresent());
    }


}