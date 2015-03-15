/**
 * Created by feixia on 15/3/13.
 */
public class Box {
    int lenx,leny;
    int s[][];
    Box(String str){
        int len = str.length();
        s = new int[30][30];
        lenx = 0;
        leny = 0;
        for(int i = 0; i < len; i++){
            if(str.charAt(i) == 'X'){
                s[lenx][leny++] = 1;
            }else if(str.charAt(i) == '.'){
                s[lenx][leny++] = 0;
            }else{
                leny = 0;
                lenx++;
            }
        }
        lenx++;
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
            }
            ans += "\n";
        }
        return ans;

    }
}