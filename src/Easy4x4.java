import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Easy4x4 extends JFrame implements ActionListener, Runnable {
	
	private JLabel label;
	private JLabel successLabel;
	private JLabel failLabel;
	private JTextField txt;
	private JPanel panel1;
	private JPanel panel2;  
	private JPanel panel3;  //성공메세지 창
	private JPanel panel4;  //실패메세지 창
	JButton[] btn;
	
	private long remainTime;
	private Timer timer = new Timer();
	private JLabel remainTimeLabel = new JLabel();
	
	private String trying;
	private Thread th;
	
	public Easy4x4() {
		setTitle ("난수 선택하기 게임");
		setSize(300, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("남은 시간");
		txt = new JTextField(18);
		
		panel1 = new JPanel(); 
		panel1.setLayout(new BorderLayout());
		
		panel1.add(label, BorderLayout.WEST);
		panel1.add(remainTimeLabel, BorderLayout.CENTER);
		panel1.add(txt, BorderLayout.EAST);
		
		
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(4, 4, 1, 1));

		btn = new JButton[16];
		int[] num = new int[16];
		Random rand = new Random();

		for (int i = 0; i < 16; i++) {
			while (true) {
				int count = 0;
				num[i] = rand.nextInt(16) + 1;
					
				 //랜덤으로 중복된 숫자 빼기
				   for(int j=0; j < i; j++){
					    if(num[i]==num[j]){
					     count++;
					    }
				    }
				   if (count == 0) {break;}
			}
		}	
			
		for (int k = 0; k < 16; k++) {
				btn[k] = new JButton("" + num[k]);
				btn[k].addActionListener(this);
				panel2.add(btn[k]);
		}
		
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
			
		setVisible(true);
		start();
		createTimer();	
//		
//		try {
//			Thread.sleep((remainTime + 1) * 1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		panel4 = new JPanel();
//		failLabel = new JLabel("실패!");
//		panel4.add(failLabel);
//		panel2.setVisible(false);
//		this.add(panel4, BorderLayout.CENTER);
//		setVisible(true);
	}
	
	public void start(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		th = new Thread(this);  // 스레드 생성
		th.start();  // 스레드 실행
	}
	
	public void run(){ // 스레드가 무한 루프될 부분
		
		  while(true){ // while 문으로 무한 루프 시키기
				try {
					Thread.sleep((remainTime + 1) * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				panel4 = new JPanel();
				failLabel = new JLabel("실패!");
				panel4.add(failLabel);
				panel2.setVisible(false);
				this.add(panel4, BorderLayout.CENTER);
				setVisible(true);
				th.stop();
		  }
	}
	
	public void createTimer() {
		remainTime = 30;
		remainTimeLabel.setText("  " + remainTime + "초");

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(remainTime < 1) {
					timer.cancel();
				}
				remainTimeLabel.setText("  " + remainTime -- + "초");
			}
		}, 1000, 1000);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String answerTry = e.getActionCommand();
		txt.setText(txt.getText() + answerTry);
		trying = txt.getText();
		
		BigInteger a = new BigInteger("12345678910111213141516");
		BigInteger b = new BigInteger(trying);
		
		if (trying != null && !"".equals(trying))
		{
			if (b.equals(a))
			{
				System.out.print("성공");
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
		// TODO Auto-generated method stub
		new Easy4x4();
	}

}
