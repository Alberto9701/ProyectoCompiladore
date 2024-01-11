public class Nodo {
    public String dato;
    private boolean terminal;
    private Nodo siguiente;

    // Constructor
    public Nodo(String dato, boolean terminal) {
        this.dato = dato;
        this.terminal = false;
        this.siguiente = null;
    }

    public Nodo() {
    }

    // Métodos getter y setter para el dato
    public String getDato() {
        return dato;
    }

    public boolean getTerminal(){
        return terminal;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }
    // Métodos getter y setter para el siguiente nodo
    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}

