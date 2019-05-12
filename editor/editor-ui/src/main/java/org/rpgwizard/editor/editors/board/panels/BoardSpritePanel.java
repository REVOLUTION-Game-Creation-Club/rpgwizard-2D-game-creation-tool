/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.editor.editors.board.panels;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.commons.lang3.ArrayUtils;
import org.rpgwizard.common.assets.board.BoardSprite;
import org.rpgwizard.common.assets.Enemy;
import org.rpgwizard.common.assets.EventType;
import org.rpgwizard.common.assets.KeyType;
import org.rpgwizard.common.assets.NPC;
import org.rpgwizard.common.assets.Program;
import org.rpgwizard.common.assets.board.model.BoardModelEvent;
import org.rpgwizard.editor.editors.board.BoardLayerView;
import org.rpgwizard.editor.MainWindow;
import org.rpgwizard.editor.utilities.EditorFileManager;
import org.rpgwizard.editor.utilities.GuiHelper;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public final class BoardSpritePanel extends BoardModelPanel {

    private final JComboBox fileComboBox;
    private final JTextField idField;
    private final JComboBox eventProgramComboBox;
    private final JComboBox threadComboBox;
    private final JSpinner xSpinner;
    private final JSpinner ySpinner;
    private final JSpinner layerSpinner;
    private int lastSpinnerLayer; // Used to ensure that the selection is valid.
    private final JComboBox eventComboBox;
    private static final String[] EVENT_TYPES = EventType.toStringArray();
    private static final String[] KEY_TYPES = KeyType.toStringArray();
    private final JComboBox<String> keyComboBox;

    public BoardSpritePanel(final BoardSprite boardSprite) {
        ///
        /// super
        ///
        super(boardSprite);
        ///
        /// fileComboBox
        ///
        String[] exts = (String[]) ArrayUtils.addAll(EditorFileManager.getTypeExtensions(NPC.class),
                EditorFileManager.getTypeExtensions(Enemy.class));
        fileComboBox = GuiHelper.getFileListJComboBox(
                new File[] { EditorFileManager.getFullPath(Enemy.class), EditorFileManager.getFullPath(NPC.class) },
                exts, true);
        fileComboBox.setSelectedItem(boardSprite.getFileName());
        fileComboBox.addActionListener((ActionEvent e) -> {
            String fileName = (String) fileComboBox.getSelectedItem();
            if (fileName == null) {
                return;
            }
            boardSprite.setFileName((String) fileComboBox.getSelectedItem());
            updateCurrentBoardEditor();
        });
        ///
        /// idField
        ///
        idField = getJTextField(((BoardSprite) model).getId());
        idField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSpriteId();
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSpriteId();
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSpriteId();
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
            }

            private void updateSpriteId() {
                ((BoardSprite) model).setId(idField.getText());
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
            }
        });
        ///
        /// activationComboBox
        ///
        exts = EditorFileManager.getTypeExtensions(Program.class);
        eventProgramComboBox = GuiHelper
                .getFileListJComboBox(new File[] { EditorFileManager.getFullPath(Program.class) }, exts, true);
        eventProgramComboBox.setSelectedItem(boardSprite.getEventProgram());
        eventProgramComboBox.addActionListener((ActionEvent e) -> {
            if (eventProgramComboBox.getSelectedItem() != null) {
                boardSprite.setEventProgram((String) eventProgramComboBox.getSelectedItem());
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
            }
        });
        ///
        /// multiTaskingTextField
        ///
        threadComboBox = GuiHelper.getFileListJComboBox(new File[] { EditorFileManager.getFullPath(Program.class) },
                exts, true);
        threadComboBox.setSelectedItem(boardSprite.getThread());
        threadComboBox.addActionListener((ActionEvent e) -> {
            if (threadComboBox.getSelectedItem() != null) {
                boardSprite.setThread((String) threadComboBox.getSelectedItem());
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
            }
        });
        ///
        /// xSpinner
        ///
        xSpinner = getJSpinner(((BoardSprite) model).getX());
        xSpinner.setValue(((BoardSprite) model).getX());
        xSpinner.addChangeListener((ChangeEvent e) -> {
            BoardSprite sprite = (BoardSprite) model;
            if (sprite.getX() != (int) xSpinner.getValue()) {
                sprite.setX((int) xSpinner.getValue());
                updateCurrentBoardEditor();
            }
        });
        ///
        /// ySpinner
        ///
        ySpinner = getJSpinner(((BoardSprite) model).getY());
        ySpinner.addChangeListener((ChangeEvent e) -> {
            BoardSprite sprite = (BoardSprite) model;
            if (sprite.getY() != (int) ySpinner.getValue()) {
                sprite.setY((int) ySpinner.getValue());
                updateCurrentBoardEditor();
            }
        });
        ///
        /// layerSpinner
        ///
        layerSpinner = getJSpinner(((BoardSprite) model).getLayer());
        layerSpinner.addChangeListener((ChangeEvent e) -> {
            BoardSprite sprite = (BoardSprite) model;
            BoardLayerView lastLayerView = getBoardEditor().getBoardView().getLayer((int) sprite.getLayer());
            BoardLayerView newLayerView = getBoardEditor().getBoardView().getLayer((int) layerSpinner.getValue());

            // Make sure this is a valid move.
            if (lastLayerView != null && newLayerView != null) {
                // Do the swap.
                sprite.setLayer((int) layerSpinner.getValue());
                newLayerView.getLayer().getSprites().add(sprite);
                lastLayerView.getLayer().getSprites().remove(sprite);
                updateCurrentBoardEditor();

                // Store new layer selection index.
                lastSpinnerLayer = (int) layerSpinner.getValue();
            } else {
                // Not a valid layer revert selection.
                layerSpinner.setValue(lastSpinnerLayer);
            }
        });
        ///
        /// keyComboBox
        ///
        keyComboBox = new JComboBox<>(KEY_TYPES);
        if (((BoardSprite) model).getEventType() == EventType.KEYPRESS) {
            keyComboBox.setSelectedItem(((BoardSprite) model).getActivationKey());
            keyComboBox.setEnabled(true);
        } else {
            keyComboBox.setEnabled(false);
        }
        keyComboBox.addActionListener((ActionEvent e) -> {
            ((BoardSprite) model).setActivationKey(keyComboBox.getSelectedItem().toString());
            MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
        });
        ///
        /// typeComboBox
        ///
        eventComboBox = new JComboBox(EVENT_TYPES);
        eventComboBox.setSelectedItem(boardSprite.getEventType().toString());
        eventComboBox.addActionListener((ActionEvent e) -> {
            String selected = eventComboBox.getSelectedItem().toString();

            if (selected.equalsIgnoreCase(EventType.OVERLAP.toString())) {
                boardSprite.setEventType(EventType.OVERLAP);
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
                keyComboBox.setEnabled(false);
            } else if (selected.equalsIgnoreCase(EventType.KEYPRESS.toString())) {
                boardSprite.setEventType(EventType.KEYPRESS);
                MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
                keyComboBox.setEnabled(true);
            }
        });
        ///
        /// this
        ///
        insert(getJLabel("Sprite File"), fileComboBox);
        insert(getJLabel("ID"), idField);
        insert(getJLabel("X"), xSpinner);
        insert(getJLabel("Y"), ySpinner);
        insert(getJLabel("Layer"), layerSpinner);
        insert(getJLabel("Event"), eventComboBox);
        insert(getJLabel("Key"), keyComboBox);
        insert(getJLabel("Event Program"), eventProgramComboBox);
        insert(getJLabel("Thread"), threadComboBox);
    }

    @Override
    public void modelMoved(BoardModelEvent e) {
        if (e.getSource() == model) {
            BoardSprite sprite = (BoardSprite) e.getSource();
            xSpinner.setValue(sprite.getX());
            ySpinner.setValue(sprite.getY());
            MainWindow.getInstance().getCurrentBoardEditor().setNeedSave(true);
        }
    }

}
