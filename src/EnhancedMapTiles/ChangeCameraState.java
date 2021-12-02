package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class ChangeCameraState extends EnhancedMapTile {
    public ChangeCameraState(Point location) {
        super((ImageLoader.load("InvisibleTile.png")), location.x, location.y, TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (intersects(player)) {
            player.setMapCameraState(true);
        }
    }
}
