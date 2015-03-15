/**
 * Created by feixia on 15/3/13.
 */
public class modulo {
    static int map[][] = new int[20][20];
    static Box box[] = new Box[100];
    static int dp[][] = new int[10][1<<15];
    static int LX,LY;
    static int cnt;
    static Pair Ans[] = new Pair[30];
    static Pair tmpAns[] = new Pair[30];
    static boolean flag;

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

        System.out.println(mp1);
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        String a = "";
        for(int i = 0; i < mp2.length(); i++){
            if(mp2.charAt(i) == ' ' ){
                box[++cnt] = new Box(a);
                a = "";
                continue;
            }
            a += mp2.charAt(i);
        }
        box[++cnt] = new Box(a);
        for(int i = 1; i <= cnt; i++){
            System.out.println(box[i]);
        }
    }
    static boolean Has(int id,int s){
        return  dp[id][s] == 1;
    }
    static int getStatus(int tmpMap[][]){
        int s = 0;
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                s = s << 1 | tmpMap[i][j];

            }
        return s;
    }
    static void go(int now,Pair tmp[],int tmpMap[][]){
        if(now == cnt){
            flag = true;
            return;
        }
        int s = getStatus(tmpMap);
        if(Has(now,s)) return ;
        dp[now][s] = 1;
        int lx = box[now].lenx;
        int ly = box[now].leny;
        for(int i = 0; i < LX; i++){
            for(int j = 0; j < LY; j++){
                if(i + lx >= LX || j + ly >= LY) continue;
                int hMap[][] = new int[30][30];
                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] ^= box[now].s[k][f];
                    }
                tmp[now].x = i;
                tmp[now].y = j;
                go(now+1,tmp,tmpMap);

                for(int k = 0; k < lx; k++)
                    for(int f = 0; f < ly; f++){
                        tmpMap[i+k][j+f] ^= box[now].s[k][f];
                    }


            }

        }

    }
    static void solve(){
        flag = false;
        int Max = LX * LY;
        for(int i = 0; i < cnt; i++){
            tmpAns[i] = new Pair(0,0);
        }
        int tmpMap[][] = new int[30][30];
        for(int i = 0; i < LX; i++)
            for(int j = 0; j < LY; j++){
                tmpMap[i][j] = map[i][j];
            }
        go(0,tmpAns,tmpMap);
    }
    public static void main(String args[]) {
        System.out.println(new Box("XXX,.XX"));
        String str = Utils.sendPost("http://www.hacker.org/modulo/index.php?gotolevel=40&go=Go+To+Level", "");
        work(str);
        solve();
    }
}
