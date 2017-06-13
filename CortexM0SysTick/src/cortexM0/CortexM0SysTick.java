package cortexM0;

import java.util.Observable;
import java.util.Random;

public class CortexM0SysTick extends Observable
{
	// instance variables - replace the example below with your own
	private int CVR, RVR;
	private boolean enableFlag, tickingFlag, countFlag;

	public CortexM0SysTick()
	{
		// initialise instance variables
		Random rand = new Random();
		CVR = rand.nextInt(55555);
		RVR = rand.nextInt(44444);
	}

	/**
	 * Zapis do tego rejestru zeruje go. Zawsze po wywoĹ‚aniu tej metody CVR
	 * = 0 oraz bit countFlag ustawiany jest na 0.
	 */
	public void setCVR(int ccc)
	{
		this.CVR = 0;
		countFlag = false;
	}

	public int getCVR()
	{
		return CVR;
	}

	public void setRVR(int RVR)
	{
		if (RVR >= 0 && RVR < (1 << 24))
			this.RVR = RVR;
	}

	public int getRVR()
	{
		return RVR;
	}

	public void setEnableFlag(boolean enableFlag)
	{
		this.enableFlag = enableFlag;
	}

	public boolean isEnableFlag()
	{
		countFlag = false;
		return enableFlag;
	}

	public void setCountFlag(boolean countFlag)
	{
		this.countFlag = countFlag;
	}

	/**
	 * Proba odczytu zeruje bit countFlag.
	 */
	public boolean isCountFlag()
	{
		boolean tmp = countFlag;
		this.countFlag = false;
		return tmp;
	}

	public void setTickingFlag(boolean flag)
	{
		this.tickingFlag = flag;
	}

	public boolean isTickingFlag()
	{
		countFlag = false;
		return tickingFlag;
	}

	/*
	 * impuls licznika: -enableFlag =1 inaczej licznik nie dziaĹ‚a
	 * -odliczanie do zera od wartosci w CVR -gdy CVR rĂłwne 0 => bit
	 * countFlag= 1 oraz przeĹ‚aduj CVR na wartosc podanÄ… w RVR -gdy
	 * tickingFlag = true i CVR !=0 wyslij przerwanie
	 */
	public void impuls()
	{
		if (!enableFlag)
			return;

		if (CVR == 0)
		{
			countFlag = true;
			CVR = RVR;
		} else
		{
			CVR--;
			if (CVR == 0)
			{

				if (tickingFlag)
				{
					setChanged();
					notifyObservers();
				}
				countFlag = true;
				CVR = RVR;
			}
		}
	}

	
	

	public String toString()
	{
		return (" countFlag = " + this.isCountFlag() + "\n enableFlag = " + this.isEnableFlag() + "\n tickingFlag = "
				+ this.isTickingFlag() + "\n CVR = " + this.getCVR() + "\n RVR = " + this.getRVR());
	}
}
