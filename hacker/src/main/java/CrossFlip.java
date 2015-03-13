import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feixia on 15/3/12.
 */
public class CrossFlip {
    static int Row, Col;
    static char mp[][] = new char[2010][2010];
    static MyBitSet mat[] = new MyBitSet[41010];

    static int getId(int x, int y) {
        return x * Col + y;
    }

    public static String format(long ms) {//将毫秒数换算成x天x时x分x秒x毫秒
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;
    }

    public static void solve() {
        System.out.println(Row + " " + Col);
        int Max = getId(Row - 1, Col - 1) + 1;
        long startTime = System.currentTimeMillis();
        System.out.println("Start");
        for (int i = 0, j = 0; i < Max; i++, j++) {
            int k = j;
            for (; mat[k].get(i) == false && k < Max; k++) ;
            if (k == Max) continue;
            for (int f = 0; f <= Max; f++) {
                boolean tmp = mat[k].get(f);
                mat[k].set(f, mat[j].get(f));
                mat[j].set(f, tmp);
            }
            for (int f = 0; f < Max; f++)
                if (f != j && mat[f].get(i) == true) {
                    mat[f].xor(mat[j]);
                }
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        String time = format(endTime - startTime);
        System.out.println("时间:" + time);
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");

    }

    public static void work(String str) {
        String tmp = "";
        int cnt = 0;
        Col = 0;
        int len = str.length();

        for (int i = 0; i < len; i++) {
            if (str.substring(i, i + 9).equals("boardinit")) {
                int j = i + 13;
                int col = 0;
                while (str.charAt(j) != '"') {
                    tmp += str.charAt(j);
                    // System.out.println(str.charAt(j));

                    if (str.charAt(j) == ',') {
                        cnt++;
                        Col = col;
                        col = 0;
                        j++;
                        continue;
                    }
                    mp[cnt][col] = str.charAt(j);
                    col++;
                    j++;
                }
                break;

            }
        }
        Row = cnt + 1;
        /*
        for(int i = 0; i < Row; i++) {
            for (int j = 0; j < Col; j++)
                System.out.print(mp[i][j] + " ");
            System.out.println();
        }
        */
        int Max = getId(Row - 1, Col - 1) + 1;

        for (int i = 0; i <= Max; i++) {
            mat[i] = new MyBitSet(Max + 100);
            mat[i].clear();

        }

        for (int i = 0; i < Row; i++)
            for (int j = 0; j < Col; j++) {
                mat[getId(i, j)].set(Max, mp[i][j] == '1');
                goUp(i, j);
                goDown(i, j);
                goLeft(i, j);
                goRight(i, j);
            }

        solve();

    }

    static void goUp(int x, int y) {
        for (int i = 0; ; i++) {
            if (x - i < 0 || mp[x - i][y] == '2') break;
            int id1 = getId(x, y);
            int id2 = getId(x - i, y);
            mat[id1].set(id2, true);

        }
    }

    static void goDown(int x, int y) {
        for (int i = 0; ; i++) {
            if (x + i >= Row || mp[x + i][y] == '2') break;
            int id1 = getId(x, y);
            int id2 = getId(x + i, y);
            mat[id1].set(id2, true);
        }
    }

    static void goLeft(int x, int y) {
        for (int i = 0; ; i++) {
            if (y - i < 0 || mp[x][y - i] == '2') break;
            int id1 = getId(x, y);
            int id2 = getId(x, y - i);
            mat[id1].set(id2, true);

        }
    }

    static void goRight(int x, int y) {
        for (int i = 0; ; i++) {
            if (y + i >= Col || mp[x][y + i] == '2') break;
            int id1 = getId(x, y);
            int id2 = getId(x, y + i);
            mat[id1].set(id2, true);
        }
    }

    public static void main(String args[]) {
        /*http://www.hacker.org/cross/?lvl=0&sol=0100*/
        for (Integer level = 183; level <= 480; level++) {
            String str = Utils.sendPost("http://www.hacker.org/cross/index.php", "");
            work(str);
            String ans = "lvl=";
            ans += level.toString();
            ans += "&sol=";
            int Max = getId(Row - 1, Col - 1) + 1;
            for (int i = 0; i < Max; i++) {
                if (mat[i].get(Max)) {
                    ans += '1';
                } else ans += '0';
            }
            Utils.sendPost("http://www.hacker.org/cross/?", ans);
        }
    }

}