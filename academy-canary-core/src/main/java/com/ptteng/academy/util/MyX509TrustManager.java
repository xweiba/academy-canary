package com.ptteng.academy.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @program: canary
 * @description: 微信工具依赖
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-19 22:30
 **/

public class MyX509TrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
