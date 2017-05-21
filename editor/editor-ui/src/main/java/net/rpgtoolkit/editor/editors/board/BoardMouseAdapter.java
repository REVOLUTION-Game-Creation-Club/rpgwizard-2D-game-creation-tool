/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.common.assets.Tile;

import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.MainWindow;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class BoardMouseAdapter extends MouseAdapter {

    private Point origin;
    private final BoardEditor editor;

    /**
     *
     *
     * @param boardEditor
     */
    public BoardMouseAdapter(BoardEditor boardEditor) {
        editor = boardEditor;
    }

    /**
     *
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (editor.getBoardView().getCurrentSelectedLayer() != null) {
            AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
            if (!checkBrushValid(brush)) {
                return;
            }

            int button = e.getButton();
            int x = (int) (e.getX() / editor.getBoardView().getZoom());
            int y = (int) (e.getY() / editor.getBoardView().getZoom());

            switch (button) {
                case MouseEvent.BUTTON1:
                    doMouseButton1Pressed(brush, x, y);
                    break;
                case MouseEvent.BUTTON2:
                    doMouseButton2Pressed(brush, x, y);
                    break;
                case MouseEvent.BUTTON3:
                    doMouseButton3Pressed(brush, x, y);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (editor.getBoardView().getCurrentSelectedLayer() != null) {
            AbstractBrush brush = MainWindow.getInstance().getCurrentBrush();
            if (!checkBrushValid(brush)) {
                return;
            }

            int x = (int) (e.getX() / editor.getBoardView().getZoom());
            int y = (int) (e.getY() / editor.getBoardView().getZoom());

            if (brush instanceof ShapeBrush
                    || brush instanceof SelectionBrush
                    || brush instanceof CustomBrush
                    || brush instanceof EraserBrush) {
                doMouseButton1Dragged(brush, x, y);
            }
        }
    }

    /**
     *
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = (int) (e.getX() / editor.getBoardView().getZoom());
        int y = (int) (e.getY() / editor.getBoardView().getZoom());
        editor.setCursorTileLocation(editor.getBoardView().getTileCoordinates(x, y));
        editor.setCursorLocation(new Point(x, y));
        editor.getBoardView().repaint();
    }

    /**
     * Deals with the creation of an object on a board layer.
     *
     * @param e
     * @param brush
     */
    private void doMouseButton1Pressed(AbstractBrush brush, int x, int y) {
        Rectangle selection = editor.getSelectionExpaned();

        Point point;
        if (brush.isPixelBased()) {
            point = new Point(x, y);
        } else {
            point = editor.getBoardView().getTileCoordinates(x, y);
        }

        origin = point;
        brush.doMouseButton1Pressed(point, editor);
        
        editor.doPaint(brush, point, selection);
    }

    /**
     * Deals with the deletion of an object on a board layer.
     *
     * @param e
     * @param brush
     */
    private void doMouseButton2Pressed(AbstractBrush brush, int x, int y) {
        Point point;
        if (brush.isPixelBased()) {
            point = new Point(x, y);
        } else {
            point = editor.getBoardView().getTileCoordinates(x, y);
        }

        brush.doMouseButton2Pressed(point, editor);
    }

    /**
     * Deals with the selection of an object on a board layer
     *
     * @param e
     * @param brush
     */
    private void doMouseButton3Pressed(AbstractBrush brush, int x, int y) {
        Point point;
        if (brush.isPixelBased()) {
            point = new Point(x, y);
        } else {
            point = editor.getBoardView().getTileCoordinates(x, y);
        }

        brush.doMouseButton3Pressed(point, editor);
    }

    /**
     *
     *
     * @param e
     * @param brush
     */
    private void doMouseButton1Dragged(AbstractBrush brush, int x, int y) {
        // Ensure that the dragging remains within the bounds of the board.
        Point point = editor.getBoardView().getTileCoordinates(x, y);
        if (!editor.getBoardView().checkTileInBounds(point.x, point.y)) {
            return;
        }
        
        if (brush.isPixelBased()) {
            point = new Point(x, y);
        }

        editor.setCursorTileLocation(point);
        editor.setCursorLocation(new Point(x, y));

        brush.doMouseButton1Dragged(point, origin, editor);

        editor.doPaint(brush, point, null);
    }

    private boolean checkBrushValid(AbstractBrush brush) {
        if (brush instanceof ShapeBrush) {
            ShapeBrush shapeBrush = (ShapeBrush) brush;
            if (shapeBrush.paintTile == null) {
                return false;
            }

            return isSameTileSize(editor.getBoard(), shapeBrush.paintTile);
        } else if (brush instanceof BucketBrush) {
            BucketBrush bucketBrush = (BucketBrush) brush;

            if (bucketBrush.pourTile == null) {
                return false;
            }

            return isSameTileSize(editor.getBoard(), bucketBrush.getPourTile());
        } else if (brush instanceof CustomBrush) {
            CustomBrush customBrush = (CustomBrush) brush;

            if (customBrush.tiles.length > 0) {
                if (customBrush.tiles[0].length > 0) {
                    if (customBrush.tiles[0][0] == null) {
                        return true; // Selection brush.
                    }

                    return isSameTileSize(editor.getBoard(), customBrush.tiles[0][0]);
                }
            }
        }

        return true;
    }

    private boolean isSameTileSize(Board board, Tile tile) {
        return board.getTileWidth() == tile.getTileSet().getTileWidth()
                && board.getTileHeight() == tile.getTileSet().getTileHeight();
    }

}
