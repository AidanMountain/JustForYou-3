package EnhancedMapTiles;

import Engine.ImageLoader;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.TileType;
import Utils.Point;
import Level.Player;

import Level.MapTile;

public class CheckPoint extends EnhancedMapTile {

    public CheckPoint(Point location){
        super(ImageLoader.load("CheckPointTrans.png"), location.x, location.y, TileType.PASSABLE, 3f);
    }

    public void update(Player player){
        if(player.getX() > this.getX()){

            if(player.getCurrentCheckPoint() != null){
                if(player.getCurrentCheckPoint().x > this.getX()){
                    return;
                }
            }

            player.setCheckPoint(new Point(this.getX()/(this.getScale()*this.getWidth()),
                    this.getY()/(this.getScale()*this.getHeight())));
        }
        super.update(player);
    }


}
