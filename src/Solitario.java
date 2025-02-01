import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Solitario {
	// REGISTAR O JOGADOR NO JOGO
	public static ArrayList<Jogador> readPlayers() {
		ArrayList<Jogador> registos = new ArrayList<Jogador>();
		try {
			// leitura dos dados do utilizador
			FileInputStream fos = new FileInputStream("db.tmp");
			ObjectInputStream oos = new ObjectInputStream(fos);
			registos = (ArrayList) oos.readObject();
			oos.close();
			return registos;
		} catch (IOException | ClassNotFoundException ioe) {
			// ioe.printStackTrace();
			return registos;
		}
	}

	public static boolean writePlayers(ArrayList<Jogador> jogadores) {
		try {
			// escrita dos dados dos utilizadores
			FileOutputStream fos = new FileOutputStream("db.tmp");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(jogadores);
			oos.close();
			return true;
		}

		catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) {
		ArrayList<Jogador> iniciar = new ArrayList<Jogador>();
		Jogador jogadorAtual = null;
		String email;
		String password;
		String jogar = "";
		boolean jogadorExistente = false;

		Scanner sc = new Scanner(System.in);
		if (jogar.toLowerCase().equals("")) {
			iniciar = readPlayers();
			System.out.println("Email: ");
			email = sc.nextLine();
			System.out.println("Password: ");
			password = sc.nextLine();

			for (int i = 0; i < iniciar.size(); i++) {
				if (iniciar.get(i).getEmail().equals(email) && iniciar.get(i).getPassword().equals(password)) {
					jogadorExistente = true;
					jogadorAtual = iniciar.get(i);
					System.out.println("Bem vindo de novo " + jogadorAtual.getnome() + "!");
					System.out.println("O numero de movimentos por jogo em media e': " + iniciar.get(i).mediaPontos());
					System.out.println("O numero maximo de pontos obtidos e': " + iniciar.get(i).maxPontos());
					System.out.println();
					break;

				}
			}

			if (!jogadorExistente) {
				String nome;
				System.out.println("Jogador inexistente. Insira o seu nome: ");
				nome = sc.nextLine();
				jogadorAtual = new Jogador(nome, email, password);
				System.out.println("Bem vindo " + nome + ", a este magnifico jogo. Espero que se divirta!!");

			}
		}

		Jogo jogo = new Jogo();
		jogo.inicia();
		int N_Mov = 0; // para fazer o print dos movimentos do jogo

		String input = " ";
		boolean jogadaValida = false;
		while (!input.equals("T")) { // enquanto nao carregar no T para terminar, corre o jogo
			System.out.println("P->Passar Cartas | R->Rebobinar | M->Mover | T->Terminar Jogo");
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextLine();
			// Executa o Passar Cartas
			if (input.equals("P")) {
				Pilha monte = jogo.getPilha(7, false);
				monte.passarCartas(jogo.getPilha(8, false));
				N_Mov++;
				System.out.println("O numero de jogadas e': " + N_Mov);
			}

			// Executa o Rebobinar
			else if (input.equals("R")) {
				Pilha disp = jogo.getPilha(8, false);
				disp.rebobinar(jogo.getPilha(7, false));
				N_Mov++;
				System.out.println("O numero de jogadas e': " + N_Mov);
			}
			// Executa o Mover
			else if (input.equals("M")) {
				// O utilizador deve definir a origem e o destino
				System.out.println("Diga a origem");
				scanner = new Scanner(System.in);
				String origem = scanner.nextLine();
				System.out.println("Diga o destino");
				scanner = new Scanner(System.in);
				String destino = scanner.nextLine();

				// Avalia se com esta jogada , o jogo termina
				int index_origem = 0;
				int index_destino = 0;
				for (int i = 0; i < 13; i++) { 
					if (origem.equals(Jogo.STRING_INPT[i])) {
						index_origem = i;
					}
					if (destino.equals(Jogo.STRING_INPT[i])) {
						index_destino = i;
					}
				}
				int cartas = 1;
				// MOVIMENTO ENTRE DISP E ARMS
				if (index_origem == 8 && index_destino > 8) {
					Pilha pilha_origem = jogo.getPilha(index_origem, false);
					jogadaValida = pilha_origem.moverCartasArm(jogo.getPilha(index_destino, false));
				}
				// MOVIMENTO ENTRE DISP E PILHAS DE TRANSICAO
				else if (index_origem == 8 && index_destino < 7) {
					Pilha pilha_origem = jogo.getPilha(index_origem, false);
					jogadaValida = pilha_origem.moverCartasTrans(jogo.getPilha(index_destino, true), 1);
					jogo.getPilha(index_destino, true).novaVis();
				}
				// MOVIMENTO ENTRE PILHAS DE TRANSICAO E ARMS
				else if (index_origem < 7 && index_destino > 8) {
					Pilha pilha_origem = jogo.getPilha(index_origem, true);
					jogadaValida = pilha_origem.moverCartasArm(jogo.getPilha(index_destino, false));
					pilha_origem.novaVis();
				}
				// MOVIMENTO ENTRE PILHAS DE TRANSICAO
				else if (index_origem < 7 && index_destino < 7) {
					Pilha pilha_origem = jogo.getPilha(index_origem, true);

					if (pilha_origem.getVis() > 1) {
						System.out.println("numero de cartas");
						scanner = new Scanner(System.in);
						cartas = scanner.nextInt();
					}

					jogadaValida = pilha_origem.moverCartasTrans(jogo.getPilha(index_destino, true), cartas);
					pilha_origem.novaVis();
					jogo.getPilha(index_destino, true).novaVis();
				}

				if (!jogadaValida) {
					// Devolve mensagem ao user se houver uma movimentaçao invalida
					System.out.println("Entrada inválida. Tente de novo");
					continue;
				}

				for (int i = 0; i < 7; i++) { // para as pilhas de transicao para tornar visivel a carta seguinte a
												// movimentada
					Pilha pilha = jogo.getPilha(i, true);
					if (!pilha.isEmpty() && pilha.getVis() == 0) {
						pilha.getCarta(pilha.tamanho() - 1).setVis(true);
					}
				}

				if (jogadaValida == true) 
				{
					N_Mov++;
				}
				System.out.println();
				System.out.println("O numero de jogadas e': " + N_Mov);
				System.out.println();
			} else {
				System.out.println("--Entrada inválida-- \n");
			}

			// Devolve o estado do jogo apos a acao
			jogo.devolveJogoAtual();
			if (input.equals("T")) {
				System.out.println("Quer jogar mais? S-Sim | N-Nao");
				jogar = sc.nextLine();
				if (jogar.equals("N")) {
					iniciar.add(jogadorAtual);
					writePlayers(iniciar);
					System.out.println("Obrigado e volte sempre!!");
					scanner.close();
				} else if (jogar.equals("S")) {
					jogo.devolveJogoAtual();
					input = "S";
					continue;
				}
			}
		}
	}

}