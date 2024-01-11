

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DescRecGram_Gram {
    public String Gramatica;
    public AnalizLexico L;
    public ElemArreglo[] ArrReglas = new ElemArreglo[100];
    public int NumReglas = 0;

    public HashSet<String> Vn = new HashSet<>();
    public HashSet<String> Vt = new HashSet<>();

    public DescRecGram_Gram(String sigma, String FileAFD, int IdentifAFD) throws IOException {
        Gramatica = sigma;
        L = new AnalizLexico(Gramatica, FileAFD, IdentifAFD);
        Vn.clear();
        Vt.clear();
    }
    public DescRecGram_Gram(String FileAFD, int IdentifAFD) throws IOException {
        L = new AnalizLexico(FileAFD, IdentifAFD);
        Vn.clear();
        Vt.clear();
    }
    public boolean SetGramatica(String sigma) {
        Gramatica = sigma;
        L.SetSigma(sigma);
        return true;
    }
    public boolean AnalizarGramatica() {
        int token;
        if (G()) {
            token = L.yylex();
            if (token == 0) {
                IdentificarTerminales();
                return true;
            }
        }
        return false;
    }
    private boolean G() {
        if (ListaReglas()) {
            return true;
        }
        return false;
    }

    private boolean ListaReglas() {
        int token;
        if (Reglas()) {
            token = L.yylex();
            if (token == TokensGram_Gram.PC) {
                if (ListaReglasP()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ListaReglasP() {
        int token;
        AnalizLexico e;
        e = L.GetEdoAnalizLexico();
        if (Reglas()) {
            token = L.yylex();
            if (token == TokensGram_Gram.PC) {
                if (ListaReglasP()) {
                    return true;
                }
            }
            return false;
        }
        L.SetEdoAnalizLexico(e);
        return true;
    }

    private boolean Reglas() {
        int token;
        Simbolo simbolo = new Simbolo();
        if (LadoIzquierdo(simbolo)) {
            Vn.add(simbolo.simb);
            token = L.yylex();
            if (token == TokensGram_Gram.FLECHA) {
                if (LadosDerechos(simbolo.simb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean LadoIzquierdo(Simbolo simbolo) {
        int token;
        token = L.yylex();
        if (token == TokensGram_Gram.SIMBOLO) {
            simbolo.simb = L.yyText;
            return true;
        }
        return false;
    }

    private boolean LadosDerechos(String simbolo) {
        if (LadoDerecho(simbolo)) {
            if (LadosDerechosP(simbolo)) {
                return true;
            }
        }
        return false;
    }

    private boolean LadosDerechosP(String simbolo) {
        int token;
        token = L.yylex();
        if (token == TokensGram_Gram.OR) {
            if (LadoDerecho(simbolo)) {
                if (LadosDerechosP(simbolo)) {
                    return true;
                }
            }
            return false;
        }
        L.UndoToken();
        return true;
    }

    private boolean LadoDerecho(String simbolo) {
        ArrayList<Nodo> Lista = new ArrayList<>();
        Lista.clear();
        if (SecSimbolos(Lista)) {
            ArrReglas[NumReglas] = new ElemArreglo();
            ArrReglas[NumReglas].InfSimbolo = new Nodo(simbolo, false);
            ArrReglas[NumReglas++].ListaLadoDerecho = Lista;
            return true;
        }
        return false;
    }

    private boolean SecSimbolos(ArrayList<Nodo> Lista) {
        int token;
        Nodo N;
        token = L.yylex();
        if (token == TokensGram_Gram.SIMBOLO) {
            N = new Nodo(L.yyText, false);
            if (SecSimbolosP(Lista)) {
                Lista.add(0, N);
                return true;
            }
        }
        return false;
    }

    private boolean SecSimbolosP(ArrayList<Nodo> Lista) {
        int token;
        Nodo N;
        token = L.yylex();
        if (token == TokensGram_Gram.SIMBOLO) {
            N = new Nodo(L.yyText, false);
            if (SecSimbolosP(Lista)) {
                Lista.add(0, N);
                return true;
            }
            return false;
        }
        L.UndoToken();
        return true;
    }

    private void IdentificarTerminales() {
        int i;
        for (i = 0; i < NumReglas; i++) {
            for (Nodo N: ArrReglas[i].ListaLadoDerecho) {
                if (!Vn.contains(N.getDato()) && !N.getDato().equals("epsilon")) {
                    N.setTerminal(true);
                    Vt.add(N.getDato());
                }
            }
        }
    }
    public HashSet<String> First(ArrayList<Nodo> l) {
        int i, j;
        Nodo N; 
        HashSet<String> R = new HashSet<>();
        R.clear();
        if (l.size() == 0)
            return R;
        for (j = 0; j < l.size(); j++) {
            N = l.get(j);
            if (N.getTerminal() || N.getDato().equals("epsilon")) {
                R.add(N.getDato());
                return R;
            }
            //N es no terminal.
            for (i = 0; i < NumReglas; i++) {
                if (ArrReglas[i].ListaLadoDerecho.get(0).getDato() == N.getDato()) {
                    continue;
                }
                if (ArrReglas[i].ListaLadoDerecho.get(0).equals(N.getDato())) {
                    R.addAll(First(ArrReglas[i].ListaLadoDerecho));
                }
            }
            if (R.contains("epsilon")) {
                if (j == (l.size() - 1)) {
                    continue;
                }
                R.remove("epsilon");
            } else {
                break;
            }
        }
        return R;
    }
    private HashSet<String> Follow(String SimbNoTerm) {
        HashSet<String> R = new HashSet<>();
        HashSet<String> Aux = new HashSet<>();
        ArrayList<Nodo> ListaPost = new ArrayList<>();

        R.clear();
        int i, j ,k;
        if(ArrReglas[0].InfSimbolo.getDato().equals(SimbNoTerm)) {
            R.add("$");
        }
        for (i = 0; i < NumReglas; i++) {
            for(j = 0; j < ArrReglas[i].ListaLadoDerecho.size(); j++) {
                if (ArrReglas[i].ListaLadoDerecho.get(j).getDato().equals(SimbNoTerm)) {
                    ListaPost.clear();
                    for (k = j+1; k < ArrReglas[i].ListaLadoDerecho.size(); k++){
                        ListaPost.add(ArrReglas[i].ListaLadoDerecho.get(k));
                    }
                    if (ListaPost.size() == 0) {
                        if (!ArrReglas[i].InfSimbolo.getDato().equals(SimbNoTerm)) {
                            R.addAll(Follow(ArrReglas[i].InfSimbolo.getDato()));
                        }
                        break;
                    }
                    Aux.clear();
                    Aux = First(ListaPost);
                    if (Aux.contains("epsilon")) {
                        Aux.remove("epsilon");
                        R.addAll(Aux);
                        if (!ArrReglas[i].InfSimbolo.getDato().equals(SimbNoTerm)){
                            R.addAll(Follow(ArrReglas[i].InfSimbolo.getDato()));
                        }
                    } else {
                        R.addAll(Aux);
                    }
                }
            }
        }
        return R;
    }
}

class Simbolo {
    String simb;
}