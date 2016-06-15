package com.github.ambry.http;

import com.github.ambry.response.AmbryHttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Zhong.Zewei Create on 2016.06.12.
 */
public class HttpHelper {

    public static AmbryHttpResponse get(String url) {
        return request(url, "GET");
    }

    public static AmbryHttpResponse delete(String url) {
        return request(url, "DELETE");
    }

    public static AmbryHttpResponse postFile(String url, Map<String, String> headers, byte[] content) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ByteArrayEntity entity = new ByteArrayEntity(content);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);

        // set headers
        setHeaders(httpPost, headers);

        AmbryHttpResponse ambryResponse = new AmbryHttpResponse();
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            ambryResponse.setCode(response.getStatusLine().getStatusCode());
            ambryResponse.setStatus(response.getStatusLine().getReasonPhrase());
            ambryResponse.setContent(responseEntity.getContent());
            ambryResponse.setHeaders(response.getAllHeaders());
            ambryResponse.setMessage("Success");
        } catch (IOException e) {
            e.printStackTrace();
            ambryResponse.setCode(404);
            ambryResponse.setMessage("Request error.");
        }
        return ambryResponse;
    }

    private static AmbryHttpResponse request(String url, String methodType) {
        AmbryHttpResponse ambryResponse = new AmbryHttpResponse();

        HttpRequestBase httpRequest;
        switch (methodType) {
            case "HEAD":
                httpRequest = new HttpHead(url);
                break;
            case "GET":
                httpRequest = new HttpGet(url);
                break;
            case "DELETE":
                httpRequest = new HttpDelete(url);
                break;
            default:
                ambryResponse.setCode(500);
                ambryResponse.setMessage("Http request method error.");
                return ambryResponse;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response;
        try {
            // not close the stream
            response = httpClient.execute(httpRequest);
            HttpEntity entity = response.getEntity();

            ambryResponse.setCode(response.getStatusLine().getStatusCode());
            ambryResponse.setStatus(response.getStatusLine().getReasonPhrase());
            ambryResponse.setContent(entity.getContent());
            ambryResponse.setHeaders(response.getAllHeaders());
            ambryResponse.setMessage("Success");
            return ambryResponse;
        } catch (IOException e) {
            e.printStackTrace();
            ambryResponse.setCode(404);
            ambryResponse.setMessage("Request error.");
        }
        return ambryResponse;
    }

    private static void setHeaders(HttpMessage httpMessage, Map<String, String> headers) {
        if (headers != null) {
            Iterator<Map.Entry<String, String>> entries = headers.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                httpMessage.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }
}
