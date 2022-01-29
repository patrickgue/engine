

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame
{


    public MainWindow()
    {
	this.setTitle("Editor");
	this.setLayout(new FlowLayout());

	this.add(new JButton("Click me^^"));

	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setVisible(true);
	this.setSize(400,400);
    }
}
