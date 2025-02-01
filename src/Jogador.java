
import java.util.ArrayList;
import java.io.Serializable;

public class Jogador implements Serializable {
	// caracteristicas que definem o jogar
	private String nome;
	private String email;
	private String password;
	private ArrayList<Integer> pontos;

	public Jogador(String nome, String email, String password) {
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.pontos = new ArrayList<Integer>();
	}

	public String getnome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Integer> getpontos() {
		return pontos;
	}

	public void setpontos(ArrayList<Integer> pontos) {
		this.pontos = pontos;
	}

	public void addpontos(int sco) {
		this.pontos.add(sco);
	}

	// calculo da media dos pontos no jogo
	public double mediaPontos() {
		double sum = 0;
		for (int i = 0; i < pontos.size(); i++) {
			sum += this.pontos.get(i);
		}

		return sum / (pontos.size() * 1.0);
	}

	// calculo do maximo de pontos obtidos
	public int maxPontos() {
		int max = 0;
		for (int i = 0; i < pontos.size(); i++) {
			if (this.pontos.get(i) > max) {
				max = this.pontos.get(i);
			}
		}

		return max;
	}
}