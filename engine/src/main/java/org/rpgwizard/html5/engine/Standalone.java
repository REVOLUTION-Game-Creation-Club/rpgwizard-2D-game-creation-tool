/**
 * Copyright (c) 2017, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.html5.engine;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cef.OS;
import org.rpgwizard.common.assets.AssetDescriptor;
import org.rpgwizard.common.assets.AssetException;
import org.rpgwizard.common.assets.AssetHandle;
import org.rpgwizard.common.assets.AssetManager;
import org.rpgwizard.common.assets.Project;
import org.rpgwizard.common.assets.files.FileAssetHandleResolver;
import org.rpgwizard.common.assets.serialization.JsonProjectSerializer;
import org.rpgwizard.html5.engine.plugin.EngineRunnable;
import org.rpgwizard.html5.engine.plugin.browser.EmbeddedBrowser;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class Standalone {

	public static boolean STANDALONE_MODE = false;

	private static Thread ENGINE_THREAD;
	private static EngineRunnable ENGINE_RUNNABLE;
	private static EmbeddedBrowser EMBEDDED_BROWSER;

	private static Project openProject(File projectFile) throws AssetException,
			IOException {
		AssetManager assetManager = AssetManager.getInstance();
		assetManager.registerResolver(new FileAssetHandleResolver());
		assetManager.registerSerializer(new JsonProjectSerializer());
		AssetHandle handle = AssetManager.getInstance().deserialize(
				new AssetDescriptor(projectFile.toURI()));
		return (Project) handle.getAsset();
	}

	public static void main(String[] args) throws AssetException, IOException {
        Standalone.STANDALONE_MODE = true;

//        System.setProperty("org.rpgwizard.execution.path", "D:/Documents/Software Development/rpgwizard/distribution/target/rpgwizard-1.1.0-windows/rpgwizard-1.1.0/builds/RPGWizard 1.1.0 - The Wizard's Tower-1507920535920");
        String resourceBase = System.getProperty("org.rpgwizard.execution.path") + File.separator + "data";
        File projectFile = new File(new File(resourceBase), "default.game");

        if (projectFile.exists()) {
            Project project = openProject(projectFile);
            
            // Start the Embedded Jetty.
            ENGINE_RUNNABLE = new EngineRunnable(resourceBase);
            ENGINE_THREAD = new Thread(ENGINE_RUNNABLE);
            ENGINE_THREAD.start();

            javax.swing.SwingUtilities.invokeLater(() -> {
                // Show the JCEF browser window.
                EMBEDDED_BROWSER = new EmbeddedBrowser("Test", "http://localhost:8080", OS.isLinux(), false, project.getResolutionWidth(), project.getResolutionHeight());
                EMBEDDED_BROWSER.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try {
                            ENGINE_RUNNABLE.stop();
                            EMBEDDED_BROWSER.stop();
                        } catch (Exception ex) {
                            Logger.getLogger(Standalone.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // JCEF keeps hanging.
                        throw new RuntimeException("Forcefully shutting down JCEF - Can ignore this exception");
                    }
                });
            });
        }
    }
}
