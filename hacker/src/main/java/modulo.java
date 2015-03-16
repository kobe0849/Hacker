import sun.misc.Sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
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
    static int cnt,Hcnt;
    static Pair Ans[] = new Pair[42];
    static Pair tmpAns[] = new Pair[42];
    static boolean flag;
    static HashSet<Long>H[] = new HashSet[40];
    static HashSet<Long> T = new HashSet<Long>();
    static int res[] = new int[40];
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
        Hcnt = cnt/2 + 1;
        Arrays.sort(box,1,cnt+1);
        for(int i = 1; i <= cnt; i++){
            System.out.println("ID " + box[i].id + " " + box[i]);
        }

    }
    static boolean Has(int id,long s){
        //System.out.println(id + " " + s);
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
    static int needTime(int tmpMap[][]){
        int cnt = 0;
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                cnt += (Mod - tmpMap[i][j]) % Mod;
            }
        return cnt;
    }
    static void go1(int now,Pair tmp[],int tmpMap[][]){
        long s = getStatus(tmpMap);
        if(now == Hcnt ){
            T.add(s);
            //System.out.println(s);
            return;
        }
        /*
        int rescnt = needTime(tmpMap);

        System.out.println(now + " " + res[now+1]+ "  s " + rescnt);

        if(res[now+1] < rescnt) return;
        */

        if(Has(now,s)) return ;
        H[now].add(s);


        int lx = box[now+1].lenx;
        int ly = box[now+1].leny;
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++){
               // if(now <= 0 && !(i==0 && j == 0)) continue;
                if(i + lx > LX || j + ly > LY) continue;
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f])) % Mod;
                    }
                tmp[now].x = i;
                tmp[now].y = j;
                //System.out.println(tmp[now].x + " dd " + tmp[now].y);
                go1(now + 1, tmp, tmpMap);
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f] * (Mod-1))) % Mod;
                    }


            }

        }
    }
    static long SS;
    static void go(int now,Pair tmp[],int tmpMap[][]){
        if(flag) return;
        long s = getStatus(tmpMap);
        if(now == cnt){
            //System.out.println(s);
            if(T.contains(s)) {
                for (int i = Hcnt; i < cnt; i++) {
                    Ans[i].x = tmp[i].x;
                    Ans[i].y = tmp[i].y;
                    System.out.println(tmp[i].x + " " + tmp[i].y);
                    System.out.println(Ans[i].x + " " + Ans[i].y);
                }
                SS = s;
                flag = true;
            }
            return;
        }
       // int rescnt = needTime(tmpMap);;
       // if(res[now+1] < rescnt) return;
        if(Has(now,s)) return ;
        H[now].add(s);
        int lx = box[now+1].lenx;
        int ly = box[now+1].leny;
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++){
                if(i + lx > LX || j + ly > LY) continue;
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f]) * (Mod-1)) % Mod;
                    }
                tmp[now].x = i;
                tmp[now].y = j;
                go(now+1,tmp,tmpMap);
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f])) % Mod;
                    }


            }

        }

    }
    static void go2(int now,Pair tmp[], int tmpMap[][]){
       if(flag) return;
        long s = getStatus(tmpMap);
        //System.out.println(s);
        if(now == Hcnt){
            if(s == SS) {
                System.out.println("ssdsds");
                for (int i = 0; i < Hcnt; i++) {
                    Ans[i].x = tmp[i].x;
                    Ans[i].y = tmp[i].y;

                    System.out.println(tmp[i].x + " 2 " + tmp[i].y);
                    System.out.println(Ans[i].x + " 2 " + Ans[i].y);
                }
                flag = true;
            }
            return;
        }
        if(Has(now,s)) {
           // System.out.println("hh");
            return ;
        }
        H[now].add(s);
        //System.out.println(now + "  " + s);
        int lx = box[now+1].lenx;
        int ly = box[now+1].leny;
        //System.out.println(lx + " " + ly);
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++){
               // if(now <= 0 && !(i==0 && j == 0)) continue;
                if(i + lx > LX || j + ly > LY) continue;
                int hMap[][] = new int[32][32];
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f])) % Mod;
                    }
                tmp[now].x = i;
                tmp[now].y = j;
                //System.out.println(tmp[now].x + " dd " + tmp[now].y);
                go2(now + 1, tmp, tmpMap);
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f] * (Mod-1))) % Mod;
                    }


            }

        }

    }
    static void solve(){
        res[cnt+1] = 0;
        for(int i = cnt ; i >= 1; i--){
            res[i] = res[i+1] + box[i].size;
        }
        System.out.println(res[1]);
        System.out.println(needTime(map));

        T.clear();
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
        for(int i = 0; i < 40; i++) {
            H[i] = new HashSet<Long>();
            H[i].clear();
        }
        go1(0, tmpAns, tmpMap);

        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                tmpMap[i][j] = 0;
            }
        System.out.println("go1Over" + ": " + T.size());
        flag = false;
        for(int i = 0; i < 40; i++) {
            H[i].clear();
        }
        go(Hcnt, tmpAns, tmpMap);
        System.out.println("goOver" + T.size());
        for(int i = 0; i < 40; i++) {
            H[i].clear();
        }
        flag = false;
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                tmpMap[i][j] = map[i][j];
            }

        if(SS != 0)
            go2(0,tmpAns,tmpMap);
    }

    public static void main(String args[]) {
        String str = Utils.sendPost("http://www.hacker.org/modulo/index.php?gotolevel=22&go=Go+To+Level", "");
        work(str);
        solve();
        /*
        http://www.hacker.org/modulo/?seq=0000020000010000000103000001
         */
        String p = "http://www.hacker.org/modulo/?seq=";
        int tx[] = new int[50];
        int ty[] = new int[50];
       /*for(int i = 0; i < cnt; i++){
            int x = Ans[i].x;
            int y = Ans[i].y;
            System.out.println("hhe " + box[i+1].id + " " + x +" " + y);
            p += y/10 ;
            p += y % 10 ;
            p += x/10 ;
            p += x % 10;
        }
*/
        for(int i = 0; i < cnt; i++){
            System.out.println("sdsd" + box[i+1].id);
            int x = Ans[i].x;
            int y = Ans[i].y;
            tx[box[i+1].id - 1] = x;
            ty[box[i+1].id - 1] = y;

            System.out.println(x + "  " + y);


        }
        for(int i = 0; i < cnt; i++){
            int x = tx[i];
            int y = ty[i];
            p += y/10 ;
            p += y % 10 ;
            p += x/10 ;
            p += x % 10;

        }

        Utils.sendPost(p, "");
        System.out.println(p);
    }
}
/*

sdsd3
0  0
sdsd8
0  0
sdsd10
1  4
sdsd1
1  1
sdsd6
1  2
sdsd7
0  3
sdsd11
1  0
sdsd12
1  2
sdsd2
0  2
sdsd9
3  2
sdsd4
2  0
sdsd5
0  3
http://www.hacker.org/modulo/?seq=010102000000000203000201030000000203040100010201

*/

/*
sdsd6
3  0
sdsd7
3  1
sdsd9
2  1
sdsd11
4  0
sdsd12
5  1
sdsd15
1  2
sdsd13
3  2
sdsd14
4  0
sdsd2
1  0
sdsd3
0  1
sdsd8
3  0
sdsd10
3  0
sdsd4
1  0
sdsd1
1  0
sdsd5
2  0

*/
/*


 */
