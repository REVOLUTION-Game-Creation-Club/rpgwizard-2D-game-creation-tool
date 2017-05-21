/**
 * Copyright (c) 2017, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.pluginsystem;

import java.io.File;
import javax.swing.ProgressMonitor;
import ro.fortsoft.pf4j.ExtensionPoint;

/**
 * A common interface for pluggable engines to implement.
 *
 * @author Joshua Michael Daly
 */
public interface Engine extends ExtensionPoint {

    /**
     * Runs the project located at the specified path. An engine implementation
     * should take a copy of the project rather than running directly against
     * what the editor is using. As the engine progresses it should update the
     * progress of the monitor which will provide UI feedback when needed.
     *
     * @param projectName name of the current project
     * @param projectCopy path to the project copy for engine use
     * @param progressMonitor for tracking engine startup progress
     * @throws java.lang.Exception
     */
    public void run(String projectName, File projectCopy, ProgressMonitor progressMonitor) throws Exception;

    /**
     * Requests that the engine instance stop as soon as possible. In this case
     * there is no need for UI feedback as the caller is also stopping
     * execution.
     *
     * @throws Exception
     */
    public void stop() throws Exception;

    /**
     * Requests that the running engine instance stop. It is the responsibility
     * of the engine to remove the temporary folder created at the run step. As
     * the engine progresses it should update the progress of the monitor which
     * will provide UI feedback when needed.
     *
     * @param progressMonitor for tracking engine startup progress
     * @throws java.lang.Exception
     */
    public void stop(ProgressMonitor progressMonitor) throws Exception;

}
