package rest.base;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class RestService {

    private final String endpoint = "http://localhost:8080";
    private final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0";
    private final int conTimeout = 5000;

    protected String postRequest(String operation, HashMap<String, String> params){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(endpoint + operation);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("accept-encoding", "gzip, deflate");
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(conTimeout);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(createBody(params));
            wr.close();

            switch(connection.getResponseCode()){
                case 200:
                    return getResponseAsJson(connection);
            }

        } catch(Exception e){

        }

        return null;
    }

    protected String getRequest(String operation){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(endpoint + operation);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("accept-encoding", "gzip, deflate");
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(conTimeout);
            connection.setDoOutput(true);

            if(connection.getResponseCode() == 200){
                return getResponseAsJson(connection);
            }

        } catch(Exception e){

        }

        return null;
    }

    private String getResponseAsJson(HttpURLConnection connection){
        try {
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            rd.close();
            if(response.length() > 0){
                return response.toString();
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    private String createBody(HashMap<String, String> payload) {
        try{
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for(HashMap.Entry<String, String> entry : payload.entrySet()){
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            return result.toString();
        }catch(Exception e){
            return null;
        }
    }
}
