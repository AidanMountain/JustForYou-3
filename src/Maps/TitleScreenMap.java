package Maps;

import Level.Map;
import Tilesets.MasterMapTileset;
import Utils.Point;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    public TitleScreenMap() {
        super("title_screen_map.txt", new MasterMapTileset(), new Point(1, 9));
    }

}
