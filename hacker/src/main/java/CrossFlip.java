import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by feixia on 15/3/12.
 */
public class CrossFlip {
    static int Row,Col;
    static int mp[][] = new int[1010][1010];
    public static void work(String str){
        String tmp = "";
        int cnt = 0;
        Col = 0;
        int len = str.length();
        for(int i = 0; i < len; i++){
            if(str.substring(i,i+9).equals("boardinit")){
                int j = i + 14;
                int col = 0;
                while(str.charAt(j) != '"'){
                    tmp += str.charAt(j);

                    if(str.charAt(j) == ','){
                        cnt++;
                        Col = col;
                        col = 0;
                        continue;
                    }

                    mp[cnt][col] = str.charAt(j) - '0';
                    col ++;
                }
                break;

            }
        }
        System.out.println(Row + " " + Col);
        for(int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++)
                System.out.println(mp[i][j] + " ");
            System.out.println();
        }
    }
    public static void main(String args[]){
        /*http://www.hacker.org/cross/?lvl=0&sol=0100*/
        String str = Utils.sendPost("www.hacker.org/cross/index.php","");
        work(str);

    }

}