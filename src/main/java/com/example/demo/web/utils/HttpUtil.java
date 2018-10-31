//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.demo.web.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public final class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        @Override
        public String handleResponse(HttpResponse response) throws IOException {
            HttpEntity entity = response.getEntity();
            Header header = entity.getContentEncoding();
            String content = null;
            if (entity != null) {
                if (entity.getContentLength() < 2147483647L) {
                    if (header != null && "gzip".equals(header.getValue())) {
                        content = EntityUtils.toString(new GzipDecompressingEntity(entity));
                    } else {
                        content = EntityUtils.toString(entity);
                    }
                } else {
                    InputStream in = entity.getContent();
                    if (header != null && "gzip".equals(header.getValue())) {
                        content = HttpUtil.unZip(in, ContentType.getOrDefault(entity).getCharset().toString());
                    } else {
                        content = HttpUtil.readInStreamToString(in, ContentType.getOrDefault(entity).getCharset().toString());
                    }

                    if (in != null) {
                        in.close();
                    }
                }
            }

            return content;
        }
    };

    public HttpUtil() {
    }

    public static String get(String uri) {
        Assert.notNull(uri, "uri can not be null");
        return get(uri, (Map) null);
    }

    public static String get(String uri, Map<String, ?> params) {
        Assert.notNull(uri, "uri can not be null");
        return request(HttpUtil.HttpMethod.GET, uri, params);
    }

    public static <T> T getJSON(String uri, Class<T> clazz) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(clazz, "clazz can not be null");
        String json = get(uri, (Map) null);
        return JsonBinder.fromJson(json, clazz);
    }

    public static <T> T getJSON(String uri, TypeReference<T> valueTypeRef) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(valueTypeRef, "valueTypeRef can not be null");
        String json = get(uri, (Map) null);
        return JsonBinder.fromJson(json, valueTypeRef);
    }

    public static <T> T getJSON(String uri, Map<String, ?> params, Class<T> clazz) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(params, "params can not be null");
        Assert.notNull(clazz, "clazz can not be null");
        String json = get(uri, params);
        return JsonBinder.fromJson(json, clazz);
    }

    public static <T> T getJSON(String uri, Map<String, ?> params, TypeReference<T> valueTypeRef) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(params, "params can not be null");
        Assert.notNull(valueTypeRef, "valueTypeRef can not be null");
        String json = get(uri, params);
        return JsonBinder.fromJson(json, valueTypeRef);
    }

    public static String post(String uri, Map<String, ?> params) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(params, "params can not be null");
        return request(HttpUtil.HttpMethod.POST, uri, (Map) params, (Map) null);
    }

    public static <T> T postJSON(String uri, Map<String, ?> params, Class<T> clazz) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(params, "params can not be null");
        Assert.notNull(clazz, "clazz can not be null");
        String json = post(uri, params);
        return JsonBinder.fromJson(json, clazz);
    }

    public static <T> T postJSON(String uri, Map<String, ?> params, TypeReference<T> valueTypeRef) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(params, "params can not be null");
        Assert.notNull(valueTypeRef, "valueTypeRef can not be null");
        String json = post(uri, params);
        return JsonBinder.fromJson(json, valueTypeRef);
    }

    public static <T> T postRawJSON(String uri, Map<String, ?> params, String rawBody, TypeReference<T> valueTypeRef) {
        Assert.notNull(uri, "uri can not be null");
        Assert.notNull(params, "params can not be null");
        Assert.notNull(rawBody, "rawBody can not be null");
        Assert.notNull(valueTypeRef, "valueTypeRef can not be null");
        HttpPost httpPost = new HttpPost(buildByParam(uri, params));
        String json = null;

        StringEntity entity;
        try {
            HttpClient client = HttpConnectionManager.getHttpClient();
            entity = new StringEntity(rawBody, DEFAULT_CHARSET);
            httpPost.setEntity(entity);
            json = (String) client.execute(httpPost, responseHandler);
            return JsonBinder.fromJson(json, valueTypeRef);
        } catch (ClientProtocolException var12) {
            logger.error("request error uri=" + uri, var12);
            entity = null;
        } catch (IOException var13) {
            logger.error("request error uri=" + uri, var13);
            entity = null;
            return (T) entity;
        } finally {
            abortConn(httpPost);
        }

        return (T) entity;
    }

    public static String request(HttpUtil.HttpMethod httpMethod, String uri, Map<String, ?> params) {
        return request(httpMethod, uri, (Map) params, (Map) null);
    }

    public static String request(HttpUtil.HttpMethod httpMethod, String uri, String cookie, Map<String, ?> params) {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("cookie", cookie);
        return request(httpMethod, uri, (Map) params, headers);
    }

    public static String request(HttpUtil.HttpMethod httpMethod, String uri, Map<String, ?> params, Map<String, String> headers) {
        return request(httpMethod, uri, params, headers, DEFAULT_CHARSET);
    }

    public static String request(HttpUtil.HttpMethod httpMethod, String uri, Map<String, ?> params, Map<String, String> headers, Charset charset) {
        HttpUriRequest request = createHttpUriRequest(httpMethod, uri, params, charset);
        if (headers != null && headers.size() > 0) {
            Iterator var6 = headers.entrySet().iterator();

            while (var6.hasNext()) {
                Entry<String, String> entry = (Entry) var6.next();
                request.addHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }

        HttpClient client = HttpConnectionManager.getHttpClient();

        try {
            String var16 = (String) client.execute(request, responseHandler);
            return var16;
        } catch (ClientProtocolException var12) {
            logger.error("request error uri=" + uri, var12);
        } catch (IOException var13) {
            logger.error("request error uri=" + uri, var13);
        } finally {
            abortConn(request);
        }

        return null;
    }

    private static HttpUriRequest createHttpUriRequest(HttpUtil.HttpMethod httpMethod, String uri, Map<String, ?> params, Charset charset) {
        switch (httpMethod) {
            case GET:
                return new HttpGet(buildByParam(uri, params, charset));
            case DELETE:
                return new HttpDelete(uri);
            case HEAD:
                return new HttpHead(uri);
            case OPTIONS:
                return new HttpOptions(uri);
            case POST:
                return buildByParam((HttpEntityEnclosingRequestBase) (new HttpPost(uri)), params);
            case PUT:
                return new HttpPut(uri);
            case TRACE:
                return new HttpTrace(uri);
            default:
                throw new IllegalArgumentException("Invalid HTTP method: " + httpMethod);
        }
    }

    private static String unZip(InputStream in, String charset) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream gis = null;

        try {
            gis = new GZIPInputStream(in);
            byte[] byteArr = new byte[1024];
            boolean var5 = false;

            int len;
            while ((len = gis.read(byteArr)) != -1) {
                baos.write(byteArr, 0, len);
            }

            String unzipString = new String(baos.toByteArray(), charset);
            String var7 = unzipString;
            return var7;
        } finally {
            if (gis != null) {
                gis.close();
            }

            if (baos != null) {
                baos.close();
            }

        }
    }

    private static String readInStreamToString(InputStream in, String charset) throws IOException {
        StringBuilder str = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, charset));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            str.append(line);
            str.append("\n");
        }

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        return str.toString();
    }

    public static String buildByParam(String url, Map<String, ?> params) {
        return buildByParam(url, params, DEFAULT_CHARSET);
    }

    public static String buildByParam(String url, Map<String, ?> params, Charset charset) {
        if (params != null && params.size() > 0) {
            List<NameValuePair> qparams = getParamsList(params);
            if (qparams != null && qparams.size() > 0) {
                String formatParams = URLEncodedUtils.format(qparams, charset);
                url = url + (url.indexOf("?") < 0 ? "?" : "&") + formatParams;
            }
        }

        return url;
    }

    public static HttpEntityEnclosingRequestBase buildByParam(HttpEntityEnclosingRequestBase request, Map<String, ?> params) {
        return buildByParam(request, params, DEFAULT_CHARSET);
    }

    public static HttpEntityEnclosingRequestBase buildByParam(HttpEntityEnclosingRequestBase request, Map<String, ?> params, Charset charset) {
        if (params != null && params.size() > 0) {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.setCharset(charset);
            params.forEach((k, v) -> {
                if (v instanceof File) {
                    entityBuilder.addBinaryBody(k, (File) v);
                } else {
                    entityBuilder.addPart(k, new StringBody(v.toString(), ContentType.create("text/plain", DEFAULT_CHARSET)));
                }

            });
            request.setEntity(entityBuilder.build());
        }

        return request;
    }

    private static List<NameValuePair> getParamsList(Map<String, ?> paramsMap) {
        List<NameValuePair> params = new ArrayList();
        paramsMap.forEach((k, v) -> {
            params.add(new BasicNameValuePair(k, v.toString()));
        });
        return params;
    }

    private static void abortConn(HttpUriRequest hrb) {
        if (hrb != null) {
            hrb.abort();
        }

    }

    public static void main(String[] args) {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("App-Key", "38729696CDB9971CE0530A769F1B071C");
        headers.put("App-Secrety", "B233F0895572F54247CE1567D724CD99");
        String uploadUrl = "http://192.168.1.90/open-platform/api/file/upload.json";
        Map<String, Object> params1 = Maps.newHashMap();
        params1.put("file", new File("D:\\statics\\img\\2016\\01\\05\\14\\b2c9cfc79b264efea012c8228e7dc55d.jpg"));
        System.out.println(request(HttpUtil.HttpMethod.POST, "http://192.168.1.90/open-platform/api/file/upload.json", (Map) params1, headers));
        String clueUrl = "http://192.168.1.90/open-platform/api/out/clue.json";
        Map<String, Object> params2 = Maps.newHashMap();
        params2.put("extend1", "中文测试1");
        params2.put("extend2", "中文测试中文测试中文测试中文测试1");
        System.out.println(request(HttpUtil.HttpMethod.POST, "http://192.168.1.90/open-platform/api/out/clue.json", (Map) params2, headers));
    }

    public static enum HttpMethod {
        GET,
        POST,
        HEAD,
        OPTIONS,
        PUT,
        DELETE,
        TRACE;

        private HttpMethod() {
        }
    }
}
