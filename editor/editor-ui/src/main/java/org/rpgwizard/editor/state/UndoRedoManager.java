/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.rpgwizard.editor.state;

import java.util.Stack;
import org.rpgwizard.common.assets.Board;
import org.rpgwizard.editor.MainWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Undo/Redo management system.
 *
 * Originally inspired by: https://courses.cs.washington.edu/courses/cse143/12au/lectures/UndoRedoStack.java
 *
 * @author Joshua Michael Daly
 */
public class UndoRedoManager extends Stack<Board> {

    private static final int MAX_UNDO = 10;
    private final Stack<Board> undoStack;

    private static final int MAX_REDO = 10;
    private final Stack<Board> redoStack;

    public UndoRedoManager() {
        undoStack = new Stack();
        redoStack = new Stack();
    }

    public UndoRedoManager(Board value) {
        super.push(value);
        undoStack = new Stack();
        redoStack = new Stack();
    }

    @Override
    public Board push(Board value) {
        final MainWindow mainWindow = MainWindow.getInstance();
        mainWindow.enableUndo(true);
        mainWindow.enableRedo(false);
        ensureCapacities();

        if (!super.isEmpty()) {
            undoStack.push(super.pop());
        }
        super.push(new Board(value));
        redoStack.clear();

        return super.peek();
    }

    @Override
    public Board pop() {
        Board value = super.pop();
        undoStack.push(value);
        redoStack.clear();
        return value;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public Board undo() throws IllegalStateException {
        if (!canUndo()) {
            throw new IllegalStateException("Nothing to undo!");
        }
        ensureCapacities();
        redoStack.push(super.pop());
        super.push(undoStack.pop());
        return new Board(super.peek());
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public Board redo() throws IllegalStateException {
        if (!canRedo()) {
            throw new IllegalStateException("Nothing to redo!");
        }
        ensureCapacities();
        undoStack.push(super.pop());
        super.push(redoStack.pop());
        return new Board(super.peek());
    }

    private void ensureCapacities() {
        while (undoStack.size() > MAX_UNDO) {
            undoStack.remove(0);
        }
        while (redoStack.size() > MAX_REDO) {
            redoStack.remove(0);
        }
    }

}
