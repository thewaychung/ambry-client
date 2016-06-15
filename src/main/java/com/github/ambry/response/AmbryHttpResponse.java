package com.github.ambry.response;

import org.apache.http.Header;
import java.io.InputStream;

/**
 * @author Zhong.Zewei Create on 2016.06.12.
 */
public class AmbryHttpResponse {

    private int code;
    private String status;
    private InputStream content;
    private Header[] headers;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final class Headers {
        // general headers
        /**
         * {@code "Cache-Control"}
         */
        public static final String CACHE_CONTROL = "Cache-Control";
        /**
         * {@code "Content-Length"}
         */
        public static final String CONTENT_LENGTH = "Content-Length";
        /**
         * {@code "Content-Type"}
         */
        public static final String CONTENT_TYPE = "Content-Type";
        /**
         * {@code "Date"}
         */
        public static final String DATE = "Date";
        /**
         * {@code "Expires"}
         */
        public static final String EXPIRES = "Expires";
        /**
         * {@code "Last-Modified"}
         */
        public static final String LAST_MODIFIED = "Last-Modified";
        /**
         * {@code "Location"}
         */
        public static final String LOCATION = "Location";
        /**
         * {@code "Pragma"}
         */
        public static final String PRAGMA = "Pragma";

        // ambry specific headers
        /**
         * mandatory in request; long; size of blob in bytes
         */
        public final static String BLOB_SIZE = "x-ambry-blob-size";
        /**
         * mandatory in request; string; name of service
         */
        public final static String SERVICE_ID = "x-ambry-service-id";
        /**
         * optional in request; date string; default unset ("infinite ttl")
         */
        public final static String TTL = "x-ambry-ttl";
        /**
         * optional in request; 'true' or 'false' case insensitive; default 'false'; indicates private content
         */
        public final static String PRIVATE = "x-ambry-private";
        /**
         * mandatory in request; string; default unset; content type of blob
         */
        public final static String AMBRY_CONTENT_TYPE = "x-ambry-content-type";
        /**
         * optional in request; string; default unset; member id.
         * <p/>
         * Expected usage is to set to member id of content owner.
         */
        public final static String OWNER_ID = "x-ambry-owner-id";
        /**
         * not allowed  in request. Allowed in response only; string; time at which blob was created.
         */
        public final static String CREATION_TIME = "x-ambry-creation-time";
        /**
         * prefix for any header to be set as user metadata for the given blob
         */
        public final static String USER_META_DATA_HEADER_PREFIX = "x-ambry-um-";

        /**
         * Header to contain the Cookies
         */
        public final static String COOKIE = "Cookie";
        /**
         * Header to be set by the clients during a Get blob call to denote, that blob should be served only if the blob
         * has been modified after the value set for this header.
         */
        public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    }
}
