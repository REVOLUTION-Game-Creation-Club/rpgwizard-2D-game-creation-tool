/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Joshua Michael Daly
 */
@Data
@AllArgsConstructor
public class Event {

    private String type;
    private String script;
    private String key;

    public Event() {

    }

    /**
     * Copy constructor.
     *
     * @param event
     */
    public Event(Event event) {
        this.type = event.type;
        this.script = event.script;
        this.key = event.key;
    }

}
