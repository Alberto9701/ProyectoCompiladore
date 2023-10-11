import java.util.HashSet;

public class ConjIj {
    public int j;
    public HashSet<Estado> ConjI;
    public int[] TransicionesAFD = new int[257];

    public ConjIj() {
        j = -1;
        ConjI = new HashSet<>();
        ConjI.clear();
        for (int k = 0; k < 257; k++) {
            TransicionesAFD[k] = -1;
        }

    }
}
