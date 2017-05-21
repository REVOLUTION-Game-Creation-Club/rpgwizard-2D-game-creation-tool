/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.sprite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import net.rpgtoolkit.common.assets.AbstractSprite;
import net.rpgtoolkit.editor.editors.sprite.AbstractSpriteEditor;

/**
 *
 * @author Joshua Michael Daly
 */
public class AddAnimationActionListener implements ActionListener {

	private final AbstractSprite sprite;

	private final JPanel animationsPanel;

	public AddAnimationActionListener(AbstractSpriteEditor editor) {
		sprite = editor.getSprite();
		animationsPanel = editor.getAnimationsPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String key = (String) JOptionPane.showInputDialog(animationsPanel,
				"Enter the handle for the new animation:", "Add Animation",
				JOptionPane.PLAIN_MESSAGE);

		if (key == null || key.isEmpty()) {
			return;
		}

		sprite.addAnimation(key, "");
	}

}
