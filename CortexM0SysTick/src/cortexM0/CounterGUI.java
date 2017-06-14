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
	JRadioButton ticking, enable, count;
	PulseGenerator generator;
	CortexM0SysTick myDemoCounter;
	Knob burstGen, contGen;
	JToggleButton genOnOff, cGen, bGen;

	private JMenu help;
	private JMenuBar menuBar;
	private JMenuItem exit, about;

	// JFrame frame;

	/*
	 * 
	 */
	public CounterGUI()
	{
		myDemoCounter = new CortexM0SysTick();

		setSize(1000, 700);
		setTitle("CortexM0 SysTick Simulator");
		toBack();

		generator = new PulseGenerator();
		generator.addActionListener(this);
		setVisible(true);

		createGUI();
	}

	public void createGUI()
	{
		
		Image img = new ImageIcon("icon.png").getImage();
		setIconImage(img);
		
		exit = new JMenuItem("Exit");
		about = new JMenuItem("About");
		help = new JMenu("Help");
		help.add(about);
		help.add(exit);
		menuBar = new JMenuBar();
		menuBar.add(help);
		setJMenuBar(menuBar);

		about.addActionListener(e ->
		{
			JOptionPane.showMessageDialog(null,
					"CortexM0 SysTick Simulator\n@Aleksandra Tusiñska 218804\n@version 14.06.2017 ");
		});
		exit.addActionListener(e ->
		{
			System.exit(0);
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				String ObjButtons[] =
				{ "Yes", "No" };
				int res = JOptionPane.showOptionDialog(null, "Are You Sure?", "Cortex M0 SysTick Simulator",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (res == 0)
				{
					System.exit(0);
				}
			}
		});

		/////////////////////////////////////////////////////////////

		// dzielenie okna na panele i ustawianie ukÅ‚adu paneli
		setLayout(new BorderLayout());

		JPanel pLeft = new JPanel();
		JPanel pCenter = new JPanel();
		JPanel pRight = new JPanel();
		add(pLeft, BorderLayout.EAST);
		add(pCenter, BorderLayout.NORTH);
		add(pRight, BorderLayout.CENTER);

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
		regRVR.setPreferredSize(new Dimension(50, 50));
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
		pCenter.setLayout(new FlowLayout());

		kImpText = new JTextField(10);
		oneImp = new JButton("1 impulse");
		hImp = new JButton("100 pulse");
		kImp = new JButton("Enter the number of pulse and press");

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



		cMode.setLayout(new BoxLayout(cMode, 1));
		cMode.setPreferredSize(new Dimension(200, 200));
		bMode.setPreferredSize(new Dimension(200, 200));
		bMode.setLayout(new BoxLayout(bMode, 1));

		pRight.add(cMode, BorderLayout.WEST);
		pRight.add(bMode, BorderLayout.EAST);
		pRight.add(titleGen, BorderLayout.NORTH);

		genOnOff = new JToggleButton("On");
		JLabel etGenerator = new JLabel("Generator");
		JLabel bText = new JLabel("Burst Mode");
		JLabel cText = new JLabel("Continuous Mode");

		burstGen = new Knob();
		contGen = new Knob();
		bGen = new JToggleButton("On");
		bGen.setPreferredSize(new Dimension(40, 40));
		cGen = new JToggleButton("On");
		cGen.setPreferredSize(new Dimension(40, 40));

		int cV = contGen.convertToHz(contGen.getKnobValue());
		int bV = burstGen.convertToHz(burstGen.getKnobValue());

		genOnOff.addActionListener(e ->
		{
			if (genOnOff.isSelected())
			{
				JOptionPane.showMessageDialog(this, "Generator is on. Please choose mode");
				genOnOff.setText("Off");
			} else
			{
				generator.halt();
				cGen.setSelected(false);
				genOnOff.setText("On");
				bGen.setSelected(false);
				cGen.setText("On");
				bGen.setText("On");
			}
		});

		bGen.addActionListener(e ->
		{
			generator.setMode(PulseGenerator.BURST_MODE);
			refresh();
			if (genOnOff.isSelected())
			{
				if (bGen.isSelected())
				{
					generator.trigger();
					cGen.setSelected(false);
					bGen.setText("Off");
					cGen.setText("On");
				} else
				{
					generator.halt();
					cGen.setSelected(false);
					bGen.setText("On");

				}
			}
		});

		cGen.addActionListener(e ->
		{
			generator.setMode(PulseGenerator.CONTINOUS_MODE);
			refresh();
			if (genOnOff.isSelected())
			{
				if (cGen.isSelected())
				{
					generator.trigger();
					bGen.setSelected(false);
					cGen.setText("Off");
					bGen.setText("On");

				} else
				{
					generator.halt();
					cGen.setText("On");
					bGen.setSelected(false);
				}
			}
		});

		burstGen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int frequency = burstGen.convertToHz(burstGen.getKnobValue());
				int time_ms = Math.round(1000 / frequency);
				generator.setPulseCount(time_ms);
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

		pack();

	}

	public void refresh()
	{
		String toString = myDemoCounter.toString();

		regCVR.setText(String.valueOf(myDemoCounter.getCVR()));
		regRVR.setText(String.valueOf(myDemoCounter.getRVR()));
		
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
