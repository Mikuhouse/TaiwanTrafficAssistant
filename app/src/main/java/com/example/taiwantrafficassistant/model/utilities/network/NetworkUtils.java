package com.example.taiwantrafficassistant.model.utilities.network;

import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateFactory;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 這個NetworkUtils的功能:
 * 抓取input stream
 * 建構URL
 */
public class NetworkUtils {
    /**
     * GithubSearchQuery 參數配置
     */
    final static String GITHUB_BASE_URL =
            "https://api.github.com/search/repositories";
    final static String PARAM_QUERY = "q";
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";
    /**
     * PtxSearchQuery 參數配置
     */
    final static String PTX_BASE_URL =
            "http://140.136.149.241/tests/download.php?ID=a55c64f5cf2d46c2908ed29af853880c&KEY=rL4dhGhxs7smLvAtSusvXF2qPcI&url=";
    //BUS_ROUTE_SEARCH
    final static String PTX_BUS_ROUTE_SEARCH_BASE_ONE = "https://ptx.transportdata.tw/MOTC/v2/Bus/Route/City/Taipei/";
    final static String PTX_BUS_ROUTE_SEARCH_BASE_TWO = "?$format=JSON";
    final static String PTX_BUS_ROUTE_SEARCH_BASE_ONE_AL = "https://ptx.transportdata.tw/MOTC/v2/Bus/Route/City/NewTaipei/";


    /**
     * 建構GithubSearchQuery URL
     * @param ptxSearchQuery 為要搜尋的東西
     * @return URL 為產生好的URL
     * @throws MalformedURLException
     */
    public static URL buildGithubSearchQueryUrl(String ptxSearchQuery) {
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, ptxSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    /**
     *
     */
    public static URL buildPtxSearchQueryUrl(String ptxSearchQuery) {
        StringBuilder strUrl = new StringBuilder(PTX_BASE_URL);
        strUrl.append(PTX_BUS_ROUTE_SEARCH_BASE_ONE);
        strUrl.append(ptxSearchQuery);
        strUrl.append(PTX_BUS_ROUTE_SEARCH_BASE_TWO);

        URL url = null;
        try {
            url = new URL(strUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildPtxSearchQueryTwoUrl(String ptxSearchQuery) {
        StringBuilder strUrl = new StringBuilder(PTX_BASE_URL);
        strUrl.append(PTX_BUS_ROUTE_SEARCH_BASE_ONE_AL);
        strUrl.append(ptxSearchQuery);
        strUrl.append(PTX_BUS_ROUTE_SEARCH_BASE_TWO);

        URL url = null;
        try {
            url = new URL(strUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        //URLEncoder.encode(url.toString());
        URLConnection urlConnection = url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            //urlConnection.disconnect();
        }
    }


    public static String getResponseFromHttpsUrl(URL url) throws IOException {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("maps.googleapis.com", session);
            }
        };

        HttpsURLConnection urlConnection =
                (HttpsURLConnection)url.openConnection();
        urlConnection.setHostnameVerifier(hostnameVerifier);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            //urlConnection.disconnect();
        }
    }
}