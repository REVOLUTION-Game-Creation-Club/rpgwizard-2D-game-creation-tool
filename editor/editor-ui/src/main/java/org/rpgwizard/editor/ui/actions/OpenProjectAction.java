/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.editor.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.rpgwizard.common.assets.Project;
import org.rpgwizard.common.utilities.CoreProperties;
import org.rpgwizard.editor.MainWindow;
import org.rpgwizard.editor.utilities.EditorFileManager;

/**
 *
 * @author
 */
public class OpenProjectAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		EditorFileManager.getFileChooser().resetChoosableFileFilters();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Toolkit Project", CoreProperties.getDefaultExtension(
						Project.class).replace(".", ""));
		EditorFileManager.getFileChooser().setFileFilter(filter);

		File mainFolder = new File(CoreProperties.getProjectsDirectory());

		if (mainFolder.exists()) {
			EditorFileManager.getFileChooser().setCurrentDirectory(mainFolder);
		}

		if (EditorFileManager.getFileChooser().showOpenDialog(
				MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {
			File file = EditorFileManager.getFileChooser().getSelectedFile();
			MainWindow mainWindow = MainWindow.getInstance();

			Project project = mainWindow.openProject(file);
			mainWindow.setProjectPath(file.getParent());
			mainWindow.setupProject(project);
		}
	}

}
