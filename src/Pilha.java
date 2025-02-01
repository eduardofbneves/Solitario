
import java.util.ArrayList;

public class Pilha {
	private ArrayList<Carta> lista;
	private int num_vis = 0;

	public Pilha() {
		this.lista = new ArrayList<Carta>();
	}

	// remocao de cartas do baralho
	public Carta removerCarta(int index) {
		Carta carta = this.lista.get(index);
		this.lista.remove(index);
		return carta;
	}

	public Carta getCarta(int index) {
		return this.lista.get(index);
	}

	// MOVER CARTAS ENTRE PILHAS DE TRANSICAO
	public boolean moverCartasTrans(Pilha outraPilha, int cartas) {
		boolean b = false;
		if (this.topCarta().getValor() == 12 && outraPilha.isEmpty()) {
			outraPilha.adicionar(this.topCarta());
			this.removerCarta(this.tamanho() - 1);
			b = true;
		} else if (!outraPilha.isEmpty()&& this.topCarta().verificarPilhas(outraPilha.topCarta()))
		{
			outraPilha.adicionar(this.topCarta());
			this.removerCarta(this.tamanho() - 1);
			b = true;
		} else if (this.getCarta(this.tamanho() - cartas).getVis() == true
				&& this.getCarta(this.tamanho() - cartas).verificarPilhas(outraPilha.topCarta()) && cartas > 1) {
			Pilha temp = new Pilha();
			for (int i = 0; i < cartas; i++) {
				temp.adicionar(this.topCarta());
				this.removerCarta(this.tamanho() - 1);
			}
			for (int i = cartas - 1; i >= 0; i--) {
				outraPilha.adicionar(temp.getCarta(i));
			}
			b = true;
		}
		return b;
	}

	// MOVER CARTAS NAS PILHAS DE ARMAZENAMENTO
	public boolean moverCartasArm(Pilha outraPilha) {
		boolean b = false;
		if (outraPilha.isEmpty() && this.topCarta().getValor() == 0) {
			outraPilha.adicionar(this.topCarta());
			this.removerCarta(this.tamanho() - 1);
			b = true;
		} else if (this.topCarta().verificarArm(outraPilha.topCarta())) {
			outraPilha.adicionar(this.topCarta());
			this.removerCarta(this.tamanho() - 1);
			b = true;
		}
		return b;
	}

	// PASSAR CARTAS DO MONTE PARA DISP
	public void passarCartas(Pilha disponiveis) {
		if (!this.isEmpty()) {
			disponiveis.adicionar(this.topCarta());
			this.removerCarta(lista.size() - 1);
		}
	}

	// REBOBINAR AS CARTAS
	public void rebobinar(Pilha monte) {
		System.out.print(this.tamanho());
		int it = this.tamanho();
		if (!this.isEmpty()) {
			Pilha temp = new Pilha();
			for (int i = 0; i < it; i++) {
				temp.adicionar(this.topCarta());
				this.removerCarta(this.tamanho() - 1);
			}
			for (int i = 0; i < temp.tamanho(); i++) {
				monte.adicionar(temp.getCarta(i));
			}
		}
	}

	public void adicionar(Carta add) {
		this.lista.add(add);
	}

	public int tamanho() {
		return this.lista.size();
	}

	public Carta topCarta() {
		return this.lista.get(lista.size() - 1);
	}

	public boolean isEmpty() {
		return this.lista.isEmpty();
	}

	// TORNAR A CARTA VISIVEL
	public void novaVis() {
		int ind = 0;
		for (int j = 0; j < lista.size(); j++) {
			if (lista.get(j).getVis() == true) {
				ind += 1;
			}
		}
		this.num_vis = ind;
	}

	public int getVis() {
		return this.num_vis;
	}
}