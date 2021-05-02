import java.util.HashSet;
import java.util.Arrays;

public class Validator {
    public static Character[] ops = {'.', '×', '-', '+', '÷'};
    public static HashSet<Character> operators= new HashSet<>(Arrays.asList(ops));

    public static Character[] temp = {'×', '-', '+', '÷'};
    public static HashSet<Character> arightmeticOps= new HashSet<>(Arrays.asList(temp));

    public static boolean checkValidity(String str){
        if ( !hasValidParanthesis(str)){
            return false;
        }
        if ( !hasValidOperator(str)){
            return false;
        }      
        return true;
    }
    public static boolean hasValidOperator(String str){
        int nChars = str.length();
        if (nChars==1){
            if (operators.contains(str.charAt(0))){
                return false;
            } 
        } else {
            if (str.charAt(nChars-1)==str.charAt(nChars-2) && operators.contains(str.charAt(nChars-1)) ){
                return false;
            } else if (operators.contains(str.charAt(nChars-1)) && operators.contains(str.charAt(nChars-2))){
                return false;
            }
        }
        return true;
    }
    public static boolean hasValidParanthesis(String str){
        int open=0;
        for(int i=0; i<str.length(); i++){
            if (str.charAt(i)!='(' && str.charAt(i)!=')'){
                continue;
            }


            if (str.charAt(i)=='('){
                open++;
                if (i>0 && !arightmeticOps.contains(str.charAt(i-1)) ){
                    return false;
                }                 
            } else if (str.charAt(i)==')'){
                open--;
                if (i>0 && operators.contains(str.charAt(i-1)) ){
                    return false;
                }                
            }
            if (open<0){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args){
        System.out.println(Validator.checkValidity("))"));
    }
 
}
