/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.editor.ui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.rpgwizard.editor.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class EastPanelAction extends AbstractAction {

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindow mainWindow = MainWindow.getInstance();
        JPanel panel = mainWindow.getEastPanel();
        JTabbedPane westLowerTabbedPane = mainWindow.getWestLowerTabbedPane();
        JTabbedPane eastLowerTabbedPane = mainWindow.getEastLowerTabbedPane();
        if (panel.isVisible()) {
            moveTab(eastLowerTabbedPane, westLowerTabbedPane, 0);
        } else {
            moveTab(westLowerTabbedPane, eastLowerTabbedPane, 1);
        }
        panel.setVisible(!panel.isVisible());
    }

    private void moveTab(JTabbedPane origin, JTabbedPane target, int index) {
        if (origin.getTabCount() > index) {
            String title = origin.getTitleAt(index);
            Component component = origin.getComponentAt(index);
            origin.removeTabAt(index);
            target.addTab(title, component);
            target.setSelectedIndex(target.getTabCount() - 1);
        }
    }

}
