import java.io.IOException;
import java.util.*;
public class AnalizLexico {
	int token, EdoActual, EdoTransicion;
	String CadenaSigma;//cadena que va a ser analizada
	public String yyText;
	boolean PasoPorEdoAcept;
	int IniLexema, FinLexema, IndiceCaracterActual;
	//inilexema: en donde empieza el lexema se queda anclado
	//finlexema: cada vez que pasa por un estado de aceptacion se actualiza finlexema con el indice que corresponda
	//inidicecaracteractual: va avanzando en el proceso de revision
	char CaracterActual;
	Stack<Integer> Pila = new Stack<Integer>(); //pila para guarda las posiciones por si tengo que hacer el undoToken
	AFD AutomataFD;
	

	//constructores
	public AnalizLexico(){
		CadenaSigma = "";
		PasoPorEdoAcept = false;
		IniLexema = FinLexema -1;
		IndiceCaracterActual = -1;
		token = -1;
		Pila.clear();
		AutomataFD = null;
	}

	public AnalizLexico(String sigma, String FileAFD, int IdAFD) throws NumberFormatException, IOException{
		AutomataFD = new AFD();
		CadenaSigma = sigma;
		PasoPorEdoAcept = false;
		IniLexema = 0;
		FinLexema = -1;
		IndiceCaracterActual = 0;
		token = -1;
		Pila.clear();
		//metodo de la clase afd pendiente
		AutomataFD.LeerAFDdeArchivo(FileAFD, IdAFD);

	}

	public AnalizLexico(String sigma, String FileAFD) throws NumberFormatException, IOException{
		AutomataFD = new AFD();
		CadenaSigma = sigma;
		PasoPorEdoAcept = false;
		IniLexema = 0;
		FinLexema = -1;
		IndiceCaracterActual = 0;
		token = -1;
		Pila.clear();
		//metodo de la clase afd pendiente
		AutomataFD.LeerAFDdeArchivo(FileAFD, -1);

	}

	public AnalizLexico(String FileAFD, int IdAFD) throws NumberFormatException, IOException{
		AutomataFD = new AFD();
		CadenaSigma = "";
		PasoPorEdoAcept = false;
		IniLexema = 0;
		FinLexema = -1;
		IndiceCaracterActual = 0;
		token = -1;
		Pila.clear();
		//metodo de la clase afd pendiente
		AutomataFD.LeerAFDdeArchivo(FileAFD, IdAFD);

	}

	public AnalizLexico(String sigma,AFD AutFD){
		AutomataFD = AutFD;
		CadenaSigma = sigma;
		PasoPorEdoAcept = false;
		IniLexema = 0;
		FinLexema = -1;
		IndiceCaracterActual = 0;
		token = -1;
		Pila.clear();

	}

	//metodos de la clase

	//metodo para sacarle una fotografia a toda la infomacion del proceso del analisis lexico
	//en cierto momento, podemos guardar la info de las variables para despues poderlas utilizar
	public AnalizLexico GetEdoAnalizLexico(){
		AnalizLexico EdoActualAnaliz = new AnalizLexico();
		EdoActualAnaliz.CaracterActual = CaracterActual;
		EdoActualAnaliz.EdoActual = EdoActual;
		EdoActualAnaliz.EdoTransicion = EdoTransicion;
		EdoActualAnaliz.FinLexema = FinLexema;
		EdoActualAnaliz.IndiceCaracterActual = IndiceCaracterActual;
		EdoActualAnaliz.IniLexema = IniLexema;
		EdoActualAnaliz.yyText = yyText;
		EdoActualAnaliz.PasoPorEdoAcept = PasoPorEdoAcept;
		EdoActualAnaliz.token = token;
		EdoActualAnaliz.Pila = Pila;
		return EdoActualAnaliz;
	}

	//para establecer el estado del analizador lexico con la informacion
	//que se obtuvo en cierto instante
	public boolean SetEdoAnalizLexico(AnalizLexico e){
		CaracterActual = e.CaracterActual;
		EdoActual = e.EdoActual;
		EdoTransicion = e.EdoTransicion;
		FinLexema = e.FinLexema;
		IndiceCaracterActual = e.IndiceCaracterActual;
		IniLexema = e.IniLexema;
		yyText = e.yyText;
		PasoPorEdoAcept = e.PasoPorEdoAcept;
		token = e.token;
		Pila = e.Pila;
		return true;
	}

	//metodo para establecer la cadena que deseo analizar

	public void SetSigma(String sigma){
		CadenaSigma = sigma;
		PasoPorEdoAcept = false;
		IniLexema = 0;
		FinLexema = -1;
		IndiceCaracterActual = 0;
		token = -1;
		Pila.clear();
	}

	//unicamente para fines didacticos para mostrar en la ejecucion que parte falta por
	//analizarse, no tiene una utilidad adicional
	public String CadenaXAnalizar(){
		return CadenaSigma.substring(IndiceCaracterActual, CadenaSigma.length() - (IndiceCaracterActual+1));
	}

	//
	public int yylex(){
		while(true){
			//recordemos que la pila tiene la info para restaurar al analizador a un
			//estado previo para hacer el undoToken
			Pila.push(IndiceCaracterActual);
			if(IndiceCaracterActual >= CadenaSigma.length()){
				yyText = "";
				return SimbolosEspeciales.FIN;
			}
			IniLexema = IndiceCaracterActual;
			EdoActual = 0;
			PasoPorEdoAcept = false;
			FinLexema = -1;
			token = -1;
			while(IndiceCaracterActual < CadenaSigma.length()){
				//vamos a traer el caracter actual
				CaracterActual = CadenaSigma.charAt(IndiceCaracterActual);
				//uso mi tabla con el estado actual que al inicio es cero, con el caracter actual
				//en la columna del codigo assci que le corresponde al caracter actual.
				EdoTransicion = AutomataFD.TablaAFD[EdoActual][(int)CaracterActual];
				System.out.println("el edo actual es");
				System.out.println(EdoActual);
				System.out.println("el caracter es");
				System.out.println(CaracterActual);
				System.out.println("el edo trans es ");
				System.out.println(EdoTransicion);
				if(EdoTransicion != -1){
					if(AutomataFD.TablaAFD[EdoTransicion][256] != -1){// si hay un valor diferente de menos uno significa que estamos en un estado de aceptacion
						System.out.println("paso por edo");
						PasoPorEdoAcept = true;
						token = AutomataFD.TablaAFD[EdoTransicion][256];
						FinLexema = IndiceCaracterActual;
					}
					IndiceCaracterActual++;
					EdoActual = EdoTransicion;
					continue;
				}
				break;

			}

			if(PasoPorEdoAcept==false){
				IndiceCaracterActual = IniLexema + 1;
				yyText = CadenaSigma.substring(IniLexema, IniLexema+1);
				token = SimbolosEspeciales.ERROR;
				return token;
			}

			//no hay transicion con el caracter actual, pero ya se habia pasado por edo de aceptacion
			yyText = CadenaSigma.substring(IniLexema, FinLexema+1);
			IndiceCaracterActual = FinLexema +1;
			if(token == SimbolosEspeciales.OMITIR)
				continue;
			else
				return token;
		}
	}

	public boolean UndoToken(){
		if(Pila.size()==0){
			return false;
		}


		IndiceCaracterActual = Pila.pop();
		return true;
	}






}
