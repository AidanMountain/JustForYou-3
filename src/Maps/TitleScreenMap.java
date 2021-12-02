package Maps;

import EnhancedMapTiles.ChangeCameraState;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import NPCs.Walrus;
import Tilesets.MasterMapTileset;
import Utils.Point;
import Level.Achievement;
import java.util.ArrayList;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    public TitleScreenMap() {
        super("title_screen_map.txt", new MasterMapTileset(), new Point(1, 9));
    }
    
    public TitleScreenMap(Achievement achievement) {
        super("title_screen_map.txt", new MasterMapTileset(), new Point(1, 9), achievement);
    }


    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        
        for(int i = 0; i < 4; i++) {
        	enhancedMapTiles.add(new ChangeCameraState(getPositionByTileIndex(13, i)));
        }
        
        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        npcs.add(new Walrus(getPositionByTileIndex(18, 9).subtract(new Point(0, 13)), this, "Congratulations you found me", achievement));

        return npcs;
    }
}
