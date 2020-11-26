/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets.board;

import org.rpgwizard.common.assets.board.EventType;
import org.rpgwizard.common.assets.board.Event;

/**
 *
 * @author Joshua Michael Daly
 */
public class KeyPressEvent extends Event {

    private String key;

    public KeyPressEvent(String key, String program) {
        super(EventType.KEYPRESS, program);
        this.key = key;
    }

    public KeyPressEvent(KeyPressEvent event) {
        super(EventType.KEYPRESS, event.getProgram());
        this.key = event.getKey();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
