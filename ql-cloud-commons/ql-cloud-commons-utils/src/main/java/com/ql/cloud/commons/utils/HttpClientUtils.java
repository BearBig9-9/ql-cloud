package com.ql.cloud.commons.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @Description:HttpClient类
 *
 * @author:hezhifang
 *
 * @time:2014年11月12日 下午11:32:57
 *
 */
public class HttpClientUtils {

    private final static String DEFAULT_CHARSET = "UTF-8";

    public static int defaultReTryTimes = 3;

    // 得到HttpClient
    /*
     * private HttpClient getHttpClient() { HttpParams mHttpParams = new BasicHttpParams(); //
     * 设置网络链接超时 // 即:Set the timeout in milliseconds until a connection is established.
     * HttpConnectionParams.setConnectionTimeout(mHttpParams, 20 * 1000); // 设置socket响应超时 // 即:in
     * milliseconds which is the timeout for waiting for data.
     * HttpConnectionParams.setSoTimeout(mHttpParams, 20 * 1000); // 设置socket缓存大小
     * HttpConnectionParams.setSocketBufferSize(mHttpParams, 8 * 1024); // 设置是否可以重定向
     * HttpClientParams.setRedirecting(mHttpParams, true); HttpClient httpClient = new
     * DefaultHttpClient(mHttpParams);
     * 
     * return httpClient; }
     */

    private HttpClient getHttpClient(int maxRetryTimes) {
        if (maxRetryTimes < 1){
            maxRetryTimes = defaultReTryTimes;
        }
        HttpParams mHttpParams = new BasicHttpParams();
        // 设置网络链接超时
        // 即:Set the timeout in milliseconds until a connection is established.
        HttpConnectionParams.setConnectionTimeout(mHttpParams, 20 * 1000);
        // 设置socket响应超时
        // 即:in milliseconds which is the timeout for waiting for data.
        HttpConnectionParams.setSoTimeout(mHttpParams, 20 * 1000);
        // 设置socket缓存大小
        HttpConnectionParams.setSocketBufferSize(mHttpParams, 8 * 1024);
        // 设置是否可以重定向
        HttpClientParams.setRedirecting(mHttpParams, true);
        HttpClient httpClient;
        if (maxRetryTimes > 0) {
            CustomHttpRequestRetryHandler retryHandler = new CustomHttpRequestRetryHandler(maxRetryTimes);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient(mHttpParams);
            defaultHttpClient.setHttpRequestRetryHandler(retryHandler);
            httpClient = defaultHttpClient;
//            httpClient = new DefaultHttpClient(mHttpParams).setHttpRequestRetryHandler(retryHandler);
        } else {
            httpClient = new DefaultHttpClient(mHttpParams);
        }


        return httpClient;

    }

    private static HttpClientUtils clientUtils = null;

    /**
     * 单例
     * 
     * @return HttpClientUtils
     */
    public static HttpClientUtils getInstance() {
        if (clientUtils == null) {
            clientUtils = new HttpClientUtils();
        }
        return clientUtils;
    }


    private String charSet = DEFAULT_CHARSET;

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    /**
     * get请求
     * 
     * @param url
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     * @throws Exception
     */
    public String get(String url) throws IOException {
        return this.get(url, 0);
    }

    public String get(String url, int maxRetryTimes) throws IOException {
        return this.get(url, null, maxRetryTimes);

    }


    public String get(String url, Map<String, String> headers) throws IOException {
        return this.get(url, headers, 0);
    }

    public String get(String url, Map<String, String> headers, int maxRetryTimes) throws IOException {
        String result = "";
        HttpClient httpClient = this.getHttpClient(maxRetryTimes);
        HttpGet request = new HttpGet(url);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        HttpResponse httpResponse = httpClient.execute(request);
        // 得到httpResponse的状态响应码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            // 得到httpResponse的实体数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getFirstHeader("Content-Encoding") != null
                    && httpResponse.getFirstHeader("Content-Encoding").getValue().equals("gzip")) {
                result = EntityUtils.toString(new GzipDecompressingEntity(httpEntity), charSet);
            } else {
                result = EntityUtils.toString(httpEntity, charSet);
            }
        } else {
            result = "fail";
        }
        return result;
    }

    /**
     * post请求
     * 
     * @param url
     * @param args
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String post(String url, Map<String, String> args) throws IOException {
        return this.post(url, args, 0);
    }

    public String post(String url, Map<String, String> args, int maxRetryTimes) throws IOException {
        return this.post(this.getHttpClient(maxRetryTimes), url, args);
    }

    public String post(String url, Map<String, String> headers, Map<String, String> args) throws IOException {
        return this.post(url, headers, args, 0);
    }

    public String post(String url, Map<String, String> headers, Map<String, String> args, int maxRetryTimes) throws IOException {
        return this.post(this.getHttpClient(maxRetryTimes), url, headers, args);
    }

    public String post(HttpClient httpClient, String url, Map<String, String> headers, Map<String, String> args)
            throws ClientProtocolException, IOException {
        String result = "";
        HttpPost request = new HttpPost(url);
        List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (args != null) {
            for (Map.Entry<String, String> entry : args.entrySet()) {
                postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                // System.out.print(entry.getValue());
            }
        }
        request.setEntity(new UrlEncodedFormEntity(postData, charSet));
        HttpResponse httpResponse = httpClient.execute(request);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            // 得到httpResponse的实体数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getFirstHeader("Content-Encoding") != null
                    && httpResponse.getFirstHeader("Content-Encoding").getValue().equals("gzip")) {
                result = EntityUtils.toString(new GzipDecompressingEntity(httpEntity), charSet);
            } else {
                result = EntityUtils.toString(httpEntity, charSet);
            }
        } else {
            result = "fail";
        }
        return result;
    }


    public String post(HttpClient httpClient, String url, Map<String, String> args) throws IOException {
        return this.post(httpClient, url, null, args);
        /*
         * String result = ""; HttpPost request = new HttpPost(url); List<BasicNameValuePair>
         * postData = new ArrayList<BasicNameValuePair>(); for (Map.Entry<String, String> entry :
         * args.entrySet()) { postData.add(new BasicNameValuePair(entry.getKey(),
         * entry.getValue())); // System.out.print(entry.getValue()); } request.setEntity(new
         * UrlEncodedFormEntity(postData, charSet)); HttpResponse httpResponse =
         * httpClient.execute(request);
         * 
         * int statusCode = httpResponse.getStatusLine().getStatusCode(); if (statusCode ==
         * HttpStatus.SC_OK) { // 得到httpResponse的实体数据 HttpEntity httpEntity =
         * httpResponse.getEntity(); if (httpResponse.getFirstHeader("Content-Encoding") != null &&
         * httpResponse.getFirstHeader("Content-Encoding").getValue().equals("gzip")) { result =
         * EntityUtils.toString(new GzipDecompressingEntity(httpEntity), charSet); } else { result =
         * EntityUtils.toString(httpEntity, charSet); } } return result;
         */
    }

    /**
     * post json数据
     * 
     * @param url
     * @param jsonData
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String postJsonData(String url, String jsonData) throws IOException {
        return this.postJsonData(url, jsonData, 0);
    }

    public String postJsonData(String url, String jsonData, int maxRetryTimes) throws IOException {
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonData, "UTF-8");
        entity.setContentType("application/json;charset=UTF-8");
        httpPost.setEntity(entity);
        httpPost.setHeader("accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        HttpResponse httpResponse = this.getHttpClient(maxRetryTimes).execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, charSet);
        }
        return result;
    }


    /**
     * httsPost请求
     * 
     * @param url
     * @param args
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String httpsPost(String url, Map<String, String> args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return this.httpsPost(url, args, 0);
    }

    public String httpsPost(String url, Map<String, String> args, int maxRetryTimes) throws IOException, NoSuchAlgorithmException,
            KeyManagementException {
        String result = null;
        // configure the SSLContext with a TrustManager
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
        socketFactory.setHostnameVerifier(new X509HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }

            public void verify(String arg0, SSLSocket arg1) throws IOException {}

            public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}

            public void verify(String arg0, X509Certificate arg1) throws SSLException {}
        });
        // 通过SchemeRegistry将SSLSocketFactory到HttpClient上
        HttpClient httpClient = this.getHttpClient(maxRetryTimes);
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));

        result = this.post(httpClient, url, args);
        return result;
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    /**
     * 获取客户端IP地址
     * */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个路由时，取第一个非unknown的ip
        if (StringUtils.isNotBlank(ip)) {
            final String[] arr = ip.split(",");
            for (final String str : arr) {
                if (!"unknown".equalsIgnoreCase(str)) {
                    ip = str;
                    break;
                }
            }
        }

        return ip;
    }

}
