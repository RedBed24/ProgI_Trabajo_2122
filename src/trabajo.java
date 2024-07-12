import java.util.Scanner;

public class trabajo {

	final static Scanner teclado = new Scanner(System.in);

	static int leernumintervalo(int a, int b) {
		int n;
		do {
			n = teclado.nextInt();
			if (n < a || n > b)
				System.out.println("Error el numero debe ser mayor o igual a " + a + " y menor o igual que " + b + ".");
		} while (n < a || n > b);
		return n;
	}

	static void display(char[][] tablero) {
		int n = tablero.length;
		for (int i = -1; i < n; i++) {
			String display = "";
			for (int j = -1; j < n; j++) {
				if (i == -1 && j == -1) {
					display = display + "\n0  ";
				} else if (i == -1) {
					if(j<9)  //if (i == -1 && j<9)
					display =display + (j + 1) + "  ";
					else  //if (i == -1 && j>=9) {
					display = display + (j + 1) + " ";
				} else if (j == -1) {
					if(i<9) //if (j == -1 && i<9)
					display = display + (i + 1) + " ";
					else //if (j == -1 && i>=9) {
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
			System.out.println(display);
		}
	}

	static boolean buscaraya(char[][] tablero) {
		int n = tablero.length;
		boolean r = false;
		
		for (int x = 0; x < n && !r; x++) // la condición de !raya es para que en cuanto se detecte que hay 5 en raya se salga y no tenga que hacer más iteraciones
			for (int y = 0; y < n && !r; y++)
				switch (tablero[x][y]) { // recorre todo el tablero y si hay algo, empieza a revisar otras casillas
				case 'x':
				case 'o':
					for (int i = 0; i <= 1 && !r; i++)
						for (int j = -1; j <= 1 && !r; j++)// en una matriz 3*3, la casilla del centro (x, y), la de arriba a la izquierda (x-1, y-1), la de arriba centro (x, y-1)...
							if (!(x + 4 * i < 0 || x + 4 * i > n || y + 4 * j < 0 || y + 4 * j > n) // este se encarga de que al comprobar esté dentro de la matriz y no de error indexOutOfBounds
									&& !(i == 0 && j == 0)) // para no comprobar en la misma celda
								if (tablero[x][y] == tablero[x + i]    [y + j]
								 && tablero[x][y] == tablero[x + 2 * i][y + 2 * j]
								 && tablero[x][y] == tablero[x + 3 * i][y + 3 * j]
								 && tablero[x][y] == tablero[x + 4 * i][y + 4 * j])
									r = true;
				}
		return r;
	}
	
	public static void main(String[] args) {

		int N;
		boolean raya = false;

		// seleción tablero
		System.out.print("Escribe el tamaño del tablero:");
		N = leernumintervalo(5, 50);

		int nfichas = N * N;
		char[][] tablero = new char[N][N];
		// seleción tablero

		// seleción de jugador
		boolean turnox = true;
		System.out.print("\nIndica el nombre del primer jugador: ");
		String p1 = teclado.next();
		System.out.print("Indica el nombre del segundo jugador: ");
		String p2 = teclado.next();

		// seleccionar aleatoriamente quien tendrá el primer tuno y qué ficha usará

		// seleción de jugador

		// juego
		do {

			display(tablero);

			if (turnox)
				System.out.println("\nLe toca al jugador " + p1);
			else
				System.out.println("\nLe toca al jugador " + p2);

			// coords, pide coords y comprueba que sean viables
			int x, y;
			do {
				System.out.print("\nIndica la coordenada horizontal:");
				x = leernumintervalo(1, N) - 1;
				System.out.print("Indica la coordenada vertical:");
				y = leernumintervalo(1, N) - 1;
				if (tablero[x][y] == 'x' || tablero[x][y] == 'o')
					System.out.println("Error, la celda está ocupada");
			} while (tablero[x][y] == 'x' || tablero[x][y] == 'o');
			// coords
			
			// rellenar
			if (turnox)
				tablero[x][y] = 'x';
			else
				tablero[x][y] = 'o';
			// rellenar

			raya = buscaraya(tablero);

			// cambio turno
			turnox = !turnox;
			nfichas--;
			// cambio turno

		} while (!raya && nfichas > 0);
		// juego

		display(tablero);
		// guardar en .txt display();

		if (!turnox && raya)
			System.out.println("\n5 en raya para el jugador " + p1 + "!");
		else if (raya)
			System.out.println("\n5 en raya para el jugador " + p2 + "!");
		else
			System.out.println("\nSe han acabado las fichas!");

		System.out.print("\nFin del programa");
	}
}