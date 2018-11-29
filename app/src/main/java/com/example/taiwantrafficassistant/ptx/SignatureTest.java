package com.example.taiwantrafficassistant.ptx;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;

public class SignatureTest {

    public static String test() {
        HttpURLConnection connection=null;
        String APIUrl = "http://ptx.transportdata.tw/MOTC/v2/Bus/Schedule/City/Taipei?$top=30&$format=JSON";
        //申請的APPID
        //（FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF 為 Guest 帳號，以IP作為API呼叫限制，請替換為註冊的APPID & APPKey）
        String APPID = "a55c64f5cf2d46c2908ed29af853880c";
        //申請的APPKey
        String APPKey = "rL4dhGhxs7smLvAtSusvXF2qPcI";

        //取得當下的UTC時間，Java8有提供時間格式DateTimeFormatter.RFC_1123_DATE_TIME
        //但是格式與C#有一點不同，所以只能自行定義
        String xdate = getServerTime();
        String SignDate = "x-date: " + xdate;


        String Signature="";
        try {
            //取得加密簽章
            Signature = HMAC_SHA1.Signature(SignDate, APPKey);
        } catch (SignatureException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        System.out.println("Signature :" + Signature);
        String sAuth = "hmac username=\"" + APPID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + Signature + "\"";
        System.out.println(sAuth);
        String response = "123";
        try{
            URL url=new URL(APIUrl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", sAuth);
            connection.setRequestProperty("x-date", xdate);
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //將InputStream轉換為Byte
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int bytesRead = 0;
            while((bytesRead = inputStream.read(buff)) != -1) {
                bao.write(buff, 0, bytesRead);
            }

            //解開GZIP
            ByteArrayInputStream bais = new ByteArrayInputStream(bao.toByteArray());
            GZIPInputStream gzis = new GZIPInputStream(bais);
            InputStreamReader reader = new InputStreamReader(gzis);
            BufferedReader in = new BufferedReader(reader);

            //讀取回傳資料
            String line;
            while ((line = in.readLine()) != null) {
                response+=(line+"\n");
            }

            return response;

        }catch(ProtocolException e){
            e.printStackTrace();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return "fail";
    }

    //取得當下UTC時間
    public static String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

}

