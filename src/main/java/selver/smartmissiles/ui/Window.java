package selver.smartmissiles.ui;


import selver.smartmissiles.debug.Debug;

import javax.swing.*;

public class Window extends JFrame{
    public static void main(String[] args)
    {
        new Window();
    }

    public Window()
    {
        super("Smart Missiles");
        Debug.setEnabled(false);
        setContentPane(new gPanel());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }


}
