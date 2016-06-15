package com.github.ambry.response;

/**
 * @author Zhong.Zewei Create on 2016.06.12.
 */
public class AmbryPostFileResponse extends AmbryBaseResponse{

    /**
     * The resource id returned after save on Ambry.
     */
    private String ambryId;

    public String getAmbryId() {
        return ambryId;
    }

    public void setAmbryId(String ambryId) {
        this.ambryId = ambryId;
    }
}
