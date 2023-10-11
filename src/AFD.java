import java.util.ArrayList;
import java.util.HashSet;
import java.io.*;
import java.util.List;

class TransEdoAFD {
    public int IdEdo;
    public int[] TransAFD;
}
public class AFD {
    public static HashSet<AFD> ConjAFDs = new HashSet<>();
    public HashSet<Character> Alfabeto = new HashSet<>();
    public int NumEstados;
    public static int[][] TablaAFD;
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

    //metodos clase AFD

    public void CrearArchivoTxt(String FileAFD) throws IOException{
        File archivo = new File(FileAFD);
        FileWriter fw = new FileWriter(archivo);
        BufferedWriter bw = new BufferedWriter(fw);

        for(int fila = 0; fila<NumEstados;fila++){
            for(int col=0;col<257;col++){
                bw.write(TablaAFD[fila][col]+",");
            }
            bw.newLine();
        }
        bw.close();
        fw.close();
    }

    public void LeerAFDdeArchivo(String FileAFD, int IdAFD) throws IOException {
        File archivo = new File(FileAFD);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        // Primero, lee todas las líneas del archivo en una lista
        List<String> lineas = new ArrayList<>();
        String linea;
        while ((linea = br.readLine()) != null) {
            lineas.add(linea);
        }

        // Usa el número de líneas para inicializar TablaAFD
        NumEstados = lineas.size();
        TablaAFD = new int[NumEstados][257];

        // Luego, llena TablaAFD con los valores del archivo
        for (int fila = 0; fila < NumEstados; fila++) {
            String[] valores = lineas.get(fila).split(",");
            for (int col = 0; col < valores.length; col++) {
                TablaAFD[fila][col] = Integer.parseInt(valores[col]);
            }
        }

        br.close();
        fr.close();
    }




}
