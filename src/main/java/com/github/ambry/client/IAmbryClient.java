package com.github.ambry.client;

import com.github.ambry.response.*;

import java.io.File;

/**
 * This is a client for Java application to invoke Ambry REST api.
 *
 * @author Zhong.Zewei Create on 2016.06.08.
 */
public interface IAmbryClient {

    /**
     * Check the status of the frontend
     * @return the response which contains code and status
     */
    AmbryBaseResponse healthCheck();

    /**
     * Get the file properties represented by the ambryId
     * @param ambryId the file's ambryId
     * @return file's info of the given resource id
     */
    AmbryBlobInfoResponse getFileProperty(String ambryId);

    /**
     * Get the file's user metadata represented by the ambryId
     * @param ambryId the file's ambryId
     * @return file's user metadata of the given resource id
     */
    AmbryUMResponse getFileUserMetadata(String ambryId);

    /**
     * Get the file with the given resource id
     * @param ambryId the file's ambryId
     * @return the file object
     */
    AmbryGetFileResponse getFile(String ambryId);

    /**
     * Get the file with given resource id, and put into the local file object.
     * @param ambryId the resource id
     * @param localFile save the download file as local
     * @return the file object and result
     */
    AmbryGetFileResponse getFile(String ambryId, File localFile);

    /**
     * Upload a file with the file's path.
     * @param filePath file's path at local.
     * @param fileType file type string, e.g.: "text/plain", "image/jpg"
     * @return information with save status and the resource id, i.e. ambryId, response from Amby
     */
    AmbryPostFileResponse postFile(String filePath, String fileType);

    /**
     * Upload a file
     * @param file the file which will be uploaded
     * @param fileType file type string, e.g.: "text/plain", "image/jpg"
     * @return save status and the resource id response from Ambry
     */
    AmbryPostFileResponse postFile(File file, String fileType);

    /**
     * Upload a file with bytes
     * @param fileBytes the file bytes which will be uploaded
     * @param fileType file type string, e.g.: "text/plain", "image/jpg"
     * @return save status and the resource id response from Ambry
     */
    AmbryPostFileResponse postFile(byte[] fileBytes, String fileType);

    /**
     * Delete a file with a given resource Id
     * @param ambryId resource id of the target file
     * @return remove status
     */
    AmbryBaseResponse deleteFile(String ambryId);

}
