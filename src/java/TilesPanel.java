import javax.swing.*;
import javax.swing.border.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class TilesPanel extends JPanel {

    private EngineDataManager dataManager;
    private JList tilesList;
    private DefaultListModel listModel;
    private JTextField tileName;
    private List<Tile> tiles;
    private JButton addButton, removeButton, setTileNameButton;

    private JCheckBox flagSlidCheckbox, flagWaterCheckbox;
    private JPanel tileNamePanel;
    
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
	
	panelInner.setLayout(new GridLayout(4,1));
	panelInner.add(new JLabel("Tile Name"));
	panelInner.add(this.tileNamePanel);
	panelInner.add(this.flagSlidCheckbox);
	panelInner.add(this.flagWaterCheckbox);
	panel.add(panelInner);
	panel.setLayout(new FlowLayout());
	
	
	this.add(panel, BorderLayout.EAST);

	this.tilesList.addListSelectionListener(e -> this.tileSelected());


	buttonBar.setLayout(new BorderLayout());
	buttonBar.setBorder(BorderFactory.createRaisedBevelBorder());
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
	    
	} else {
	    this.removeButton.setEnabled(false);
	    this.tileName.setEnabled(false);
	    this.tileName.setText("");
	    this.flagSlidCheckbox.setEnabled(false);
	    this.flagSlidCheckbox.setSelected(false);
	    this.flagWaterCheckbox.setEnabled(false);
	    this.flagWaterCheckbox.setSelected(false);
	}
    }

    private void addTile() {
	this.tiles.add(new Tile("New Tile", 0));
	this.listModel.addElement("New Tile");
	this.tilesList.setSelectedIndex(this.listModel.size() - 1);

	this.dataManager.setTiles(this.tiles);
    }

    private void removeTile() {
	int index = this.tilesList.getSelectedIndex();
	this.tilesList.clearSelection();
	this.listModel.remove(index);
	this.tiles.remove(index);

	this.dataManager.setTiles(this.tiles);
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
	}
    }


}
