import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.awt.GraphicsEnvironment;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TicTacToe {

	// Steuerung + Shift + F => Code formatieren!!!
	static JFrame frame;
	static JPanel panel;
	static JButton[] buttons = new JButton[9];
	static boolean xTurn = true;

	static int reinfolge = 2;
	static int durchgänge = 0;
	static boolean nextRound = true;
	static char symbol;
	static int eingabe;
	static String player1 = "";
	static String player2 = "";
	static char[][] felder = new char[][] { { '-', '-', '-' }, { '-', '-', '-', }, { '-', '-', '-' } };
	static Scanner scr = new Scanner(System.in);

	static void spielBeenden() {

		System.out.println("Program wird beendet");

		String s = "";
		for (int i = 0; i < felder.length; i++) {
			for (int j = 0; j < felder[i].length; j++) {
				s += felder[i][j];
			}
		}

		try (PrintWriter out = new PrintWriter("savegame.txt")) {
			out.println("Reihenfolge: " + reinfolge);
			out.println("Spieler 1 = " + player1);
			out.println("Spieler 2 = " + player2);
			out.println("Durchgang = " + durchgänge);
			out.println("Aktuelles Spielfeld: " + s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);
	}

	static void gewonnen(String player) {

		char symbol;
		if (player == player1) {
			symbol = 'X';
		} else {
			symbol = 'O';
		}

		for (int i = 0; i < 3; i++) {
			if (felder[i][0] == symbol && felder[i][1] == symbol && felder[i][2] == symbol) {
				System.out.println("Gewonnen!");
				nextRound = nächsteRunde();
			}
			if (felder[0][i] == symbol && felder[1][i] == symbol && felder[2][i] == symbol) {
				System.out.println("Gewonnen!");
				nextRound = nächsteRunde();
			}
		}

		if (felder[0][0] == symbol && felder[1][1] == symbol && felder[2][2] == symbol) {
			System.out.println("Gewonnen!");
			nextRound = nächsteRunde();
		}
		if (felder[0][2] == symbol && felder[1][1] == symbol && felder[2][0] == symbol) {
			System.out.println("Gewonnen!");
			nextRound = nächsteRunde();
		}
	}

	static boolean belegtesFeldPrüfen(int i, int j, String player) {

		if (felder[i][j] == 'X' || felder[i][j] == 'O') {
			System.out.println("feld schon belegt");
			spielSteineSetzen(player);
			return false;
		}
		return true;
	}

	static void spielSteineSetzen(String player) {

		String zeichenFürGUI = "";
		if (player == player1) {
			symbol = 'X';
			zeichenFürGUI = "X";
		} else {
			symbol = 'O';
			zeichenFürGUI = "O";
		}

		eingabe = scr.nextInt();
		switch (eingabe) {
		case 1:
			if (belegtesFeldPrüfen(0, 0, player) == false) {
				break;
			}
			felder[0][0] = symbol;
			buttons[0].setText(zeichenFürGUI);
			break;
		case 2:
			if (belegtesFeldPrüfen(0, 1, player) == false) {
				break;
			}
			belegtesFeldPrüfen(0, 1, player);
			felder[0][1] = symbol;
			buttons[1].setText(zeichenFürGUI);
			break;
		case 3:
			if (belegtesFeldPrüfen(0, 2, player) == false) {
				break;
			}
			belegtesFeldPrüfen(0, 2, player);
			felder[0][2] = symbol;
			buttons[2].setText(zeichenFürGUI);
			break;
		case 4:
			if (belegtesFeldPrüfen(1, 0, player) == false) {
				break;
			}
			belegtesFeldPrüfen(1, 0, player);
			felder[1][0] = symbol;
			buttons[3].setText(zeichenFürGUI);
			break;
		case 5:
			if (belegtesFeldPrüfen(1, 1, player) == false) {
				break;
			}
			belegtesFeldPrüfen(1, 1, player);
			felder[1][1] = symbol;
			buttons[4].setText(zeichenFürGUI);
			break;
		case 6:
			if (belegtesFeldPrüfen(1, 2, player) == false) {
				break;
			}
			belegtesFeldPrüfen(1, 2, player);
			felder[1][2] = symbol;
			buttons[5].setText(zeichenFürGUI);
			break;
		case 7:
			if (belegtesFeldPrüfen(2, 0, player) == false) {
				break;
			}
			belegtesFeldPrüfen(2, 0, player);
			felder[2][0] = symbol;
			buttons[6].setText(zeichenFürGUI);
			break;
		case 8:
			if (belegtesFeldPrüfen(2, 1, player) == false) {
				break;
			}
			belegtesFeldPrüfen(2, 1, player);
			felder[2][1] = symbol;
			buttons[7].setText(zeichenFürGUI);
			break;
		case 9:
			if (belegtesFeldPrüfen(2, 2, player) == false) {
				break;
			}
			belegtesFeldPrüfen(2, 2, player);
			felder[2][2] = symbol;
			buttons[8].setText(zeichenFürGUI);
			break;

		}

	}

	static void spielfeldAnzeigen() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(felder[i][j]);
			}
			System.out.println();
		}

	}

	static String name() {

		System.out.println("Bitte ihre Name eingeben für Spieler:");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine().toLowerCase();

		while (name.equals(player1) == true) {
			if (name.equals(player1)) {
				System.out.println("Name schon vergeben, bitte einen anderen nehmen!");
				name = scanner.nextLine().toLowerCase();
			}
		}

		return name;
	}



	static boolean nächsteRunde() {

		System.out.println("Für nächste Runde bittr 'y' (Ja) eingeben, ansonsten 'n' (Nein)");
		Scanner scanner = new Scanner(System.in);
		String spielerEingabe = scanner.nextLine().toLowerCase();
		System.out.println(spielerEingabe);
		if (spielerEingabe.contains("y")) {
			reinfolge = 1;
			durchgänge = 0;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					felder[i][j] = '-';
				}
			}

			for (int i = 0; i < 9; i++) {
				buttons[i].setText("");
			}
			spielfeldAnzeigen();

			return true;
		}

		if (spielerEingabe.contains("n")) {
			spielBeenden();
			return false;
		} else {
			durchgänge = 0;
			System.out.println("Falsche Eingabe, heißt Spiel wird beendet!");
			spielBeenden();
			return true;
		}
	}

	public static void main(String[] args) throws FileNotFoundException, URISyntaxException, MalformedURLException,
			UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {


		frame = new JFrame("Tic Tac Toe");
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 3));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton();
			buttons[i].setFont(new Font("Sans", Font.PLAIN, 40));
			panel.add(buttons[i]);
		}

		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);

		System.out.println("Name über ifTest ist: " + player1);

		System.out.println("Herzlich willkommen zum TICTACTOE spiel");

		player1 = name();
		player2 = name();

		System.out.println("Spieler 1 heist: " + player1);
		System.out.println("Spieler 2 heist: " + player2);

		System.out.println();

		spielfeldAnzeigen();

		while (durchgänge < 9 || nextRound == true) {
			if (reinfolge % 2 == 0) {
				System.out.println("Spieler " + player1 + " ist dran!");
				spielSteineSetzen(player1);
				spielfeldAnzeigen();
				gewonnen(player1);
				System.out.println();
				reinfolge++;
				durchgänge++;
			} else {
				System.out.println("Spieler " + player2 + " ist dran!");
				spielSteineSetzen(player2);
				spielfeldAnzeigen();
				gewonnen(player2);
				System.out.println();
				durchgänge++;
				reinfolge++;

			}
		}
		spielBeenden();
	}

}
