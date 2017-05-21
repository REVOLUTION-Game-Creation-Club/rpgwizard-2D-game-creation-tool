/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.utilities;

import java.awt.Graphics;
import java.awt.Image;
import net.rpgtoolkit.editor.ui.resources.Icons;

/**
 *
 * @author Joshua Michael Daly
 */
public class TransparentDrawer {

  public static void drawTransparentBackground(Graphics g, int width, int height) {
    Image tileImage = Icons.getIcon("transparent", Icons.Size.LARGE).getImage();
    
    for (int x = 0; x < width; x += tileImage.getWidth(null)) {
      for (int y = 0; y < height; y += tileImage.getHeight(null)) {
        g.drawImage(tileImage, x, y, null);
      }
    }
  }

}
