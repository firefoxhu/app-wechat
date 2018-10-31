package com.xyls.wechat.appwechat.share;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 公众平台通用接口工具类
 *
 * @author james
 * @date 2015-02-27
 */
@Component
public class WxConfig {
    // 获取access_token的接口地址（GET） 限2000（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 获取jsapi_ticket的接口地址（GET） 限2000（次/天）
    public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    // token对应的key
    private static final String TOKEN = "token";
    // ticket对应的key
    private static final String TICKET = "ticket";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 外部获取签名入口
     *
     * @return
     */
    public Map<String, Object> getSignature(String appId, String secret, String url) {


        Object cacheAccessToken = redisTemplate.opsForValue().get(TOKEN);

        Object ticketAccessToken = redisTemplate.opsForValue().get(TICKET);


        if (cacheAccessToken != null || ticketAccessToken != null) {

            Token accessToken = (Token) cacheAccessToken;

            Token ticketToken = (Token) ticketAccessToken;

            if (!accessToken.isExpried() && !ticketToken.isExpried()) {
                return redisTemplate.opsForHash().entries("data");
            }
        }


        //获取access_token
        String accessUrl = access_token_url.replace("APPID", appId).replace("APPSECRET", secret);
        JSONObject accessJson = httpRequest(accessUrl, "GET", null);
        // 如果请求成功
        Token access_token = null;
        if (null != accessJson) {
            access_token = new Token(accessJson.getString("access_token"), accessJson.getIntValue("expires_in"));// 正常过期时间是7200秒
        }
        if (access_token == null) return null;

        String ticketUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", access_token.getToken());
        JSONObject ticketJson = httpRequest(ticketUrl, "GET", null);
        // 如果请求成功
        Token token2 = null;
        if (null != ticketUrl) {
            token2 = new Token(ticketJson.getIntValue("expires_in"));
            token2.setTicket(ticketJson.getString("ticket"));
        }
        long timstamp = System.currentTimeMillis();
        // 生成签名的随机串
        String noncestr = RandomStringUtils.random(4, "utf-8");
        String signature = signature(token2.getTicket(), timstamp + "", noncestr, url);
        Map<String, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("timestamp", timstamp);
        map.put("nonceStr", noncestr);
        map.put("signature", signature);

        redisTemplate.opsForValue().set(TOKEN, access_token, 3600, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(TICKET, token2, 3600, TimeUnit.SECONDS);
        redisTemplate.opsForHash().putAll("data", map);

        return map;
    }

    /**
     * 签名
     *
     * @param timestamp
     * @return
     */
    private static String signature(String jsapi_ticket, String timestamp, String noncestr, String url) {
        String[] paramArr = new String[]{"jsapi_ticket=" + jsapi_ticket,
                "timestamp=" + timestamp, "noncestr=" + noncestr, "url=" + url};
        Arrays.sort(paramArr);
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2])
                .concat("&" + paramArr[3]);
        System.out.println("拼接之后的content为:" + content);
        String gensignature = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行 sha1 加密
            byte[] digest = md.digest(content.toString().getBytes());
            gensignature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将 sha1 加密后的字符串与 signature 进行对比
        if (gensignature != null) {
            return gensignature;// 返回signature
        } else {
            return "false";
        }
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    private static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
            // jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("Weixin server connection timed out.");
        } catch (Exception e) {
            System.out.println("https request error:{}" + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

}