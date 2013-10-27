package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StatsContent extends JPanel {
	private String[] tableHeaders = {"Pos", "Name", "W/L Ratio", "Date", "Fastest Win MM:SS", "Life"};
	private Object[][] data = {{"1", "Kathy", "Smith","Snowboarding", "",""},{"2", "Kathy", "Smith","Snowboarding", "",""}};

	private JTable statsTable;
	private JScrollPane spTable;

	public StatsContent(String difficulty) {
		initComponents();
	}

	public void initComponents() {
		statsTable = new JTable(data, tableHeaders);
		spTable = new JScrollPane(statsTable);
		spTable.setPreferredSize(new Dimension(550, 300));
		statsTable.setFillsViewportHeight(true);

		add(spTable);
	}
}
