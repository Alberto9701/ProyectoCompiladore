import java.util.HashSet;
import java.io.*;

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

    public void LeerAFDdeArchivo(String FileAFD, int idAFD) throws NumberFormatException, IOException{
        File archivo = new File(FileAFD);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        String linea;
        int fila = 0;

        while( (linea=br.readLine())!=null ){
            String[] valores = linea.split(",");

            for(int col = 0;col<valores.length;col++){
                TablaAFD[fila][col] = Integer.parseInt(valores[col]);
            }
            fila++;
        }

        br.close();
        fr.close();


    }
}
