package cortexM0;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterGUI extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel opisCVR, opisRVR;
	JTextField rejCVR, rejRVR, kImpuls;
	JButton jImpuls, stoImpuls;
	JRadioButton tickingFlag, enableFlag, countFlag;
	CortexM0SysTick myDemoCounter = new CortexM0SysTick(); 
	/*
	 * 
	 */
	public CounterGUI() {
		setSize(444,444);
		createGUI();
		setVisible(true);
	}
	public void createGUI() {
		
		//Container contentPane = getContentPane();
		
		//dzielenie okna na panele i ustawianie układu paneli 
		setLayout(new BorderLayout());
		
		JPanel pLewy = new JPanel();
		JPanel pSrodkowy = new JPanel();
		JPanel pPrawy = new JPanel();
		add(pLewy, BorderLayout.WEST);
		add(pSrodkowy, BorderLayout.CENTER);
		add(pPrawy, BorderLayout.EAST);
		
		pLewy.setLayout(new GridLayout(0,2));
		
		//rejestry - Panel Lewy
		opisCVR = new JLabel("CVR");
		pLewy.add(opisCVR);
		rejCVR = new JTextField(3);
		pLewy.add(rejCVR);
		opisRVR = new JLabel("RVR");
		pLewy.add(opisRVR);
		rejRVR = new JTextField(3);
		pLewy.add(rejRVR);
		
		rejCVR.addActionListener(this);
		rejRVR.addActionListener(this);
		
		
		//flagi - Panel Lewy
		countFlag = new JRadioButton();
		JLabel opisCount = new JLabel("CountFlag");
		countFlag.setEnabled(true);
		countFlag.addActionListener(this);
		pLewy.add(opisCount);
		pLewy.add(countFlag);
		
		enableFlag = new JRadioButton();
		JLabel opisEnable = new JLabel("EnableFlag");
		enableFlag.setEnabled(true);
		enableFlag.addActionListener(this);
		pLewy.add(opisEnable);
		pLewy.add(enableFlag);
		
		tickingFlag = new JRadioButton();
		JLabel opisTicking = new JLabel("TickingFlag");
		tickingFlag.setEnabled(true);
		tickingFlag.addActionListener(this);
		pLewy.add(opisTicking);
		pLewy.add(tickingFlag);
		
		//przycisk wysyłania impulsów
		kImpuls = new JTextField(10);
		jImpuls = new JButton("1 impuls");
		stoImpuls = new JButton("100 impulsów");
		jImpuls.addActionListener(this);
		stoImpuls.addActionListener(this);
		pSrodkowy.add(jImpuls);
		pSrodkowy.add(stoImpuls);
		pSrodkowy.add(kImpuls);
		
		
		JLabel opisGenerator = new JLabel("Tu będą pierdy!");
		pPrawy.add(opisGenerator);
		

		
		validate();
		pack();
	}
	/************************************************
	 * main
	 */
	public static void main(String[] args) {
		new CounterGUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == enableFlag) 
			myDemoCounter.setEnableFlag(enableFlag.isSelected());
			//pLewy.setBackground(Color.GREEN);
		
		if(e.getSource() == tickingFlag) 
			myDemoCounter.setTickingFlag(tickingFlag.isSelected());
		
		if(e.getSource() == jImpuls)
			myDemoCounter.impuls();
		
		if(e.getSource() == stoImpuls)
			for(int i =0; i <= 100; i++) myDemoCounter.impuls();
		
		rejCVR.setText("" + myDemoCounter.getCVR());
		rejRVR.setText("" + myDemoCounter.getRVR());
	}
	
	class CounterInfoComponent extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			
		}
	}
}
