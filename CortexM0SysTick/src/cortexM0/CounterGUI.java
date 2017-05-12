package cortexM0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterGUI extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel textCVR, textRVR;
	JTextField regCVR, regRVR, kImpText;
	JButton oneImp, hImp, kImp;
	JRadioButton ticking, enable, count, genOnOff;
	PulseGenerator generator;
	CortexM0SysTick myDemoCounter = new CortexM0SysTick();

	/*
	 * 
	 */
	public CounterGUI()
	{
		setSize(444, 444);
		createGUI();
		generator = new PulseGenerator();
		generator.addActionListener(this);
		setVisible(true);
	}

	public void createGUI()
	{

		// Container contentPane = getContentPane();

		// dzielenie okna na panele i ustawianie układu paneli
		setLayout(new BorderLayout());

		JPanel pLeft = new JPanel();
		JPanel pCenter = new JPanel();
		JPanel pRight = new JPanel();
		add(pLeft, BorderLayout.WEST);
		add(pCenter, BorderLayout.CENTER);
		add(pRight, BorderLayout.EAST);

		// pLeft.setBackground(Color.YELLOW);
		// pCenter.setBackground(Color.GREEN);
		// pRight.setBackground(Color.RED);

		pLeft.setLayout(new GridLayout(0, 2));

		/************************************************
		 * LEFT PANEL REGISTERS
		 */

		textCVR = new JLabel("CVR");
		regCVR = new JTextField(3);
		regCVR.setEnabled(true);

		textRVR = new JLabel("RVR");
		regRVR = new JTextField(3);
		regRVR.setEnabled(true);

		pLeft.add(regRVR);
		pLeft.add(textCVR);
		pLeft.add(textRVR);
		pLeft.add(regCVR);

		regCVR.addActionListener(e ->
		{
			try
			{
				myDemoCounter.setCVR(Integer.parseInt(regCVR.getText()));
				regCVR.setText(myDemoCounter.getCVR() + "");
			} catch (NumberFormatException nf)
			{
			}
			regCVR.setText(myDemoCounter.getCVR() + "");
		});

		regRVR.addActionListener(e ->
		{
			try
			{
				myDemoCounter.setRVR(Integer.parseInt(regRVR.getText()));
			} catch (NumberFormatException nf)
			{
			}
			regRVR.setText(myDemoCounter.getRVR() + "");
		});

		count = new JRadioButton();
		JLabel textCount = new JLabel("Count Flag");
		count.setEnabled(true);

		count.addActionListener(e ->
		{
			myDemoCounter.setCountFlag(count.isSelected());
		});

		pLeft.add(textCount);
		pLeft.add(count);

		enable = new JRadioButton();
		JLabel textEnable = new JLabel("Enable Flag");
		enable.setEnabled(true);

		enable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				myDemoCounter.setEnableFlag(enable.isSelected());
				// displayCounterState
			}
		});

		pLeft.add(textEnable);
		pLeft.add(enable);

		ticking = new JRadioButton();
		JLabel textTicking = new JLabel("Ticking Flag");
		ticking.setEnabled(true);

		ticking.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				myDemoCounter.setEnableFlag(ticking.isSelected());
				// displayCounterState
			}
		});

		pLeft.add(textTicking);
		pLeft.add(ticking);

		/************************************
		 * CENTER PANEL IMPULS SENDING 
		 */
		pCenter.setLayout(new GridLayout(0, 2));
		kImpText = new JTextField(10);
		oneImp = new JButton("1 impuls");
		hImp = new JButton("100 imp");
		kImp = new JButton("send");

		oneImp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				myDemoCounter.impuls();
				regCVR.setText("" + myDemoCounter.getCVR());
				regRVR.setText("" + myDemoCounter.getRVR());

				if (myDemoCounter.isCountFlag())
				{
					myDemoCounter.setCountFlag(true);
					count.setSelected(true);
				} else
					count.setSelected(false);

				if (myDemoCounter.isTickingFlag())
				{
					if (myDemoCounter.isCountFlag())
						myDemoCounter.setCountFlag(true);
					ticking.setSelected(true);
				} else
					ticking.setSelected(false);
			}
		});

		hImp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for (int i = 0; i < 100; i++)
					myDemoCounter.impuls();
				regCVR.setText("" + myDemoCounter.getCVR());
				regRVR.setText("" + myDemoCounter.getRVR());
				if (myDemoCounter.isCountFlag())
				{
					myDemoCounter.setCountFlag(true);
					count.setSelected(true);
				} else
					count.setSelected(false);

				if (myDemoCounter.isTickingFlag())
				{
					if (myDemoCounter.isCountFlag())
						myDemoCounter.setCountFlag(true);
					ticking.setSelected(true);
				} else
					ticking.setSelected(false);
			}
		});

		kImp.addActionListener(new ActionListener()
		{
			int kImp = 0;

			public void actionPerformed(ActionEvent e)
			{
				try
				{
					kImp = Integer.parseInt(kImpText.getText());
				} catch (NumberFormatException nf)
				{
				}
				
				for (int i = 0; i < kImp; i++)
					myDemoCounter.impuls();
				regCVR.setText("" + myDemoCounter.getCVR());
				regRVR.setText("" + myDemoCounter.getRVR());
				if (myDemoCounter.isCountFlag())
				{
					myDemoCounter.setCountFlag(true);
					count.setSelected(true);
				} else
					count.setSelected(false);

				if (myDemoCounter.isTickingFlag())
				{
					if (myDemoCounter.isCountFlag())
						myDemoCounter.setCountFlag(true);
					ticking.setSelected(true);
				} else
					ticking.setSelected(false);
			}
		});

		pCenter.add(oneImp);
		pCenter.add(hImp);
		pCenter.add(kImp);
		pCenter.add(kImpText);

		/*****************************************************
		 * RIGHT PANEL GENERATOR
		 * ***************************************************
		 */

		JRadioButton genOnOff = new JRadioButton();
		JLabel etGenerator = new JLabel("Generator", SwingConstants.CENTER);
		pRight.add(etGenerator);
		pRight.add(genOnOff);

		validate();

		pack();
	}

	/************************************************
	 * main
	 */
	public static void main(String[] args)
	{
		new CounterGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}

}
