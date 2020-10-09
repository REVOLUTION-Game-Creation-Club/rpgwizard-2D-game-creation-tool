/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * This class is responsible for managing a tilset inside the editor It stores all of the tiles in the set in a big
 * Array of tiles!
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class TileSet extends AbstractAsset {

    private String name;
    private int tileWidth;
    private int tileHeight;
    private String image;
    private Map<String, Map<String, String>> tileData;

    private BufferedImage bufferedImage;
    private LinkedList<Tile> tiles;

    public TileSet(AssetDescriptor descriptor) {
        super(descriptor);
        image = null;
        tiles = new LinkedList<>();
        tileData = new HashMap<>();
    }

    /**
     * Creates a new TileSet with a descriptor.
     *
     * @param descriptor
     * @param tileWidth
     * @param tileHeight
     */
    public TileSet(AssetDescriptor descriptor, int tileWidth, int tileHeight) {
        super(descriptor);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        image = null;
        tiles = new LinkedList<>();
        tileData = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets a tile from a specified location in the array.
     *
     * @param index
     *            Index of the array to get the tile from
     * @return Tile object representing the tile from the requested index
     */
    public Tile getTile(int index) {
        return tiles.get(index);
    }

    public int getTileIndex(Tile tile) {
        return tiles.indexOf(tile);
    }

    /**
     * Returns an array of all the tiles in the tiles
     *
     * @return Object array of all the tiles in the tiles
     */
    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Map<String, String>> getTileData() {
        return tileData;
    }

    public void setTileData(Map<String, Map<String, String>> tileData) {
        this.tileData = tileData;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setTiles(LinkedList<Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Adds a new tile to the tiles, it will add the tile at the end of the array
     *
     * @param newTile
     *            Tile object to add to the array
     */
    public void addTile(Tile newTile) {
        tiles.add(newTile);
    }

    /**
     * Add metadata about a tile to this tileset.
     * 
     * @param index
     * @param data
     */
    public void addTileData(int index, Map<String, String> data) {
        tileData.put(String.valueOf(index), data);
    }

    /**
     * Read the tile metadata for the specific index, if any.
     * 
     * @param index
     * @return
     */
    public Map<String, String> readTileData(int index) {
        if (tileData.containsKey(String.valueOf(index))) {
            return tileData.get(String.valueOf(index));
        }
        return Map.of();
    }

    /**
     * Clears all loaded tiles in this set.
     */
    public void clearTiles() {
        tiles = new LinkedList<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + this.tileWidth;
        hash = 83 * hash + this.tileHeight;
        hash = 83 * hash + Objects.hashCode(this.image);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TileSet other = (TileSet) obj;
        if (this.tileWidth != other.tileWidth) {
            return false;
        }
        if (this.tileHeight != other.tileHeight) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.image, other.image);
    }

}
