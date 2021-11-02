package Engine;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//Event which sets the key to down in the keyboard class
public class KeyDownEvent extends AbstractAction {

    private Key eventKey;
    public KeyDownEvent(Key key){
        eventKey = key;
    }

    public void actionPerformed(ActionEvent e){
        Keyboard.setKeyDown(eventKey);
    }

}