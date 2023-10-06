public class Transicion {
    private char SimbInf;
    private char SimbSup;
    private Estado Edo;

    public Transicion (char simb, Estado e) {
        SimbInf = simb;
        SimbSup = simb;
        Edo = e;
    }

    public Transicion (char simb1, char simb2, Estado e) {
        SimbInf = simb1;
        SimbSup = simb2;
        Edo = e;
    }

    public Transicion () {
        Edo = null;
    }

    public void setTransicion( char s1, char s2, Estado e) {
        SimbInf = s1;
        SimbSup = s2;
        Edo = e;
    }
    public void setTransicion( char s1, Estado e) {
        SimbInf = s1;
        SimbSup = s1;
        Edo = e;
    }

    public char getSimbInf() {
        return SimbInf;
    }

    public void setSimbInf(char simbInf) {
        SimbInf = simbInf;
    }

    public char getSimbSup() {
        return SimbSup;
    }

    public void setSimbSup(char simbSup) {
        SimbSup = simbSup;
    }

    public Estado getEdoTrans(char s) {
        if(SimbInf <= s && s <= SimbSup) {
            return Edo;
        }
        return null;
    }
}
