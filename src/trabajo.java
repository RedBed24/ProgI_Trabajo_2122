import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class trabajo {

	final static Scanner teclado = new Scanner(System.in);

	static int leernumintervalo(int mínimo, int máximo) {
		// Comprueba que el número introducido se encuentre en el intervalo [a,b]
		int n;
		do {
			n = teclado.nextInt();
			if (n < mínimo || n > máximo)
				System.out.println("Error el número debe ser mayor o igual a " + mínimo + " y menor o igual que " + máximo + ".");
		} while (n < mínimo || n > máximo);
		return n;
	}

	static boolean buscaraya(char[][] tablero) {
		// Método para buscar el 5 en raya
		int n = tablero.length;
		boolean r = false;

		for (int x = 0; x < n && !r; x++) // la condición de !raya es para que en cuanto se detecte que hay 5 en raya se salga y no tenga que hacer más iteraciones
			for (int y = 0; y < n && !r; y++)
				switch (tablero[x][y]) { // Recorre todo el tablero y si hay algo, empieza a revisar.
				case 'x':
				case 'o':
					for (int i = 0; i <= 1 && !r; i++)
						for (int j = -1; j <= 1 && !r; j++)// En una matriz 3*3, la casilla del centro (x, y), la de arriba a la izquierda (x-1, y-1), la de arriba centro (x, y-1)...
							if (!(x + 4 * i < 0 || x + 4 * i >= n || y + 4 * j < 0 || y + 4 * j >= n) // Se encarga de que esté dentro de la matriz y no dé error indexOutOfBounds
							    && !(i == 0 && j == 0)) // Para no comprobar en la misma celda
								if (tablero[x][y] == tablero[x + i]    [y + j]
								 && tablero[x][y] == tablero[x + 2 * i][y + 2 * j]
								 && tablero[x][y] == tablero[x + 3 * i][y + 3 * j]
								 && tablero[x][y] == tablero[x + 4 * i][y + 4 * j])
									r = true;
				}
		return r;
	}

	static boolean ElecciónPrimerTurno() { // De forma aleatoria, se elige el primer turno.
		boolean turnox;
		int primerturno = (int) (Math.random() * 2); //TODO el int se puede hacer así y no usamos una variable double que guarda más información que un int, basicamente al poner el paréntesis le da prioridad a la operacion math*2, sin él hace int math y luego * 2
		if (primerturno == 0) // Se toma la parte entera del número aleatorio
			turnox = true;
		else
			turnox = false;
		return turnox;
	}

	static void Turno(boolean turnox, String jugadorX, String jugadorO) { // Indicación del turno
		if (turnox) //TODO no es necesario usar un _, la variable solo se va a leer, si se cambiase su valor sí tendriamos que cambiar el nombre
			System.out.println("\nLe toca a " + jugadorX + ". Marca tu x.");
		else
			System.out.println("\nLe toca a " + jugadorO + ". Marca tu o.");
	}

	static void ResultadoPartida(boolean turnox, boolean raya, String jugadorX, String jugadorO) { // RESULTADO DE LA PARTIDA
		if (!turnox && raya) //TODO lo mismo que en la anterior
			System.out.println("\n¡5 en raya para " + jugadorX + "!");
		else if (raya)
			System.out.println("\n¡5 en raya para " + jugadorO + "!");
		else
			System.out.println("\n¡Se han acabado las fichas!");
	}

	static void display(char[][] tablero, String pX, String pO, boolean guardaentxt) throws IOException {
		FileWriter fichero = new FileWriter("C:\\Users\\Public\\Tablero.txt");
		// Creamos el documento y añadimos la ruta donde queremos guardarlo
		int n = tablero.length;
		
		if (guardaentxt) {
			fichero.write("\r\n");
			fichero.write("Tablero: " + n + "x" + n + ".\n");
			fichero.write("Jugador X: " + pX + "  |  Jugador O: " + pO);
			fichero.write("\r\n");
		}
		
		for (int i = -1; i < n; i++) {
			String display = "\r\n"; //TODO este cambio ahorra unas líneas abajo, cuando se debe cambiar de linea, en vez de usar un println o guardar en el txt un \r\n, esta linea lo hace directamente
			for (int j = -1; j < n; j++) {
				if (i == -1 && j == -1) {
					display = display + "\n0  ";
				} else if (i == -1) {
					if (j < 9) // if (i == -1 && j<9)
						display = display + (j + 1) + "  ";
					else // if (i == -1 && j>=9) {
						display = display + (j + 1) + " ";
				} else if (j == -1) {
					if (i < 9) // if (j == -1 && i<9)
						display = display + (i + 1) + " ";
					else // if (j == -1 && i>=9) {
						display = display + (i + 1);
				} else {
					switch (tablero[j][i]) {
					case 'x':
						display = display + " x ";
						break;
					case 'o':
						display = display + " o ";
						break;
					default:
						display = display + "[ ]";
					}
				}
			}
			System.out.print(display);
			if (guardaentxt) {
				fichero.write(display);
			}
		}
		if (guardaentxt)
			fichero.close(); // Cerramos el fichero
	}

	public static void main(String[] args) throws IOException {

		boolean raya = false;

		// Selección del tablero
		System.out.print("Escribe el tamaño del tablero:");
		int N = leernumintervalo(5, 50);
		int nfichas = N * N;
		char[][] tablero = new char[N][N];

		// Identificación de los jugadores
		System.out.println("Nombre del primer jugador: ");
		String p1 = teclado.next();
		System.out.println("Nombre del segundo jugador: ");
		String p2 = teclado.next();

		// Elección del primer turno
		boolean turnox = ElecciónPrimerTurno();

		// Elección de las fichas
		int XoO = (int) (Math.random() * 2); //TODO ya lo he explicado antes
		String pX, pO;
		if (XoO == 0) {
			pX = p1;
			pO = p2;
		} else {
			pX = p2;
			pO = p1;
		}

		// *********************Comienzo del juego*************************
		do {
			// Dibujo del tablero
			display(tablero, pX, pO, false);
			// Indicación del turno
			Turno(turnox, pX, pO);

			// Movimiento del jugador
			int x, y;
			do {
				System.out.print("\nIndica la columna:");
				x = leernumintervalo(1, N) - 1;
				System.out.print("Indica la fila:");
				y = leernumintervalo(1, N) - 1;
				if (tablero[x][y] == 'x' || tablero[x][y] == 'o')
					System.out.println("Error, la celda está ocupada.");
			} while (tablero[x][y] == 'x' || tablero[x][y] == 'o');

			// Colocación de la ficha en el tablero
			if (turnox)
				tablero[x][y] = 'x';
			else
				tablero[x][y] = 'o';

			// Comprobar 5 en raya
			raya = buscaraya(tablero);

			// Cambio de turno
			turnox = !turnox;
			nfichas--;

		} while (!raya && nfichas > 0);
		// ************************Final del juego******************************

		display(tablero, pX, pO, true);

		// RESULTADO DE LA PARTIDA
		ResultadoPartida(turnox, raya, pX, pO);

		System.out.print("\nFin del programa");
	}
}