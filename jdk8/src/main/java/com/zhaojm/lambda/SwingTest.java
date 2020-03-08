package com.zhaojm.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingTest {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("My JFrame");
        JButton jButton = new JButton("My JButton");
        /*jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botton Pressed!");
            }
        });*/
        jButton.addActionListener(event -> System.out.println("Botton Pressed!"));

        jFrame.add(jButton);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
