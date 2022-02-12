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
    private JButton mapButtons[][];

    private int selectionX, selectionY;


    public MapPanel(EngineDataManager dataManager) {
	this.dataManager = dataManager;
	this.maps = this.dataManager.getMaps();
	
	this.setLayout(new BorderLayout());

	this.mainGridPanel = new JPanel();
	this.mainGridPanel.setLayout(new GridLayout(32,32));
	this.propertiesPanel = new JPanel();
	

	this.mainScrollPane = new JScrollPane(this.mainGridPanel);
	this.add(this.mainScrollPane, BorderLayout.CENTER);
	this.add(this.propertiesPanel, BorderLayout.EAST);

	this.initSelectionPanel();
    }

    private void initGrid(GameMap map) {
	this.mainScrollPane.setVisible(false);
	this.mainGridPanel.removeAll();
        this.mapButtons = new JButton[32][32];
	for (int y = 0; y < 32; y++) {
	    for (int x = 0; x < 32; x++) {
		int tileIndex = map.getMap()[x][y];
		this.mapButtons[x][y] = new JButton(dataManager.getTiles().get(tileIndex).getName());
		this.mainGridPanel.add(this.mapButtons[x][y]);
		this.mapButtons[x][y].addActionListener(e -> {});
	    }
	}
	this.mainScrollPane.setVisible(true);
    }

    private void initSelectionPanel() {
	JPanel leftSection = new JPanel();
	JPanel rightSection = new JPanel();
	
	this.selectionPanel = new JPanel();
	this.selectionPanel.setLayout(new BorderLayout());
	this.selectionPanel.setBorder(BorderFactory.createRaisedBevelBorder());
	
        this.mapSelection = new JComboBox();
	this.mapNameField = new JTextField(10);
	this.mapNameSet = new JButton("Set Name");
	this.mapAdd = new JButton("Add");
	this.mapRemove = new JButton("Remove");
	
	this.mapSelection.addActionListener(e -> this.selectMap());
	this.mapAdd.addActionListener(e -> this.addMap());
	this.mapNameSet.addActionListener(e -> this.changeMapName());

	leftSection.add(this.mapSelection);
	leftSection.add(this.mapNameField);
	leftSection.add(this.mapNameSet);

	rightSection.add(this.mapAdd);
	rightSection.add(this.mapRemove);
	this.disableSelectionInputs();
	this.setSelectionComboBoxLabels();

	this.selectionPanel.add(leftSection, BorderLayout.WEST);
	this.selectionPanel.add(rightSection, BorderLayout.EAST);
	
	this.add(this.selectionPanel, BorderLayout.NORTH);
    }

    private void setSelectionComboBoxLabels() {
	this.mapSelection.removeAllItems();
	this.mapSelection.addItem("<<Select>>");
	for (int i = 0; i < this.maps.size(); i++) {
	    this.mapSelection.addItem(this.maps.get(i).getName());
	}
    }

    private void selectMap() {
	int index = this.mapSelection.getSelectedIndex();

	if (index == 0) {
	    this.disableSelectionInputs();
	    this.mainScrollPane.setVisible(false);
	    this.mainGridPanel.removeAll();
	    this.mainScrollPane.setVisible(true);
	} else if (index > 0) {
	    GameMap map = this.maps.get(index - 1);
	    this.mapNameField.setText(map.getName());
	    this.mapNameField.setEnabled(true);
	    this.mapNameSet.setEnabled(true);
	    this.mapRemove.setEnabled(true);
	    this.initGrid(map);
	}
    }

    private void disableSelectionInputs() {
	this.mapNameField.setText("");
	this.mapNameField.setEnabled(false);
	this.mapNameSet.setEnabled(false);
	this.mapRemove.setEnabled(false);
    }

    private void addMap() {
	this.maps.add(new GameMap("New Map", 32, 32));
	this.setSelectionComboBoxLabels();
	this.mapSelection.setSelectedIndex(this.maps.size());
	this.broadcastChange();
    }


    private void changeMapName() {
	if (this.mapNameField.getText() != null || this.mapNameField.getText().length() == 0) {
	    new Alert("Name is empty");
	    return;
	}
	int currentIndex = this.mapSelection.getSelectedIndex();
	this.maps.get(currentIndex - 1).setName(this.mapNameField.getText());
	this.setSelectionComboBoxLabels();
	this.mapSelection.setSelectedIndex(currentIndex);
    }
    
    private void broadcastChange() {
	this.dataManager.setMaps(this.maps);
    }
}
