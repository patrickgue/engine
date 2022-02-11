import javax.swing.*;
import java.awt.*;
import java.util.List;


public class MapPanel extends JPanel {
    private JScrollPane mainScrollPane;
    private JPanel mainGridPanel, propertiesPanel, selectionPanel;
    private JComboBox mapSelection = new JComboBox();
    private JTextField mapNameField;
    private JButton mapNameSet, mapAdd, mapRemove;

    private EngineDataManager dataManager;
    private List<GameMap> maps;


    public MapPanel(EngineDataManager dataManager) {
	this.dataManager = dataManager;
	this.maps = this.dataManager.getMaps();
	
	this.setLayout(new BorderLayout());

	this.mainGridPanel = new JPanel();
	this.mainGridPanel.setLayout(new GridLayout(32,32));

        

	for (int i = 0; i < 32 * 32; i++) {
	    this.mainGridPanel.add(new JButton((i % 32) + "/" + (i / 32)));
	}


	this.propertiesPanel = new JPanel();
	

	this.mainScrollPane = new JScrollPane(this.mainGridPanel);
	this.add(this.mainScrollPane, BorderLayout.CENTER);
	this.add(this.propertiesPanel, BorderLayout.EAST);

	this.initSelectionPanel();
    }


    private void initSelectionPanel() {
	JPanel leftSection = new JPanel();
	JPanel rightSection = new JPanel();

	
	this.selectionPanel = new JPanel();
	this.selectionPanel.setLayout(new BorderLayout());
	
        this.mapSelection = new JComboBox();
	this.mapNameField = new JTextField(10);
	this.mapNameSet = new JButton("set name");
	this.mapAdd = new JButton("add");
	this.mapRemove = new JButton("remove");

	leftSection.add(this.mapSelection);
	leftSection.add(this.mapNameField);
	leftSection.add(this.mapNameSet);

	rightSection.add(this.mapAdd);
	rightSection.add(this.mapRemove);

	this.setSelectionComboBoxLabels();

	this.selectionPanel.add(leftSection, BorderLayout.WEST);
	this.selectionPanel.add(rightSection, BorderLayout.EAST);
	
	this.add(this.selectionPanel, BorderLayout.NORTH);
    }

    private void setSelectionComboBoxLabels() {
	for (int i = 0; i < this.maps.size(); i++) {
	    this.mapSelection.addItem(this.maps.get(i).getName());
	}
    }
}
