package com.github.thewaychung.client;

import com.github.thewaychung.http.*;
import com.github.thewaychung.response.*;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a client for Java application to invoke Ambry REST api.
 *
 * @author Zhong.Zewei Create on 2016.06.08.
 */
public class AmbryClient implements IAmbryClient{

    private final String hostname;
    private final int port;
    private final String endpoint;

    private final Logger logger = LoggerFactory.getLogger(AmbryClient.class);

    /**
     * Create an AmbryClient
     * @param hostname the Ambry host to connect to.
     * @param port the port to connect to
     */
    public AmbryClient(String hostname, int port) {
        if (!hostname.contains("://")) {
            this.hostname = "http://" + hostname;
        } else {
            this.hostname = hostname;
        }
        this.port = port;
        this.endpoint = this.hostname + ":" + this.port;
    }

    public AmbryClient(String hostname) {
        this(hostname, 1174);// set with default port
    }

    @Override
    public AmbryBaseResponse healthCheck() {
        AmbryHttpResponse httpResponse = HttpHelper.get(endpoint + "/healthCheck");
        String content = new String(readFromStream(httpResponse.getContent()));
        AmbryBaseResponse response = new AmbryBaseResponse();
        response.setStatus(content);
        response.setCode(httpResponse.getCode());
        response.setMessage(httpResponse.getMessage());
        return response;
    }

    @Override
    public AmbryBlobInfoResponse getFileProperty(String ambryId) {
        AmbryHttpResponse httpResponse = HttpHelper.get(endpoint +"/"+ambryId+"/BlobInfo");
        AmbryBlobInfoResponse response = new AmbryBlobInfoResponse();
        response.setCode(httpResponse.getCode());
        response.setStatus(httpResponse.getStatus());

        Header[] headers = httpResponse.getHeaders();
        if (headers == null) {
            response.setMessage("Get file properties fail.");
            return response;
        }
        for (Header header : headers) {
            switch (header.getName()) {
                case AmbryHttpResponse.Headers.BLOB_SIZE:
                    response.setBlobSize(header.getValue());
                    break;
                case AmbryHttpResponse.Headers.SERVICE_ID:
                    response.setServiceId(header.getValue());
                    break;
                case AmbryHttpResponse.Headers.CREATION_TIME:
                    response.setCreationTime(new Date(header.getValue()));
                    break;
                case AmbryHttpResponse.Headers.PRIVATE:
                    response.setIsPrivate(new Boolean(header.getValue()));
                    break;
                case AmbryHttpResponse.Headers.AMBRY_CONTENT_TYPE:
                    response.setContentType(header.getValue());
                    break;
                case AmbryHttpResponse.Headers.OWNER_ID:
                    response.setOwnerId(header.getValue());
                    break;
                case AmbryHttpResponse.Headers.USER_META_DATA_HEADER_PREFIX+"description":
                    response.setUmDesc(header.getValue());
                    break;
                default:
                    break;
            }
        }
        response.setMessage(httpResponse.getMessage());
        return response;
    }

    @Override
    public AmbryUMResponse getFileUserMetadata(String ambryId) {
        AmbryHttpResponse httpResponse = HttpHelper.get(endpoint +"/"+ambryId+"/UserMetadata");
        AmbryUMResponse response = new AmbryUMResponse();
        response.setCode(httpResponse.getCode());
        response.setStatus(httpResponse.getStatus());
        response.setMessage(httpResponse.getMessage());

        Header[] headers = httpResponse.getHeaders();
        if (headers == null) {
            return response;
        }
        for (Header header : headers) {
            switch (header.getName()) {
                case AmbryHttpResponse.Headers.USER_META_DATA_HEADER_PREFIX+"description":
                    response.setUmDesc(header.getValue());
                    break;
                default:
                    break;
            }
        }
        return response;
    }

    @Override
    public AmbryGetFileResponse getFile(String ambryId) {
        AmbryHttpResponse httpResponse = HttpHelper.get(endpoint +"/"+ambryId);
        AmbryGetFileResponse response = new AmbryGetFileResponse();
        response.setCode(httpResponse.getCode());
        response.setStatus(httpResponse.getStatus());
        response.setMessage(httpResponse.getMessage());
        response.setContent(httpResponse.getContent());

        Header[] headers = httpResponse.getHeaders();
        if (headers == null) {
            return response;
        }
        for (Header header : headers) {
            switch (header.getName()) {
                case AmbryHttpResponse.Headers.BLOB_SIZE:
                    response.setBlobSize(header.getValue());
                    break;
                case AmbryHttpResponse.Headers.AMBRY_CONTENT_TYPE:
                    response.setContentType(header.getValue());
                    break;
                default:
                    break;
            }
        }
        return response;
    }

    @Override
    public AmbryGetFileResponse getFile(String ambryId, File localFile) {
        AmbryGetFileResponse response = getFile(ambryId);
        if (response.getContent() != null) {
            readFromStream(response.getContent(), localFile);
        }
        return response;
    }

    @Override
    public AmbryPostFileResponse postFile(String filePath, String fileType) {
        File file = new File(filePath);
        return postFile(file, fileType);
    }

    @Override
    public AmbryPostFileResponse postFile(File file, String fileType) {
        AmbryPostFileResponse response = new AmbryPostFileResponse();

        if (!file.exists()) {
            response.setCode(404);
            response.setMessage("Local file does not exist: " + file.getPath());
            return response;
        }

        try {
            InputStream inputStream = new FileInputStream(file);
            response = postFile(readFromStream(inputStream), fileType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public AmbryPostFileResponse postFile(byte[] fileBytes, String fileType) {
        AmbryPostFileResponse response = new AmbryPostFileResponse();

        if(fileType==null || fileType=="") {
            fileType = "text/plain";
        }
        AmbryHttpResponse httpResponse = HttpHelper.postFile(endpoint + "/", generateHeaders(fileBytes, fileType), fileBytes);
        response.setCode(httpResponse.getCode());
        response.setStatus(httpResponse.getStatus());

        Header[] headers = httpResponse.getHeaders();
        if (headers == null) {
            response.setMessage("Upload file failure.");
            return response;
        }
        for (Header header : headers) {
            switch (header.getName()) {
                case AmbryHttpResponse.Headers.LOCATION:
                    response.setAmbryId(header.getValue());
                    logger.info("ambry post file get resource id: " + header.getValue());
                    break;
                default:
                    break;
            }
        }
        response.setMessage(httpResponse.getMessage());
        return response;
    }

    @Override
    public AmbryBaseResponse deleteFile(String ambryId) {
        AmbryHttpResponse httpResponse = HttpHelper.delete(hostname + ":" + port + "/" + ambryId);

        AmbryBaseResponse response = new AmbryBaseResponse();
        response.setCode(httpResponse.getCode());
        response.setStatus(httpResponse.getStatus());
        response.setMessage(httpResponse.getMessage());
        return response;
    }

    public byte[] readFromStream(InputStream inputStream) {
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        byte[] readBuff = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(readBuff)) != -1) {
                buff.write(readBuff, 0, len);
            }
            try {
                buff.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            logger.error("Cannot read content from stream: ", e);
            e.printStackTrace();
        } finally {
            try {
                buff.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buff.toByteArray();
    }

    private void readFromStream(InputStream inputStream, File file) {
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] readBuff = new byte[1024];
            int len;
            while ((len = inputStream.read(readBuff)) != -1) {
                outputStream.write(readBuff, 0, len);
            }
        } catch (IOException e) {
            logger.error("Cannot read content from stream: ", e);
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, String> generateHeaders(byte[] fileContent, String fileType) {
        Map<String, String> headers = new HashMap<>();
        headers.put(AmbryHttpResponse.Headers.BLOB_SIZE, fileContent.length+"");
        headers.put(AmbryHttpResponse.Headers.SERVICE_ID, "ambry-client");
        headers.put(AmbryHttpResponse.Headers.AMBRY_CONTENT_TYPE, fileType);
        return headers;
    }
}
