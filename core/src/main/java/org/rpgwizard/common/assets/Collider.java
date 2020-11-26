/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Joshua Michael Daly
 */
@Data
public class Collider {

    private boolean enabled;
    private int x;
    private int y;
    private List<Point> points;

    public Collider() {
        enabled = true;
        points = new ArrayList<>();
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

}
