import javax.swing.*;
import java.awt.*;


public class MapPanel extends JPanel {
    private JScrollPane mainScrollPane;
    private JPanel mainGridPanel;


    public MapPanel() {
	this.setLayout(new BorderLayout());

	this.mainGridPanel = new JPanel();
	this.mainGridPanel.setLayout(new GridLayout(32,32));

        

	for (int i = 0; i < 32 * 32; i++) {
	    this.mainGridPanel.add(new JButton("" + i));
	}

	this.mainScrollPane = new JScrollPane(this.mainGridPanel);
	this.add(this.mainScrollPane, BorderLayout.CENTER);
    }
}
