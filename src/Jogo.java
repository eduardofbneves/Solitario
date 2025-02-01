import java.util.ArrayList;
import java.util.Arrays;

public class Jogo {
    //atencao que o indice da String_INPT e' importante para os movimentos no solitario
	public static final String[] STRING_INPT =  {"Pil-1", "Pil-2", "Pil-3", "Pil-4", "Pil-5", 
			"Pil-6", "Pil-7", "Monte", "Disp", "Arm-1", "Arm-2", "Arm-3", "Arm-4"};
	
	Baralho bar = new Baralho(); //baralho completo

	Pilha disp1 = new Pilha(); //pilha inicial com cartas de armazenamento tendo 1 carta visivel no inicio do jogo
	Pilha disp2 = new Pilha(); //pilha vazia no inicio, recebendo a carta do disp 1 a cada jogada
	Pilha arm1 = new Pilha();
	Pilha arm2 = new Pilha();
	Pilha arm3 = new Pilha();
	Pilha arm4 = new Pilha();

	Pilha pilha1 = new Pilha();
	Pilha pilha2 = new Pilha();
	Pilha pilha3 = new Pilha();
	Pilha pilha4 = new Pilha();
	Pilha pilha5 = new Pilha();
	Pilha pilha6 = new Pilha();
	Pilha pilha7 = new Pilha();

	boolean mostraSegundaCarta = false;
	
	private ArrayList<Pilha> lista = new ArrayList<Pilha>(Arrays.asList(pilha1, pilha2, pilha3,
			pilha4, pilha5, pilha6, pilha7));

	private ArrayList<Pilha> di = new ArrayList<Pilha>(Arrays.asList(disp1, disp2, arm1, arm2, arm3, arm4));

	private int altura = 0; // definida para o tamanho da carta

	private Object arm;
	
	public Pilha getPilha(int i, boolean trans) {
		Pilha pilha;
		if (trans) {
			pilha = lista.get(i);
		}
		else {
			pilha = di.get(i-7);
		}
		return pilha;
	}

	//DEFINICAO DA DISPOSICAO DAS CARTAS EM FUNCAO DA PILHA
	public void inicia() {  
		Baralho bar = new Baralho(); 
		saudacao();
		Carta carta;
		
		for (int i = 0; i<7; i++) {
			// 28 cartas vao para as pilhas de transicao
			for (int j = 0; j<=i; j++) {
				carta = bar.tirarCarta();
				if (i!=j) {
					carta.setVis(false); // esconder cartas necessarias
				}
				this.lista.get(i).adicionar(carta);
			}
			this.lista.get(i).novaVis();
		}

		// 24 cartas vao para o monte

		for (int i = 0; i<24; i++) {
			this.disp1.adicionar(bar.tirarCarta());
		}

		this.novaAltura();
		//String[][] print_array = this.arrayCards();
		devolveJogoAtual();
	}

	public void novaAltura() { //para definir as 7 pilhas de transicao 
		int l = 0;
		for (int i = 0; i<7; i++) {
			if (l<this.lista.get(i).tamanho()) {
				l = this.lista.get(i).tamanho();
			}
		}
		this.altura = (l-1)*4;
	}

	public void novoDisp() { //para definir pilhas de cartas nao usadas
		int d = 0;
		for (int i =0;i<2; i++) {
			if (d<this.di.get(i).tamanho()) {
				d = this.di.get(i).tamanho();
			}
		}
		this.altura = d*5;
	}

	public void novoArm() { //pilhas de armazenamento
		int a = 0;
		for (int i =0;i<4; i++) {
			if (a<((ArrayList<Pilha>) this.arm).get(i).tamanho()) {
				a = ((ArrayList<Pilha>) this.arm).get(i).tamanho();
			}
		}

		this.altura = a*5;
	}

	public String[][] arrayCards() {
		this.novaAltura();
		String[][] array = new String[this.altura + 4][41]; // [41] largura do tabuleiro
		Pilha p;
		String[][] carta;

		for (int i = 0; i<7; i++) {
			p = this.lista.get(i);
			for (int j = 0; j<p.tamanho(); j++) {

				carta = p.getCarta(j).toArray();

				for (int row = 0; row < 4; row++) {
					for (int col = 0; col < 5; col++) {
						array[4*j+row][(5 + 1)*i+col] = carta[row][col];
					}
				}

			}
		}
		return array;
	}

	public String[][] arrayCardsT() {
		
		String[][] array = new String[5][41];
		Pilha p;
		//String[][] carta1={{"-","-","-","-","-"},{"|"," "," "," ","|"},{"|"," "," "," ","|"},{"-","-","-","-","-"}};
		String[][] carta;
		
		for (int i = 0; i<6; i++) {
			p = this.di.get(i);
			
			if (i<2 && !p.isEmpty()) {
				carta = p.topCarta().toArray();
				for (int row = 0; row < 4; row++) {
					for (int col = 0; col < 5; col++) {
						array[row][(5+1)*i+col] = carta[row][col];
					}
				}
			}
			else if (i>=2 && !p.isEmpty()) {
				carta = p.topCarta().toArray();
				for (int row = 0; row < 4; row++) {
					for (int col = 0; col < 5; col++) {
						array[row][(5+1)*i+col+6] = carta[row][col];
					}
				}

			}
		}
		
		return array;
		
	}



	private static void saudacao() {
		String s ="                 _______  _______  ___      ___   _______  _______   ______    ___   ________" + "\n"
				+ "		|       ||       ||   |    |   | |       ||   _   | |    _ |  |   |" + " |        |" +"\n"
				+ "		|  _____||   _   ||   |    |   | |_     _||  | |  | |   | ||  |   |" +  " |   _    |"+"\n"
				+ "		| |_____ |  | |  ||   |    |   |   |   |  |  |_|  | |   |_||_ |   |" +   " |  | |   |"+"\n"
				+ "		|_____  ||  |_|  ||   |___ |   |   |   |  |       | |    __  ||   |" +  " |  |_|   |"+"\n"
				+ "		 _____| ||       ||       ||   |   |   |  |   _   | |   |  | ||   |" +  " |        |"+"\n"
				+ "		|_______||_______||_______||___|   |___|  |__| |__| |___|  |_||___|" +   " |________|";

		System.out.println(s);
		System.out.println();
	}
    //impressao da matriz
	public static void print2D(String mat[][]) {

		// Loop through all rows
		for (String[] row : mat) {
			// Iteracao por linha
			for (String x : row) {
				// Iteracao por cada valor na linha
				if (x == null) { // se vazio inserir espaco
					System.out.print(" ");
				}
				else { // se nao inserir o simbolo
					System.out.print(x);
				}

			}
			System.out.println(); // Nova linha no final de ler a linha toda
		}
	}
	//Rebobinar as cartas
	public void rebobinar() {

		if(!disp1.isEmpty()) {
			disp1.moverCartasTrans(disp2, 1);
		}
		else {
			disp2.moverCartasTrans(disp1, disp2.tamanho());
		}
	}

	//Devolucao do jogo atual >> diposicao das cartas
	public void devolveJogoAtual() {
		System.out.println("Monte " + "Disp  " + "      " + "Arm-1 " + "Arm-2 " + "Arm-3 " + "Arm-4");
		print2D(this.arrayCardsT());
		System.out.println("Pil-1 " + "Pil-2 " + "Pil-3 " + "Pil-4 " + "Pil-5 " + "Pil-6 " + "Pil-7 ");
		print2D(this.arrayCards());
	}
}
