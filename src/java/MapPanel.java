import javax.swing.*;
import java.awt.*;


public class MapPanel extends JPanel {
    private JScrollPane mainScrollPane;
    private JPanel mainGridPanel;


    public MapPanel() {
	this.setLayout(new BorderLayout());

	this.mainScrollPane = new JScrollPane();
	this.mainGridPanel = new JPanel();
	this.mainGridPanel.setLayout(new GridLayout(2,2));

	
	this.mainScrollPane.add(mainGridPanel);

	
	this.add(this.mainScrollPane, BorderLayout.CENTER);

	for (int i = 0; i < 32; i++) {
	    this.mainGridPanel.add(new JButton("" + i));
	    System.out.println(i);
	}
    }
}
