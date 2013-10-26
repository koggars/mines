package gui;

import java.util.Stack;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.Board;

import javax.swing.*;


public class BoardFrame extends JPanel {

	private final int NUM_IMAGES = 16;
	private final int CELL_SIZE = 15;

	private final int COVER_FOR_CELL = 0xF;
	private final int MARK_FOR_CELL = 0xD;
	private final int EMPTY_CELL = 0;
	private final int[] MINE_CELLS = {0x9, 0xA, 0xB, 0xC};
	private final int[] MINE_LIVES = {10, 25, 50, 100};

	private final int LAST_MINE = MINE_CELLS[MINE_CELLS.length - 1];

	private final int DRAW_COVER = COVER_FOR_CELL;
	private final int DRAW_MARK = 0xD;
	private final int DRAW_WRONG_MARK = 0xE;
	private int mines_left;
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;
	private boolean inGame;
	private int health = 100;
	private Image[] img;
	private StatusPane statusbar;

	private boolean gameStarted = false;

	private Stack<ArrayList<Integer>> undoStack;
	private Stack<ArrayList<Integer>> redoStack;

	private Board board;

	private int[] currentField;
	private MinesMain mainFrame;

	public BoardFrame(StatusPane statusbar, Board board, MinesMain mainFrame) {

		this.statusbar = statusbar;
		this.board = board;
		statusbar.setStatus(0);
		img = new Image[NUM_IMAGES];

		for (int i = 0; i < NUM_IMAGES; i++) {
			img[i] = (new ImageIcon("images/tiles/" + Integer.toHexString(i).toUpperCase() + ".png")).getImage();
		}
		newGame();
		addMouseListener(new MinesAdapter());

		this.mainFrame = mainFrame;

	}

	public void setMenus(JMenuItem undoMenu, JMenuItem redoMenu) {
		this.undoMenu = undoMenu;
		this.redoMenu = redoMenu;
	}

	public void newGame() {
		health = 100;
		undoStack = new Stack<ArrayList<Integer>>();
		redoStack = new Stack<ArrayList<Integer>>();

		currentField = board.getField();
		mines_left = board.getMines();
		inGame = true;
		statusbar.setVisible(true);
		statusbar.setMines(board.getMinesLeft());
		repaint();
	}

	public void paint(Graphics g) {

		int cell;
		int uncover = 0;

		int[] rowColMines = board.getRowsColsMines();
		int rows = rowColMines[0];
		int cols = rowColMines[1];

		int[] field = board.getField().clone();

		if (inGame)
			checkLife();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				cell = field[(i * cols) + j];

				if (!inGame) {
					if (cell < 0) {
						cell *= -1;
						cell -= COVER_FOR_CELL;

						if (board.isMine(cell))
							cell = DRAW_MARK;
						else
							cell = DRAW_WRONG_MARK;
					} else if (board.isMine(cell - COVER_FOR_CELL))
						cell -= COVER_FOR_CELL;
					else if (cell >= COVER_FOR_CELL)
						cell = DRAW_COVER;

				} else {
					if (cell < 0)
						cell = DRAW_MARK;
					else if (cell > (LAST_MINE + COVER_FOR_CELL))
						cell -= COVER_FOR_CELL;
					else if (cell > (LAST_MINE)) {
						cell = DRAW_COVER;
						uncover++;
					}
				}
				g.drawImage(img[cell], (j * CELL_SIZE),
						(i * CELL_SIZE), this);

			}
		}


		if (uncover == 0 && inGame) {
			inGame = false;
			statusbar.setStatus(2);
			statusbar.stopClock();
			mainFrame.gameOver(true);
		} else if (!inGame) {
			statusbar.setStatus(3);
			statusbar.stopClock();
			mainFrame.gameOver(false);
		}
	}

	public boolean ifPropertyMarked(int value) {
		for (int mine : MINE_CELLS) {
			if (value - MARK_FOR_CELL - COVER_FOR_CELL == mine)
				return true;
		}
		return false;
	}

	public int[] getField() {

		return this.currentField;

	}

	public void solveGame() {
		inGame = false;
		statusbar.setVisible(false);
		statusbar.setStatus(5);
		statusbar.stopClock();
	}

	public void mouseClick(MouseEvent e) {
		ArrayList<Integer> currentMove;
		int[] rowColMines = board.getRowsColsMines();
		int rows = rowColMines[0];
		int cols = rowColMines[1];
		;
		int[] field = board.getField();


		int x = e.getX();
		int y = e.getY();

		int cCol = x / CELL_SIZE;
		int cRow = y / CELL_SIZE;

		boolean rep = false;


		if (!inGame) {
			newGame();
			repaint();
		}


		if ((x < cols * CELL_SIZE) && (y < rows * CELL_SIZE)) {

			if (e.getButton() == MouseEvent.BUTTON3) {

				if (field[(cRow * cols) + cCol] < 0 || field[(cRow * cols) + cCol] > LAST_MINE) {
					rep = true;

					if (field[(cRow * cols) + cCol] <= (LAST_MINE + COVER_FOR_CELL) && field[(cRow * cols) + cCol] > 0) {
						if (mines_left > 0) {
							field[(cRow * cols) + cCol] *= -1;
							mines_left--;
							statusbar.setMines(mines_left);
						} else
							statusbar.setStatus(4);
					} else if (field[(cRow * cols) + cCol] < 0) {
						mines_left++;
						field[(cRow * cols) + cCol] *= -1;
						statusbar.setMines(mines_left);
						currentField = field.clone();

					}
				}

			} else {

				if (field[(cRow * cols) + cCol] < 0) {
					return;
				}

				if ((field[(cRow * cols) + cCol] > LAST_MINE) &&
						(field[(cRow * cols) + cCol] <= LAST_MINE + COVER_FOR_CELL)) {
					field[(cRow * cols) + cCol] -= COVER_FOR_CELL;
					rep = true;
					currentMove = new ArrayList<Integer>();
					if (board.isMine(field[(cRow * cols) + cCol])) {
						hitMine(field[(cRow * cols) + cCol]);
					} else {
						currentMove.add((cRow * cols) + cCol);
						board.setLastMoves(currentMove);
						board.find_empty_cells((cRow * cols) + cCol);
						field = board.getField();
					}
					currentField = field.clone();
					if (currentMove.size() > 0) ;
					undoStack.push(currentMove);
				}
			}

			if (rep)
				repaint();

		}
	}

	public boolean isInGame() {
		return inGame;
	}

	public void checkLife() {
		inGame = health > 0;
		if (health < 0)
			health = 0;

		statusbar.setLife(health);

		if (!inGame)
			statusbar.stopClock();
	}

	public void hitMine(int value) {
		if (board.isMine(value)) {
			if (mines_left > 0)
				mines_left--;

			statusbar.setMines(mines_left);
			int index = value - MINE_CELLS[0];

			health -= MINE_LIVES[index];

			checkLife();
		}
	}

	class MinesAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {

			if (!gameStarted && inGame) {
				statusbar.setStatus(1);
				statusbar.startClock();
			}

			if (inGame) {
				checkLife();
				mouseClick(e);
			}

			undoMenu.setEnabled(undoStack.size() > 0 && inGame);
			redoMenu.setEnabled(redoStack.size() > 0 && inGame);
		}
	}

	public String dumpStacks() {
		String out = "";
		while (undoStack.size() > 0) {
			ArrayList<Integer> moves = undoStack.pop();
			out = "Move " + undoStack.size() + " ->  S " + moves.size() + " --> ";
			for (int tmp : moves) {
				out += tmp + " | ";
			}

		}
		return out;
	}

	public void undoMove() {
		if (inGame && undoStack.size() > 0) {
			int[] field = board.getField();
			ArrayList<Integer> moves = undoStack.pop();
			redoStack.push(moves);
			for (int tmp : moves) {
				field[tmp] += COVER_FOR_CELL;
			}

			currentField = field.clone();
			repaint();
		}
		undoMenu.setEnabled(undoStack.size() > 0);
		redoMenu.setEnabled(redoStack.size() > 0);
	}

	public void redoMove() {
		if (inGame && redoStack.size() > 0) {
			int[] field = board.getField();
			ArrayList<Integer> moves = redoStack.pop();
			undoStack.push(moves);
			for (int tmp : moves) {
				field[tmp] -= COVER_FOR_CELL;
			}

			currentField = field.clone();
			repaint();
		}

		undoMenu.setEnabled(undoStack.size() > 0);
		redoMenu.setEnabled(redoStack.size() > 0);
	}

}
