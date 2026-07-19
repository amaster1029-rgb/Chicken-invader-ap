package ChickenInvaders.ui;

import ChickenInvaders.database.DatabaseManager;
import ChickenInvaders.main.GameMain;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;


public class HighScorePanel extends JPanel {
    private GameMain gameMain;
    private DefaultTableModel tableModel;

    public HighScorePanel(GameMain gameMain){
        this.gameMain = gameMain;
        setLayout(new BorderLayout());
        setBackground(new Color(0x5E005D));

        JLabel titleLabel = new JLabel("HIGH SCORE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setForeground(new Color(0xF2B69E1B));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Username", "Top Score", "Level Reached", "Date & Time"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        table.setEnabled(false);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(50, 50, 80));
        table.getTableHeader().setForeground(Color.white);
        table.setBackground(new Color(30, 30, 50));
        table.setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(20, 20, 40));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(20, 20, 40));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> gameMain.showPanel("MainMenu"));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshTableData();
            }
        });
    }

    private void refreshTableData(){
        tableModel.setRowCount(0);
        List<String[]> highScores = DatabaseManager.getHighScoresList();
        
        for (String[] row : highScores)
            tableModel.addRow(row);
    }
}
