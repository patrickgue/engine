import javax.swing.*;
import java.awt.*;
import java.util.List;


public class MapPanel extends JPanel implements EditPanel {
    private JScrollPane mainScrollPane;
    private JPanel mainGridPanel, propertiesPanel, selectionPanel;
    private JComboBox mapSelection = new JComboBox(), tileSelection;
    private JTextField mapNameField;
    private JButton mapNameSet, mapAdd, mapRemove;

    private EngineDataManager dataManager;
    private List<GameMap> maps;
    private JButton mapButtons[][];

    private int selectionX, selectionY;
    private boolean disableMapTileChange = false;
    
    private ChangesEvent changesEvent;

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
	this.initPropertiesPanel();
    }

    private void initGrid(GameMap map) {
	this.mainScrollPane.setVisible(false);
	this.mainGridPanel.removeAll();
        this.mapButtons = new JButton[32][32];
	for (int y = 0; y < 32; y++) {
	    for (int x = 0; x < 32; x++) {
		final int xx = x;
		final int yy = y;
		this.mapButtons[x][y] = new JButton("");
		this.mapButtons[x][y].setPreferredSize(new Dimension(70, 70));
		this.mainGridPanel.add(this.mapButtons[x][y]);
		this.mapButtons[x][y].addActionListener(e -> {
			this.selectGridElement(xx, yy);
		    });
	    }
	}
	this.setGridButtonLabels(map);
	this.mainScrollPane.setVisible(true);
    }

    private void setGridButtonLabels(GameMap map) {
	for (int y = 0; y < 32; y++) {
	    for (int x = 0; x < 32; x++) {
		int tileIndex = map.getMap()[x][y];
		if (tileIndex >= 0 && tileIndex < dataManager.getTiles().size()) {
		    if (y == this.selectionY && x == this.selectionX) {
			this.mapButtons[x][y].setText("[" + dataManager.getTiles().get(tileIndex).getName() + "]");
		    } else {
			this.mapButtons[x][y].setText(dataManager.getTiles().get(tileIndex).getName());
		    }
		}
		
	    }
	}
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

    private void initPropertiesPanel() {
	JPanel innerPanel = new JPanel();
	innerPanel.setLayout(new GridLayout(5,1));
	this.propertiesPanel.setLayout(new FlowLayout());
	this.tileSelection = new JComboBox();
	this.tileSelection.addActionListener(e -> {
		if (this.mapSelection.isEnabled()
		    && this.mapSelection.getSelectedIndex() > 0 /* 0 is no valid selection */
		    && this.mapSelection.getItemCount() > 0) {
		    this.tileSelectionChanged();
		}
	    });
	
	
	this.updateTileComboBox();

	innerPanel.add(new JLabel("Tile"));
	innerPanel.add(this.tileSelection);
	this.tileSelection.setEnabled(false);


	this.propertiesPanel.add(innerPanel);
    }

    private void selectGridElement(int x, int y) {
	this.selectionX = x;
	this.selectionY = y;

	GameMap map = this.getCurrentMap();
	this.tileSelection.setEnabled(true);
	this.tileSelection.setSelectedIndex(map.getMap()[x][y]);
    }

    private void tileSelectionChanged() {
	if (!this.disableMapTileChange) {
	    int newIndex = this.tileSelection.getSelectedIndex();
	    if (newIndex >= 0) {
		this.getCurrentMap().getMap()[this.selectionX][this.selectionY] = newIndex;
		this.setGridButtonLabels(this.getCurrentMap());
		this.broadcastChange();
	    }
	}
    }

    private void updateTileComboBox() {
	this.disableMapTileChange = true;
	this.tileSelection.removeAllItems();
	for (Tile t : this.dataManager.getTiles()) {
	    this.tileSelection.addItem(t.getName());
	}
	this.disableMapTileChange = false;
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
	    GameMap map = this.getCurrentMap();
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
	if (this.mapNameField.getText() == null || this.mapNameField.getText().length() == 0) {
	    new Alert("Name is empty");
	    return;
	}
	int currentIndex = this.mapSelection.getSelectedIndex();
	this.maps.get(currentIndex - 1).setName(this.mapNameField.getText());
	this.setSelectionComboBoxLabels();
	this.mapSelection.setSelectedIndex(currentIndex);
    }
    
    public void broadcastChange() {
	this.dataManager.setMaps(this.maps);

	if (this.changesEvent != null) {
	    this.changesEvent.changesRegistered();
	}
    }

    public void processChanges() {
	this.updateTileComboBox();
    }

    private GameMap getCurrentMap() {
	int index = this.mapSelection.getSelectedIndex();
	GameMap map = this.maps.get(index - 1);
	return map;
    }

    public void listenForChanges(ChangesEvent e) {
	this.changesEvent = e;
    }

}
