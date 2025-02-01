

// static nos metodos usa-se quando nao e preciso inicializar as classes
// metodos sem static tem que se inicializar as classes

public class Carta {

	final String[] VALOR = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "D", "J", "Q", "K" };
	final String[] NAIPE = { "E", "O", "P", "C" };
	private final int valor;
	private final int naipe;
	private boolean vis = true;

	public Carta(int valor, int naipe) {
		this.valor = valor;
		this.naipe = naipe;
	}

	public boolean verificarPilhas(Carta outraCarta) { // verificar a disposicao dentro das pilhas
		if (this.valor < outraCarta.valor && this.naipe % 2 != outraCarta.naipe % 2) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verificarArm(Carta outraCarta) {// verificar a disposicao nas pilhas de armazenamento
		if (this.valor > outraCarta.valor && this.naipe == outraCarta.naipe) {
			return true;
		} else {
			return false;
		}
	}

	public boolean maiorValor(Carta outraCarta) { // comparar duas cartas para determinar qual tem o maior valor
		if (this.valor > outraCarta.valor) {
			return true;
		} else {
			return false;
		}
	}

	public boolean corCarta(Carta outraCarta) { //destinguir as cartas pelo atributo cor
		if (this.naipe % 2 != outraCarta.naipe % 2) {
			return true;
		} else {
			return false;
		}
	}

	public String getCor() { //comparamos as cartas por cores 
		if (this.naipe % 2 == 0) {
			return "Vermelho";
		} else {
			return "Preto";
		}
	}

	public int getValor() {
		return this.valor;
	}

	public void setVis(boolean vis) { //tornar a carta numa pilha visivel
		this.vis = vis;
	}

	public boolean getVis() {
		return this.vis;
	}
    //DESENHO DAS CARTAS 
	public String[][] toArray() {
		String[][] array = new String[4][5];

		if (this.vis) {
			array = new String[][] { { "-", "-", "-", "-", "-" }, { "|", " ", VALOR[this.valor], " ", "|" },
					{ "|", " ", NAIPE[this.naipe], " ", "|" }, { "-", "-", "-", "-", "-" } };
		} else {
			array = new String[][] { { "-", "-", "-", "-", "-" }, { "|", " ", " ", " ", "|" },
					{ "|", " ", " ", " ", "|" }, { "-", "-", "-", "-", "-" } };
		}

		return array;
	}

	public String toString() { 
		String ret = VALOR[this.valor] + " de " + NAIPE[this.naipe];
		return ret;
	}
}