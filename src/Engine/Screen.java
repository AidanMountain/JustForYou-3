package Engine;

// Base Screen class
// This game engine runs off the idea of "screens", which are classes that contain their own update/draw methods for a particular piece of the game
// For example, there may be a "MenuScreen" or a "PlayGameScreen"
// All achievement status is managed in this class though a new achievement manager class should be made that can work with multiple screens
public abstract class Screen {
    public abstract void initialize();
    public abstract void update();
    public abstract void draw(GraphicsHandler graphicsHandler);
    public static boolean anyAchievementFound = false;
    public static boolean creditAchievementStatus = false;
    public static boolean levelOneAchievementStatus = false;

    public static boolean getAchievementStatus(int id){
        switch(id){
            case(0):
                return creditAchievementStatus;
            case(1):
                return levelOneAchievementStatus;
            default:
                return true;
        }
    }

    public static void setAchievementStatus(int id, boolean status){
        switch(id){
            case(0):
                creditAchievementStatus = status;
                break;
            case(1):
                levelOneAchievementStatus = status;
                break;
            default:
                break;
        }
    }
}
