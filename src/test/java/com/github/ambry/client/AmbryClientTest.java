package com.github.ambry.client;

import com.github.ambry.response.AmbryBaseResponse;
import com.github.ambry.response.AmbryBlobInfoResponse;
import com.github.ambry.response.AmbryGetFileResponse;
import com.github.ambry.response.AmbryPostFileResponse;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Test AmbryClient methods
 * @author Zhong.Zewei Create on 2016.06.08.
 */
public class AmbryClientTest {

    AmbryClient ambryClient;

    @Before
    public void setUp() {
        ambryClient = new AmbryClient("localhost");
    }

    @After
    public void tearDown() {
        ambryClient = null;
    }

    @Test
    public void testHealthCheck() {
        AmbryBaseResponse response = ambryClient.healthCheck();
        Assert.assertEquals(response.getStatus(), "GOOD");
    }

    @Test
    public void testPostFileAndGetFileProperty() {
        int num = new Random().nextInt(1024);
        byte[] content = getRandomBytes(num);
        AmbryPostFileResponse postResponse = ambryClient.postFile(content, "test/text");
        Assert.assertNotNull(postResponse.getAmbryId());

        String ambryId = postResponse.getAmbryId();
        AmbryBlobInfoResponse response = ambryClient.getFileProperty(ambryId);
        Assert.assertEquals(response.getBlobSize(), num +"");
        Assert.assertEquals(response.getContentType(), "test/text");
    }

    @Test
    public void testGetFile() {
        int num = new Random().nextInt(1024);
        byte[] content = getRandomBytes(num);
        AmbryPostFileResponse postResponse = ambryClient.postFile(content, "image/gif");

        String ambryId = postResponse.getAmbryId();
        AmbryGetFileResponse response = ambryClient.getFile(ambryId);
        byte[] data = ambryClient.readFromStream(response.getContent());


        Assert.assertEquals(content.length, data.length);
        for (int i = 0; i<content.length; i++) {
            Assert.assertEquals(content[i], data[i]);
        }
    }

    @Test
    public void testDeleteFile() {
        int num = new Random().nextInt(1024);
        byte[] content = getRandomBytes(num);
        AmbryPostFileResponse postResponse = ambryClient.postFile(content, "image/gif");

        String ambryId = postResponse.getAmbryId();
        AmbryBaseResponse delResponse = ambryClient.deleteFile(ambryId);
        Assert.assertEquals(delResponse.getCode(), 202);

    }

    private static byte[] getRandomBytes(int size) {
        byte[] bytes = new byte[size];
        new Random().nextBytes(bytes);
        return bytes;
    }
}
