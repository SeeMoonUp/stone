/**
 *
 */
package com.javalemon.stone.common.utils.web;

import cc.laowantong.utils.IntegerUtil;
import cc.laowantong.utils.log.QMPLogger;
import cc.laowantong.utils.web.https.TrustAnyTrustManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Http&Https连接Util类
 *
 * @author fei.bian
 *
 */
public class URLConnUtil {

	private static final Log GCW_LOG = LogFactory.getLog(URLConnUtil.class.getName());

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final int DEFAULT_CONN_TIMEOUT = 3000;
	public static final int DEFAULT_SO_TIMEOUT = 3000;
	public static final boolean DEFAULT_IF_LOGGING = true;
	public static final String[] DEFAULT_EXCLUDE_PARAMS = null;
	private static final String EXCLUDE_SPLIT = "*";
	private static final String SPLIT = "##";

	public static final Map<String, String> EXCEPTION_MAP = new HashMap<String, String>();

	static {
		String UNSUPPORT_ENCODING_EXCEPTION = "1";
		String CLIENT_PROTOCOL_EXCEPTION = "2";
		String PARSE_EXCEPTION = "3";
		String IO_EXCEPTION = "4";
		String URI_SYNTAX_EXCEPTION = "5";
		String EXCEPTION = "6";
		EXCEPTION_MAP.put("UNSUPPORT_ENCODING_EXCEPTION",
				UNSUPPORT_ENCODING_EXCEPTION);
		EXCEPTION_MAP.put("CLIENT_PROTOCOL_EXCEPTION",
				CLIENT_PROTOCOL_EXCEPTION);
		EXCEPTION_MAP.put("PARSE_EXCEPTION", PARSE_EXCEPTION);
		EXCEPTION_MAP.put("IO_EXCEPTION", IO_EXCEPTION);
		EXCEPTION_MAP.put("URI_SYNTAX_EXCEPTION", URI_SYNTAX_EXCEPTION);
		EXCEPTION_MAP.put("EXCEPTION", EXCEPTION);
	}

	/**
	 * http get 无参数，默认连接时间，默认打日志
	 *
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, null, DEFAULT_CONN_TIMEOUT,
				DEFAULT_SO_TIMEOUT, DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8, null);
	}

	/**
	 * http get 无参数，默认打日志
	 *
	 * @param url
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doGet(String url, int connTimeOut, int soTimeOut) {
		return doGet(url, null, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8, null);
	}

	public static String doGet(String url, int connTimeOut, int soTimeOut, String userAgent) {
		return doGet(url, null, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8, userAgent);
	}

	public static String doGet(String url, String userAgent, int connTimeOut, int soTimeOut,String contentEncode) {
		return doGet(url, null, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,contentEncode,userAgent);
	}
	public static String doGet(String url, String userAgent, int connTimeOut, int soTimeOut) {
		return doGet(url, null, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8, userAgent);
	}

	/**
	 * http get 有参数，默认打日志
	 *
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doGet(String url, List<NameValuePair> params,
							   int connTimeOut, int soTimeOut) {
		return doGet(url, params, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8, null);
	}

	/**
	 * http get 有参数，是否打日志
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @return
	 */
	public static String doGet(String url, List<NameValuePair> params,
							   int connTimeOut, int soTimeOut, boolean ifLogging) {
		return doGet(url, params, connTimeOut, soTimeOut, ifLogging, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8, null);
	}

	/**
	 * http get 有参数，可设置是否打印Log，日志过滤敏感参数
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @param excludeParams
	 * @return
	 */
	public static String doGet(String url, List<NameValuePair> params,
							   int connTimeOut, int soTimeOut, boolean ifLogging, String[] excludeParams,String contentEncode, String userAgent) {
		long startTime = System.currentTimeMillis();

		String queryURL = "";
		int statusCode = 200;
		HttpGet httpget = null;
		String respContent = null;
		HttpEntity entity = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpParams httpparams = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpparams, connTimeOut);
		HttpConnectionParams.setSoTimeout(httpparams, soTimeOut);
		if (StringUtils.isNotBlank(userAgent)) {
			httpparams.setParameter("User-Agent", userAgent);
		}

		try {
			httpget = new HttpGet(url);
			// 设置参数
			if (params != null && params.size() > 0) {
				httpget.setURI(new URI(httpget.getURI().toString()
						+ "?"
						+ EntityUtils.toString(new UrlEncodedFormEntity(params,
						HTTP.UTF_8))));
			}
			queryURL = httpget.getURI().toString();
			// 发送请求
			long exeStartTime = System.currentTimeMillis();
			HttpResponse httpresp = httpclient.execute(httpget);
			if (ifLogging) {
				GCW_LOG.debug(new StringBuilder().append("[HTTP GET]").append("conT:").append(connTimeOut)
						.append("##").append("readT:").append(soTimeOut).append("##").append("realT:").append(System.currentTimeMillis() - exeStartTime).append("ms")
						.append("##").append(queryURL));
			}
			// 获取返回数据
			entity = httpresp.getEntity();
			respContent = EntityUtils.toString(entity,contentEncode);
			entity.consumeContent();
			// 获取状态码
			StatusLine sl = httpresp.getStatusLine();
			if (sl != null) {
				statusCode = sl.getStatusCode();
			}

			queryURL = excludeSensitiveParams(excludeParams, queryURL);
		} catch (UnsupportedEncodingException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("UNSUPPORT_ENCODING_EXCEPTION");
		} catch (ClientProtocolException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("CLIENT_PROTOCOL_EXCEPTION");
		} catch (ParseException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("PARSE_EXCEPTION");
		} catch (IOException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("IO_EXCEPTION");
		} catch (URISyntaxException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("URI_SYNTAX_EXCEPTION");
		} catch (Exception e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("EXCEPTION");
		} finally {
			// 关闭资源
			if (httpget != null) {
				httpget.abort();
			}
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		GCW_LOG.debug(QMPLogger.prepareHttpLogContent(GET, queryURL,
				url, System.currentTimeMillis() - startTime, statusCode,
				respContent, ifLogging));
		return respContent;
	}

	/**
	 * http post 无参数，默认连接时间，默认打日志
	 *
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		return doPost(url, null, DEFAULT_CONN_TIMEOUT,
				DEFAULT_SO_TIMEOUT, DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * http post 无参数，默认打日志
	 *
	 * @param url
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doPost(String url, int connTimeOut, int soTimeOut) {
		return doPost(url, null, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * http post 有参数，默认打日志
	 *
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doPost(String url, List<NameValuePair> params,
								int connTimeOut, int soTimeOut) {
		return doPost(url, params, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}
	public static String doPost(String url, List<NameValuePair> params,
								int connTimeOut, int soTimeOut, String reqCharset,String resCharset) {
		return doPost(url, params, connTimeOut, soTimeOut, true, DEFAULT_EXCLUDE_PARAMS,reqCharset,resCharset);
	}
	/**
	 * http post 有参数，是否打日志
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @return
	 */
	public static String doPost(String url, List<NameValuePair> params,
								int connTimeOut, int soTimeOut, boolean ifLogging) {
		return doPost(url, params, connTimeOut, soTimeOut, ifLogging, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}
	public static String doPost(String url, List<NameValuePair> params,
								int connTimeOut, int soTimeOut, boolean ifLogging ,String reqCharset,String resCharset) {
		return doPost(url, params, connTimeOut, soTimeOut, ifLogging, DEFAULT_EXCLUDE_PARAMS,reqCharset,resCharset);
	}
	/**
	 * http post 有参数，可设置是否打印Log，日志过滤敏感参数
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @param excludeParams
	 * @return
	 */
	public static String doPost(String url, List<NameValuePair> params,
								int connTimeOut, int soTimeOut, boolean ifLogging, String[] excludeParams,String reqCharset,String resCharset) {
		long startTime = System.currentTimeMillis();

		String queryURL = "";
		int statusCode = 200;
		HttpPost httppost = null;
		String respContent = null;
		HttpEntity entity = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpParams httpparams = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpparams, connTimeOut);
		HttpConnectionParams.setSoTimeout(httpparams, soTimeOut);

		try {

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null,
					new TrustManager[] { new TrustAnyTrustManager() }, null);

			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
			socketFactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", socketFactory, 443);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);


			httppost = new HttpPost(url);
			// 设置参数
			if (params != null && params.size() > 0) {
				httppost.setEntity(new UrlEncodedFormEntity(params, reqCharset));
			}
			queryURL = httppost.getURI().toString();
			if (ifHasParam(queryURL)) {
				queryURL = queryURL + prepareParamsForUrl(params, true);
			} else {
				queryURL = queryURL + "?" + prepareParamsForUrl(params, false);
			}
			long exeStartTime = System.currentTimeMillis();
			HttpResponse httpresp = httpclient.execute(httppost);
			if (ifLogging) {
				GCW_LOG.debug(new StringBuilder().append("[HTTP POST]").append("conT:").append(connTimeOut)
						.append("##").append("readT:").append(soTimeOut).append("##").append("realT:").append(System.currentTimeMillis() - exeStartTime).append("ms")
						.append("##").append(queryURL));
			}
			// 获取返回数据
			entity = httpresp.getEntity();
			respContent = EntityUtils.toString(entity,resCharset);
			entity.consumeContent();
			StatusLine sl = httpresp.getStatusLine();
			if (sl != null) {
				statusCode = sl.getStatusCode();
			}

			queryURL = excludeSensitiveParams(excludeParams, queryURL);
		} catch (UnsupportedEncodingException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("UNSUPPORT_ENCODING_EXCEPTION");
		} catch (ClientProtocolException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("CLIENT_PROTOCOL_EXCEPTION");
		} catch (ParseException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("PARSE_EXCEPTION");
		} catch (IOException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("IO_EXCEPTION");
		} catch (Exception e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("EXCEPTION");
		} finally {
			// 关闭资源
			if (httppost != null) {
				httppost.abort();
			}
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		GCW_LOG.debug(QMPLogger.prepareHttpLogContent(POST,
				queryURL, url, System.currentTimeMillis() - startTime,
				statusCode, respContent, ifLogging));
		return respContent;
	}

	/**
	 * https get 无参数，默认连接时间，默认打日志
	 *
	 * @param url
	 * @return
	 */
	public static String doHttpsGet(String url) {
		return doHttpsGet(url, null, DEFAULT_CONN_TIMEOUT,
				DEFAULT_SO_TIMEOUT, DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * https get 无参数，默认打日志
	 *
	 * @param url
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doHttpsGet(String url, int connTimeOut, int soTimeOut) {
		return doHttpsGet(url, null, connTimeOut, soTimeOut,
				DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * https get 有参数，默认打日志
	 *
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doHttpsGet(String url, List<NameValuePair> params,
									int connTimeOut, int soTimeOut) {
		return doHttpsGet(url, params, connTimeOut, soTimeOut,
				DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}
	public static String doHttpsGet(String url, List<NameValuePair> params,
									int connTimeOut, int soTimeOut,String reqEncode,String resDecode) {
		return doHttpsGet(url, params, connTimeOut, soTimeOut,
				DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,reqEncode,resDecode);
	}
	/**
	 * https get 有参数，是否打日志
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @return
	 */
	public static String doHttpsGet(String url, List<NameValuePair> params,
									int connTimeOut, int soTimeOut, boolean ifLogging) {
		return doHttpsGet(url, params, connTimeOut, soTimeOut,
				ifLogging, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * https get 有参数，可设置是否打印Log，日志过滤敏感参数
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @param excludeParams
	 * @return
	 */
	public static String doHttpsGet(String url, List<NameValuePair> params,
									int connTimeOut, int soTimeOut, boolean ifLogging, String[] excludeParams,String reqEncode,String resDecode) {
		long startTime = System.currentTimeMillis();

		String queryURL = "";
		int statusCode = 200;
		HttpGet httpget = null;
		String respContent = null;
		HttpEntity entity = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpParams httpparams = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpparams, connTimeOut);
		HttpConnectionParams.setSoTimeout(httpparams, soTimeOut);

		try {

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null,
					new TrustManager[] { new TrustAnyTrustManager() }, null);

			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
			socketFactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", socketFactory, 443);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);

			httpget = new HttpGet(url);
			// 设置参数
			if (params != null && params.size() > 0) {
				httpget.setURI(new URI(httpget.getURI().toString()
						+ "?"
						+ EntityUtils.toString(new UrlEncodedFormEntity(params,
						reqEncode))));
			}
			queryURL = httpget.getURI().toString();
			// 发送请求
			long exeStartTime = System.currentTimeMillis();
			HttpResponse httpresp = httpclient.execute(httpget);
			if (ifLogging) {
				GCW_LOG.debug(new StringBuilder().append("[HTTPS GET]").append("conT:").append(connTimeOut)
						.append("##").append("readT:").append(soTimeOut).append("##").append("realT:").append(System.currentTimeMillis() - exeStartTime).append("ms")
						.append("##").append(queryURL));
			}
			// 获取返回数据
			entity = httpresp.getEntity();
			respContent = EntityUtils.toString(entity,resDecode);
			entity.consumeContent();
			// 获取状态码
			StatusLine sl = httpresp.getStatusLine();
			if (sl != null) {
				statusCode = sl.getStatusCode();
			}

			queryURL = excludeSensitiveParams(excludeParams, queryURL);
		} catch (UnsupportedEncodingException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("UNSUPPORT_ENCODING_EXCEPTION");
		} catch (ClientProtocolException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("CLIENT_PROTOCOL_EXCEPTION");
		} catch (ParseException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("PARSE_EXCEPTION");
		} catch (IOException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("IO_EXCEPTION");
		} catch (URISyntaxException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("URI_SYNTAX_EXCEPTION");
		} catch (Exception e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(GET,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("EXCEPTION");
		} finally {
			// 关闭资源
			if (httpget != null) {
				httpget.abort();
			}
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		GCW_LOG.debug(QMPLogger.prepareHttpLogContent(GET, queryURL,
				url, System.currentTimeMillis() - startTime, statusCode,
				respContent, ifLogging));
		return respContent;
	}

	/**
	 * https post 无参数，默认连接时间，默认打日志
	 *
	 * @param url
	 * @return
	 */
	public static String doHttpsPost(String url) {
		return doHttpsPost(url, null, DEFAULT_CONN_TIMEOUT,
				DEFAULT_SO_TIMEOUT, DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * https post 无参数，默认打日志
	 *
	 * @param url
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doHttpsPost(String url, int connTimeOut, int soTimeOut) {
		return doHttpsPost(url, null, connTimeOut, soTimeOut,
				DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * https post 有参数，默认打日志
	 *
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static String doHttpsPost(String url, List<NameValuePair> params,
									 int connTimeOut, int soTimeOut) {
		return doHttpsPost(url, params, connTimeOut, soTimeOut,
				DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}
	public static String doHttpsPost(String url, List<NameValuePair> params,
									 int connTimeOut, int soTimeOut,String reqCharset,String resCharset) {
		return doHttpsPost(url, params, connTimeOut, soTimeOut,
				DEFAULT_IF_LOGGING, DEFAULT_EXCLUDE_PARAMS,reqCharset,resCharset);
	}

	/**
	 * https post 有参数，是否打日志
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @return
	 */
	public static String doHttpsPost(String url, List<NameValuePair> params,
									 int connTimeOut, int soTimeOut, boolean ifLogging) {
		return doHttpsPost(url, params, connTimeOut, soTimeOut,
				ifLogging, DEFAULT_EXCLUDE_PARAMS,HTTP.UTF_8,HTTP.UTF_8);
	}

	/**
	 * https post 有参数，可设置是否打印Log，日志过滤敏感参数
	 * @param url
	 * @param params
	 * @param connTimeOut
	 * @param soTimeOut
	 * @param ifLogging
	 * @param excludeParams
	 * @return
	 */
	public static String doHttpsPost(String url, List<NameValuePair> params,
									 int connTimeOut, int soTimeOut, boolean ifLogging, String[] excludeParams,String reqCharset,String resCharset) {
		long startTime = System.currentTimeMillis();

		String queryURL = "";
		int statusCode = 200;
		HttpPost httppost = null;
		String respContent = null;
		HttpEntity entity = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpParams httpparams = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpparams, connTimeOut);
		HttpConnectionParams.setSoTimeout(httpparams, soTimeOut);

		try {

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null,
					new TrustManager[] { new TrustAnyTrustManager() }, null);

			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
			socketFactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme("https", socketFactory, 443);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);

			httppost = new HttpPost(url);
			// 设置参数
			if (params != null && params.size() > 0) {
				httppost.setEntity(new UrlEncodedFormEntity(params, reqCharset));
			}
			queryURL = httppost.getURI().toString();
			if (ifHasParam(queryURL)) {
				queryURL = queryURL + prepareParamsForUrl(params, true);
			} else {
				queryURL = queryURL + "?" + prepareParamsForUrl(params, false);
			}
			long exeStartTime = System.currentTimeMillis();
			HttpResponse httpresp = httpclient.execute(httppost);
			if (ifLogging) {
				GCW_LOG.debug(new StringBuilder().append("[HTTPS POST]").append("conT:").append(connTimeOut)
						.append("##").append("readT:").append(soTimeOut).append("##").append("realT:").append(System.currentTimeMillis() - exeStartTime).append("ms")
						.append("##").append(queryURL));
			}
			// 获取返回数据
			entity = httpresp.getEntity();
			respContent = EntityUtils.toString(entity,resCharset);
			entity.consumeContent();
			StatusLine sl = httpresp.getStatusLine();
			if (sl != null) {
				statusCode = sl.getStatusCode();
			}

			queryURL = excludeSensitiveParams(excludeParams, queryURL);
		} catch (UnsupportedEncodingException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("UNSUPPORT_ENCODING_EXCEPTION");
		} catch (ClientProtocolException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("CLIENT_PROTOCOL_EXCEPTION");
		} catch (ParseException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("PARSE_EXCEPTION");
		} catch (IOException e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("IO_EXCEPTION");
		} catch (Exception e) {
			GCW_LOG.error(QMPLogger.prepareHttpLogContent(POST,
					queryURL, url, System.currentTimeMillis() - startTime,
					statusCode, respContent, ifLogging), e);
			return EXCEPTION_MAP.get("EXCEPTION");
		} finally {
			// 关闭资源
			if (httppost != null) {
				httppost.abort();
			}
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		GCW_LOG.info(QMPLogger.prepareHttpLogContent(POST,
				queryURL, url, System.currentTimeMillis() - startTime,
				statusCode, respContent, ifLogging));
		return respContent;
	}

	/**
	 * 过滤敏感字符
	 * @param excludeParams
	 * @param queryURL
	 * @return
	 */
	private static String excludeSensitiveParams(String[] excludeParams,
												 String queryURL) {
		if (excludeParams != null && excludeParams.length > 0) {
			try {
				for (int i = 0; i < excludeParams.length; i++) {
					String excludeParam = StringUtils.trimToEmpty(excludeParams[i]);
					if (StringUtils.isBlank(excludeParam)) {
						continue;
					}
					int a = queryURL.indexOf(excludeParam + "=");
					if (a > 0) {
						String excludeValue = "";
						int b = queryURL.indexOf("&", a);
						if (b > 0) {
							excludeValue = queryURL.substring(a + excludeParam.length() + 1, b);
							if (excludeValue != null && excludeValue.length() > 0) {
								excludeValue = excludeValue.replaceAll("\\S", EXCLUDE_SPLIT);
								queryURL = queryURL.substring(0, a + excludeParam.length() + 1) + excludeValue + queryURL.substring(b, queryURL.length());
							}
						} else {
							excludeValue = queryURL.substring(a + excludeParam.length() + 1, queryURL.length());
							if (excludeValue != null && excludeValue.length() > 0) {
								excludeValue = excludeValue.replaceAll("\\S", EXCLUDE_SPLIT);
								queryURL = queryURL.substring(0, a + excludeParam.length() + 1) + excludeValue;
							}
						}
					}
				}
			} catch (Exception e) {
				GCW_LOG.error("excludeSensitiveParams error!", e);
				return queryURL;
			}
		}
		return queryURL;
	}



	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								String value) {
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
			params.add(new BasicNameValuePair(name, value));
		}
	}

	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								int value) {
		addParam(params, name, String.valueOf(value));
	}

	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								long value) {
		addParam(params, name, String.valueOf(value));
	}

	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								boolean value) {
		addParam(params, name, String.valueOf(value));
	}

	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								double value) {
		addParam(params, name, String.valueOf(value));
	}

	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								float value) {
		addParam(params, name, String.valueOf(value));
	}

	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								short value) {
		addParam(params, name, String.valueOf(value));
	}

	/**
	 * 增加参数值
	 *
	 * @param params
	 * @param name
	 * @param value
	 */
	public static void addParam(List<NameValuePair> params, String name,
								byte value) {
		addParam(params, name, String.valueOf(value));
	}



	/**
	 * 新增Cookie
	 *
	 * @param cookies
	 * @param name
	 * @param value
	 * @param domian
	 * @param outOfDay
	 */
	public static void addCookie(List<BasicClientCookie2> cookies, String name,
								 String value, String domian, int outOfDay) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, outOfDay);
		BasicClientCookie2 cookie = new BasicClientCookie2(name, value);
		cookie.setDomain(domian);
		cookie.setExpiryDate(cal.getTime());
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setVersion(1);
		cookie.setAttribute("version", "1");
		cookie.setAttribute("domain", domian);
		cookies.add(cookie);
	}

	/**
	 * 组装需要打印的URL参数信息
	 *
	 * @param params
	 * @return
	 */
	public static String prepareParamsForUrl(List<NameValuePair> params,
											 boolean ifHasParam) {
		if (params != null && params.size() > 0) {
			StringBuffer paramsStr = new StringBuffer();
			for (NameValuePair param : params) {
				paramsStr.append("&").append(param.getName()).append("=")
						.append(param.getValue());
			}
			if (ifHasParam) {
				return StringUtils.isNotBlank(paramsStr.toString()) ? paramsStr
						.toString().trim() : "";
			} else {
				return StringUtils.isNotBlank(paramsStr.toString()) ? paramsStr
						.toString().trim().substring(1) : "";
			}
		}
		return "";
	}

	/**
	 * 是否url后面添加了参数
	 *
	 * @param url
	 * @return
	 */
	public static boolean ifHasParam(String url) {
		if (StringUtils.isNotBlank(url)) {
			if (url.indexOf("?") > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 组装请求参数
	 * @param params
	 * @param method
	 * @return
	 */
	public static String prepareParams(Map<String, String> params, String method) {
		StringBuffer paramsSB = new StringBuffer("");
		if (params != null && params.size() > 0) {
			String key = "";
			String value = "";
			for (Iterator<String> it = params.keySet().iterator(); it.hasNext(); ) {
				key = it.next();
				value = params.get(key);
				if ("GET".equalsIgnoreCase(method)) {
					if (paramsSB.length() == 0) {
						paramsSB.append("?").append(key).append("=").append(value);
					} else {
						paramsSB.append("&").append(key).append("=").append(value);
					}
				} else if ("POST".equalsIgnoreCase(method)) {
					if (paramsSB.length() == 0) {
						paramsSB.append(key).append("=").append(value);
					} else {
						paramsSB.append("&").append(key).append("=").append(value);
					}
				}
			}
			return paramsSB.toString();
		} else {
			return paramsSB.toString();
		}
	}

	/**
	 * 发起https请求并获取结果
	 * @param targetUrl 请求地址
	 * @param RequestMethod 请求方式（GET、POST）
	 * @param output 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject doHttpsRequest(String targetUrl, String RequestMethod, String output) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 建立连接
			URL url = new URL(targetUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if (output != null) {
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}
			// 流处理
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			// 关闭连接、释放资源
			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
		}
		return jsonObject;
	}

	/**
	 * 获取图片资源
	 * @param url
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return
	 */
	public static byte[] doGetImg(String url, int connTimeOut, int soTimeOut) {

		String queryURL = "";
		int statusCode = 200;
		HttpGet httpget = null;
		HttpEntity entity = null;
		byte[] byteArray = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpParams httpparams = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpparams, connTimeOut);
		HttpConnectionParams.setSoTimeout(httpparams, soTimeOut);

		try {
			httpget = new HttpGet(url);
			queryURL = httpget.getURI().toString();
			// 发送请求
			HttpResponse httpresp = httpclient.execute(httpget);
			// 获取返回数据
			entity = httpresp.getEntity();
			byteArray = EntityUtils.toByteArray(entity);
			// 获取状态码
			StatusLine sl = httpresp.getStatusLine();
			if (sl != null) {
				statusCode = sl.getStatusCode();
			}
			GCW_LOG.info("queryURL: " + queryURL);
		} catch (UnsupportedEncodingException e) {
			GCW_LOG.error("doGetImg error! statusCode: " + statusCode, e);
			return null;
		} catch (ClientProtocolException e) {
			GCW_LOG.error("doGetImg error! statusCode: " + statusCode, e);
			return null;
		} catch (ParseException e) {
			GCW_LOG.error("doGetImg error! statusCode: " + statusCode, e);
			return null;
		} catch (IOException e) {
			GCW_LOG.error("doGetImg error! statusCode: " + statusCode, e);
			return null;
		} catch (Exception e) {
			GCW_LOG.error("doGetImg error! statusCode: " + statusCode, e);
			return null;
		} finally {
			// 关闭资源
			if (httpget != null) {
				httpget.abort();
			}
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return byteArray;
	}

	public static String prepareHttpLogContent(String method, String queryURL, String url,
											   long responseTime, int httpStatusCode, String result,
											   boolean ifLogging) {

		StringBuffer sb = new StringBuffer();
		sb.append(SPLIT).append(method).append(SPLIT).append(responseTime).append("ms").append(SPLIT).append(httpStatusCode);
		if (ifLogging) {
			sb.append(SPLIT).append(queryURL).append(SPLIT).append(escapeEmpty(result));
		} else {
			sb.append(SPLIT).append(url);
		}
		return sb.toString();
	}

	public static String escapeEmpty(String str) {
		if (StringUtils.isNotEmpty(str)) {
			str = str.replace("\r", "");
			str = str.replace("\n", "");
			str = str.replace("\t", "");
		}
		return str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * System.out.println(URLConnUtil.doGet("http://www.youleyu.com", null,
		 * 3000, 3000, true));
		 * System.out.println(ifHasParam("http://fffff.fsdjla.com?aaa=33"));
		 * List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
		 * URLConnUtil.addParam(requestParams, "bb", "cc");
		 * URLConnUtil.addParam(requestParams, "xx", "ee");
		 * URLConnUtil.doPost("http://www.youleyu.com/bdb.do?cd=33", requestParams,
		 * 3000, 3000, true);
		 * URLConnUtil.doHttpsGet("https://dynamic.12306.cn/otsweb/", null,
		 * 30000, 30000, true);
		 * URLConnUtil.doHttpsPost("https://dynamic.12306.cn/otsweb/", null,
		 * 30000, 30000, true);
		 *
		 * List<BasicClientCookie2> clist = new ArrayList<BasicClientCookie2>();
		 * URLConnUtil.addCookie(clist, "_q", "Fermi", ".qunar.com", 1);
		 */
	}

	/**
	 * 文件分块上传
	 * @param serverURL
	 * @param targetFile
	 * @param params
	 * @return
	 */
	public static String doHttpBlockUpload(String serverURL, File targetFile, Map<String, String> params) {

		String content = "";
		try {

			HttpClient httpClient = new DefaultHttpClient();

//			HttpHost proxy = new HttpHost("127.0.0.1", 8888);
//			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

//			SSLContext sslContext = SSLContext.getInstance("SSL");
//			sslContext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, null);
//
//			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
//			socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//			Scheme sch = new Scheme("http", socketFactory, 443);
//			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			HttpPost post = new HttpPost(serverURL);
			post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
			post.setHeader("Accept-Language","zh-cn,zh;q=0.5");
			post.setHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.7");
			post.setHeader("Connection","keep-alive");

//			HttpParams httpparams = httpClient.getParams();
//			HttpConnectionParams.setConnectionTimeout(httpparams, 1000 * 60 * 30);
//			HttpConnectionParams.setSoTimeout(httpparams, 1000 * 60 * 30);

//			System.setProperty("sun.net.client.defaultConnectTimeout", "1000 * 60 * 30");
//			System.setProperty("sun.net.client.defaultReadTimeout", "1000 * 60 * 30");

			Integer blockNumber = IntegerUtil.parseString2Int(params.get("blockNumber"));
			Integer blockIndex = IntegerUtil.parseString2Int(params.get("blockIndex"));

//			BlockStreamBody contentBody = new BlockStreamBody(blockNumber, blockIndex, targetFile);
			FileBody fileBody = new FileBody(targetFile);

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			List<String> keys = new ArrayList<String>(params.keySet());
			Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
				String key = iterator.next();
				if (StringUtils.isNotBlank(params.get(key)) && !StringUtils.equals("blockNumber", key) && ! StringUtils.equals("blockIndex", key)) {
					multipartEntityBuilder.addPart(key, new StringBody(params.get(key), ContentType.TEXT_PLAIN));
				}
			}

			multipartEntityBuilder.addPart("mediatrunk", fileBody);
			HttpEntity entity = multipartEntityBuilder.setBoundary("--***********laowantong*******").setContentType(ContentType.MULTIPART_FORM_DATA).build();
			post.setEntity(entity);

			HttpResponse response = httpClient.execute(post);
			content = EntityUtils.toString(response.getEntity());
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			GCW_LOG.info("doHttpBlockUpload Exception:", e);
			e.printStackTrace();
		}
		GCW_LOG.info("=====content==========================\n" + content);
		return content.trim();
	}


	/**
	 * 以json格式发送数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doHttpsPost(String url, String params) {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);

		String result = "";

		try {

			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,DEFAULT_CONN_TIMEOUT);//连接时间

			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,DEFAULT_SO_TIMEOUT);//数据传输时间

			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

			StringEntity se = new StringEntity(params,"UTF-8");
			se.setContentType("text/json");

			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

			httpPost.setEntity(se);

			HttpResponse response = httpClient.execute(httpPost);

			if(response != null && response.getStatusLine().getStatusCode() == 200) {

				result = EntityUtils.toString(response.getEntity());
				// 生成 JSON 对象
				GCW_LOG.debug("result:" + result + " params :" + params);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
