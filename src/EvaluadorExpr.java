import java.io.IOException;

public class EvaluadorExpr {
    String Expresion;
    public float result;
    public String ExprPost;
    public AnalizLexico L;

    public EvaluadorExpr(String sigma, AFD AutFD){
        Expresion = sigma;
        L = new AnalizLexico(Expresion, AutFD);
    }

    public EvaluadorExpr(String sigma, String FileAFD, int IdentiAFD) throws NumberFormatException, IOException{
        Expresion = sigma;
        L= new AnalizLexico(Expresion, FileAFD, IdentiAFD);
    }

    public EvaluadorExpr(String FileAFD, int IdentiAFD) throws NumberFormatException, IOException{
        L = new AnalizLexico(FileAFD, IdentiAFD);
    }

    public void SetExpresion(String sigma){
        Expresion = sigma;
        L.SetSigma(sigma);
    }

    public boolean IniVal(){
        int Token;
        float v;
        String Postfijo= "";
        v = 0;
        if(E(v,Postfijo)){
            Token = L.yylex();
            if(Token==0){
                this.result = v;
                this.ExprPost = Postfijo;
                return true;
            }        
        }
        return false;
    }

    boolean E(float v, String Post){
        if(T(v,Post)){
            if(Ep(v,Post)){
                return true;
            }
        }
        return false;
    }

    boolean Ep(float v, String Post){
        int Token;
        float v2 = 0;
        String Post2 = "";
        Token=L.yylex();
        if(Token==10 || Token ==20){
            if(T(v2,Post2)){
                v = v + (Token == 10? v2:-v2 );
                Post = Post + " " + Post2+ " " + (Token == 10?"+":"-");
                if(Ep(v,Post)){
                    return true;
                } 
            }
            return false;
        }
        L.UndoToken();
        return true;

    }

    boolean T(float v, String Post){
        if(F(v, Post)){
            if(Tp(v, Post)){
                return true;
            }
        }
        return false;
    }

    boolean Tp(float v, String Post){
        int Token;
        float v2 = 0;
        String Post2 = "";
        Token = L.yylex();

        if(Token==30 || Token ==40){
            if(F(v2,Post2)){
                v = v * (Token ==30?v2:1/v2);
                Post = Post + " " + Post2 + " " + (Token == 30?"*":"/");
                if(Tp(v,Post)){
                    return true;
                }
            }
            return false;
        }
        L.UndoToken();
        return true;
    }

    boolean F(float v, String Post){
        int Token;
        Token = L.yylex();
        switch(Token){
            case 50:
                if(E(v,Post)){
                    Token = L.yylex();
                    if(Token==60){
                        return true;
                    }
                }
                return false;
            case 70:
                v = Float.parseFloat(L.yyText);
                Post = L.yyText;
                return true;
            
        }
        return false;
    }


}
