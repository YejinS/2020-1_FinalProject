import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.event.*;

public class StartTry extends JFrame {
	private JPanel panel;
	private JPanel panel1;
	private JPanel basePanel;
	private JLabel titleLabel;
	private JLabel label;
	private JButton startButton, closeButton;
	private String selectedLevel;
	private JComboBox cb;
	JComboBox levelList;

	public StartTry() {
		setTitle("난수 선택하기 게임");
		setSize(300, 150);

		panel = new JPanel();
		panel1 = new JPanel();

		titleLabel = new JLabel("난수 선택하기 게임");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		label = new JLabel("난이도를 선택하세요.");

		String[] level = { "Easy 3x3", "Easy 4x4", "Easy 5x5", "Hard 3x3", "Hard 4x4", "Hard 5x5" };
		JComboBox levelList = new JComboBox(level);
		levelList.setSelectedIndex(0);
		levelList.addActionListener(new ComboListener());

		panel.add(label);
		panel.add(levelList);

		startButton = new JButton("시작!");
		startButton.setSize(20, 40);
		panel1.add(startButton);

		this.add(titleLabel, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		this.add(panel1, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String selectedLevel = (String) cb.getSelectedItem();
			System.out.print(selectedLevel);

			startButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (selectedLevel.equals("Easy 3x3")) {
						Easy3x3 easy3x3 = new Easy3x3();
						easy3x3.setDefaultCloseOperation(easy3x3.DISPOSE_ON_CLOSE);
						easy3x3.setVisible(true);
					} else if (selectedLevel.equals("Easy 4x4")) {
						Easy4x4 easy4x4 = new Easy4x4();
						easy4x4.setDefaultCloseOperation(easy4x4.DISPOSE_ON_CLOSE);
						easy4x4.setVisible(true);
					} else if (selectedLevel.equals("Easy 5x5")) {
						Easy5x5 easy5x5 = new Easy5x5();
						easy5x5.setDefaultCloseOperation(easy5x5.DISPOSE_ON_CLOSE);
						easy5x5.setVisible(true);
					} else if (selectedLevel.equals("Hard 3x3")) {
						Hard hard3x3 = new Hard();
						hard3x3.setDefaultCloseOperation(hard3x3.DISPOSE_ON_CLOSE);
						hard3x3.setVisible(true);
					} else if (selectedLevel.equals("Hard 4x4")) {
						Hard4x4 hard4x4 = new Hard4x4();
						hard4x4.setDefaultCloseOperation(hard4x4.DISPOSE_ON_CLOSE);
						hard4x4.setVisible(true);
					} else if (selectedLevel.equals("Hard 5x5")) {
						Hard5x5 hard5x5 = new Hard5x5();
						hard5x5.setDefaultCloseOperation(hard5x5.DISPOSE_ON_CLOSE);
						hard5x5.setVisible(true);
					}

				}
			});
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartTry start = new StartTry();

	}

}