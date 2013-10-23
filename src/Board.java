package src;

import java.util.Random;
import java.util.ArrayList;


public class Board {

	private final int COVER_FOR_CELL = 10;
	private final int EMPTY_CELL = 0;
	private final int MINE_CELL = 9;
	private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;

	private int mines_left;
	private int[] field;
	private int mines = 40;
	private int rows = 16;
	private int cols = 16;
	private long randomSeed;
	private int all_cells;
	private ArrayList<Integer> lastMoves;

	public Board(int diff, long seed) {
		init(diff, seed);

		generateBoard();
	}

	public Board(int diff, String seed) {
		init(diff, convertSeed(seed));

		generateBoard();
	}

	private void init(int diff, long seed) {
		int[] sizeArr = {16, 20, 25};
		int[] minesArr = {40, 80, 160};
		this.randomSeed = seed;
		mines = minesArr[diff];

		rows = sizeArr[diff];

		cols = sizeArr[diff] + sizeArr[diff] / 3 * diff;

		mines_left = mines;

		all_cells = rows * cols;
	}

	public Board(String difficulty, long seed, int[] data) {
		int diff;

		switch (difficulty.charAt(0)) {
			case 'E':
				diff = 0;
				break;
			case 'M':
				diff = 1;
				break;
			case 'H':
				diff = 2;
				break;
			default:
				diff = 0;
		}

		init(diff, seed);
		field = new int[data.length];

		for (int i = 0; i < field.length; i++)
			field[i] = data[i];
	}

	public void find_empty_cells(int j) {
		int current_col = j % cols;
		int cell;

		if (current_col > 0) {
			cell = j - cols - 1;
			if (cell >= 0) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					lastMoves.add(cell);
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
			}

			cell = j - 1;
			if (cell >= 0) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					lastMoves.add(cell);
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
			}

			cell = j + cols - 1;
			if (cell < all_cells) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					lastMoves.add(cell);
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
			}
		}

		cell = j - cols;
		if (cell >= 0) {
			if (field[cell] > MINE_CELL) {
				field[cell] -= COVER_FOR_CELL;
				lastMoves.add(cell);
				if (field[cell] == EMPTY_CELL)
					find_empty_cells(cell);
			}
		}
		cell = j + cols;
		if (cell < all_cells) {
			if (field[cell] > MINE_CELL) {
				field[cell] -= COVER_FOR_CELL;
				lastMoves.add(cell);
				if (field[cell] == EMPTY_CELL)
					find_empty_cells(cell);
			}
		}
		if (current_col < (cols - 1)) {
			cell = j - cols + 1;
			if (cell >= 0) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					lastMoves.add(cell);
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
			}

			cell = j + cols + 1;
			if (cell < all_cells) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					lastMoves.add(cell);
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
			}
			cell = j + 1;
			if (cell < all_cells) {
				if (field[cell] > MINE_CELL) {
					field[cell] -= COVER_FOR_CELL;
					lastMoves.add(cell);
					if (field[cell] == EMPTY_CELL)
						find_empty_cells(cell);
				}
			}
		}

	}

	private void generateBoard() {
		Random random;
		int current_col;

		int i = 0;
		int position = 0;
		int cell = 0;

		random = new Random(randomSeed);

		field = new int[all_cells];

		for (i = 0; i < all_cells; i++)
			field[i] = COVER_FOR_CELL;


		i = 0;
		while (i < mines) {

			position = (int) (all_cells * random.nextDouble());

			if ((position < all_cells) &&
					(field[position] != COVERED_MINE_CELL)) {


				current_col = position % cols;
				field[position] = COVERED_MINE_CELL;
				i++;

				if (current_col > 0) {
					cell = position - 1 - cols;
					if (cell >= 0)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
					cell = position - 1;
					if (cell >= 0)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;

					cell = position + cols - 1;
					if (cell < all_cells)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
				}

				cell = position - cols;
				if (cell >= 0)
					if (field[cell] != COVERED_MINE_CELL)
						field[cell] += 1;
				cell = position + cols;
				if (cell < all_cells)
					if (field[cell] != COVERED_MINE_CELL)
						field[cell] += 1;

				if (current_col < (cols - 1)) {
					cell = position - cols + 1;
					if (cell >= 0)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
					cell = position + cols + 1;
					if (cell < all_cells)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
					cell = position + 1;
					if (cell < all_cells)
						if (field[cell] != COVERED_MINE_CELL)
							field[cell] += 1;
				}
			}
		}
	}

	public void setLastMoves(ArrayList<Integer> lm) {
		lastMoves = lm;
	}

	public int getMinesLeft() {
		return mines_left;
	}

	private long convertSeed(String randomSeed) {
		String longStr = "";
		for (int i = 0; i < randomSeed.length(); i++) {
			longStr += "" + Character.getNumericValue(randomSeed.charAt(i));
		}
		if (longStr.length() > 12) {
			longStr = longStr.substring(0, 12);
		}
		return Long.valueOf(longStr);
	}

	public long getRandomSeed() {
		return randomSeed;
	}

	public int[] getField() {
		return this.field;
	}

	public int[] getRowsColsMines() {
		int[] out = {rows, cols, mines};

		return out;
	}

	public String toString() {
		String out = "";

		for (int i = 0; i < rows; i++) {
			out += "|";
			for (int j = 0; j < cols; j++) {
				out += field[(i * cols) + j] + "|";
			}
			out += "\n";
		}

		return out;
	}
}
