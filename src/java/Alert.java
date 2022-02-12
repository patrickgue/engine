import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Alert extends JFrame{
    private JButton okButton;
    private JLabel label;
    
    public Alert(String label) {
	this.setLayout(new BorderLayout());
	this.label = new JLabel(label);
	this.label.setBounds(10,10,10,10);
	this.add(this.label, BorderLayout.CENTER);
	this.okButton = new JButton("Ok");
	this.okButton.addActionListener(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

	this.add(okButton, BorderLayout.SOUTH);
	this.setSize(250,100);
	this.setVisible(true);
	
    }
}
