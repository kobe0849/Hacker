/**
 * Created by feixia on 15/3/12.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class RunAway {
    static int Map[][] = new int[1010][1010];
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        param+="&name=kobe0849&password=kobe20110849";
        System.out.println(param);
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static String getAllHtml(String urlString) {
        try {
            java.net.URL url = new java.net.URL(urlString);  //根据 String 表示形式创建 URL 对象。
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();// 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
            java.io.InputStreamReader isr = new java.io.InputStreamReader(conn.getInputStream());//返回从此打开的连接读取的输入流。
            java.io.BufferedReader br = new java.io.BufferedReader(isr);//创建一个使用默认大小输入缓冲区的缓冲字符输入流。

            String temp;
            String ans=null;
            while ((temp = br.readLine()) != null) {  //按行读取输出流
                //  if(temp.contains("FlashVars")&&temp.contains("value"))
                ans+=temp+"\n";
            }
            br.close();
            isr.close();
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    static int getval(String tmp,String r){
        int len  = r.length();
        int res = 0;
        for(int i = 0; i < tmp.length(); i++){
            if(tmp.substring(i,i+len).equals(r)){
                int j = i + len + 1;
                while(tmp.charAt(j) != '&'){
                    res = res * 10 + (tmp.charAt(j) - '0');
                    j++;
                }
                break;

            }
        }
        return res;
    }
    static void getMap(String tmp,String r,int lenx,int leny){
        for(int i = 0; i <= lenx + 10; i++)
            for(int j = 0; j < leny + 10; j++){
                Map[i][j] = 0;
            }
        int len = r.length();
        for(int i = 0; i < tmp.length(); i++){
            if(tmp.substring(i,i + len).equals(r)){
                int j = i + len + 1;
                for(int k = 0; k < lenx; k++)
                    for(int f = 0; f < leny; f++){
                        if(tmp.charAt(j) == '.'){
                            Map[k][f] = 0;
                        }else Map[k][f] = 1;
                        j++;
                    }
                break;

            }
        }
        for(int i = 0; i < lenx; i++) {
            for (int j = 0; j < leny; j++) {
                System.out.print(Map[i][j] + " ");
            }
            System.out.println();
        }


    }
    static String Ans;
    static int flag = 0;
    static int t[][] = new int[1000][1000];
    static int vis[][] = new int[1000][1000];
    static void dfs(int x,int y,int mx,int my,String ans){
        System.out.println(x + " ss " + y + " " + mx + " " + my);
        if(x == mx && y == my){
            flag = 1;
            Ans = ans;
            return;
        }
        if(vis[x][y] == 1) return;
        vis[x][y] = 1;
        String s1 = ans,s2 = ans;
        if(x + 1 <= mx && t[x+1][y] != 1){
            dfs(x + 1,y,mx,my,s1 += 'D');
        }
        if(y + 1 <= my && t[x][y+1] != 1){
            dfs(x,y + 1,mx,my,s2 += 'R');
        }
    }
    static String getans(int t[][],int lx,int ly){
        System.out.println(lx + " " + ly);
        for(int i = 0; i <= lx; i++)
            for(int j = 0; j <= ly; j++){
                vis[i][j] = 0;
            }
        Ans = "";
        String ans = "";
        flag = 0;
        dfs(0,0,lx,ly,ans);
        return Ans;
    }
    static String work(String s){
        String tmp1 = "";
        for(int i = 0; i < s.length(); i++){
            if(s.substring(i,i+10).equals("FlashVars=")){
                int j = i + 11;
                while(s.charAt(j) != '"') {tmp1 += s.charAt(j);j++;}
                break;
            }

        }
        System.out.println(tmp1);
        int Max = getval(tmp1,"FVinsMax");
        int Min = getval(tmp1,"FVinsMin");
        System.out.println(Min + " " + Max);
        int lenx = getval(tmp1,"FVboardX");
        int leny = getval(tmp1,"FVboardY");
        System.out.println(lenx + "  " + leny);
        getMap(tmp1,"FVterrainString",lenx,leny);
        for(int l = Min; l <= Max; l++){
            for(int right = 0; right <= l; right++){
                int x = 0,y = 0;
                int down = l - right;
                for(int k = 0; k <= down; k++)
                    for(int f = 0; f <= right; f++){
                        t[k][f] = 0;
                    }
                while(x <= lenx && y <= leny ){
                    for(int k = 0; k <= down; k++)
                        for(int f = 0; f <= right; f++){
                            if(Map[x + k][y + f] == 1){
                                t[k][f] = 1;
                            }

                        }
                    x += down;
                    y += right;
                }
                String str = getans(t,down,right);
                if(str != "") {
                    System.out.println(str);
                    return str;

                }


            }

        }

        return "";

    }
    public static void main(String[] args) {
        for(int h = 7; h <= 100; ++h) {
            String s = sendPost("http://www.hacker.org/runaway/index.php", "");
            String ans =  "path=";
            ans += work(s);
            sendPost("http://www.hacker.org/runaway/index.php", ans);

            System.out.println("hello");
        }
        // System.out.println(s);
    }
}
