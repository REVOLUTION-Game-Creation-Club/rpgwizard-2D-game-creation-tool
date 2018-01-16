/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.common.assets.listeners;

import org.rpgwizard.common.assets.events.BoardChangedEvent;
import java.util.EventListener;

/**
 * Implementors of this interface will use the contained method definitions to
 * inform their listeners of new event on a <code>Board</code>.
 * 
 * @author Joshua Michael Daly
 */
public interface BoardChangeListener extends EventListener {
	/**
	 * A general board changed event.
	 * 
	 * @param e
	 */
	public void boardChanged(BoardChangedEvent e);

	/**
	 * A new layer has been added to the board.
	 * 
	 * @param e
	 */
	public void boardLayerAdded(BoardChangedEvent e);

	/**
	 * A layer has been moved up on this board.
	 * 
	 * @param e
	 */
	public void boardLayerMovedUp(BoardChangedEvent e);

	/**
	 * A layer has been moved down on this board.
	 * 
	 * @param e
	 */
	public void boardLayerMovedDown(BoardChangedEvent e);

	/**
	 * A layer has been cloned on this board.
	 * 
	 * @param e
	 */
	public void boardLayerCloned(BoardChangedEvent e);

	/**
	 * A layer has been deleted on this board.
	 * 
	 * @param e
	 */
	public void boardLayerDeleted(BoardChangedEvent e);

	/**
	 * A BoardSprite has been added.
	 * 
	 * @param e
	 */
	public void boardSpriteAdded(BoardChangedEvent e);

	/**
	 * A BoardSprite has been removed.
	 * 
	 * @param e
	 */
	public void boardSpriteRemoved(BoardChangedEvent e);

	/**
	 * A BoardLayerImage has been added.
	 * 
	 * @param e
	 */
	public void boardLayerImageAdded(BoardChangedEvent e);

	/**
	 * A BoardLayerImage has been removed.
	 * 
	 * @param e
	 */
	public void boardLayerImageRemoved(BoardChangedEvent e);

}
