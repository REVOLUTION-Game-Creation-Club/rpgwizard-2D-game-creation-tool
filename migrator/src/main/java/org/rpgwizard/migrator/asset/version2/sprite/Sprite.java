/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.migrator.asset.version2.sprite;

import org.rpgwizard.migrator.asset.version2.Trigger;
import org.rpgwizard.migrator.asset.version2.Collider;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.rpgwizard.migrator.asset.version2.AbstractAsset;

/**
 *
 * @author Joshua Michael Daly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, includeFieldNames = true)
public class Sprite extends AbstractAsset {
    
    private String name;
    private String description;
    private Map<String, String> animations;
    private Collider collider;
    private Trigger trigger;
    private Map<String, String> data;
    
    public Sprite() {
        animations = new HashMap<>();
        collider = new Collider();
        trigger = new Trigger();
        data = new HashMap<>();
    }
    
}
