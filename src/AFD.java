import java.util.ArrayList;
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

    public static AFD LeerAFDdeArchivo(String FileAFD, int idAFD) throws NumberFormatException, IOException{
        File archivo = new File(FileAFD);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        int NumeroDeEstados = 0;
        String linea;
        //int fila = 0;
        ArrayList<ArrayList<Integer>> Aux = new ArrayList<>();
        

        while( (linea=br.readLine())!=null ){

            String[] valores = linea.split(",");
            ArrayList<Integer> fila = new ArrayList<>();

            for(int col = 0;col<valores.length;col++){
                //TablaAFD[fila][col] = Integer.parseInt(valores[col]);
                fila.add(Integer.parseInt(valores[col]));
            }
            Aux.add(fila);
            NumeroDeEstados++;
            //fila++;

        }
        
        /*int nFilas = Aux.size();
        int nCols = Aux.get(0).size();*/

        AFD nuevoAfd = new AFD(NumeroDeEstados, 1);

        for(int i=0;i<NumeroDeEstados;i++){
            ArrayList<Integer> fila1 = Aux.get(i);
            for(int j=0;j<257;j++){
                TablaAFD[i][j] = fila1.get(j);
            }
        }


        br.close();
        fr.close();
        return nuevoAfd;





    }

    
}
