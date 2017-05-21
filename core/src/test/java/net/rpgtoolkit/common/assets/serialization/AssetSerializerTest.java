/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets.serialization;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AnimationEnum;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.common.assets.BoardLayer;
import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.assets.Enemy;
import net.rpgtoolkit.common.assets.Project;
import net.rpgtoolkit.common.assets.GraphicEnum;
import net.rpgtoolkit.common.assets.Item;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.TileSet;
import net.rpgtoolkit.common.assets.BoardVectorType;
import net.rpgtoolkit.common.assets.Event;
import net.rpgtoolkit.common.assets.EventType;
import net.rpgtoolkit.common.assets.SpriteSheet;
import net.rpgtoolkit.common.assets.files.FileAssetHandleResolver;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyAnimatedTileSerializer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Some pretty basic checks to ensure the file serializers work.
 *
 * @author Joshua Michael Daly
 */
public class AssetSerializerTest {

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("project.path", "src/test/resources");

        AssetManager assetManager = AssetManager.getInstance();

        assetManager.registerResolver(new FileAssetHandleResolver());

        // Legacy.
        assetManager.registerSerializer(new LegacyAnimatedTileSerializer());

        // JSON.
        assetManager.registerSerializer(new JsonAnimationSerializer());
        assetManager.registerSerializer(new JsonPlayerSerializer());
        assetManager.registerSerializer(new JsonBoardSerializer());
        assetManager.registerSerializer(new JsonProjectSerializer());
        assetManager.registerSerializer(new JsonSpecialMoveSerializer());
        assetManager.registerSerializer(new JsonEnemySerializer());
        assetManager.registerSerializer(new JsonItemSerializer());
        assetManager.registerSerializer(new JsonTileSetSerializer());
    }

    @Test
    public void testProjectSerializer() throws Exception {
        String path = AssetSerializerTestHelper.getPath("Test.game");
        JsonProjectSerializer serializer = new JsonProjectSerializer();

        // Deserialize original.
        Project asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkProject(asset);

        // Serialize a temporary version and deserialize it.
        path = AssetSerializerTestHelper.serialize(asset, serializer);
        asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkProject(asset);
    }
    
    private void checkProject(Project asset) {
        Assert.assertEquals("The Wizard's Tower", asset.getName());
        Assert.assertEquals(640, asset.getResolutionWidth());
        Assert.assertEquals(480, asset.getResolutionHeight());
        Assert.assertEquals(false, asset.isFullScreen());
        Assert.assertEquals("Room1.board", asset.getInitialBoard());
        Assert.assertEquals("Hero.character", asset.getInitialCharacter());
        Assert.assertEquals("Start.program", asset.getStartupProgram());
        Assert.assertEquals("GameOver.program", asset.getGameOverProgram());
    }

    @Test
    public void testAnimationSerializier() throws Exception {
        String path = AssetSerializerTestHelper.getPath(
                "Animations/Hero_world_attack_north.animation");
        JsonAnimationSerializer serializer = new JsonAnimationSerializer();

        // Deserialize original.
        Animation asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkAnimation(asset);

        // Serialize a temporary version and deserialize it.
        path = AssetSerializerTestHelper.serialize(asset, serializer);
        asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkAnimation(asset);
    }

    private void checkAnimation(Animation asset) {
        Assert.assertEquals(0.2, asset.getFrameRate(), 25);
        Assert.assertEquals(90, asset.getAnimationHeight());
        Assert.assertEquals(55, asset.getAnimationWidth());
        
        SpriteSheet spriteSheet = asset.getSpriteSheet();
        Assert.assertEquals("attack1_north.png", spriteSheet.getFileName());
        Assert.assertEquals(0, spriteSheet.getX());
        Assert.assertEquals(0, spriteSheet.getY());
        Assert.assertEquals(100, spriteSheet.getWidth());
        Assert.assertEquals(25, spriteSheet.getHeight());  
        
        Assert.assertEquals("hit.wav", asset.getSoundEffect());
    }

    @Test
    public void testBoardSerializier() throws Exception {
        String path = AssetSerializerTestHelper.getPath(
                "Boards/Room.board");
        JsonBoardSerializer serializer = new JsonBoardSerializer();

        // Deserialize original.
        Board asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkBoard(asset);

        // Serialize a temporary version and deserialize it.
        path = AssetSerializerTestHelper.serialize(asset, serializer);
        asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkBoard(asset);
    }

    private void checkBoard(Board asset) {
        Assert.assertEquals("Room", asset.getName());
        Assert.assertEquals(3, asset.getWidth());
        Assert.assertEquals(3, asset.getHeight());
        Assert.assertEquals(32, asset.getTileWidth());
        Assert.assertEquals(32, asset.getTileHeight());
        Assert.assertTrue(asset.getTileSets().containsKey("Default.tileset"));

        Assert.assertTrue(asset.getLayers().get(0).getSprites().size() == 1);
        BoardSprite sprite = asset.getLayers().get(0).getSprites().get(0);
        Assert.assertEquals("Block.item", sprite.getFileName());
        Assert.assertEquals(1, sprite.getX());
        Assert.assertEquals(2, sprite.getY());
        Assert.assertEquals(0, sprite.getLayer());

        Assert.assertTrue(asset.getLayers().size() == 1);
        BoardLayer layer = asset.getLayers().get(0);
        Assert.assertEquals("Floor", layer.getName());
        Assert.assertEquals(3, layer.getTiles()[0].length);
        Assert.assertEquals(3, layer.getTiles()[1].length);

        Assert.assertTrue(layer.getVectors().size() == 1);
        BoardVector vector = layer.getVectors().get(0);
        Assert.assertEquals("walls", vector.getHandle());
        Assert.assertEquals(2, vector.getPoints().size());
        Assert.assertEquals(true, vector.isClosed());
        Assert.assertEquals(BoardVectorType.SOLID, vector.getType());

        Assert.assertEquals(1, asset.getStartingPositionX());
        Assert.assertEquals(3, asset.getStartingPositionY());
        Assert.assertEquals(0, asset.getStartingLayer());
        
        Assert.assertEquals("room.prg", asset.getFirstRunProgram());
        Assert.assertEquals("room.wav", asset.getBackgroundMusic());
    }

    @Test
    public void testTileSetSerializer() throws Exception {
        String path = AssetSerializerTestHelper.getPath(
                "TileSets/Default.tileset");
        JsonTileSetSerializer serializer = new JsonTileSetSerializer();

        // Deserialize original.
        TileSet asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkTileSet(asset);

        // Serialize a temporary version and deserialize it.
        path = AssetSerializerTestHelper.serialize(asset, serializer);
        asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkTileSet(asset);
    }

    private void checkTileSet(TileSet asset) {
        Assert.assertEquals("Default", asset.getName());
        Assert.assertEquals(32, asset.getTileWidth());
        Assert.assertEquals(32, asset.getTileHeight());
        Assert.assertEquals(Arrays.asList(
                "source1.png",
                "source2.jpg",
                "source3.gif"), asset.getImages());
    }

    @Test
    public void testCharacterSerializer() throws Exception {
        String path = AssetSerializerTestHelper.getPath(
                "Characters/Hero.character");
        JsonPlayerSerializer serializer = new JsonPlayerSerializer();

        // Deserialize original.
        Player asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkPlayer(asset);

        // Serialize a temporary version and deserialize it.
        path = AssetSerializerTestHelper.serialize(asset, serializer);
        asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkPlayer(asset);
    }

    private void checkPlayer(Player asset) {
        Assert.assertEquals("Hero", asset.getName());
        Assert.assertEquals(1, asset.getLevel());
        Assert.assertEquals(10, asset.getMaxLevel());
        Assert.assertEquals(1, asset.getExperience(), 0);
        Assert.assertEquals(100, asset.getMaxExperience(), 0);
        Assert.assertEquals(3, asset.getHealth(), 0);
        Assert.assertEquals(10, asset.getMaxHealth(), 0);
        Assert.assertEquals(1, asset.getAttack(), 0);
        Assert.assertEquals(10, asset.getMaxAttack(), 0);
        Assert.assertEquals(1, asset.getDefence(), 0);
        Assert.assertEquals(10, asset.getMaxDefence(), 0);
        Assert.assertEquals(1, asset.getMagic(), 0);
        Assert.assertEquals(10, asset.getMaxMagic(), 0);

        Map<String, String> expectedGraphics = new HashMap();
        expectedGraphics.put(GraphicEnum.PROFILE.toString(), "hero.png");
        Map<String, String> actualGraphics = asset.getGraphics();
        checkMapsEqual(expectedGraphics, actualGraphics);

        Map<String, String> expectedAnimations = new HashMap();
        for (AnimationEnum key : AnimationEnum.values()) {
            expectedAnimations.put(key.toString(), "");
        }
        expectedAnimations.put(AnimationEnum.NORTH.toString(),
                "Hero_world_attack_north.animation");
        Map<String, String> actualAnimations = asset.getAnimations();
        checkMapsEqual(expectedAnimations, actualAnimations);

        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(30, 0));
        points.add(new Point(30, 20));
        points.add(new Point(0, 20));
        
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(EventType.OVERLAP, "myprogram"));
        
        BoardVector expectedBaseVector = buildBoardVector(BoardVectorType.SOLID, false, "", 0, points, events);
        expectedBaseVector.setPoints(points);
        Assert.assertEquals(expectedBaseVector, asset.getBaseVector());

        points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(37, 0));
        points.add(new Point(37, 30));
        points.add(new Point(0, 30));
        BoardVector expectedActivationVector = buildBoardVector(BoardVectorType.SOLID, false, "", 0, points, events);
        Assert.assertEquals(expectedActivationVector, asset.getActivationVector());

        Assert.assertEquals(new Point(0, 47), asset.getBaseVectorOffset());
        Assert.assertEquals(new Point(-3, 42), asset.getActivationVectorOffset());
    }

    @Test
    public void testEnemySerialzier() throws Exception {
        String path = AssetSerializerTestHelper.getPath(
                "Enemies/Goblin.enemy");
        JsonEnemySerializer serializer = new JsonEnemySerializer();

        // Deserialize original.
        Enemy asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkEnemy(asset);

        // Serialize a temporary version and deserialize it.
        path = AssetSerializerTestHelper.serialize(asset, serializer);
        asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkEnemy(asset);
    }

    private void checkEnemy(Enemy asset) {
        Assert.assertEquals("Goblin", asset.getName());
        Assert.assertEquals(1, asset.getLevel());
        Assert.assertEquals(3, asset.getHealth(), 0);
        Assert.assertEquals(1, asset.getAttack(), 0);
        Assert.assertEquals(1, asset.getDefence(), 0);
        Assert.assertEquals(1, asset.getMagic(), 0);
        Assert.assertEquals(10, asset.getExperienceReward(), 0);
        Assert.assertEquals(10, asset.getGoldReward(), 0);

        Map<String, String> expectedGraphics = new HashMap();
        expectedGraphics.put(GraphicEnum.PROFILE.toString(), "goblin.png");
        Map<String, String> actualGraphics = asset.getGraphics();
        checkMapsEqual(expectedGraphics, actualGraphics);

        Map<String, String> expectedAnimations = new HashMap();
        for (AnimationEnum key : AnimationEnum.values()) {
            expectedAnimations.put(key.toString(), "");
        }
        expectedAnimations.put(AnimationEnum.NORTH.toString(),
                "Goblin_world_attack_north.animation");
        Map<String, String> actualAnimations = asset.getAnimations();
        checkMapsEqual(expectedAnimations, actualAnimations);

        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(30, 0));
        points.add(new Point(30, 20));
        points.add(new Point(0, 20));
        
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(EventType.OVERLAP, "myprogram"));
        
        BoardVector expectedBaseVector = buildBoardVector(BoardVectorType.SOLID, false, "", 0, points, events);
        expectedBaseVector.setPoints(points);
        Assert.assertEquals(expectedBaseVector, asset.getBaseVector());

        points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(37, 0));
        points.add(new Point(37, 30));
        points.add(new Point(0, 30));
        BoardVector expectedActivationVector = buildBoardVector(BoardVectorType.SOLID, false, "", 0, points, events);
        Assert.assertEquals(expectedActivationVector, asset.getActivationVector());

        Assert.assertEquals(new Point(0, 47), asset.getBaseVectorOffset());
        Assert.assertEquals(new Point(-3, 42), asset.getActivationVectorOffset());
    }

    @Test
    public void testItemSerializer() throws Exception {
        String path = AssetSerializerTestHelper.getPath(
                "Items/Block.item");
        JsonItemSerializer serializer = new JsonItemSerializer();

        // Deserialize original.
        Item asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkItem(asset);

        // Serialize a temporary version and deserialize it.
        path = AssetSerializerTestHelper.serialize(asset, serializer);
        asset = AssetSerializerTestHelper.deserializeFile(path, serializer);
        checkItem(asset);
    }

    private void checkItem(Item asset) {
        Assert.assertEquals("Block", asset.getName());
        Assert.assertEquals("A pushable block.", asset.getDescription());
        Assert.assertEquals(0, asset.getFrameRate(), 0);

        Map<String, String> expectedGraphics = new HashMap();
        expectedGraphics.put(GraphicEnum.PROFILE.toString(), "block.png");
        Map<String, String> actualGraphics = asset.getGraphics();
        checkMapsEqual(expectedGraphics, actualGraphics);

        Map<String, String> expectedAnimations = new HashMap();
        for (AnimationEnum key : AnimationEnum.values()) {
            expectedAnimations.put(key.toString(), "");
        }
        expectedAnimations.put(AnimationEnum.SOUTH.toString(), "Block_south.animation");
        Map<String, String> actualAnimations = asset.getAnimations();
        checkMapsEqual(expectedAnimations, actualAnimations);

        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(30, 0));
        points.add(new Point(30, 20));
        points.add(new Point(0, 20));
        
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(EventType.OVERLAP, "myprogram"));
        
        BoardVector expectedBaseVector = buildBoardVector(BoardVectorType.SOLID, false, "", 0, points, events);
        expectedBaseVector.setPoints(points);
        Assert.assertEquals(expectedBaseVector, asset.getBaseVector());

        points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(37, 0));
        points.add(new Point(37, 30));
        points.add(new Point(0, 30));
        BoardVector expectedActivationVector = buildBoardVector(BoardVectorType.SOLID, false, "", 0, points, events);
        Assert.assertEquals(expectedActivationVector, asset.getActivationVector());

        Assert.assertEquals(new Point(0, 47), asset.getBaseVectorOffset());
        Assert.assertEquals(new Point(-3, 42), asset.getActivationVectorOffset());
    }

    private void checkMapsEqual(Map<String, String> expected, Map<String, String> actual) {
        Assert.assertEquals(expected.keySet(), actual.keySet());
        Assert.assertArrayEquals(expected.values().toArray(), actual.values().toArray());
    }

    private BoardVector buildBoardVector(BoardVectorType type, boolean isClosed, String handle, int layer, ArrayList<Point> points, ArrayList<Event> events) {
        BoardVector boardVector = new BoardVector();
        boardVector.setType(type);
        boardVector.setIsClosed(isClosed);
        boardVector.setHandle(handle);
        boardVector.setLayer(layer);
        boardVector.setPoints(points);
        boardVector.setEvents(events);

        return boardVector;
    }

}
