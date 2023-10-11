import java.util.*;

public class AFN {
    public static HashSet<AFN> ConjDeAFNs = new HashSet<AFN>();
    Estado EdoIni;
    HashSet<Estado> EdosAFN = new HashSet<Estado>();
    HashSet<Estado> EdosAcept = new HashSet<Estado>();
    HashSet<Character> Alfabeto = new HashSet<Character>();
    boolean SeAgregoAFNUnionLexico;
    public int IdAFN;

    @Override
    public String toString() {
        return Integer.toString(IdAFN);
    }
    public AFN() {
        IdAFN = 0;
        EdoIni = null;
        EdosAFN.clear();
        EdosAcept.clear();
        Alfabeto.clear();
        SeAgregoAFNUnionLexico = false;
    }

    public AFN CrearAFNBasico (char s) {
        Transicion t;
        Estado e1, e2;
        e1 = new Estado();
        e2 = new Estado();
        t = new Transicion(s, e2);
        e1.getTrans().add(t);
        e2.setEdoAcept(true);
        Alfabeto.add(s);
        EdoIni = e1;
        EdosAFN.add(e1);
        EdosAFN.add(e2);
        EdosAcept.add(e2);
        SeAgregoAFNUnionLexico = false;
        return this;
    }
    public AFN CrearAFNBasico (char s1, char s2) {
        char i;
        Transicion t;
        Estado e1, e2;
        e1 = new Estado();
        e2 = new Estado();
        t = new Transicion(s1, s2, e2);
        e1.getTrans().add(t);
        e2.setEdoAcept(true);
        for (i = s1; i <= s2; i++) {
            Alfabeto.add(i);
        }
        EdoIni = e1;
        EdosAFN.add(e1);
        EdosAFN.add(e2);
        EdosAcept.add(e2);
        SeAgregoAFNUnionLexico = false;
        return this;
    }

    public AFN UnirAFN(AFN f2) {
        Estado e1 = new Estado();
        Estado e2 = new Estado();
        Transicion t1 = new Transicion(SimbolosEspeciales.EPSILON, this.EdoIni);
        Transicion t2 = new Transicion(SimbolosEspeciales.EPSILON, f2.EdoIni);
        e1.getTrans().add(t1);
        e1.getTrans().add(t2);
        for (Estado e: this.EdosAcept) {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, e2));
            e.setEdoAcept(false);
        }
        for (Estado e: f2.EdosAcept) {
            e.getTrans().add(new Transicion(SimbolosEspeciales.EPSILON, e2));
            e.setEdoAcept(false);
        }
        this.EdosAcept.clear();
        f2.EdosAcept.clear();
        this.EdoIni = e1;
        e2.setEdoAcept(true);
        this.EdosAcept.add(e2);
        this.EdosAFN.addAll(f2.EdosAFN);
        this.EdosAFN.add(e1);
        this.EdosAFN.add(e2);
        this.Alfabeto.addAll(f2.Alfabeto);
        return this;
    }

    public AFN ConcAFN(AFN f2) {
        for (Transicion t: f2.EdoIni.getTrans()) {
            for (Estado e: this.EdosAcept) {
                e.getTrans().add(t);
                e.setEdoAcept(false);
            }
        }
        f2.EdosAFN.remove(f2.EdoIni);
        this.EdosAcept = f2.EdosAcept;
        this.EdosAFN.addAll(f2.EdosAFN);
        this.Alfabeto.addAll(f2.Alfabeto);
        return this;
    }

    public HashSet<Estado> CerraduraEpsilon(Estado e) {
        HashSet<Estado> R = new HashSet<Estado>();
        Stack<Estado> S = new Stack<Estado>();
        Estado aux, edo;
        R.clear();
        S.clear();

        S.push(e);
        while (S.size() != 0) {
            aux = S.pop();
            R.add(aux);
            for (Transicion t: aux.getTrans()) {
                if ((edo = t.getEdoTrans(SimbolosEspeciales.EPSILON)) != null) {
                    if (!R.contains(edo)) {
                        S.push(edo);
                    }
                }
            }
        }
        return R;
    }
    public HashSet<Estado> Mover(Estado Edo, char Simb) {
        HashSet<Estado> C = new HashSet<Estado>();
        Estado aux;
        C.clear();

        for (Transicion t: Edo.getTrans()) {
            aux = t.getEdoTrans(Simb);
            if (aux != null) {
                C.add(aux);
            }
        }
        return C;
    }
    public HashSet<Estado> Mover(HashSet<Estado> Edos, char Simb) {
        HashSet<Estado> C = new HashSet<Estado>();
        Estado aux;
        C.clear();

        for (Estado Edo: Edos) {
            for (Transicion t: Edo.getTrans()) {
                aux = t.getEdoTrans(Simb);
                if (aux != null) {
                    C.add(aux);
                }
            }
        }
        return C;
    }

    public HashSet<Estado> IrA(HashSet<Estado> Edos, char Simb) {
        HashSet<Estado> C = new HashSet<Estado>();
        HashSet<Estado> ConjEdos = new HashSet<Estado>();
        C.clear();
        ConjEdos.clear();
        ConjEdos = Mover(Edos, Simb);
        for (Estado e: ConjEdos) {
            C = CerraduraEpsilon(e);
        }
        return C;
    }

    public AFD ConvAFNaAFD () {
        int NumEdosAFD;
        int i = 0, ContadorEdos;
        ConjIj Ij, Ik;
        boolean existe;
        
        HashSet<Estado> ConjAux = new HashSet<>();
        HashSet<ConjIj> EdosAFD = new HashSet<>();
        Queue<ConjIj> EdosSinAnalizar = new LinkedList<>();

        EdosAFD.clear();
        EdosSinAnalizar.clear();

        ContadorEdos = 0;
        Ij = new ConjIj();
        Ij.ConjI = CerraduraEpsilon(this.EdoIni);
        System.out.println("Estados de cerradura epsilon");
        for (Estado e: Ij.ConjI) {
            System.out.println(e.getIdEstado());
        }
        Ij.j = ContadorEdos;

        EdosAFD.add(Ij);
        EdosSinAnalizar.add(Ij);
        ContadorEdos++;
        while (EdosSinAnalizar.size() != 0) {
            Ij = EdosSinAnalizar.poll();
            for (char c: this.Alfabeto) {
                Ik = new ConjIj();
                Ik.ConjI = IrA(Ij.ConjI, c);
                
                if (Ik.ConjI.size()==0) {
                    continue;
                }
                existe = false;
                
                for (ConjIj I : EdosAFD) {
                    if (I.ConjI.equals(Ik.ConjI)) {
                        existe = true;
                        Ij.TransicionesAFD[c] = I.j;
                        break;
                    }
                }
                if (!existe) {
                    Ik.j = ContadorEdos;
                    Ij.TransicionesAFD[c] = Ik.j;
                    EdosAFD.add(Ik);
                    EdosSinAnalizar.add(Ik);
                    ContadorEdos++;
                }
            }
        }
        
        for (ConjIj cj : EdosAFD) {
            for (Estado e :  cj.ConjI) {
                for (Estado e2 : this.EdosAcept) {
                    if (e2.equals(e)) {
                        ConjAux.add(e2);
                    }
                }
            }
        }


        NumEdosAFD = ContadorEdos;
        TransEdoAFD hola;
        return null;
    }
}
