package ru.lqgen.lk;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

public class CheckApp {
    public static void main(String[] args) throws Exception {
        String url = System.getenv("url");
        String debug = System.getenv("debug");

        System.setProperty("javax.net.debug", debug == null ? "all" : debug);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpsURLConnection.setDefaultHostnameVerifier((String hostname, SSLSession session) -> true);

        HttpGet httpGet = new HttpGet(url);
        httpClient.execute(httpGet, response -> {
            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            System.out.println(result.toString());
            return response;
        });
    }
}
