import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username","admin");
        params.put("password","admin");
        String info = doGet("https://www.baidu.com",params);
        System.out.println(info);

//        //打开浏览器
//        CloseableHttpClient client = HttpClients.createDefault();
//        //输入网址
//        String url = "https://www.baidu.com";
//        //敲回车发送请求
//        HttpGet get = new HttpGet(url);
//        CloseableHttpResponse response = client.execute(get);
//        //解析服务器端的响应信息
//        int statusCode = response.getStatusLine().getStatusCode();
//        if(statusCode == 200){
//           //获取响应信息
//           HttpEntity entity = response.getEntity();
////            //这个输入流对着服务端的响应内容
////            InputStream ips = entity.getContent();
////            //看具体业务
////            byte[] bs = new byte[1024];
////            int len;
////            while((len=ips.read(bs))!=-1){
////                System.out.println(new String(bs,0,len));
////            }
//            String info = EntityUtils.toString(entity, "utf-8");
//            System.out.println(info);
//        }else{
//            System.out.println(statusCode);
//        }
//        client.close();
    }

    //
    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    //
    public static String doGet(String url) throws IOException {
        //打开浏览器
        CloseableHttpClient client = HttpClients.createDefault();
        //敲回车发送请求
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);
        //解析服务器端的响应信息
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 200){
            //获取响应信息
            HttpEntity entity = response.getEntity();
            String info = EntityUtils.toString(entity, "utf-8");
            return info;
        }else{
            return String.valueOf(statusCode);
        }
    }
}
