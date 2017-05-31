/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.editor.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.rpgwizard.editor.MainWindow;
import org.rpgwizard.editor.ui.AssetEditorWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class SaveAllAction extends AbstractAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SaveAction.class);

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindow w = MainWindow.getInstance();
        for (AssetEditorWindow editor : w.getOpenEditors()) {
            if (editor.doesNeedSave()) {
                try {
                    editor.save();
                } catch (Exception ex) {
                    LOGGER.error("Failed to invoke save for asset frame=[{}]",
                            editor, ex);

                    JOptionPane.showMessageDialog(MainWindow.getInstance(),
                            "Error saving file!", "Error on Save",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
