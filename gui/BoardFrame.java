package gui;

import java.util.Stack;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.Board;

import javax.swing.*;


public class BoardFrame extends JPanel {

	private final int NUM_IMAGES = 13;
	private final int CELL_SIZE = 15;

	private final int COVER_FOR_CELL = 10;
	private final int MARK_FOR_CELL = 10;
	private final int EMPTY_CELL = 0;
	private final int MINE_CELL = 9;
	private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
	private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

	private final int DRAW_MINE = 9;
	private final int DRAW_COVER = 10;
	private final int DRAW_MARK = 11;
	private final int DRAW_WRONG_MARK = 12;
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


	public BoardFrame(StatusPane statusbar, Board board) {

		this.statusbar = statusbar;
		this.board = board;
		statusbar.setStatus(0);
		img = new Image[NUM_IMAGES];

		for (int i = 0; i < NUM_IMAGES; i++) {
			img[i] = (new ImageIcon("images/tiles/" + (i) + ".png")).getImage();
		}

		newGame();
		addMouseListener(new MinesAdapter());

	}

	public void setMenus(JMenuItem undoMenu, JMenuItem redoMenu) {
		this.undoMenu = undoMenu;
		this.redoMenu = redoMenu;
	}

	public void newGame() {
		undoStack = new Stack<ArrayList<Integer>>();
		redoStack = new Stack<ArrayList<Integer>>();

		currentField = board.getField();
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

		int[] field = board.getField();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				cell = field[(i * cols) + j];

				if (inGame && cell == MINE_CELL)
					inGame = false;

				if (!inGame) {
					if (cell == COVERED_MINE_CELL) {
						cell = DRAW_MINE;
					} else if (cell == MARKED_MINE_CELL) {
						cell = DRAW_MARK;
					} else if (cell > COVERED_MINE_CELL) {
						cell = DRAW_WRONG_MARK;
					} else if (cell > MINE_CELL) {
						cell = DRAW_COVER;
					}


				} else {
					if (cell > COVERED_MINE_CELL)
						cell = DRAW_MARK;
					else if (cell > MINE_CELL) {
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
		} else if (!inGame) {
			statusbar.setStatus(3);
			statusbar.stopClock();
		}
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
		int mines = rowColMines[2];
		int mines_left = board.getMinesLeft();
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

				if (field[(cRow * cols) + cCol] > MINE_CELL) {
					rep = true;

					if (field[(cRow * cols) + cCol] <= COVERED_MINE_CELL) {
						if (mines_left > 0) {
							field[(cRow * cols) + cCol] += MARK_FOR_CELL;
							mines_left--;
							statusbar.setMines(mines_left);
						} else
							statusbar.setStatus(4);
					} else {

						field[(cRow * cols) + cCol] -= MARK_FOR_CELL;
						mines_left++;
						statusbar.setMines(mines_left);
						currentField = field.clone();

					}
				}

			} else {

				if (field[(cRow * cols) + cCol] > COVERED_MINE_CELL) {
					return;
				}

				if ((field[(cRow * cols) + cCol] > MINE_CELL) &&
						(field[(cRow * cols) + cCol] < MARKED_MINE_CELL)) {
					currentMove = new ArrayList<Integer>();
					currentMove.add((cRow * cols) + cCol);
					field[(cRow * cols) + cCol] -= COVER_FOR_CELL;
					rep = true;

					if (field[(cRow * cols) + cCol] == MINE_CELL)
						inGame = false;
					if (field[(cRow * cols) + cCol] == EMPTY_CELL) {
						board.setLastMoves(currentMove);
						board.find_empty_cells((cRow * cols) + cCol);
						field = board.getField();
					}
					currentField = (int[]) field.clone();
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

	class MinesAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {

			if (!gameStarted) {
				statusbar.setStatus(1);
				statusbar.startClock();
			}
			if (inGame)
				mouseClick(e);

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
