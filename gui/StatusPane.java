package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusPane extends JPanel {
	private JLabel mines;
	private JLabel life;
	private JLabel elapsedTime;
	private JLabel status;

	private String elapsedTimeStr = "";
	private int currentLife;
	private ClockListener clock = new ClockListener();
	private Timer timer = new Timer(53, clock);
	private boolean timerOn = false;
	private Date elapsedDateTime;
	private long startTime;
	private SimpleDateFormat dateformat = new SimpleDateFormat("mm:ss.SSS");

	public StatusPane(int width) {
		setPreferredSize(new Dimension(width, 50));
		initCompontents(width);
	}

	public void initCompontents(int width) {
		mines = new JLabel("Mines: 0");
		life = new JLabel("Life: 100%");
		status = new JLabel("Status: Ready");
		elapsedTime = new JLabel("Elapsed Time: 0");
		elapsedTimeStr = "0";
		currentLife = 100;

		Dimension first = new Dimension(width * 2 / 3 - 5, 20);
		Dimension second = new Dimension(width / 3 - 5, 20);


		status.setPreferredSize(first);
		mines.setPreferredSize(second);
		mines.setHorizontalAlignment(JLabel.RIGHT);
		elapsedTime.setPreferredSize(first);
		if (width <= 240)
			elapsedTime.setFont(elapsedTime.getFont().deriveFont(8f));

		life.setPreferredSize(second);
		life.setHorizontalAlignment(JLabel.RIGHT);

		timer.setInitialDelay(0);

		resetClock();
		add(status);
		add(mines);
		add(elapsedTime);
		add(life);
	}

	public void resetClock() {
		startTime = System.currentTimeMillis();
		updateClock();
	}

	public void startClock() {
		timer.start();
		timerOn = true;
	}

	public void stopClock() {
		updateClock();
		timer.stop();
		timerOn = false;
	}

	private void updateClock() {
		elapsedDateTime = new Date(System.currentTimeMillis() - startTime);
		setElapsedTime(dateformat.format(elapsedDateTime));
	}


	private class ClockListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (timerOn)
				updateClock();
		}
	}

	public String getElapsedDateTime() {
		return dateformat.format(elapsedDateTime);
	}

	public void setElapsedTime(String time) {
		elapsedTime.setText("Elapsed Time: " + time);
		elapsedTimeStr = time;
	}

	public void setMines(int m) {
		mines.setText("Mines: " + m);
	}

	public void setLife(int l) {
		currentLife = l;
		life.setText("Life: " + l + "%");
	}

	public void setStatus(int stat) {
		String statStr = "Ready";

		switch (stat) {
			case 0:
				statStr = "Ready";
				break;
			case 1:
				statStr = "In Game";
				break;
			case 2:
				statStr = "Game Won";
				break;
			case 3:
				statStr = "Game Lost";
				break;
			case 4:
				statStr = "No marks left";
				break;
			case 5:
				statStr = "Game Solved";
				break;
		}

		status.setText("Status: " + statStr);
	}

	public String getElapsedTimeStr() {
		return elapsedTimeStr;
	}

	public int getCurrentLife() {
		return currentLife;
	}
}