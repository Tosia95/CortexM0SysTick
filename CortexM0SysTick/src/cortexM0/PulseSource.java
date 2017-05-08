package cortexM0;
import java.awt.event.*;

public interface PulseSource {
    final static byte BURST_MODE = 0;
    final static byte CONTINOUS_MODE = 1;

    void addActionListener(ActionListener listener);
    void removeActionListener(ActionListener listener);
   
    void trigger() ;
    void setMode(byte mode) ;
    byte getMode() ;
    void halt() ;   // zatrzymaj generację 
    void setPulseDelay(int ms) ;
    int getPulseDelay() ;
    void setPulseCount(int burst) ;
}