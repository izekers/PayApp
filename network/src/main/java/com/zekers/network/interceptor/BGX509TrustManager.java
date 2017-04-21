package com.zekers.network.interceptor;

import java.security.cert.CertificateException;

import javax.net.ssl.X509TrustManager;

/**
 * 密匙管理
 * Created by Zekers on 2016/12/23.
 */
public class BGX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(
            java.security.cert.X509Certificate[] chain,
            String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(
            java.security.cert.X509Certificate[] chain,
            String authType) throws CertificateException {
    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }
}
