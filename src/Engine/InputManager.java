package Engine;

import javax.swing.*;

//This class adds functionality to keyboard keys using java KeyBindings
//Actions attached to each key are created in a separate class and then instantiated here
//The actions attached to the class interface with functionality in the keyboard class

public class InputManager {

    private GamePanel gamePanel;
    private KeyLocker keyLocker;

    public InputManager(GamePanel gamePanel){

        this.gamePanel = gamePanel;
        SetBindings();

    }

    public void CheckFocusInput(){
        if(!gamePanel.hasFocus()){
            Keyboard.ClearDown();
            keyLocker.ClearLocked();
        }
    }

    public void setKeyLocker(KeyLocker keyLocker){
        this.keyLocker = keyLocker;
    }

    private void SetBindings(){
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "UpArrowDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "UpArrowUp");

        gamePanel.getActionMap().put("UpArrowDown", new KeyDownEvent(Key.UP));
        gamePanel.getActionMap().put("UpArrowUp", new KeyUpEvent(Key.UP));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "DownArrowDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "DownArrowUp");

        gamePanel.getActionMap().put("DownArrowDown", new KeyDownEvent(Key.DOWN));
        gamePanel.getActionMap().put("DownArrowUp", new KeyUpEvent(Key.DOWN));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "LeftArrowDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "LeftArrowUp");

        gamePanel.getActionMap().put("LeftArrowDown", new KeyDownEvent(Key.LEFT));
        gamePanel.getActionMap().put("LeftArrowUp", new KeyUpEvent(Key.LEFT));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "RightArrowDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "RightArrowUp");

        gamePanel.getActionMap().put("RightArrowDown", new KeyDownEvent(Key.RIGHT));
        gamePanel.getActionMap().put("RightArrowUp", new KeyUpEvent(Key.RIGHT));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "SpaceDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "SpaceUp");

        gamePanel.getActionMap().put("SpaceDown", new KeyDownEvent(Key.SPACE));
        gamePanel.getActionMap().put("SpaceUp", new KeyUpEvent(Key.SPACE));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "EnterDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released ENTER"), "EnterUp");

        gamePanel.getActionMap().put("EnterDown", new KeyDownEvent(Key.ENTER));
        gamePanel.getActionMap().put("EnterUp", new KeyUpEvent(Key.ENTER));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("1"), "OneDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released 1"), "OneUp");

        gamePanel.getActionMap().put("OneDown", new KeyDownEvent(Key.ONE));
        gamePanel.getActionMap().put("OneUp", new KeyUpEvent(Key.ONE));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("P"), "PDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released P"), "PUp");

        gamePanel.getActionMap().put("PDown", new KeyDownEvent(Key.P));
        gamePanel.getActionMap().put("PUp", new KeyUpEvent(Key.P));

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "EscapeDown");
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("released ESCAPE"), "EscapeUp");

        gamePanel.getActionMap().put("EscapeDown", new KeyDownEvent(Key.ESC));
        gamePanel.getActionMap().put("EscapeUp", new KeyUpEvent(Key.ESC));


    }

}
