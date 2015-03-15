import sun.misc.Sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Created by feixia on 15/3/13.
 */
public class modulo {
    static  int Mod = 3;
    static int map[][] = new int[20][20];
    static Box box[] = new Box[100];
    static int LX,LY;
    static int cnt;
    static Pair Ans[] = new Pair[30];
    static Pair tmpAns[] = new Pair[30];
    static boolean flag;
    static HashSet<Long>H[] = new HashSet[25];

    public static void work(String str){
        int len = str.length();
        String mp1 = "";
        String mp2 = "";
        for(int i = 0; i < len; i++){
            if(str.substring(i,i+13).equals("board\" value=")){
                int j = i + 14;
                while(str.charAt(j)!= '"'){
                    mp1 += str.charAt(j);
                    j++;
                }
                break;
            }
        }
        LX = 0;
        LY = 0;
        cnt = 0;
        for(int i = 0; i < mp1.length(); i++){
            if(mp1.charAt(i) == ','){
                LX++;
                LY=0;
                continue;
            }
            map[LX][LY++] = mp1.charAt(i) - '0';
        }
        LX++;
        for(int i = 0; i < len; i++){
            if(str.substring(i,i+14).equals("pieces\" value=")){
                int j = i + 15;
                while(str.charAt(j)!= '"'){
                    mp2 += str.charAt(j);
                    j++;
                }
                break;
            }
        }
        System.out.println(LX + " " + LY);
        //System.out.println(mp1);
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        String a = "";
        for(int i = 0; i < mp2.length(); i++){
            if(mp2.charAt(i) == ' ' ){
                box[++cnt] = new Box(a,cnt);
                a = "";
                continue;
            }
            a += mp2.charAt(i);
        }
        box[++cnt] = new Box(a,cnt);
        System.out.println(cnt);
       // Arrays.sort(box,1,cnt+1);
        for(int i = 1; i <= cnt; i++){
            System.out.println(box[i]);
        }

    }
    static boolean Has(int id,long s){
        return  H[id].contains(s);
    }
    static  long getStatus(int tmpMap[][]){
        long s = 0;
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                s = s * Mod + tmpMap[i][j];

            }
        return s;
    }
    static void go(int now,Pair tmp[],int tmpMap[][]){
        //System.out.println(now + "sdsd");
        if(flag) return;
        long s = getStatus(tmpMap);
        if(now == cnt){
            if(s == 0) {
                System.out.println("ssdsds");
                for (int i = 0; i < cnt; i++) {
                    Ans[i].x = tmp[i].x;
                    Ans[i].y = tmp[i].y;
                    System.out.println(tmp[i].x + " " + tmp[i].y);
                    System.out.println(Ans[i].x + " " + Ans[i].y);
                }
                flag = true;
            }
            return;
        }
        if(Has(now,s)) return ;
        H[now].add(s);
       // System.out.println(now + "  " + s
        int lx = box[now+1].lenx;
        int ly = box[now+1].leny;
        //System.out.println(lx + " " + ly);
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++){
                if(i + lx > LX || j + ly > LY) continue;
                int hMap[][] = new int[30][30];
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f])) % Mod;
                    }
                tmp[now].x = i;
                tmp[now].y = j;
                //System.out.println(tmp[now].x + " dd " + tmp[now].y);
                go(now+1,tmp,tmpMap);
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f] * (Mod-1))) % Mod;
                    }


            }

        }

    }
    static void solve(){
        flag = false;
        int Max = LX * LY;
        for(int i = 0; i < cnt; i++){
            tmpAns[i] = new Pair(0,0);
            Ans[i] = new Pair(0,0);
        }
        int tmpMap[][] = new int[30][30];
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                tmpMap[i][j] = map[i][j];
            }
        for(int i = 0; i < 25; i++) {
            H[i] = new HashSet<Long>();
            H[i].clear();
        }
        go(0, tmpAns, tmpMap);
    }

    public static void main(String args[]) {
        String str = Utils.sendPost("http://www.hacker.org/modulo/index.php?gotolevel=22&go=Go+To+Level", "");
        work(str);
        solve();
        /*
        http://www.hacker.org/modulo/?seq=0000020000010000000103000001
         */
        String p = "http://www.hacker.org/modulo/?seq=";
        for(int i = 0; i < cnt; i++){
            int x = Ans[i].x;
            int y = Ans[i].y;
            System.out.println(x + "  " + y);
            p += y/10 ;
            p += y % 10 ;
            p += x/10 ;
            p += x % 10;

        }
        Utils.sendPost(p,"");
        System.out.println(p);
    }
}
