import sun.misc.Sort;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by feixia on 15/3/13.
 */
/*

http://www.hacker.org/modulo/?seq=0204000201000200020100000000040204010001010301000301020002020502
 */
public class modulo {
    static  int Mod = 3;
    static int leftTop[] = new int[40];
    static int leftDown[] = new int[40];
    static int rightTop[] = new int[40];
    static int rightDown[] = new int[40];
    static int map[][] = new int[20][20];
    static Box box[] = new Box[100];
    static int LX,LY,Max;
    static int cnt,Hcnt = 6;
    static Pair Ans[] = new Pair[42];
    static Pair tmpAns[] = new Pair[42];
    static boolean flag;
    static HashSet<Long>H[] = new HashSet[40];
    static HashSet<Long>HS[] = new HashSet[40];
    static HashSet<Long> T = new HashSet<Long>();
    static int res[] = new int[40];
    static long PreS;
    static boolean Has(int id,long s){
        return  H[id].contains(s);
    }
    static void gaoLeft(){
        HS[cnt] = new HashSet<Long>();
        HS[cnt].add(0l);
        for(int now = cnt; now >= 1; now--) {
            HS[now-1] = new HashSet<Long>();
            Iterator<Long> iterator = HS[now].iterator();
            while (iterator.hasNext()) {
                Long st = iterator.next();
                if(now >= cnt-1)
                System.out.println(st);
                HS[now-1].add(st);
                int lenx = box[now].lenx;
                int leny = box[now].leny;
                Long tmpl[] = new Long[20];
                Long tmpr[] = new Long[20];
                Long ts = st;
                for (int i = 0; i < LX; i++) {
                    tmpl[LX - i - 1] = ts % Mod;
                    ts /= Mod;
                }
                for(int i = 0; i < LX; i++) {
                    tmpr[LX - i - 1] = ts % Mod;
                    ts /= Mod;
                }
                Long t2[] = new Long[20];
                for (int i = 0; i + lenx - 1< LX; i++) {

                   for (int j = 0; j < LX; j++) {
                        t2[j] = tmpl[j];
                    }
                    for (int j = 0; j < lenx; j++) {
                        int k = i + j;
                        if (box[now].s[j][0] == 1) {
                            t2[k] = (t2[k] - 1 + Mod) % Mod;
                        }
                    }
                    Long ss = 0l;
                    for (int j = 0; j < LX; j++) {
                        ss = ss * Mod + t2[j];
                    }
                    HS[now-1].add(ss);
                }

            }
            System.out.println(now + "  ff " + HS[now-1].size());

        }

    }
    static long getStatus(int tmpMap[][]){
        long s = 0;
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                s = s * Mod + tmpMap[i][j];
            }

       /* for (int k = 0; k < LX; k++) {
            s = s * Mod + tmpMap[k][1];
        }
        for (int k = 0; k < LX; k++) {
            s = s * Mod + tmpMap[k][0];
        }
        */
        return s;
    }
    static long getLeftStatus(int tmpMap[][]){
        long s = 0;
        for(int i = 0; i < LX; i++){
            s = s * Mod + tmpMap[i][0];
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
    static boolean checkEdge(int now,int tmpMap[][]){
        int cnt1 = (Mod - tmpMap[0][0]) % Mod;
        if(cnt1 > leftTop[now+1]){
            return false;
        }
        cnt1 = (Mod - tmpMap[LX - 1][0]) % Mod;
        if(cnt1 > leftDown[now+1]){
            return false;
        }
        cnt1 = (Mod - tmpMap[0][LY-1]) % Mod;
        if(cnt1 > rightTop[now+1]){
            return false;
        }
        cnt1 = (Mod - tmpMap[LX-1][LY-1]) % Mod;
        if(cnt1 > rightDown[now+1]){
            return false;
        }
        return true;
    }
    static void go(int now,Pair tmp[],int tmpMap[][],int goID){
        long s = getStatus(tmpMap);
        if(goID == 1) {
            if (now == Hcnt) {
                T.add(s);
                return;
            }
            long lstatus = getLeftStatus(tmpMap);
            if(!HS[now].contains(lstatus)) {
                return;
            }
            if(!checkEdge(now, tmpMap)) {
                return;
            }
            int cnt = needTime(tmpMap);
            if(cnt > res[now+1]&& now <= 8) return;
            if(cnt > res[now+1]) return;

        }else if(goID == 2){
            if(flag) return;
            if(now == cnt){
                if(T.contains(s)) {
                    for (int i = Hcnt; i < cnt; i++) {
                        Ans[i].x = tmp[i].x;
                        Ans[i].y = tmp[i].y;
                    }
                    PreS = s;
                    flag = true;
                }
                return;
            }
        }else{
            if(flag) return;
            if(now == Hcnt){
                if(s == PreS) {
                    for (int i = 0; i < Hcnt; i++) {
                        Ans[i].x = tmp[i].x;
                        Ans[i].y = tmp[i].y;
                    }
                    flag = true;
                }
                return;
            }
            long lstatus = getLeftStatus(tmpMap);
            if(!HS[now].contains(lstatus)) return;
            if(!checkEdge(now,tmpMap)) {
                return;
            }
            int cnt = needTime(tmpMap);
            if(cnt > res[now+1] && now <= 8) return;
            if(cnt > res[now+1]) return;

        }
        if(Has(now,s)) return ;
        H[now].add(s);
        int lx = box[now+1].lenx;
        int ly = box[now+1].leny;
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++){
              if(i + lx > LX || j + ly > LY) continue;
                for(int g = 0; g < box[now+1].size; g++) {
                        int k = box[now+1].x[g];
                        int f = box[now+1].y[g];
                        if(goID == 1 || goID == 3)
                            tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f])) % Mod;
                        else{
                            tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f]) * (Mod-1)) % Mod;
                        }
                    }
                tmp[now].x = i;
                tmp[now].y = j;
                go(now + 1, tmp, tmpMap,goID);
                for(int g = 0; g < box[now+1].size; g++) {
                        int k = box[now+1].x[g];
                        int f = box[now+1].y[g];
                        if(goID == 2)
                            tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f])) % Mod;
                        else{
                            tmpMap[i+k][j+f] = (tmpMap[i+k][j+f] + (box[now+1].s[k][f]) * (Mod-1)) % Mod;
                        }
                    }


            }

        }
    }
    static void init(){
        System.out.println(cnt);
        Arrays.sort(box,1,cnt+1);
        for(int i = 1; i <= cnt; i++){
            System.out.println("ID " + box[i].id + " " + box[i] + " f " + box[i].lenx);
        }
        res[cnt+1] = 0;
        for(int i = cnt ; i >= 1; i--){
            res[i] = res[i+1] + box[i].size;
        }
        //leftTop
        leftTop[cnt+1] = 0;
        for(int i = cnt; i >= 1; i--){
            leftTop[i] = leftTop[i+1] + box[i].s[0][0];
        }
        //rightTop
        rightTop[cnt+1] = 0;
        for(int i = cnt; i >= 1; i--){
            int ly = box[i].leny;
            rightTop[i] = rightTop[i+1] + box[i].s[0][ly-1];
        }
        //leftDown
        leftDown[cnt+1] = 0;
        for(int i = cnt; i >= 1; i--){
            int lx = box[i].lenx;
            leftDown[i] = leftDown[i+1] + box[i].s[lx-1][0];
        }
        //rightDown
        rightDown[cnt+1] = 0;
        for(int i = cnt; i >= 1; i--){
            int ly = box[i].leny;
            int lx = box[i].lenx;
            rightDown[i] = rightDown[i+1] + box[i].s[lx-1][ly-1];
            System.out.println(i + " " + rightDown[i]);
        }
    }
    static void solve(){
        init();
        gaoLeft();
        T.clear();
        flag = false;
        Max = LX * LY;
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
        go(0, tmpAns, tmpMap, 1);
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                tmpMap[i][j] = 0;
            }
        System.out.println("go1Over" + ": " + T.size());
        flag = false;
        for(int i = 0; i < 40; i++) {
            H[i].clear();
        }
        go(Hcnt, tmpAns, tmpMap, 2);
        System.out.println("goOver");
        for(int i = 0; i < 40; i++) {
            H[i].clear();
        }

        flag = false;
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                tmpMap[i][j] = map[i][j];
            }

        if(PreS != 0)
            go(0, tmpAns, tmpMap, 3);
    }
    public static void main(String args[]) {
        String str = Utils.sendPost("http://www.hacker.org/modulo/index.php?gotolevel=22&go=Go+To+Level", "");
        work(str);
        solve();
        String p = "http://www.hacker.org/modulo/?seq=";
        int tx[] = new int[50];
        int ty[] = new int[50];
        for(int i = 0; i < cnt; i++){
            int x = Ans[i].x;
            int y = Ans[i].y;
            tx[box[i+1].id - 1] = x;
            ty[box[i+1].id - 1] = y;
        }
        for(int i = 0; i < cnt; i++){
            int x = tx[i];
            int y = ty[i];
            p += y/10 ;
            p += y % 10 ;
            p += x/10 ;
            p += x % 10;

        }
        System.out.println(p);
    }
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

    }
}