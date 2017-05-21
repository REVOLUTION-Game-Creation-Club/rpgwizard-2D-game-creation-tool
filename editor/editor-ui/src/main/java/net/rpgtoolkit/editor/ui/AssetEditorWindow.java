/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import net.rpgtoolkit.common.assets.AbstractAsset;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.editor.utilities.EditorFileManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AssetEditorWindow extends JInternalFrame {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AssetEditorWindow.class);

	protected boolean needSave;

	public AssetEditorWindow() {

	}

	public boolean doesNeedSave() {
		return needSave;
	}

	protected void setNeedSave(boolean needSave) {
		if (this.needSave == needSave) {
			return;
		}

		this.needSave = needSave;
		setTitle(getTitle() + "*");
	}

	public abstract AbstractAsset getAsset();

	public AssetEditorWindow(String title, boolean resizeable,
			boolean closeable, boolean maximizable, boolean iconifiable,
			ImageIcon icon) {
		super(title, resizeable, closeable, maximizable, iconifiable);
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		setFrameIcon(icon);
	}

	public abstract void save() throws Exception;

	protected void save(AbstractAsset asset) throws Exception {
		File original;
		File backup = null;
		if (asset.getDescriptor() == null) {
			if (!selectDescriptor(asset)) {
				return; // Save was aborted by the user.
			}
		} else {
			// This will throw an exception if it can't make a backup.
			backup = EditorFileManager.backupFile(new File(asset
					.getDescriptor().getURI()));
		}

		original = new File(asset.getDescriptor().getURI());
		try {
			AssetManager.getInstance().serialize(
					AssetManager.getInstance().getHandle(asset));
			setTitle(original.getName());
			needSave = false;
			setTitle(getTitle().replace("*", ""));
		} catch (Exception ex) {
			LOGGER.error("Failed to save asset=[{}].", asset, ex);

			if (backup != null) {
				// Existing file that failed during save.
				FileUtils.copyFile(backup, original);
				FileUtils.deleteQuietly(backup);
			} else {
				// New file that failed during save.
				asset.setDescriptor(null);
				FileUtils.deleteQuietly(original);
			}

			throw new Exception("Failed to save asset.");
		}

		if (backup != null) {
			FileUtils.deleteQuietly(backup);
		}
	}

	public abstract void saveAs(File file) throws Exception;

	private boolean selectDescriptor(AbstractAsset asset) {
		File file = EditorFileManager.saveByType(asset.getClass());

		if (file == null) {
			return false;
		}

		asset.setDescriptor(new AssetDescriptor(file.toURI()));
		return true;
	}

}
