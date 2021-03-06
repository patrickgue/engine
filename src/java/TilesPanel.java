import javax.swing.*;
import javax.swing.border.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class TilesPanel extends JPanel implements EditPanel {

    private EngineDataManager dataManager;
    private JList tilesList;
    private DefaultListModel listModel;
    private JTextField tileName;
    private List<Tile> tiles;
    private JButton addButton, removeButton, setTileNameButton, selectColorButton;

    private JCheckBox flagSlidCheckbox, flagWaterCheckbox;
    private JPanel tileNamePanel;

    private ChangesEvent changesEvent;
    
    public TilesPanel(EngineDataManager dataManager) {
	JPanel panel = new JPanel(),
	    panelInner = new JPanel(),
	    buttonBar = new JPanel();
	this.dataManager = dataManager;
	this.listModel = new DefaultListModel();
	this.tilesList = new JList(this.listModel);
	this.setLayout(new BorderLayout());
	
	this.tiles = this.dataManager.getTiles();
	
	for (int i = 0; i < tiles.size(); i++) {
	    this.listModel.addElement(tiles.get(i).getName());
	}

	this.add(tilesList, BorderLayout.CENTER);


	this.tileNamePanel = new JPanel();
	this.tileNamePanel.setLayout(new BorderLayout());
	this.tileNamePanel.setBorder(new EmptyBorder(0,0,0,0));
	this.tileName = new JTextField(7);
	this.tileName.setEnabled(false);
	this.setTileNameButton = new JButton("Set");
	this.setTileNameButton.addActionListener(e -> this.tileAttributeChanged());

	this.tileNamePanel.add(this.tileName, BorderLayout.CENTER);
	this.tileNamePanel.add(this.setTileNameButton, BorderLayout.EAST);
	
	this.flagSlidCheckbox = new JCheckBox("Solid");
	this.flagSlidCheckbox.setEnabled(false);
	this.flagSlidCheckbox.addActionListener(e -> this.tileAttributeChanged());
	this.flagWaterCheckbox = new JCheckBox("Water");
	this.flagWaterCheckbox.setEnabled(false);
	this.flagWaterCheckbox.addActionListener(e -> this.tileAttributeChanged());

	this.selectColorButton = new JButton("Choose Color");
	this.selectColorButton.setEnabled(false);
	this.selectColorButton.addActionListener(e -> this.chooseTileColor());
	
	panelInner.setLayout(new GridLayout(6,1));
	panelInner.add(new JLabel("Tile Name"));
	panelInner.add(this.tileNamePanel);
	panelInner.add(this.flagSlidCheckbox);
	panelInner.add(this.flagWaterCheckbox);
	panelInner.add(this.selectColorButton);
	panel.add(panelInner);
	panel.setLayout(new FlowLayout());
	
	
	this.add(panel, BorderLayout.EAST);

	this.tilesList.addListSelectionListener(e -> this.tileSelected());


	buttonBar.setLayout(new BorderLayout());
	buttonBar.setBorder(BorderFactory.createMatteBorder(
                                    0, 0, 1, 0, Color.gray));
	JPanel rightPanel = new JPanel(), leftPanel = new JPanel();
	this.addButton = new JButton("Add");
	this.removeButton = new JButton("Remove");

	this.removeButton.setEnabled(false);

	//leftPanel.add(new JLabel("Tiles"));

	rightPanel.add(this.addButton);
	addButton.addActionListener(e -> this.addTile());
	
	rightPanel.add(this.removeButton);
	removeButton.addActionListener(e -> this.removeTile());

	buttonBar.add(leftPanel, BorderLayout.WEST);
        buttonBar.add(rightPanel, BorderLayout.EAST);
	
	this.add(buttonBar, BorderLayout.NORTH);

    }

    private void tileSelected() {
	int index = this.tilesList.getSelectedIndex();
	if (index > -1) {
	    Tile t = this.tiles.get(index);
	    this.tileName.setEnabled(true);
	    this.removeButton.setEnabled(true);
	    this.tileName.setText(t.getName());
	    this.flagSlidCheckbox.setEnabled(true);
	    this.flagSlidCheckbox.setSelected((t.getFlags() & 0b00000001) > 0);
	    this.flagWaterCheckbox.setEnabled(true);
	    this.flagWaterCheckbox.setSelected((t.getFlags() & 0b00000010) > 0);
	    this.selectColorButton.setEnabled(true);
	    this.selectColorButton.setBackground(t.getColor());
	    
	} else {
	    this.removeButton.setEnabled(false);
	    this.tileName.setEnabled(false);
	    this.tileName.setText("");
	    this.flagSlidCheckbox.setEnabled(false);
	    this.flagSlidCheckbox.setSelected(false);
	    this.flagWaterCheckbox.setEnabled(false);
	    this.flagWaterCheckbox.setSelected(false);
	    this.selectColorButton.setEnabled(false);
	    this.selectColorButton.setBackground(null);
	}
    }

    private void addTile() {
	this.tiles.add(new Tile("New Tile", 0));
	this.listModel.addElement("New Tile");
	this.tilesList.setSelectedIndex(this.listModel.size() - 1);

	this.broadcastChange();
    }

    private void removeTile() {
	int index = this.tilesList.getSelectedIndex();
	if (this.dataManager.isTileIndexUsedInMaps(index)) {
	    new Alert("Can't delete, tile \"" + this.tiles.get(index).getName() + "\" is in use");
	} else {
	    this.tilesList.clearSelection();
	    this.listModel.remove(index);
	    this.tiles.remove(index);

	    this.broadcastChange();
	}
    }

    private void tileAttributeChanged() {
	int index = this.tilesList.getSelectedIndex();
	if (index != -1) {
	    this.listModel.set(index, this.tileName.getText());
	    this.tiles.get(index).setName(this.tileName.getText());
	    int flags = this.tiles.get(index).getFlags();
	    if (this.flagSlidCheckbox.isSelected()) {flags |= 0b00000001;} else {flags &= 0b11111110;}
	    if (this.flagWaterCheckbox.isSelected()) {flags |= 0b00000010;} else {flags &= 0b11111101;}
	    this.tiles.get(index).setFlags(flags);

	    this.dataManager.setTiles(this.tiles);
	    this.broadcastChange();
	}
    }

    private void chooseTileColor() {
	var frame = new JFrame();
	var chooseButton = new JButton("Set Color");
	var colorChooser = new JColorChooser();
	frame.setLayout(new BorderLayout());

	frame.add(colorChooser, BorderLayout.CENTER);
	frame.add(chooseButton, BorderLayout.SOUTH);
	
	frame.setSize(400,300);
	frame.setVisible(true);

	chooseButton.addActionListener(e -> {
		int index = this.tilesList.getSelectedIndex();
		if (index != -1) {
		    this.tiles.get(index).setColor(colorChooser.getColor());
		    this.selectColorButton.setBackground(this.tiles.get(index).getColor());
		}
	    });
    }


    public void broadcastChange() {
	this.dataManager.setTiles(this.tiles);

	if (this.changesEvent != null) {
	    this.changesEvent.changesRegistered();
	}
    }
    public void processChanges() {}

    public void listenForChanges(ChangesEvent e) {
	this.changesEvent = e;
    }

}
