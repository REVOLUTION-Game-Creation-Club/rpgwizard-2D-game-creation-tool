/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.editor.editors.sprite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.rpgwizard.common.assets.sprite.Sprite;
import org.rpgwizard.editor.editors.SpriteEditor;

/**
 *
 * @author Joshua Michael Daly
 */
public class AddAnimationActionListener implements ActionListener {

    private final Sprite sprite;
    private final SpriteEditor editor;

    public AddAnimationActionListener(SpriteEditor editor) {
        sprite = editor.getSprite();
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String key = (String) JOptionPane.showInputDialog(editor, "Enter the handle for the new animation:",
                "Add Animation", JOptionPane.PLAIN_MESSAGE);

        if (key == null || key.isEmpty()) {
            return;
        }

        sprite.addAnimation(key, "");
    }

}
