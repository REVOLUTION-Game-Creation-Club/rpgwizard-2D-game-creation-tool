/**
 * Copyright (c) 2015, rpgwizard.org, some files forked from rpgtoolkit.net <info@rpgwizard.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.rpgwizard.editor.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractModelPanel extends JPanel {

    protected static final int COLUMNS = 10;

    protected Object model;
    protected Font font;

    protected GridBagLayout layout;

    protected static final Insets DEFAULT_INSETS = new Insets(10, 10, 10, 10);
    protected final GridBagConstraints constraints;
    protected int row;

    public AbstractModelPanel(Object model) {
        this.model = model;
        font = new JLabel().getFont();
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        row = 0;
        setLayout(layout);
    }

    public Object getModel() {
        return model;
    }

    public final JLabel getJLabel(String text) {
        JLabel label = new JLabel(text);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        return label;
    }

    public final JTextField getJTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setColumns(COLUMNS);
        textField.setFont(font);
        return textField;
    }

    public final JButton getJButton(String text) {
        JButton button = new JButton(text);
        button.setFont(font);
        return button;
    }

    public final JSpinner getJSpinner(Object value) {
        JSpinner spinner = new JSpinner();
        spinner.setValue(value);
        spinner.setFont(font);
        return spinner;
    }

    public final JSpinner getJSpinner(Integer value) {
        JSpinner spinner = new JSpinner(
                new SpinnerNumberModel(value, new Integer(0), new Integer(Integer.MAX_VALUE), new Integer(1)));
        spinner.setFont(font);
        return spinner;
    }

    public void tearDown() {
        // Do nothing by default, let children override it.
    }

    protected void insert(JLabel label, JComponent component) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.insets = DEFAULT_INSETS;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(label, constraints);

        constraints.gridx = 1;
        constraints.gridy = row;
        constraints.weightx = 1;
        constraints.insets = DEFAULT_INSETS;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(component, constraints);

        row++;
    }

}
