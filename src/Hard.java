import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Hard extends JFrame implements ActionListener, Runnable {

	private JLabel label;
	private JLabel successLabel;
	private JLabel failLabel;
	private JTextField txt;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3; // �����޼��� â
	private JPanel panel4; // ���и޼��� â
	JButton[] btn;

	private long remainTime;
	private Timer timer = new Timer();
	private JLabel remainTimeLabel = new JLabel();

	private long answer = 0;
	private String trying;
	private Thread th;

	public Hard() {
		setTitle("���� �����ϱ� ����");
		setSize(300, 400);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		label = new JLabel("���� �ð�");
		txt = new JTextField(10);

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());

		panel1.add(label, BorderLayout.WEST);
		panel1.add(remainTimeLabel, BorderLayout.CENTER);
		panel1.add(txt, BorderLayout.EAST);

		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(3, 3, 1, 1));

		btn = new JButton[9];
		int[] num = new int[9];
		Random rand = new Random();

		for (int i = 0; i < 9; i++) {

			while (true) {
				int count = 0;
				num[i] = rand.nextInt(9) + 1;

				// �������� �ߺ��� ���� ����
				for (int j = 0; j < i; j++) {
					if (num[i] == num[j]) {
						count++;
					}
				}
				if (count == 0) {
					break;
				}
			}
		}

		for (int k = 0; k < 9; k++) {
			btn[k] = new JButton("" + num[k]);
			btn[k].addActionListener(this);
			panel2.add(btn[k]);
		}

		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);

		setVisible(true);
		start();
//		run();
//		createTimer();
	}

	public void start() {
		th = new Thread(this);
		th.start();
	}

	// ���� �������� ��½�� ���� ������ �������� �����ϱ� ���� ��
	public void setAnswer(long a) {
		answer = a;
	}

	public long getAnswer() {
		return answer;
	}

	public void run() {
		while (true) {
			showPattern();
		}
	}

	public void showPattern() {
		String answerS = "";
		// txt.setEnabled(false);
		remainTime = 20;
		remainTimeLabel.setText("  " + remainTime + "��");

		for (int i = 0; i < 10; i++) {

			int index = (int) (Math.random() * btn.length);
			Color color = btn[index].getBackground();
			btn[index].setBackground(Color.yellow);

			try {
				Thread.sleep(1000);
				btn[index].setBackground(null);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			btn[index].setBackground(color);
			answerS = answerS + btn[index].getText(); // random������ ���ڵ��� ��Ʈ������ �� �ڸ��� �ڸ���� ����
			this.invalidate();
		}

		answer = Long.parseLong(answerS);
		txt.setEnabled(true);
		createTimer();

		try {
			Thread.sleep((remainTime + 1) * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.print("����");
		panel4 = new JPanel();
		failLabel = new JLabel("����!");
		panel4.add(failLabel);
		panel2.setVisible(false);
		this.add(panel4, BorderLayout.CENTER);
		setVisible(true);
		th.stop();
	}

	public void createTimer() {

		remainTime = 20;
		remainTimeLabel.setText("  " + remainTime + "��");

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (remainTime < 1) {
					timer.cancel();
				}
				remainTimeLabel.setText("  " + remainTime-- + "��");
			}
		}, 1000, 1000);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String answerTry = e.getActionCommand();
		txt.setText(txt.getText() + answerTry); // ����� �Է°� �ޱ�
		trying = txt.getText(); // �ؽ�Ʈ �ʵ忡 �Էµ� ���� '������� ����'���� �����ϱ�

		if (trying != null && !"".equals(trying)) {
			if (answer == Long.parseLong(trying)) // ���� ���� answer�� �Է°� try�� ������
			{
				panel3 = new JPanel();
				successLabel = new JLabel("����!");
				panel3.add(successLabel);
				panel2.setVisible(false);
				this.add(panel3, BorderLayout.CENTER);
				setVisible(true);
				timer.cancel();
			}
		}
	}

	public static void main(String[] args) {
		// StartTry start = new StartTry();
		new Hard();
	}

}
