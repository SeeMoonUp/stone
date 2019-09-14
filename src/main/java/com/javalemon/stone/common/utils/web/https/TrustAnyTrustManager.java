package com.javalemon.stone.common.utils.web.https;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 信任所有
 * @author fei.bian
 *
 */
public class TrustAnyTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(
			X509Certificate[] paramArrayOfX509Certificate, String paramString)
			throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkServerTrusted(
			X509Certificate[] paramArrayOfX509Certificate, String paramString)
			throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
