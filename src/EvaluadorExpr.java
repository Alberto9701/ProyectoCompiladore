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

    


}
