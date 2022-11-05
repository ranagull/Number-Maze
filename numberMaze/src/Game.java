import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
	public enigma.console.Console cn = Enigma.getConsole("--- Number Maze ---", 160, 30, 15);
	public TextMouseListener tmlis;
	public KeyListener klis;
	public TextAttributes reset = new TextAttributes(Color.WHITE);
	public TextAttributes attr = new TextAttributes(Color.BLACK, Color.WHITE);
	public Object[][] gameArea = new Object[23][55];

	public static Number[][] block = new Number[23][55];
	public static boolean gameOver = true;
	public int num_x, num_y;
	public int score = 0;
	public int turn;
	public Backpack backpack = new Backpack();
	public int[][] dotsblock = new int[23][55];
	public int gameMode;
	// ------ Standard variables for mouse and keyboard ------
	public int mousepr; // mouse pressed?
	public int mousex, mousey; // mouse text coords.
	public int keypr; // key pressed?
	public int rkey; // key (for press/release)
	// ----------------------------------------------------

	public void clearScreen() {
		cn.getTextWindow().setCursorPosition(0, -1);
		for (int i = 0; i < cn.getTextWindow().getColumns(); i++) {
			for (int j = 0; j < cn.getTextWindow().getRows(); j++) {
				System.out.print(" ");
			}
		}
	}

	public boolean printMenu(Boolean game) throws InterruptedException, FileNotFoundException {
		TextAttributes reset = new TextAttributes(Color.WHITE);
		TextAttributes attr2 = new TextAttributes(Color.MAGENTA);
		clearScreen();

		File r = new File("menu.txt");
		Scanner sc2 = new Scanner(r, "UTF-8");
		int im = 0;
		while (sc2.hasNextLine()) {
			cn.getTextWindow().setCursorPosition(40, im);
			if (im >= 6){
				cn.getTextWindow().setCursorPosition(66, im);
				attr2 = new TextAttributes(Color.BLUE.darker(),Color.WHITE);
			}
			cn.setTextAttributes(attr2);
			String line = sc2.nextLine();
			System.out.println(line);
			im++;
		}
		File r2 = new File("soundOn.txt");
		Scanner sc3 = new Scanner(r2, "UTF-8");
		int em = 3;
		while (sc3.hasNextLine()) {
			cn.getTextWindow().setCursorPosition(112, em);
			attr2 = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(attr2);
			String line = sc3.nextLine();
			System.out.println(line);
			em++;
		}
		cn.setTextAttributes(reset);
		attr2 = new TextAttributes(Color.RED,Color.WHITE);
		game = true;
		cn.getTextWindow().setCursorPosition(70, 16); int n = 16;
		cn.getTextWindow().output(">>>", attr2);
		while (true) {
			if (keypr == 1) {
				if (rkey == KeyEvent.VK_UP && (n == 17)) {
					cn.getTextWindow().setCursorPosition(70, 17);
					cn.getTextWindow().output("    ", attr2);
					cn.getTextWindow().setCursorPosition(70, 16); n =16;
					cn.getTextWindow().output(">>> ", attr2);
					game = true; gameMode=5;
				} 
				if (rkey == KeyEvent.VK_UP && (n == 19)) {
					cn.getTextWindow().setCursorPosition(70, 19);
					cn.getTextWindow().output("    ", attr2);
					cn.getTextWindow().setCursorPosition(70, 17); n =17;
					cn.getTextWindow().output(">>> ", attr2);
					game = true; gameMode=10;
				} 
				else if (rkey == KeyEvent.VK_DOWN && (n == 16)) {
					cn.getTextWindow().setCursorPosition(70, 16);
					cn.getTextWindow().output("    ", attr2);
					cn.getTextWindow().setCursorPosition(70, 17); n = 17;
					cn.getTextWindow().output(">>> ", attr2);
					game = true ; gameMode=10;
				}
				else if (rkey == KeyEvent.VK_DOWN && (n == 17)) {
					cn.getTextWindow().setCursorPosition(70, 17);
					cn.getTextWindow().output("    ", attr2);
					cn.getTextWindow().setCursorPosition(70, 19); n =19;
					cn.getTextWindow().output(">>> ", attr2);
					game = false;
				}else if (rkey == KeyEvent.VK_ENTER)
					break;
				keypr = 0;
			}
			Thread.sleep(20);
		}
		clearScreen();
		return game;
	}

	public void printStart() throws FileNotFoundException {

		cn.getTextWindow().setCursorPosition(0, 5);
		File f = new File("ascii_pacman.txt");
		Scanner sc = new Scanner(f, "UTF-8");
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			System.out.println(line);
		}
		int x = 12;
		File f2 = new File("ascii_trophy.txt");
		Scanner sc2 = new Scanner(f2, "UTF-8");
		while (sc2.hasNextLine()) {
			String line = sc2.nextLine();
			cn.getTextWindow().setCursorPosition(99, x);
			System.out.println(line);
			x++;
		}
		TextAttributes items_color = new TextAttributes(Color.BLACK, Color.WHITE);
		cn.getTextWindow().setCursorPosition(90, 5);
		cn.setTextAttributes(items_color);
		System.out.println("  Run through the maze avoiding the numbers!  ");
		cn.getTextWindow().setCursorPosition(90, 8);
		System.out.println("  Throw the little numbers in your backpacks  ");
		cn.getTextWindow().setCursorPosition(102, 9);
		System.out.println("  and Keep Going!  ");
		cn.setTextAttributes(reset);
	}

	public void printGameArea() {
		cn.getTextWindow().setCursorPosition(0, 0);
		for (int i = 0; i < gameArea.length; i++) {
			for (int j = 0; j < gameArea[i].length; j++) {

				if (String.valueOf(gameArea[i][j]).equals("#")) {
					cn.setTextAttributes(attr);
					Number new_num = new Number(-1);
					block[i][j] = new_num;
					dotsblock[i][j] = 0;
				} else {
					cn.setTextAttributes(reset);
					Number new_num = new Number(0);
					block[i][j] = new_num;
					dotsblock[i][j] = 0;
				}
				System.out.print(gameArea[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printInputQ(CircularQueue inputQueue) { // its new for display numbers
		int cx = 115;
		int cy = 0;
		cn.setTextAttributes(reset);
		cn.getTextWindow().setCursorPosition(cx, cy);
		System.out.println("Input");
		cn.setTextAttributes(attr);
		cn.getTextWindow().setCursorPosition(cx, cy + 1);
		System.out.println("<<<<<<<<<<");

		for (int i = 0; i < 25; i++) {
			cn.setTextAttributes(reset);
			cn.getTextWindow().setCursorPosition(cx + i, cy + 2);
			if (i < 10)
				System.out.print(inputQueue.peek());
			inputQueue.enqueue(inputQueue.dequeue());
		}

		cn.setTextAttributes(attr);
		cn.getTextWindow().setCursorPosition(cx, cy + 3);
		System.out.println("<<<<<<<<<<");
		cn.setTextAttributes(reset);
	}

	public void Writer() {
		for (int i = 0; i < block.length; i++) {
			int a = i, b = 0;
			for (int j = 0; j < block[i].length; j++) {
				if (block[i][j].getMoveType().equals("user")) {
					cn.getTextWindow().setCursorPosition(b, a);
					TextAttributes items_color = new TextAttributes(Color.WHITE, block[i][j].getColor());
					cn.setTextAttributes(items_color);

					System.out.print((char) (block[i][j].getItem() + '0') + " ");
					cn.setTextAttributes(reset);

				} else if (block[i][j].getItem() > 0 && block[i][j].getItem() < 10) {

					cn.getTextWindow().setCursorPosition(b, a);
					TextAttributes items_color = new TextAttributes(Color.BLACK, block[i][j].getColor());
					cn.setTextAttributes(items_color);

					System.out.print((char) (block[i][j].getItem() + '0') + " ");
					cn.setTextAttributes(reset);

				} else if (dotsblock[i][j] == 1) {
					cn.getTextWindow().setCursorPosition(b, a);
					TextAttributes items_color = new TextAttributes(Color.WHITE);
					cn.setTextAttributes(items_color);

					System.out.print((char) ('.') + " ");
					cn.setTextAttributes(reset);
				} else if ((block[i][j].getItem() == -1)) {
					cn.getTextWindow().setCursorPosition(b, a);
					TextAttributes items_color = new TextAttributes(block[i][j].getColor(), Color.WHITE);
					cn.setTextAttributes(items_color);

					System.out.print("# ");
					cn.setTextAttributes(reset);
				} else if (block[i][j].getItem() == 0) {
					cn.getTextWindow().setCursorPosition(b, a);
					System.out.print("  ");

				} else {
					cn.getTextWindow().setCursorPosition(b, a);
					TextAttributes items_color = new TextAttributes(Color.BLACK, block[i][j].getColor());
					cn.setTextAttributes(items_color);

					System.out.print((char) (block[i][j].getItem() + '0') + " ");
					cn.setTextAttributes(reset);
				}
				b = b + 2;
			}
		}
	}

	public void printGameArea2(String name, int score, int type) throws IOException {
		String[] names = new String[999];
		int[] scores = new int[999];
		int var_while = 0;
		clearScreen();

		cn.getTextWindow().setCursorPosition(0, 0);
		File f = new File("ascii_computer.txt");
		Scanner sc = new Scanner(f, "UTF-8");
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			System.out.println(line);
		}
		sc.close();
		File f2 = new File("diamond.txt");
		Scanner sc2 = new Scanner(f2, "UTF-8");
		int im = 2;
		while (sc2.hasNextLine()) {
			cn.getTextWindow().setCursorPosition(68, im);
			String line = sc2.nextLine();
			System.out.println(line);
			cn.getTextWindow().setCursorPosition(97, im);
			System.out.println(line);
			im++;
		}
		sc2.close();
		File fff = new File("scoretable.txt");
		if (type == 1) {
			if(name.equals("")) name = "Default";
			FileWriter fw = new FileWriter(fff, true);
			fw.write(name + " " + score + "\n");
			fw.close();
		}
		File ff = new File("scoretable.txt");
		Scanner scc = new Scanner(ff, "UTF-8");
		while (scc.hasNextLine()) {
			String line = scc.nextLine();
			String[] namescore = line.split(" ");
			names[var_while] = namescore[0];
			scores[var_while] = Integer.parseInt(namescore[1]);
			var_while++;
		}
		scc.close();
		for (int i = 0; i < scores.length; i++) {
			for (int j = 0; j < scores.length; j++) {
				if (scores[i] > scores[j]) {
					int temp = scores[i];
					scores[i] = scores[j];
					scores[j] = temp;
					String temp_two = names[i];
					names[i] = names[j];
					names[j] = temp_two;
				}
			}
		}
		cn.getTextWindow().setCursorPosition(82, 5);
		System.out.println("   HIGH SCORES");
		for (int i = 0; i < scores.length; i++) {
			if (names[i] != null) {
				cn.getTextWindow().setCursorPosition(82, 7 + i);
				System.out.print(names[i] + "");
				cn.getTextWindow().setCursorPosition(95, 7 + i);
				System.out.println(scores[i] + "");
			}
		}
	}

	public void createInputQ(CircularQueue inputQueue) { // creating input queue, its adding 25 random numbers to queue.
		Random rnd = new Random();
		for (int i = 0; i < 25; i++) {
			int rnd_num = rnd.nextInt(100) + 1;
			if (rnd_num <= gameMode) {
				rnd_num = rnd.nextInt(3) + 7;
			} else if (rnd_num <= (gameMode+15)) {
				rnd_num = rnd.nextInt(3) + 4;
			} else if (rnd_num <= 100) {
				rnd_num = rnd.nextInt(3) + 1;
			}
			inputQueue.enqueue(rnd_num);
		}
		int cntr = 0;
		while (cntr != 25) { 	 //Send 25 numbers to maze at the start of the game.
			Number num = new Number((int) inputQueue.peek());
			inputQueue.enqueue(inputQueue.dequeue());
			int rand_int1 = rnd.nextInt(23);			// find a location
			int rand_int2 = rnd.nextInt(55);
			if (block[rand_int1][rand_int2].getItem() == 0) {
				block[rand_int1][rand_int2] = num;
				cntr++;
			}
		}
	}

	public boolean move(int i, int j, int add1, int add2, Backpack backpack, Number new_num) {
		//The function that can perform the movements of all numbers.
		if (block[i + add1][j + add2].getMoveType().equals("user")) {
			if (block[i][j].getItem() > block[i + add1][j + add2].getItem()) {
				gameOver = false;
				return true;
			} else if (block[i][j].getItem() <= block[i + add1][j + add2].getItem()) {
				backpack.add((int) block[i][j].getItem());
				block[i][j] = new_num;
				return false;
			}
		} else {
			block[i + add1][j + add2] = block[i][j];
			new_num = new Number(0);
			block[i][j] = new_num;
			return false;
		}
		return false;
	}

	public void calculateScore(Number num) {
		if (num.getItem() != -3 && num.getItem() != -4) {
			score += num.getScoreFactor() * num.getItem(); //the formula for score
			if (num.getItem() != 0 && block[num_y][num_x].getItem() != 9) {
				block[num_y][num_x].setItem(block[num_y][num_x].getItem() + 1);
			} else if (num.getItem() != 0) {
				block[num_y][num_x].setItem(1);
			}
			cn.getTextWindow().setCursorPosition(115, 20);
			System.out.println("Score: " + score);
		}
	}

	public void pathFinding(int red_y, int red_x) {
		Stack path = new Stack(500);					//stack for path finding
		Coordinate[][] board = new Coordinate[23][55];		//coordinate array for numbers's coordinates
		
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board[a].length; b++) {
				board[a][b] = new Coordinate(b, a, -1, -1);  // set the coordinates
				block[a][b].setTried(false); 				// set the tried false
			}
		}
		Coordinate currentCrdnte = new Coordinate(num_x, num_y, -1, -1);   
		//current coordinate initially has the user location. Then, in each step, 
		//the position of the number is sent to the current coordinate, allowing it to do path finding.
		boolean flag = false;
		//The flag becomes true when the user finds the path finding number.
		int type = 0;
		//These types change the order of assigning numbers to the stack, making it easier to find paths.
		if (red_x <= currentCrdnte.getX() && red_y >= currentCrdnte.getY())
			type = 1;
		else if (red_x > currentCrdnte.getX() && red_y > currentCrdnte.getY())
			type = 2;
		else if (red_x < currentCrdnte.getX() && red_y < currentCrdnte.getY())
			type = 3;

		while (true) {

			if (currentCrdnte.getX() == red_x && currentCrdnte.getY() == red_y)
				break;
			if (type == 1) { // sol alt
				flag = ctrl((-1), 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // upper
				flag = ctrl(0, 1, red_x, red_y, currentCrdnte, path, board);if (flag)break; // right
				flag = ctrl(1, 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // lower
				flag = ctrl(0, (-1), red_x, red_y, currentCrdnte, path, board);if (flag)break; // left
			} else if (type == 2) { // sag alt
				flag = ctrl((-1), 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // upper
				flag = ctrl(0, (-1), red_x, red_y, currentCrdnte, path, board);if (flag)break; // left
				flag = ctrl(1, 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // lower
				flag = ctrl(0, 1, red_x, red_y, currentCrdnte, path, board);if (flag)break; // right
			} else if (type == 3) { // sol ust
				flag = ctrl(1, 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // lower
				flag = ctrl(0, 1, red_x, red_y, currentCrdnte, path, board);if (flag)break; // right
				flag = ctrl((-1), 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // upper
				flag = ctrl(0, (-1), red_x, red_y, currentCrdnte, path, board);if (flag)break; // left
			} else {
				flag = ctrl(1, 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // lower
				flag = ctrl(0, (-1), red_x, red_y, currentCrdnte, path, board);if (flag)break; // left
				flag = ctrl((-1), 0, red_x, red_y, currentCrdnte, path, board);if (flag)break; // upper
				flag = ctrl(0, 1, red_x, red_y, currentCrdnte, path, board);if (flag)break; // right				
			}
			//The value is pushed by reversing the stack.
			// In this way, the queue algorithm is imitated and easier to find paths.
			if (!path.isEmpty()) {
				Stack temp = new Stack(500);
				while(!path.isEmpty()) {
					temp.push(path.pop());
				}
				currentCrdnte = (Coordinate) temp.pop();
				while(!temp.isEmpty()) {
					path.push(temp.pop());
				}
			}
			else
				break;
		}
		if (flag) {//if there is a path

			block[red_y][red_x].setMoved(true); //for the red number to move once in a loop
			int index_y = board[red_y][red_x].getPrevY();
			int index_x = board[red_y][red_x].getPrevX();
			int temp3 = index_y;
			int temp4 = index_x;
			Number new_num = new Number(-2);// user

			if (index_y > -1 && index_x > -1) {

				if (block[index_y][index_x].getMoveType().equals("user")) {
					//if there is a number picking

					if (block[red_y][red_x].getItem() > block[index_y][index_x].getItem()) {
						gameOver = false;
					} else if (block[red_y][red_x].getItem() <= block[index_y][index_x].getItem()) {
						backpack.add((int) block[red_y][red_x].getItem());
						block[red_y][red_x] = new_num;
					}

				} else {
					//With the help of an independent array, points are printed on the screen
					// by taking their previous coordinates.
					while (!(board[index_y][index_x].getPrevY() == num_y
							&& board[index_y][index_x].getPrevX() == num_x)) {
						int temp = index_x;
						int temp2 = index_y;

						index_y = board[temp2][temp].getPrevY();
						index_x = board[temp2][temp].getPrevX();
						dotsblock[index_y][index_x] = 1;

						if (index_y < 0 || index_x < 0)
							break;
					}

					block[temp3][temp4] = block[red_y][red_x];
					new_num = new Number(0);
					block[red_y][red_x] = new_num;
					Writer();
				}
			}
		}
	}

	public boolean ctrl(int add1, int add2, int red_x, int red_y, Coordinate currentCrdnte, Stack path,
			Coordinate[][] board) {

		if (currentCrdnte.getY() + add1 < 23 && currentCrdnte.getX() + add2 < 55) {

			if (block[currentCrdnte.getY() + add1][currentCrdnte.getX() + add2].getItem() == 0
					&& block[currentCrdnte.getY() + add1][currentCrdnte.getX() + add2].isTried() != true) {

				//setting for previous coordinates		
				board[currentCrdnte.getY() + add1][currentCrdnte.getX() + add2].setPrevX(currentCrdnte.getX());
				board[currentCrdnte.getY() + add1][currentCrdnte.getX() + add2].setPrevY(currentCrdnte.getY());
				
				// to push the numbers around the current coordinate 
				path.push(board[currentCrdnte.getY() + add1][currentCrdnte.getX() + add2]);
				block[currentCrdnte.getY() + add1][currentCrdnte.getX() + add2].setTried(true);

				return false;
			} else if (currentCrdnte.getY() + add1 == red_y && currentCrdnte.getX() + add2 == red_x) {
				//If the number on the path find is reached

				board[red_y][red_x].setPrevX(currentCrdnte.getX());
				board[red_y][red_x].setPrevY(currentCrdnte.getY());
				return true;
			}
		}
		return false;
	}

	public void deleteDots() {
		//deleting path dots
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				if (dotsblock[i][j] == 1) {
					dotsblock[i][j] = 0;
				}
			}
		}
	}

	Game() throws Exception { // --- Contructor
		// ------ Standard code for mouse and keyboard ------ Do not change
		tmlis = new TextMouseListener() {
			public void mouseClicked(TextMouseEvent arg0) {
			}

			public void mousePressed(TextMouseEvent arg0) {
				if (mousepr == 0) {
					mousepr = 1;
					mousex = arg0.getX();
					mousey = arg0.getY();
				}
			}

			public void mouseReleased(TextMouseEvent arg0) {
			}
		};
		cn.getTextWindow().addTextMouseListener(tmlis);
		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);
		//////////////////////////////
		Random rand = new Random();
		boolean game = true;
		String username = "user";

		// FILE OPERATIONS
		File f = new File("maze.txt");
		Scanner sc = new Scanner(f, "UTF-8");
		int row = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			for (int i = 0; i < 55; i++) {
				gameArea[row][i] = line.charAt(i);
			}
			row++;
		}
		Music music = new Music();
		
		while (game) {
			music.playMusic("pac-man.wav");
			CircularQueue inputQueue = new CircularQueue(25);// Input queue(numbers) (25)
			int numbersCounter = 25;
			block = new Number[23][55];
			gameOver = true;
			num_x = 0;
			num_y = 0;
			score = 0;
			turn = 0;
			backpack = new Backpack();
			dotsblock = new int[23][55];

			game = printMenu(game);
			if (!game)
				break;
			Scanner sc1 = new Scanner(System.in);
			printStart();

			TextAttributes items_color = new TextAttributes(Color.BLACK, Color.WHITE);
			cn.setTextAttributes(items_color);
			cn.getTextWindow().setCursorPosition(15, 20);
			System.out.print("--- > ");
			cn.setTextAttributes(reset);
			System.out.print(" Please Enter Your Name: ");
			username = sc1.nextLine();
			clearScreen();

			Time time = new Time();
			printGameArea();
			// Selecting number for input Queue
			createInputQ(inputQueue);
			printInputQ(inputQueue);

			int rnd_x = 0;
			int rnd_y = 0;
			while (!(block[rnd_y][rnd_x].getItem() >= 0)) {
				rnd_x = rand.nextInt(55);
				rnd_y = rand.nextInt(23);
			}
			int px = rnd_x;
			int py = rnd_y;
			num_x = px;
			num_y = py;

			Number new_num = new Number(-2);// user
			block[py][px] = new_num;//
			
		
			
			
			while (true) {
			
				Number deneme = new Number(0);
				deneme.setItem(0);
				calculateScore(deneme);
				time.printTime(cn);

				// an item is inserted into the maze every 5 seconds.
				if (time.checkTime(5000, 1)) {
					Number num = new Number((int) inputQueue.peek());
					inputQueue.enqueue(inputQueue.dequeue());
					numbersCounter++;
					printInputQ(inputQueue);
					boolean inserter = true;
					while (inserter) {// find a location
						int rand_int1 = rand.nextInt(23);
						int rand_int2 = rand.nextInt(55);
						if (block[rand_int1][rand_int2].getItem() == 0) {
							block[rand_int1][rand_int2] = num;
							inserter = false;
						}
					}
				}
				if (time.checkTime(4000, 3) && block[num_y][num_x].getItem() == 1) {
					block[num_y][num_x].setItem(2);
					Writer();
				}
				if (time.checkTime(300, 2)) {
					turn = backpack.swapElement(rkey, keypr, cn);
					Number turnnum = new Number(turn);
					calculateScore(turnnum);
					backpack.printBackpack(cn);
					deleteDots();
					for (int i = 0; i < block.length; i++) { // RANDOM MOVEMENT FOR 4 5 6
						for (int j = 0; j < block[i].length; j++) {
							if ((block[i][j].getMoveType().equals("random"))) {
								boolean stop = true;
								while (stop) {
									int rand_int1 = rand.nextInt(4) + 1;
									if (rand_int1 == 1 && j - 1 > -1) {// left
										if ((block[i][j - 1].getItem() == 0
												|| block[i][j - 1].getMoveType().equals("user")
												|| block[i][j - 1].getItem() == -4) && !block[i][j - 1].isMoved()) {
											if (move(i, j, 0, -1, backpack, new_num))
												break;

											stop = false;
										}
									} else if (rand_int1 == 2 && j + 1 < 55) {// right
										if ((block[i][j + 1].getItem() == 0
												|| block[i][j + 1].getMoveType().equals("user")
												|| block[i][j + 1].getItem() == -4) && !block[i][j + 1].isMoved()) {
											if (move(i, j, 0, 1, backpack, new_num))
												break;

											stop = false;
										}
									} else if (rand_int1 == 3 && i - 1 > -1) {// up
										if ((block[i - 1][j].getItem() == 0
												|| block[i - 1][j].getMoveType().equals("user")
												|| block[i - 1][j].getItem() == -4) && !block[i - 1][j].isMoved()) {
											if (move(i, j, -1, 0, backpack, new_num))
												break;

											stop = false;
										}
									} else if (rand_int1 == 4 && i + 1 < 23) {// down
										if ((block[i + 1][j].getItem() == 0
												|| block[i + 1][j].getMoveType().equals("user")
												|| block[i + 1][j].getItem() == -4) && !block[i + 1][j].isMoved()) {
											if (move(i, j, 1, 0, backpack, new_num))
												break;

											stop = false;
										}
									}
								}
							}
							if ((block[i][j].getMoveType().equals("pathFinding") && !block[i][j].isMoved())) {
								pathFinding(i, j);
								block[i][j].setMoved(true);

							} else
								continue;
						}

					}
					for (int k = 0; k < 23; k++) {
						for (int k2 = 0; k2 < 55; k2++) {
							block[k][k2].setMoved(false);
						}
					}
					if (block[num_y][num_x].getItem() != 1) {
						if (keypr == 1) { // if keyboard button pressed

							Number value = block[py][px];
							new_num = new Number(0);
							if (rkey == KeyEvent.VK_LEFT && (px - 1) > -1 && block[py][px - 1].getItem() != -1) {
								if (block[py][px - 1].getItem() != 0) {
									if (block[py][px].getItem() >= block[py][px - 1].getItem()) {
										backpack.add((int) block[py][px - 1].getItem());
									} else
										gameOver = false;
								}
								block[py][px] = new_num;
								px--;
							}
							if (rkey == KeyEvent.VK_RIGHT && (px + 1) < 55 && block[py][px + 1].getItem() != -1) {
								if (block[py][px + 1].getItem() != 0) {
									if (block[py][px].getItem() >= block[py][px + 1].getItem()) {
										backpack.add((int) block[py][px + 1].getItem());
									} else
										gameOver = false;
								}
								block[py][px] = new_num;
								px++;
							}
							if (rkey == KeyEvent.VK_UP && (py - 1) > -1 && block[py - 1][px].getItem() != -1) {
								if (block[py - 1][px].getItem() != 0) {
									if (block[py][px].getItem() >= block[py - 1][px].getItem()) {
										backpack.add((int) block[py - 1][px].getItem());
									} else
										gameOver = false;
								}
								block[py][px] = new_num;
								py--;
							}
							if (rkey == KeyEvent.VK_DOWN && (py + 1) < 23 && block[py + 1][px].getItem() != -1) {
								if (block[py + 1][px].getItem() != 0) {
									if (block[py][px].getItem() >= block[py + 1][px].getItem()) {
										backpack.add((int) block[py + 1][px].getItem());
									} else
										gameOver = false;
								}
								block[py][px] = new_num;
								py++;
							}
							char rckey = (char) rkey;
							// left right up down
							if (rckey == '%' || rckey == '\'' || rckey == '&' || rckey == '(') {
								block[py][px] = value;
								num_y = py;
								num_x = px;
							}
							keypr = 0; // last action
						}
					}
					Writer();
					if (gameOver == false) {
						break;
					}
				}
				Thread.sleep(20);
			}
			music.stop();
			music.playMusic("game-over.wav");
			printGameArea2(username, score, 1);
			Thread.sleep(6000);
			music.stop();
		}
		music.stop();
		printGameArea2(username, score, 2);
	}
}