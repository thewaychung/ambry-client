package com.github.ambry.response;

import java.io.InputStream;

/**
 * @author Zhong.Zewei Create on 2016.06.12.
 */
public class AmbryGetFileResponse extends AmbryBaseResponse{

    private String blobSize;
    private String contentType;

    /**
     * The file object get from Ambry.
     */
    private InputStream content;

    public String getBlobSize() {
        return blobSize;
    }

    public void setBlobSize(String blobSize) {
        this.blobSize = blobSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }
}
