package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import src.IO.*;

public class StatsContent extends JPanel {
	private String[] tableHeaders = {"Pos", "Name", "W/L Ratio"};
	private StatsFileIO sfIO = new StatsFileIO();
	private JTable statsTable;
	private JScrollPane spTable;

	public StatsContent(String difficulty) {
		initComponents(getTableData(difficulty));
	}

	public void initComponents(Object[][] data) {
		statsTable = new JTable(data, tableHeaders);
		spTable = new JScrollPane(statsTable);
		spTable.setPreferredSize(new Dimension(550, 300));
		statsTable.setFillsViewportHeight(true);

		add(spTable);
	}

	private Object[][] getTableData(String diff) {
		char d = diff.toLowerCase().charAt(0);

		ArrayList<StatsData> sds = sfIO.getStatsData(d);


		Object[][] out = new Object[sds.size()][3];

		for (int i = 0; i < out.length; i++) {

			StatsData sd = sds.get(i);
			Object[] line = {i + 1, sd.getUser(), sd.getWinLossRatio()};
			out[i] = line;
		}

		return out;
	}
}
