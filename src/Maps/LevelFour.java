package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Enemies.Mouse;
import Engine.ImageLoader;
import EnhancedMapTiles.CheckPoint;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import Enemies.*;

import NPCs.Human;


import Tilesets.MasterMapTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

public class LevelFour extends Map {

    public LevelFour() { this(new Point(1,17)); }
    public LevelFour(Point spawn) { super("level_four.txt", new MasterMapTileset(), spawn); }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        enemies.add(new BugEnemy(getPositionByTileIndex(17, 22), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(34, 18), Direction.LEFT));
        
        enemies.add(new BugEnemy(getPositionByTileIndex(63, 23), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(65, 23), Direction.LEFT));

        enemies.add(new BugEnemy(getPositionByTileIndex(68, 23), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(69, 23), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(72, 23), Direction.LEFT));

        enemies.add(new BugEnemy(getPositionByTileIndex(89, 23), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(91, 23), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(93, 23), Direction.LEFT));
//        enemies.add(new BugEnemy(getPositionByTileIndex(104, 23), Direction.LEFT));
//        enemies.add(new BugEnemy(getPositionByTileIndex(106, 23), Direction.LEFT));
//        enemies.add(new BugEnemy(getPositionByTileIndex(108, 23), Direction.LEFT));
//        enemies.add(new BugEnemy(getPositionByTileIndex(110, 23), Direction.LEFT));
//        enemies.add(new BugEnemy(getPositionByTileIndex(112, 23), Direction.LEFT));
//        enemies.add(new BugEnemy(getPositionByTileIndex(114, 23), Direction.LEFT));

        enemies.add(new DinosaurEnemy(getPositionByTileIndex(14, 18).addY(2), getPositionByTileIndex(18, 18).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(20, 18).addY(2), getPositionByTileIndex(24, 18).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(29, 14).addY(2), getPositionByTileIndex(32, 14).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(45, 14).addY(2), getPositionByTileIndex(52, 14).addY(2), Direction.RIGHT));

        enemies.add(new Mouse(getPositionByTileIndex(16, 22), Direction.LEFT));
        enemies.add(new Mouse(getPositionByTileIndex(43, 11), Direction.LEFT));
        enemies.add(new Mouse(getPositionByTileIndex(62, 23), Direction.LEFT));
        enemies.add(new Mouse(getPositionByTileIndex(66, 23), Direction.LEFT));
        enemies.add(new Mouse(getPositionByTileIndex(73, 23), Direction.LEFT));

        enemies.add(new BossMouse(getPositionByTileIndex(109, 20), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(111, 20), Direction.LEFT));

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(74, 16),
                getPositionByTileIndex(78, 16),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        
         enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(89, 15),
                getPositionByTileIndex(93, 15),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(106, 15),
                getPositionByTileIndex(110, 15),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));
        
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(113, 15),
                getPositionByTileIndex(116, 15),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(129, 13)
        ));

        enhancedMapTiles.add(new CheckPoint(getPositionByTileIndex(61, 17)));
        enhancedMapTiles.add(new CheckPoint(getPositionByTileIndex(102, 17)));

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        npcs.add(new Human(getPositionByTileIndex(125, 14), this));

        return npcs;
    }
    
    
}
