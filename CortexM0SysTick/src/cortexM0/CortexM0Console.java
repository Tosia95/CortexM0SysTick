package cortexM0;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Console representation of CortexM0 SysTick
 */

public class CortexM0Console implements Observer
{
	// instance variables - replace the example below with your own
	private CortexM0SysTick myCounter;

	/**
	 * Constructor for objects of class KonsolowaPrezentacjaSysTick
	 */
	public CortexM0Console()
	{
		myCounter = new CortexM0SysTick();
		myCounter.addObserver(this);
		counterShow();
	}

	/*
	 * exception handler
	 */
	public void update(Observable subject, Object arg)
	{
		display("interrupt!!");
	}

	public void counterShow()
	{
		Scanner in = new Scanner(System.in);
		display("Pokaz działania licznika. Wciśnij odpowiedni klawisz, aby wybrać akcję:\n"
				+ "[1] Wyświetl stan licznika.\n" + "[2] Impuls.\n" + "[3] Włącz licznik.\n"
				+ "[4] Włącz przerwania.\n" + "[5] Wyjdz konsoli.\n\n");
		while (true)
		{
			int choose = in.nextInt();
			switch (choose)
			{

			case 1:
				display("Stan licznika:\n" + myCounter);
				break;
			case 2:
				myCounter.impuls();
				display("Impuls wysłany.");
				break;
			case 3:
				myCounter.setEnableFlag(true);
				display("Licznik włączony.");
				break;
			case 5:
				System.exit(0);
			case 4:
				myCounter.setTickingFlag(true);
				break;
			default:
				display("Zły klawisz.");
			}
		}
	}

	public void display(String msg)
	{
		System.out.println(msg);
	}

	public static void main(String[] args)
	{
		new CortexM0Console();
	}
}