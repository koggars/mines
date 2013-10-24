package gui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.GridLayout;


public class StatsFrame extends JFrame {
	private JTabbedPane tabbedPane = new JTabbedPane();


	public StatsFrame() {
		initComponents();

		this.setResizable(false);
		this.repaint();
		this.pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initComponents() {
		String[] title = {"Easy", "Medium", "Hard"};
		for (int i = 0; i < 3; i++) {
			JPanel tabbedPannel = makeTextPanel("Tabbed Plane " + title[i] + " " + i);

			tabbedPane.addTab(title[i], tabbedPannel);
		}

		add(tabbedPane);
	}

	private JPanel makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);

		panel.setPreferredSize(new Dimension(400, 400));
		return panel;
	}

}
