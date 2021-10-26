package Engine;


import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

//event that sets a key to up in the keyboard class
public class KeyUpEvent extends AbstractAction{

    private Key eventKey;
    public KeyUpEvent(Key key){
        eventKey = key;
    }

    public void actionPerformed(ActionEvent e){
        Keyboard.setKeyUp(eventKey);
    }

}