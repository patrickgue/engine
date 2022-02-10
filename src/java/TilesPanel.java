import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

public class TilesPanel extends JPanel {

    private EngineDataManager dataManager;
    private JList tilesList;
    private DefaultListModel listModel;
    private JTextField tileName;
    private List<Tile> tiles;
    private JButton addButton, removeButton;

    private JCheckBox flagSlidCheckbox;
    private JCheckBox flagWaterCheckbox;
    
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


	this.tileName = new JTextField();
	this.tileName.setEnabled(false);
	this.tileName.addCaretListener(e -> this.tileNameChanged());

	this.flagSlidCheckbox = new JCheckBox("Solid");
	this.flagSlidCheckbox.setEnabled(false);
	this.flagWaterCheckbox = new JCheckBox("Water");
	this.flagWaterCheckbox.setEnabled(false);
	
	panelInner.setLayout(new GridLayout(5,1));
	panelInner.add(new JLabel("Tile Name"));
	panelInner.add(this.tileName);
	panelInner.add(this.flagSlidCheckbox);
	panelInner.add(this.flagWaterCheckbox);
	panel.add(panelInner);
	panel.setLayout(new FlowLayout());
	
	
	this.add(panel, BorderLayout.EAST);

	this.tilesList.addListSelectionListener(e -> this.tileSelected());


	this.addButton = new JButton("Add");
	this.removeButton = new JButton("Remove");

	this.removeButton.setEnabled(false);

	buttonBar.add(this.addButton);
	addButton.addActionListener(e -> this.addTile());
	
	buttonBar.add(this.removeButton);
	removeButton.addActionListener(e -> this.removeTile());

	this.add(buttonBar, BorderLayout.SOUTH);

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
    }

    private void removeTile() {
	int index = this.tilesList.getSelectedIndex();
	this.tilesList.clearSelection();
	this.listModel.remove(index);
	this.tiles.remove(index);
    }

    private void tileNameChanged() {
	int index = this.tilesList.getSelectedIndex();
	if (index != -1) {
	    this.listModel.set(index, this.tileName.getText());
	    this.tiles.get(index).setName(this.tileName.getText());
	}
    }

    

}
