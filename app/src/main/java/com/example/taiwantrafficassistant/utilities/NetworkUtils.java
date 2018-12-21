package com.example.taiwantrafficassistant.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

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

    //BUS_STOP
    final static String PTX_BUS_STOP_ONE = "https://ptx.transportdata.tw/MOTC/v2/Bus/StopOfRoute/City/Taipei/";
    final static String PTX_BUS_STOP_TWO = "?$select=Stops%2CRouteName&$filter=RouteName%2FZh_tw%20eq%20'";
    final static String PTX_BUS_STOP_THREE = "'&$top=1&$format=JSON";


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



    /**
     *
     */
    public static URL buildPtxBusStop(String ptxSearchQuery) {
        StringBuilder strUrl = new StringBuilder(PTX_BASE_URL);
        StringBuilder strPtxSearchQueryUrl = new StringBuilder("");
        strPtxSearchQueryUrl.append(PTX_BUS_STOP_ONE);
        strPtxSearchQueryUrl.append(ptxSearchQuery);
        strPtxSearchQueryUrl.append(PTX_BUS_STOP_TWO);
        strPtxSearchQueryUrl.append(ptxSearchQuery);
        strPtxSearchQueryUrl.append(PTX_BUS_STOP_THREE);

        try {
            strUrl.append(URLEncoder.encode(strPtxSearchQueryUrl.toString(), "UTF-8"));
        }catch (Exception e){
            System.out.println("Encoding failed");
        }

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
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
            urlConnection.disconnect();
        }
    }
}