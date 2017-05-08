package cortexM0;
import java.awt.event.*;
import java.awt.*;

public class PulseGenerator extends Thread implements PulseSource {
    int delay = 100;
    byte mode;
    boolean on;
    ActionListener actionListener;
    
    public PulseGenerator() {
        mode = PulseSource.CONTINOUS_MODE ;
        start() ;
    }
    
    public void run() {
        while(true) {
            while(on) {
               if(actionListener!=null)
                   actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "one impuls"));
               // fire up pulse event
               // test mode & check if last pulse
               try {
                    sleep(delay);// sleep for a moment
               } catch (InterruptedException e) {}
            }
            
            try {
                sleep(1);
            } catch (InterruptedException e) {}
        }
    }

    public void addActionListener(ActionListener pl){
        actionListener = AWTEventMulticaster.add(actionListener, pl);
    }	
    
    public void removeActionListener(ActionListener pl){
        actionListener = AWTEventMulticaster.remove(actionListener, pl);
    }	
          
    public void trigger(){
        on = true;
    }
    
    public void setMode(byte mode){}
    
    public byte getMode(){
        return mode;
    }
    
    public void halt(){
        on = false;
    }
    
    public void setPulseDelay(int ms){}
    
    public int getPulseDelay(){
        return delay;
    }
    
    public void setPulseCount(int burst){}
}