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
	private JPanel panel3; // 성공메세지 창
	private JPanel panel4; // 실패메세지 창
	JButton[] btn;

	private long remainTime;
	private Timer timer = new Timer();
	private JLabel remainTimeLabel = new JLabel();

	private long answer = 0;
	private String trying;
	private Thread th;

	public Hard() {
		setTitle("난수 선택하기 게임");
		setSize(300, 400);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		label = new JLabel("남은 시간");
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

				// 랜덤으로 중복된 숫자 빼기
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

	// 실제 랜덤으로 번쩍인 숫자 순서를 정답으로 설정하기 위한 것
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
		remainTimeLabel.setText("  " + remainTime + "초");

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
			answerS = answerS + btn[index].getText(); // random생성된 숫자들은 스트링으로 한 자리씩 자리잡기 위함
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

		System.out.print("실패");
		panel4 = new JPanel();
		failLabel = new JLabel("실패!");
		panel4.add(failLabel);
		panel2.setVisible(false);
		this.add(panel4, BorderLayout.CENTER);
		setVisible(true);
		th.stop();
	}

	public void createTimer() {

		remainTime = 20;
		remainTimeLabel.setText("  " + remainTime + "초");

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (remainTime < 1) {
					timer.cancel();
				}
				remainTimeLabel.setText("  " + remainTime-- + "초");
			}
		}, 1000, 1000);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String answerTry = e.getActionCommand();
		txt.setText(txt.getText() + answerTry); // 사용자 입력값 받기
		trying = txt.getText(); // 텍스트 필드에 입력된 값을 '사용자의 정답'으로 설정하기

		if (trying != null && !"".equals(trying)) {
			if (answer == Long.parseLong(trying)) // 실제 정답 answer과 입력값 try가 같으면
			{
				panel3 = new JPanel();
				successLabel = new JLabel("성공!");
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
