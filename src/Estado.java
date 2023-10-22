import java.util.HashSet;

public class Estado {
    public static int valorToken = 10;
    static int ContadorIdEstado = 0;
    private int idEstado;
    private boolean EdoAcept;
    private int Token;
    private HashSet<Transicion> Trans = new HashSet<Transicion>();

    public Estado() {
        EdoAcept = false;
        Token = -1;

        idEstado = ContadorIdEstado++;
        Trans.clear();
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public boolean isEdoAcept() {
        return EdoAcept;
    }

    public void setEdoAcept(boolean edoAcept) {
        EdoAcept = edoAcept;
    }

    public int getToken() {
        return Token;
    }

    public void setToken(int token) {
        Token = token;
    }

    public HashSet<Transicion> getTrans() {
        return Trans;
    }

    public void setTrans(HashSet<Transicion> trans) {
        Trans = trans;
    }

    @Override
    public String toString() {
        return Integer.toString(idEstado);
    }
}
