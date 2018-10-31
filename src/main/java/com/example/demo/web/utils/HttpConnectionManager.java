//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.demo.web.utils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

final class HttpConnectionManager {
    private static final int MAX_TOTAL_CONNECTIONS = 800;
    private static final int MAX_ROUTE_CONNECTIONS = 20;
    private static final int CONNECT_TIMEOUT = 20000;
    private static final int SOCKET_TIMEOUT = 60000;
    private static final long CONN_MANAGER_TIMEOUT = 0L;
    private static final HttpHost DEFAULT_TARGETHOST = new HttpHost("https://api.weixin.qq.com", 443);
    private static HttpParams parentParams;
    private static PoolingClientConnectionManager cm;
    private static DefaultHttpClient httpClient;

    HttpConnectionManager() {
    }

    public static final SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init((KeyManager[]) null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());
            return new SSLSocketFactory(ctx);
        } catch (Exception var1) {
            return null;
        }
    }

    protected static HttpClient getHttpClient() {
        return httpClient;
    }

    protected static PoolingClientConnectionManager getCm() {
        return cm;
    }

    static {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, getSSLSocketFactory()));
        cm = new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(800);
        cm.setDefaultMaxPerRoute(20);
        cm.setMaxPerRoute(new HttpRoute(DEFAULT_TARGETHOST), 200);
        parentParams = new BasicHttpParams();
        parentParams.setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        parentParams.setParameter("http.useragent", "weijing");
        parentParams.setParameter("http.protocol.content-charset", "UTF-8");
        parentParams.setParameter("http.default-host", DEFAULT_TARGETHOST);
        parentParams.setParameter("http.protocol.cookie-policy", "compatibility");
        parentParams.setParameter("http.conn-manager.timeout", 0L);
        parentParams.setParameter("http.protocol.allow-circular-redirects", true);
        parentParams.setParameter("http.protocol.handle-redirects", true);
        parentParams.setParameter("http.socket.timeout", '\uea60');
        parentParams.setParameter("http.connection.timeout", 20000);
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            if (executionCount >= 3) {
                return false;
            } else if (exception instanceof NoHttpResponseException) {
                return true;
            } else if (exception instanceof SSLHandshakeException) {
                return false;
            } else {
                HttpRequest request = (HttpRequest) context.getAttribute("http.request");
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                return idempotent;
            }
        };
        httpClient = new DefaultHttpClient(cm, parentParams);
        httpClient.setHttpRequestRetryHandler(httpRequestRetryHandler);
    }
}
