package cortexM0;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CounterGUI extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel textCVR, textRVR;
	JTextField regCVR, regRVR, kImpText, genMode;
	JButton oneImp, hImp, kImp;
	JRadioButton ticking, enable, count, genOnOff, bGen, cGen;
	PulseGenerator generator;
	CortexM0SysTick myDemoCounter;
	Knob burstGen, contGen;
	//JFrame frame;

	/*
	 * 
	 */
	public CounterGUI()
	{	
		myDemoCounter = new CortexM0SysTick();
		
		
		
		setSize(1000, 500);
		setTitle("CortexM0 SysTick Simulator");
		toBack();
		
		generator = new PulseGenerator();
		generator.addActionListener(this);
		setVisible(true);
		createGUI();
	}

	public void createGUI()
	{	
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	       addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent e){
	           String ObjButtons[] = {"Yes","No"};
	           int res = JOptionPane.showOptionDialog(null, 
	           "Are You Sure?", "Cortex M0 SysTick Simulator", 
	           JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
	           ObjButtons,ObjButtons[1]);
	           if(res==0)
	           {
	               System.exit(0);          
	           }
	         }
	       });     
	       
	       
		
		
		
		/////////////////////////////////////////////////////////////

		// dzielenie okna na panele i ustawianie układu paneli
		setLayout(new BorderLayout());

		JPanel pLeft = new JPanel();
		JPanel pCenter = new JPanel();
		JPanel pRight = new JPanel();
		add(pLeft, BorderLayout.EAST);
		add(pCenter, BorderLayout.CENTER);
		add(pRight, BorderLayout.WEST);

		// pLeft.setBackground(Color.YELLOW);
		// pCenter.setBackground(Color.GREEN);
		// pRight.setBackground(Color.RED);

		pLeft.setLayout(new GridLayout(0, 2));

		/************************************************
		 * LEFT PANEL REGISTERS
		 */

		textCVR = new JLabel("CVR");
		regCVR = new JTextField(3);
		regCVR.setPreferredSize(new Dimension(200, 100));
		regCVR.setEnabled(true);

		textRVR = new JLabel("RVR");
		regRVR = new JTextField(3);
		regRVR.setPreferredSize(new Dimension(200, 100));
		regRVR.setEnabled(true);

		pLeft.add(textRVR);
		pLeft.add(regRVR);
		pLeft.add(textCVR);
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
		kImp = new JButton("Enter the number of pulse and press");

		kImpText.setPreferredSize(new Dimension(40, 40));
		oneImp.setPreferredSize(new Dimension(40, 40));
		hImp.setPreferredSize(new Dimension(40, 40));
		kImp.setPreferredSize(new Dimension(40, 40));

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

		pRight.setLayout(new BorderLayout());

		JPanel cMode = new JPanel();
		JPanel bMode = new JPanel();
		JPanel titleGen = new JPanel();
		pRight.add(cMode, BorderLayout.CENTER);
		pRight.add(bMode, BorderLayout.SOUTH);
		pRight.add(titleGen, BorderLayout.NORTH);

		JRadioButton genOnOff = new JRadioButton();
		JLabel etGenerator = new JLabel("Generator");
		JLabel bText = new JLabel("Burst Mode");
		JLabel cText = new JLabel("Continuous Mode");

		burstGen = new Knob();
		contGen = new Knob();
		bGen = new JRadioButton();
		bGen.setPreferredSize(new Dimension(40, 40));
		cGen = new JRadioButton();
		cGen.setPreferredSize(new Dimension(40, 40));

		genOnOff.addActionListener(e ->
		{
			if (genOnOff.isSelected())
			{	
				JOptionPane.showMessageDialog(this, "Generator is on. Choose mode");
				generator.trigger();
			} else
			{
				generator.halt();
				cGen.setSelected(false);
				bGen.setSelected(false);
			}
		});

		bGen.addActionListener(e ->
		{
			generator.setMode(PulseGenerator.BURST_MODE);
			refresh();
			if (genOnOff.isSelected())
			{
				generator.trigger();
				cGen.setSelected(false);
			}
		});

		cGen.addActionListener(e ->
		{
			generator.setMode(PulseGenerator.CONTINOUS_MODE);
			refresh();
			if (genOnOff.isSelected())
			{
				generator.trigger();
				bGen.setSelected(false);
			}
		});

		burstGen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int frequency = burstGen.convertToHz(burstGen.getKnobValue());
				int time_ms = Math.round(1000 / frequency);
				generator.setPulseDelay(time_ms);
			}
		});

		contGen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int frequency = contGen.convertToHz(contGen.getKnobValue());
				int time_ms = Math.round(1000 / frequency);
				generator.setPulseDelay(time_ms);
			}
		});

		titleGen.add(etGenerator);
		titleGen.add(genOnOff);
		cMode.add(cText);
		cMode.add(cGen);
		cMode.add(contGen);
		bMode.add(bText);
		bMode.add(bGen);
		bMode.add(burstGen);

		validate();

		// pack();
		

	}

	public void refresh()
	{
		String toString = myDemoCounter.toString();
		// String tostringtab[] = tostring.split("\\s+");

		regCVR.setText(String.valueOf(myDemoCounter.getCVR()));
		regRVR.setText(String.valueOf(myDemoCounter.getRVR()));
		// TF_InterruptNumber.setText(String.valueOf(NoOfInterrupts));
		/*
		 * if (generator.getMode() == generator.BURST_MODE)
		 * genMode.setText("Burst"); else genMode.setText("Continuous");
		 */
	}

	/*
	 * public void update(Observable subject, Object arg) { NoOfInterrupts++; }
	 */
	public void stateChanged(ChangeEvent e)
	{
		generator.setPulseCount(burstGen.getKnobValue());
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == generator)
		{
			myDemoCounter.impuls();
			refresh();
		}

	}

	/************************************************
	 * main
	 */
	public static void main(String[] args)
	{
		new CounterGUI();
	}

}
