package com.example.demo;

import org.springframework.beans.factory.annotation.Value;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetConfig {
    private static boolean saveFlvFile;
    private static String saveFlVFilePath;

    public static String getSaveFlVFilePath() {
        return saveFlVFilePath;
    }

    public static void setSaveFlVFilePath(String saveFlVFilePath) {
        NetConfig.saveFlVFilePath = saveFlVFilePath;
    }

    public static boolean isSaveFlvFile() {
        return saveFlvFile;
    }

    public static void setSaveFlvFile(boolean saveFlvFile) {
        NetConfig.saveFlvFile = saveFlvFile;
    }

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }
}
