package com.eoemusic.eoebackend.utils;

import com.eoemusic.eoebackend.domain.ResResult;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.SSLException;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 10/03/23 2:53 AM
 */
public final class HttpUtil {

  private final static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpUtil.class);
  private static RequestConfig requestConfig = null;//设置请求和传输超时时间
  private final static String boundary = "@_#_$_%";
  private final static Charset utf8Charset = Charset.forName("UTF-8");
  private final static ContentType bossContentType = ContentType
      .create("multipart/form-data", "UTF-8");
  private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

  public static void init(int socketTimeout, int connectTimeout) throws Exception {
    logger.info("---" + connectTimeout);
    requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).
        setConnectTimeout(connectTimeout).build();
  }

  //根据不同渠道不同客户端证书
  protected static <T extends HttpRequestBase> ResResult doRequest(T request) {
    CloseableHttpClient httpClient;
    httpClient = HttpClients.createDefault();
    ResResult resResult = null;
    try {
      request.setConfig(requestConfig);
      resResult = httpClient.execute(request, response -> {
        return new ResResult(
            response.getEntity() == null ? null : EntityUtils.toString(response.getEntity()),
            response.getStatusLine().getStatusCode());
      });
    } catch (ConnectTimeoutException e) {
      logger.error("1 url:{} exception:{}", request.getURI(), e);
    } catch (SocketTimeoutException e) {

      logger.error("2 url:{} exception:{}", request.getURI(), e);
    } catch (HttpHostConnectException e) {

      logger.error("3 url:{} exception:{}", request.getURI(), e);
    } catch (SSLException e) {

      logger.error("4 url:{} exception:{}", request.getURI(), e);
    } catch (UnknownHostException e) {

      logger.error("5 url:{} exception:{}", request.getURI(), e);
    } catch (IOException e) {
      
      logger.error("6 url:{} exception:{}", request.getURI(), e);
    } finally {
      try {
        httpClient.close();
      } catch (IOException e) {
        logger.error("doRequest:", e);
      }
    }
    return resResult;
  }

  public static ResResult doPostJson(String url, String data, String appKey) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
    httpPost.setHeader("appKey", appKey);
    return doRequest(httpPost);
  }

  public static ResResult doPostJsonForOSPB017(String url, String data, String uniChannelId,
      String provinceCode, String areaCode) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
    return doRequest(httpPost);
  }

  public static ResResult doPostJson(String url, String data) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));

    return doRequest(httpPost);
  }

  public static ResResult doPost(URI url) {
    if (StringUtils.isEmpty(url) || StringUtils.isEmpty(url.getHost())) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    return doRequest(httpPost);
  }


  //有参数的 POST
  public static ResResult doPost(URI url, String paramName, String paramValue) throws Exception {
    if (StringUtils.isEmpty(url) || StringUtils.isEmpty(url.getHost())) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    List nvps = new ArrayList();
    nvps.add(new BasicNameValuePair(paramName, paramValue));
    httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

    return doRequest(httpPost);
  }

  public static ResResult doChannelPostRepeat(String url, String jsonParas, int tryCount) {

    ResResult resResult = doChannelPost(url, jsonParas);
    if (tryCount > 0) {
      if (resResult.getStatus() != HttpStatus.SC_OK) {
        for (int i = 0; i < tryCount; i++) {
          logger.info("try..." + i + "  url:" + url);
          resResult = doChannelPost(url, jsonParas);
          if (resResult.getStatus() == HttpStatus.SC_OK) {
            break;
          }
        }
      }
    }
    return resResult;
  }

  public static ResResult doChannelProvincePost(String url, String channelId, String jsonParas,
      String provinceCode, String apiCode, String logApiCode) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    httpPost.setHeader("domain", "COP");
    httpPost.setHeader("apicode", apiCode);
    httpPost.setHeader("routevalue", provinceCode);
    httpPost.setHeader("appid", "CMCOP");
    httpPost.setHeader("accesschannel", channelId);
    httpPost.setHeader("messageid", UUID.randomUUID().toString().replace("-", ""));
    httpPost.setHeader("timestamp",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss")));
    httpPost.setHeader("version", "v1");
    httpPost.setHeader("sign", "CMCOP");
    httpPost.setHeader("channelId", channelId);
    ResResult resResult = doRequest(httpPost);
    return resResult;
  }

  public static ResResult doChannelProvincePost(String url, String channelId, String jsonParas,
      String provinceCode, String appKey, String apiCode, String sessionId, String u) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    httpPost.setHeader("domain", "COP");
    httpPost.setHeader("apicode", apiCode);
    httpPost.setHeader("routevalue", provinceCode);
    httpPost.setHeader("appid", "CMCOP");
    httpPost.setHeader("accesschannel", channelId);
    httpPost.setHeader("messageid", UUID.randomUUID().toString().replace("-", ""));
    httpPost.setHeader("timestamp",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMddHHmmss")));
    httpPost.setHeader("version", "v1");
    httpPost.setHeader("sign", "CMCOP");
    httpPost.setHeader("channelId", channelId);
    httpPost.setHeader("appKey", appKey);
    httpPost.setHeader("sessionId", sessionId);
    httpPost.setHeader("centerName", u);
    return doRequest(httpPost);
  }

  public static ResResult doChannelPost(String url, String jsonParas) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    return doRequest(httpPost);
  }

  public static ResResult doChannelPost(String url, Map<String, String> headers, String channelId,
      String jsonParas) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);

    if (null != headers) {
      headers.entrySet().forEach(entry -> httpPost.setHeader(entry.getKey(), entry.getValue()));
      //headers.entrySet().forEach(entry -> logger.info("url:"+url+"  headers:"+entry.getKey()+""+entry.getValue()));
    }

    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    return doRequest(httpPost);
  }

  public static ResResult doChannelPostHaveHeader(String url, String channelId, String provinceCode,
      String jsonParas, String appKey) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    httpPost.setHeader("channelId", channelId);
    if (!StringUtils.isEmpty(provinceCode)) {
      httpPost.setHeader("provinceCode", provinceCode);
    }
    httpPost.setHeader("appKey", appKey);
    return doRequest(httpPost);
  }

  public static ResResult doChannelPostHaveHeaderSessionId(String url, String channelId,
      String provinceCode, String jsonParas, String appKey, String sessionId) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    httpPost.setHeader("channelId", channelId);
    if (!StringUtils.isEmpty(provinceCode)) {
      httpPost.setHeader("provinceCode", provinceCode);
    }
    httpPost.setHeader("appKey", appKey);
    if (!StringUtils.isEmpty(sessionId)) {
      httpPost.setHeader("sessionId", sessionId);
    }
    return doRequest(httpPost);
  }

  public static ResResult doChannelPostHaveHeader(String url, String channelId, String jsonParas) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    httpPost.setHeader("channelId", channelId);
    return doRequest(httpPost);
  }

  public static ResResult doPost(String url, Map<String, String> headers, String jsonParas) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    if (null != headers) {
      headers.entrySet().forEach(entry -> httpPost.setHeader(entry.getKey(), entry.getValue()));
    }
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    return doRequest(httpPost);
  }

  public static ResResult doPost(String url, Map<String, String> headers, String jsonParas,
      String feedbackUrl) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    if (null != headers) {
      headers.entrySet().forEach(entry -> httpPost.setHeader(entry.getKey(), entry.getValue()));
    }
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    httpPost.setHeader("feedbackUrl", feedbackUrl);
    return doRequest(httpPost);
  }

  public static ResResult doGet(URI url) {
    if (StringUtils.isEmpty(url) || StringUtils.isEmpty(url.getHost())) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpGet httpget = new HttpGet(url);
    return doRequest(httpget);
  }

  public static ResResult doGet(URI url, Map<String, String> headers) {
    if (StringUtils.isEmpty(url) || StringUtils.isEmpty(url.getHost())) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpGet httpget = new HttpGet(url);
    return doRequest(httpget);
  }


  public static ResResult doChannelPostHaveHeader2(String url, String channelId, String apiCode,
      String jsonParas, String appKey) {
    if (StringUtils.isEmpty(url)) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpPost httpPost = new HttpPost(url);
    StringEntity entity = new StringEntity(jsonParas, "utf-8");//解决中文乱码问题
    entity.setContentEncoding("UTF-8");
    entity.setContentType("application/json");
    httpPost.setEntity(entity);
    httpPost.setHeader("channelId", channelId);
    httpPost.setHeader("apicode", apiCode);
    httpPost.setHeader("appKey", appKey);
    return doRequest(httpPost);
  }

  public static ResResult doGet2(URI url, String appKey) {
    if (StringUtils.isEmpty(url) || StringUtils.isEmpty(url.getHost())) {
      return new ResResult("Request URL is empty", 500);
    }
    HttpGet httpget = new HttpGet(url);

    return doRequest(httpget);
  }

  /**
   * 一二级对接需要的headers
   */
  public static Map<String, String> getAddHeader(String provinceCode, String apiCode,
      String channelId, String sessionId) {
    Map<String, String> headers = new HashMap<>();
    headers.put("domain", "COP");
    headers.put("routevalue", provinceCode);
    headers.put("apicode", apiCode);
    headers.put("appid", "CMCOP");
    headers.put("timestamp", sf.format(new Date()));
    headers.put("messageid", UUID.randomUUID().toString().replace("-", ""));
    headers.put("sign", "CMCOP");
    headers.put("version", "v1");
    headers.put("accessChannel", channelId);
    headers.put("channelId", channelId);
    headers.put("sessionId", sessionId);
    return headers;
  }


}