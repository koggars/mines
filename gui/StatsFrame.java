package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class StatsFrame extends JFrame {
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JButton closeBtn = new JButton("Close");

	public StatsFrame() {
		initComponents();

		this.setPreferredSize(new Dimension(600, 300));
		this.setResizable(false);
		this.repaint();
		this.pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initComponents() {
		String[] title = {"Easy", "Medium", "Hard"};
		for (int i = 0; i < 3; i++) {
			StatsContent tabbedPannel = new StatsContent(title[i]);

			tabbedPane.addTab(title[i], tabbedPannel);
		}

		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				dispose();
			}
		});

		add(tabbedPane, BorderLayout.CENTER);
		add(closeBtn, BorderLayout.SOUTH);
	}

}
