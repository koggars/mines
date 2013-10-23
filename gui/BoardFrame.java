package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.Board;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


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

	private boolean inGame;
	private Image[] img;
	private JLabel statusbar;

	private Board board;

	private int[] currentField;


	public BoardFrame(JLabel statusbar, Board board) {

		this.statusbar = statusbar;
		this.board = board;

		img = new Image[NUM_IMAGES];

		for (int i = 0; i < NUM_IMAGES; i++) {
			img[i] = (new ImageIcon("images/tiles/" + (i) + ".png")).getImage();
		}


		int[] rowColMines = board.getRowsColsMines();
		int rows = rowColMines[0];
		int cols = rowColMines[0];

		newGame();
		addMouseListener(new MinesAdapter());
		repaint();

	}

	public void newGame() {
		inGame = true;
		statusbar.setVisible(true);
		statusbar.setText(Integer.toString(board.getMinesLeft()));
	}

	public void paint(Graphics g) {

		int cell = 0;
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
			statusbar.setText("Game won");
		} else if (!inGame)
			statusbar.setText("Game lost");
	}

	public int[] getField() {

		return this.currentField;

	}

	public Board getBoard() {
		return board;
	}

	public void solveGame() {

		inGame = false;
		statusbar.setVisible(false);
	}

	public void mouseClick(MouseEvent e) {
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
							statusbar.setText(Integer.toString(mines_left));
						} else
							statusbar.setText("No marks left");
					} else {

						field[(cRow * cols) + cCol] -= MARK_FOR_CELL;
						mines_left++;
						statusbar.setText(Integer.toString(mines_left));
						currentField = field.clone();

					}
				}

			} else {

				if (field[(cRow * cols) + cCol] > COVERED_MINE_CELL) {
					return;
				}

				if ((field[(cRow * cols) + cCol] > MINE_CELL) &&
						(field[(cRow * cols) + cCol] < MARKED_MINE_CELL)) {

					field[(cRow * cols) + cCol] -= COVER_FOR_CELL;
					rep = true;

					if (field[(cRow * cols) + cCol] == MINE_CELL)
						inGame = false;
					if (field[(cRow * cols) + cCol] == EMPTY_CELL) {
						board.find_empty_cells((cRow * cols) + cCol);
						field = board.getField();
					}
					currentField = (int[]) field.clone();
				}
			}

			if (rep)
				repaint();

		}
	}

	class MinesAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			mouseClick(e);
		}
	}
}
