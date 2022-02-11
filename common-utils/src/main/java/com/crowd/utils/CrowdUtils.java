package com.crowd.utils;

import com.crowd.constant.CrowdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CrowdUtils {
    private static Logger logger= LoggerFactory.getLogger(CrowdUtils.class);

    /**
     * 判断请求方法的类型是ajax还是其他请求
     * @param request 请求的request对象
     * @return 返回bool值，若为真则为ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        // 1.获取请求消息头信息
        String acceptInformation = request.getHeader("Accept");
        String xRequestInformation = request.getHeader("X-Requested-With");

        logger.info("开始获取请求属性[acceptInformation]和[xRequestInformation],用于判断请求是否是一个ajax请求！");

        logger.info("[acceptInformation]:"+acceptInformation);
        logger.info("[xRequestInformation]:"+xRequestInformation);

       // 返回最终判断
        return ((acceptInformation!=null&&acceptInformation.contains("application/json"))||(xRequestInformation!=null&&xRequestInformation.equals("XMLHttpRequest")));
        }

    /**
     * 对明文进行加密
     * @param source 传入的明文
     * @return 加密后的明文
     */
    public static String md5(String source){
        // 判断source是否有效，避免空指针异常
        if(source == null||source.length() == 0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 获取MessageDigest对象
        String algorithm = "md5";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 获取明问文字符串
            byte[] input = source.getBytes();
            // 执行md5加密
            byte[] output = messageDigest.digest(input);
            // 创建bigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum,output);
            // 将16进制的bigInteger装换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return null;
    }


}
