package example.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public static void get(String host, int port, String path) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL("http://" + host + ":" + port + path).openConnection());

            if (httpURLConnection.getResponseCode() >= 300) {
                System.out.println("请求失败:" + host + ":" + port + path + "  --> 应答: " + httpURLConnection.getResponseCode());
                return;
            }

            System.out.println("请求成功:" + host + ":" + port + path + "  --> 应答: " + httpURLConnection.getResponseCode());

            StringBuffer resultBuffer = new StringBuffer();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {

                String tempLine = null;
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            }

            System.out.println(resultBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
