/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 *
 * @author evenal
 */
public class SimpleForm extends JPanel
{

    private Box labels, fields;
    private JPanel buttons;


    public SimpleForm() {
        setLayout(new BorderLayout());
        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        add(buttons, BorderLayout.SOUTH);
        JPanel form = new JPanel();
        add(form, BorderLayout.CENTER);
        form.setLayout(new FlowLayout());
        labels = Box.createVerticalBox();
        form.add(labels);
        fields = Box.createVerticalBox();
        form.add(fields);
    }


    public void addFormField(JLabel label, JTextField field) {
        labels.add(label);
        fields.add(field);
    }


    public void addFormField(String label, JTextField field) {
        addFormField(new JLabel(label), field);
    }


    public void addCheckBox(String label, JCheckBox field) {
        labels.add(new JLabel(label));
        fields.add(field);
    }


    public void addComboBox(String label, JComboBox field) {
        labels.add(new JLabel(label));
        fields.add(field);
    }


    public void addField(Action action, JTextField field) {
        labels.add(new JButton(action));
    }


    public void addButton(JButton button) {
        buttons.add(button);
    }

}