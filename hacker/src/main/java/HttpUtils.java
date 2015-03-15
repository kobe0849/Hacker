
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http4的工具类
 * Created by toutian on 14-4-15.
 */
public class HttpUtils {

   public static String get(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("Connection", "Keep-Alive");//设置为短连接
        CloseableHttpResponse response = httpclient.execute(httpGet);

        try {
            String content = null;
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);

            return content;
        } finally {
            System.out.println("ss");
            response.close();
        }
    }
    public static String post(String url, Map<String, String> params) throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
       // httpclient.setReceiveTimeout(1000 * 60 * 5);

        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000000).setSocketTimeout(1000000).setConnectTimeout(2000000).build();
        httpPost.setConfig(requestConfig);

        //组装post参数
        if(params != null && params.size() > 0) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = httpclient.execute(httpPost);

        try {
            String content = null;
                HttpEntity entity = response.getEntity();

                content = EntityUtils.toString(entity);

            return content;
        } finally {
            response.close();
        }
    }

    public static String getS(String h){
        for(int i = 0; i < h.length(); i++){
            if(h.substring(i,i+9).equals("boardinit")){
                int j = i + 13;
                for(;;j++)
                    if(h.charAt(j) == '"') break;
                return h.substring(i,j+2);
            }
        }
        return null;

    }

    public static void main(String[] args) throws IOException {

        String  h = Utils.sendPost("http://www.hacker.org/cross/index.php", "");
        //String h = "<script>var boardinit = \"001,111\";var level = 3;</script>";
        h = getS(h);
        String url = "http://10.15.2.106:6648/feixia";
      /*  HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("str", h));
        nvps.add(new BasicNameValuePair("level", "3"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        httpclient.execute(httpPost);
*/
        Map<String, String> paraMap = new HashMap<String, String>();


        String  ss = java.net.URLEncoder.encode(h,"UTF-8");
        paraMap.put("str", h);
        paraMap.put("level","3");
        System.out.println(url);
        String result = HttpUtils.post(url,paraMap);
        System.out.println();
        System.out.println(result);
    }

}
