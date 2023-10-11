import java.util.HashSet;

class TransEdoAFD {
    public int IdEdo;
    public int[] TransAFD;
}
public class AFD {
    public static HashSet<AFD> ConjAFDs = new HashSet<>();
    public HashSet<Character> Alfabeto = new HashSet<>();
    public int NumEstados;
    public int[][] TablaAFD;
    public int idAFD;

    public AFD() {
        idAFD  = -1;
    }

    public AFD(int NumeroDeEstados, int IdAutFD) {
        TablaAFD = new int[NumeroDeEstados][257];
        for (int i = 0; i < NumeroDeEstados; i++) {
            for (int j = 0; j <257; j++) {
                TablaAFD[i][j] = -1;
            }
        }
        NumEstados = NumeroDeEstados;
        idAFD = IdAutFD;
        AFD.ConjAFDs.add(this);
    }
}
