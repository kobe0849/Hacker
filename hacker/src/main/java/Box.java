import java.util.Comparator;

/**
 * Created by feixia on 15/3/13.
 */
public class Box implements Comparable<Box>{
    int lenx,leny;
    int S;
    int id;
    int size;
    int s[][];
    int x[];
    int y[];
    Box(String str,int _id){
        size = 0;
        id = _id;
        int len = str.length();
        s = new int[30][30];
        x = new int[30];
        y = new int[30];
        lenx = 0;
        leny = 0;
        for(int i = 0; i < len; i++){
            if(str.charAt(i) == 'X'){
                s[lenx][leny++] = 1;
                size++;
                x[size-1] = lenx;
                y[size-1] = leny-1;
            }else if(str.charAt(i) == '.'){
                s[lenx][leny++] = 0;
            }else{
                leny = 0;
                lenx++;
            }
        }
        lenx++;
        S = lenx * leny;
    };
    @Override
    public String toString(){
        String ans = "";
        Integer a = new Integer(lenx);
        Integer b = new Integer(leny);
        ans += a.toString() + " " + b.toString();
        ans += "\n";
        for(int i = 0; i < lenx; i++) {
            for (int j = 0; j < leny; j++) {
                ans += (char) (s[i][j] + '0');
                ans += " ";
            }
            ans += "\n";
        }
        return ans;

    }
    public int getScore(){
        int score = 0;
        //if(s[lenx-1][leny-1] != 0)
        //    score += 1000;
        score += (7-lenx) * (6 - leny);
        return score;
    }
    @Override
        public int compareTo(Box o) {
                // 按名字排序
                 return  -o.getScore() + this.getScore();
           }
}
