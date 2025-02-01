import java.util.ArrayList;
public class Baralho {
	private static ArrayList<Carta> lista;
	
	public Baralho() { // definicao do baralho 
		lista = new ArrayList<Carta>();
		int index = 0;
		
		for (int naipe = 0; naipe <= 3; naipe++){
		      for (int valor = 0; valor <= 12; valor++){
		        lista.add(index, new Carta(valor, naipe));
		        index++;
		      }
		}
		baralhar();
	}
	
	public Carta tirarCarta() { // lista com as cartas
		Carta carta = lista.get(0);
		lista.remove(0);
		return carta;
	}
	
	public void tamanho() {
		System.out.println(lista.size());
	}
	
	
	public static void baralhar() { // para baralhar as cartas
		
	ArrayList<Carta> shuffledDeck = new ArrayList<Carta>();

    while (lista.size() > 0) {
        int index = (int) (Math.random() * lista.size());
        shuffledDeck.add(lista.remove(index));
    }

    lista = shuffledDeck; // baralhar a lista de cartas
}
	
	
	public static void prnt() {
		String str = "";
		for (int i=0; i<52; i++) {
			str += lista.get(i).toString() + "\n";
		}
		System.out.println(str);
		System.out.println();
	}
	
}