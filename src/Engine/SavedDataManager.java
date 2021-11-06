package Engine;

import java.io.*;

public class SavedDataManager implements Serializable {

    private short curLevel;

    public SavedDataManager(){ curLevel = 0;}


    public short getCurLevel() {return curLevel;}
    public void setCurLevel(short newLevel){curLevel = newLevel;}

    public void save(){
        try{
            FileOutputStream f = new FileOutputStream(new File("SaveGameData.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(this);

            o.close();
            f.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load(){
        try{
            FileInputStream f = new FileInputStream(new File("SaveGameData.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            SavedDataManager savedData = (SavedDataManager) o.readObject();
            if(savedData == null){
                this.curLevel = 0;
            }
            else{
                this.curLevel = savedData.getCurLevel();
            }

            o.close();
            f.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
