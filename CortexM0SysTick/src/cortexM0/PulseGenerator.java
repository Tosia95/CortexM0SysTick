package cortexM0;
import java.awt.event.*;
import java.awt.*;


public class PulseGenerator extends Thread implements PulseSource
{
	private byte mode;
	private boolean triggered;
	private int ms;
	private int burst, tmp_burst;
	ActionListener actionListener;

	public PulseGenerator()
	{
		triggered = false;
		mode = PulseSource.CONTINOUS_MODE;
		ms = 1000;
		burst = 0;
		tmp_burst = burst;
		start();
	}

	public void addActionListener(ActionListener tgl)
	{
		actionListener = AWTEventMulticaster.add(actionListener, tgl);
	}

	public void removeActionListener(ActionListener tgl)
	{
		actionListener = AWTEventMulticaster.remove(actionListener, tgl);
	}

	public void run()
	{
		while (true)
		{
			if (triggered)
			{
				if (mode == PulseSource.BURST_MODE)
				{
					if (tmp_burst > 0)
					{
						tmp_burst--;
						if (actionListener != null)
							actionListener
									.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "burst_tick"));
						try
						{
							sleep(ms);
						} catch (InterruptedException e)
						{
						}
					} else
					{
						try
						{
							sleep(250);
						} catch (InterruptedException e)
						{
						}
					}
					if (tmp_burst == 0)
					{
						triggered = false;
					}
				}

				if (mode == PulseSource.CONTINOUS_MODE)
				{
					if (actionListener != null)
						actionListener
								.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "continous_tick"));
					try
					{
						sleep(ms);
					} catch (InterruptedException e)
					{
					}
				}
			} else
			{
				try
				{
					sleep(250);
				} catch (InterruptedException e)
				{
				}
			}
		}
	}

	public void trigger()
	{
		triggered = true;
		tmp_burst = burst;
	}

	public void setMode(byte mode)
	{
		this.mode = mode;
	}

	public byte getMode()
	{
		return mode;
	}

	public void halt()
	{
		triggered = false;
	}

	public void setPulseDelay(int ms)
	{
		this.ms = ms;
	}

	public int getPulseDelay()
	{
		return ms;
	}

	public void setPulseCount(int burst)
	{
		this.burst = burst;
	}
}