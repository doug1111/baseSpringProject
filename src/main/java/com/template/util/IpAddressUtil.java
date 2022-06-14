package com.template.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * IP地址工具类
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
public class IpAddressUtil {

    /**
     * 获取ip地址
     * @param request 请求的request
     * @return String ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            String benji = "127.0.0.1";
            String bj = "0:0:0:0:0:0:0:1";
            if (benji.equals(ipAddress) || bj.equals(ipAddress)) {
                ///根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                }
                catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (inet != null) {
                    ipAddress = inet.getHostAddress();
                }
            }
        }
        ///对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        int i = 15;
        String s = ",";
        if (ipAddress != null && ipAddress.length() > i) {
            if (ipAddress.indexOf(s) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 解析 用户代理(User-Agent)
     * @param userAgent 用户代理User-Agent, UA
     * @return "设备类型:%s,操作系统:%s,浏览器:%s,浏览器版本:%s,浏览器引擎:%s,用户代理(User-Agent):[%s]"
     */
    public static String getDevice(String userAgent) {
        //解析agent字符串
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        //获取浏览器对象
        Browser browser = ua.getBrowser();
        //获取操作系统对象
        OperatingSystem os = ua.getOperatingSystem();
        return String.format("设备类型:%s,操作系统:%s,浏览器:%s,浏览器版本:%s,浏览器引擎:%s",
                os.getDeviceType(),
                os.getName(),
                browser.getName(),
                browser.getVersion(userAgent),
                browser.getRenderingEngine()
        );
    }
}