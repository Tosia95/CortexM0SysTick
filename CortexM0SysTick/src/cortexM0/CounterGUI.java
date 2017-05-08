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
	PulseGenerator generator;
	CortexM0SysTick myDemoCounter = new CortexM0SysTick();

	/*
	 * 
	 */
	public CounterGUI() {
		setSize(444, 444);
		createGUI();
		generator = new PulseGenerator();
		generator.addActionListener(this);
		setVisible(true);
	}

	public void createGUI() {

		// Container contentPane = getContentPane();

		// dzielenie okna na panele i ustawianie układu paneli
		setLayout(new BorderLayout());

		JPanel pLewy = new JPanel();
		JPanel pSrodkowy = new JPanel();
		JPanel pPrawy = new JPanel();
		add(pLewy, BorderLayout.WEST);
		add(pSrodkowy, BorderLayout.CENTER);
		add(pPrawy, BorderLayout.EAST);

		pLewy.setLayout(new GridLayout(0, 2));

		// rejestry - Panel Lewy

		opisCVR = new JLabel("CVR");
		rejCVR = new JTextField(3);
		rejCVR.setEnabled(true);

		opisRVR = new JLabel("RVR");
		rejRVR = new JTextField(3);
		rejRVR.setEnabled(true);

		pLewy.add(rejRVR);
		pLewy.add(opisCVR);
		pLewy.add(opisRVR);
		pLewy.add(rejCVR);

		rejCVR.addActionListener(e -> {
			try {
				myDemoCounter.setCVR(Integer.parseInt(rejCVR.getText()));
				rejCVR.setText(myDemoCounter.getCVR() + "");
			} catch (NumberFormatException nf) {
			}
			rejCVR.setText(myDemoCounter.getCVR() + "");
		});

		rejRVR.addActionListener(e -> { // wyrazenie LAMBDA
			try {
				myDemoCounter.setRVR(Integer.parseInt(rejRVR.getText()));
			} catch (NumberFormatException nf) {
			}
			rejRVR.setText(myDemoCounter.getRVR() + "");
		});

		// flagi - Panel Lewy

		countFlag = new JRadioButton();
		JLabel opisCount = new JLabel("CountFlag");
		countFlag.setEnabled(true);

		countFlag.addActionListener(e -> {
			myDemoCounter.setEnableFlag(countFlag.isSelected());
		});

		pLewy.add(opisCount);
		pLewy.add(countFlag);

		enableFlag = new JRadioButton();
		JLabel opisEnable = new JLabel("EnableFlag");
		enableFlag.setEnabled(true);

		enableFlag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myDemoCounter.setEnableFlag(enableFlag.isSelected());
				// displayCounterState
			}
		});

		pLewy.add(opisEnable);
		pLewy.add(enableFlag);

		tickingFlag = new JRadioButton();
		JLabel opisTicking = new JLabel("TickingFlag");
		tickingFlag.setEnabled(true);
		tickingFlag.addActionListener(this);

		pLewy.add(opisTicking);
		pLewy.add(tickingFlag);

		// przycisk wysylania impulsow
		kImpuls = new JTextField(10);
		jImpuls = new JButton("1 impuls");
		stoImpuls = new JButton("100 impulsów");
		jImpuls.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				myDemoCounter.impuls();
				rejCVR.setText("" + myDemoCounter.getCVR());
				rejRVR.setText("" + myDemoCounter.getRVR());
				
				if (myDemoCounter.isCountFlag()) 
				{
					myDemoCounter.setCountFlag(true);
					countFlag.setSelected(true);
				} 
				else
					countFlag.setSelected(false);

				if (myDemoCounter.isTickingFlag()) 
				{
					if (myDemoCounter.isCountFlag())
					myDemoCounter.setCountFlag(true);
					tickingFlag.setSelected(true);
				}	 
				else
					tickingFlag.setSelected(false);
			}
		});

		stoImpuls.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i<100; i++)
                    myDemoCounter.impuls();
                rejCVR.setText("" +myDemoCounter.getCVR());
                rejRVR.setText("" +myDemoCounter.getRVR());
                if(myDemoCounter.isCountFlag()) {
                    myDemoCounter.setCountFlag(true);
                    countFlag.setSelected(true);
                } else
                    countFlag.setSelected(false);
                
                if(myDemoCounter.isTickingFlag()) {
                    if(myDemoCounter.isCountFlag())
                        myDemoCounter.setCountFlag(true);
                    tickingFlag.setSelected(true);
                } else
                    tickingFlag.setSelected(false);
            }
        });


		pSrodkowy.add(jImpuls);
		pSrodkowy.add(stoImpuls);
		pSrodkowy.add(kImpuls);

		JRadioButton genOnOff = new JRadioButton();
		JLabel etGenerator = new JLabel("Generator", SwingConstants.CENTER);
		pPrawy.add(etGenerator);
		pPrawy.add(genOnOff);

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
		int n = 0;
		try {
			n = Integer.parseInt(kImpuls.getText());
		} catch (NumberFormatException nf) {
		}
		for (int i = 0; i <= n; i++) {
			myDemoCounter.impuls();
		}

	}

	// NIE MAM POJECIA CO DALEJ
	// WIEM CO CHCIALABYM ZROBIC, ALE NIE WIEM JAK SIE ZABRAC :(((
	///
}
