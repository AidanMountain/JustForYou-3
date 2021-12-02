package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Enemies.LawnMowerOfDeath;
import Engine.ImageLoader;
import EnhancedMapTiles.*;
import GameObject.Rectangle;
import Level.*;

import NPCs.DashingDuck;
import NPCs.Walrus;
import Tilesets.MasterMapTileset;

import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

public class LevelTwo extends Map {

    public LevelTwo() {
        this(new Point(1, 11));
    }
    public LevelTwo(Point spawn) {
        super("level_two.txt", new MasterMapTileset(), spawn);
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        enemies.add(new LawnMowerOfDeath(getPositionByTileIndex(7, 17), Direction.LEFT));

        enemies.add(new BugEnemy(getPositionByTileIndex(21, 18), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(29, 18), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(81, 18), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(60, 22), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(42, 22), Direction.LEFT));

        enemies.add(new DinosaurEnemy(getPositionByTileIndex(21, 12).addY(2), getPositionByTileIndex(24, 12).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(113, 18).addY(2), getPositionByTileIndex(117, 18).addY(2), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(93, 11).addY(2), getPositionByTileIndex(97, 11).addY(2), Direction.LEFT));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(34, 21),
                getPositionByTileIndex(35, 21),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(34, 19),
                getPositionByTileIndex(35, 19),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(34, 15),
                getPositionByTileIndex(35, 15),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(35, 14),
                getPositionByTileIndex(40, 14),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(40, 13),
                getPositionByTileIndex(42, 13),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(50,13),
                getPositionByTileIndex(55,13),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(55,15),
                getPositionByTileIndex(60,15),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(97,18),
                getPositionByTileIndex(105,15),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(121, 15)
        ));

        for(int i = 0; i < 30; i++) {
        	enhancedMapTiles.add(new SkyWater(
        			getPositionByTileIndex(31 + i, 18)
        	));
        	for(int j = 0; j < 4; j++) {
                enhancedMapTiles.add(new Water(
                        getPositionByTileIndex(31 + i, 19 + j)
                ));
            }
        }
        
        for(int i = 0; i < 10; i++) {
        	enhancedMapTiles.add(new SkyWater(
        			getPositionByTileIndex(97 + i, 18)
        	));
        	for(int j = 0; j < 4; j++) {
                enhancedMapTiles.add(new Water(
                        getPositionByTileIndex(97 + i, 19 + j)
                ));
            }
        }

        enhancedMapTiles.add(new CheckPoint(getPositionByTileIndex(33, 10)));
        enhancedMapTiles.add(new CheckPoint(getPositionByTileIndex(47, 8)));
        enhancedMapTiles.add(new CheckPoint(getPositionByTileIndex(82, 15)));
        

        return enhancedMapTiles;
    }

    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        npcs.add(new DashingDuck(getPositionByTileIndex(10, 15).subtract(new Point(0, 13)), this));

        return npcs;
    }
}
