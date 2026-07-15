package ChickenInvaders.ui;

import ChickenInvaders.main.GameMain;
import javax.swing.*;
import java.awt.*;

public class HowToPlayPanel extends JPanel {

    public HowToPlayPanel(GameMain gameMain) {
        setLayout(null);
        setBackground(new Color(0x9F1717));

        JLabel titleLabelControl = new JLabel("CONTROLS", SwingConstants.CENTER);
        titleLabelControl.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabelControl.setForeground(Color.white);
        titleLabelControl.setBounds(150, 40, 400, 50);

        JLabel title1 = new JLabel("Move right ................... → / D", SwingConstants.CENTER);
        title1.setFont(new Font("Arial", Font.ITALIC, 20));
        title1.setForeground(Color.white);
        title1.setBounds(150, 110, 400, 50);

        JLabel title2 = new JLabel("Move left ..................... ← / A", SwingConstants.CENTER);
        title2.setFont(new Font("Arial", Font.ITALIC, 20));
        title2.setForeground(Color.white);
        title2.setBounds(150, 150, 400, 50);

        JLabel title3 = new JLabel("Move up ...................... ↑ / W", SwingConstants.CENTER);
        title3.setFont(new Font("Arial", Font.ITALIC, 20));
        title3.setForeground(Color.white);
        title3.setBounds(150, 190, 400, 50);

        JLabel title4 = new JLabel("Move down .................. ↓ / S", SwingConstants.CENTER);
        title4.setFont(new Font("Arial", Font.ITALIC, 20));
        title4.setForeground(Color.white);
        title4.setBounds(150, 230, 400, 50);

        JLabel title5 = new JLabel("Shot .......................... Space", SwingConstants.CENTER);
        title5.setFont(new Font("Arial", Font.ITALIC, 20));
        title5.setForeground(Color.white);
        title5.setBounds(150, 270, 400, 50);

        JLabel title6 = new JLabel("Pause ............................... P", SwingConstants.CENTER);
        title6.setFont(new Font("Arial", Font.ITALIC, 20));
        title6.setForeground(Color.white);
        title6.setBounds(150, 310, 400, 50);

        JLabel title7 = new JLabel("End the game ................ Esc", SwingConstants.CENTER);
        title7.setFont(new Font("Arial", Font.ITALIC, 20));
        title7.setForeground(Color.white);
        title7.setBounds(150, 350, 400, 50);

        JButton backToMenu = new JButton("Back to Menu");
        backToMenu.setBounds(275, 420, 150, 40);

        backToMenu.addActionListener(e -> gameMain.showPanel("MainMenu"));

        add(titleLabelControl);
        add(title1);
        add(title2);
        add(title3);
        add(title4);
        add(title5);
        add(title6);
        add(title7);
        add(backToMenu);
    }
}