package com.github.thewaychung.response;

import java.util.Date;

/**
 * @author Zhong.Zewei Create on 2016.06.12.
 */
public class AmbryBlobInfoResponse extends AmbryBaseResponse {

    private String blobSize;
    private String serviceId;
    private Date creationTime;
    private boolean isPrivate;
    private String contentType;
    private String ownerId;
    private String umDesc;

    public String getUmDesc() {
        return umDesc;
    }

    public void setUmDesc(String umDesc) {
        this.umDesc = umDesc;
    }

    public String getBlobSize() {
        return blobSize;
    }

    public void setBlobSize(String blobSize) {
        this.blobSize = blobSize;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

}
