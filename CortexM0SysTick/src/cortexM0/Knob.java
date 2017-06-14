package cortexM0;

import java.awt.*;
import java.awt.event.*;

public class Knob extends Component implements MouseListener, MouseMotionListener
{
	int knobValue = 0;
	int newKnobValue;
	boolean pressed = false;
	boolean entered = false;

	ActionListener actionListener;// Refers to a list of ActionListener objects

	public Knob()
	{ // konstruktor bezargumentowy
		this(0);
	}// end of constructor

	public Knob(int knobValue)
	{ // konstruktor
		this.knobValue = this.newKnobValue = 0;
		addMouseListener(this);
		addMouseMotionListener(this);
	}// end of constructor

	public void addActionListener(ActionListener listener)
	{
		actionListener = AWTEventMulticaster.add(actionListener, listener);
	}// end addActionListener()
		// -----------------------------------------------------------------------
		// The following method removes ActionListener objects from the list
		// described above

	public void removeActionListener(ActionListener listener)
	{
		actionListener = AWTEventMulticaster.remove(actionListener, listener);
	}// end removeActionListener

	public Dimension getMinimumSize()
	{// overridden getMinimumSize()
		return new Dimension(100, 100);
	}// end getMinimumSize()

	public Dimension getPreferredSize()
	{// overridden getPreferredSize()
		return new Dimension(150, 150);
	}

	public void paint(Graphics g)
	{
		Dimension d = getSize();
		int r, w, h;
		r = Math.min(d.height, d.width);
		h = d.height;
		w = d.width;
		
		g.setColor(Color.black);
		g.fillOval((w - r) / 2, (h - r) / 2, r+10, r+10);
		
		g.setColor(Color.gray);
		g.fillOval((w - r) / 2, (h - r) / 2, r+5, r+5);
		
		g.setColor(Color.pink);
		g.fillOval((w - r) / 2, (h - r) / 2, r - 1, r - 1);
		//g.setColor(Color.black);
		//g.drawString("0",r-,r);

		g.setColor(Color.black);
		int alfa = 90 - knobValue;
		g.drawLine(w / 2 + (int) ((r / 2 - 50) * Math.cos(alfa * Math.PI / 180)), 													
				h / 2 - (int) ((r / 2 - 50) * Math.sin(alfa * Math.PI / 180)),
				w / 2 + (int) ((r / 2 - 5) * Math.cos(alfa * Math.PI / 180)),
				h / 2 - (int) ((r / 2 - 5) * Math.sin(alfa * Math.PI / 180)));


	    g.setColor(Color.black);
	    setFont(new Font("Helvetica", Font.BOLD, 16));     
	    g.drawString(new Integer(convertToHz(newKnobValue)).toString(), w/2-10, h/2-5);
	     }

	
	public void setKnobValue(int newKnobValue)
	{
		this.newKnobValue = newKnobValue;
		// repaint(); Thread does it instead
	}

	public int getKnobValue()
	{
		return (newKnobValue);
	}

	public boolean contains(int x, int y)
	{
		int mx = getSize().width / 2;
		int my = getSize().height / 2;
		int r = Math.min(mx, my);
		return (((mx - x) * (mx - x) + (my - y) * (my - y)) <= r * r);
	}// end contains()

	public void mouseClicked(MouseEvent me)
	{
	}

	public void mouseEntered(MouseEvent me)
	{
		entered = true;
		repaint();
	}

	public void mouseExited(MouseEvent me)
	{
		entered = false;
		pressed = false;
		repaint();
	}

	public void mousePressed(MouseEvent me)
	{
		pressed = true;
		repaint();
	}

	public void mouseReleased(MouseEvent me)
	{

	}

	public void mouseMoved(MouseEvent me)
	{

	}

	public void mouseDragged(MouseEvent me)
	{
		int x, y;
		if (pressed == true)
		{

			x = me.getX();
			y = me.getY();
			double yy = (getSize().height / 2.0 - y);
			double xx = (x - getSize().width / 2.0);
			newKnobValue = 90 - (int) (180 / Math.PI * Math.atan(yy / xx));
			if (xx < 0)
				newKnobValue += 180;
			if (newKnobValue >= 220 || newKnobValue <= 140)
				knobValue = newKnobValue;
			else
				newKnobValue = knobValue;
		}
		repaint();
		if (actionListener != null)
			actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "knob"));
	}

	public int convertToHz(int angle)
	{
		if (angle == 0)
			angle = 360;
		if (angle >= 1 && angle <= 140)
		{
			angle += 141;
			return angle;
		} else
		{
			angle -= 219;
			return angle;
		}
	}

	public static void main(String args[])
	{
		Frame f = new Frame("Knob");
		f.add(new Knob());
		f.setSize(300, 300);
		f.setVisible(true);
	}

} // end Knob