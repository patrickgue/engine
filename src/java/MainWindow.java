

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainWindow extends JFrame
{

    private final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
    
    private JButton button;
    private EngineDataManager dataManager;
    private JTabbedPane tabs;
    private JPanel infoPanel, mapsTab;
    private JTextField nameField, versionField;

    private TilesPanel tilesTab;
    private MapPanel mapTab;



    public MainWindow()
    {
	this.dataManager = new EngineDataManager();
	
	this.setTitle("Editor");

	this.setLayout(new BorderLayout());
	this.initMenuBar();
	this.initInfoPanel();

	this.tabs = new JTabbedPane();
	this.add(this.tabs, BorderLayout.CENTER);

	this.tilesTab = new TilesPanel(this.dataManager);
	this.tabs.addTab("Tiles", null, this.tilesTab, null);

        this.mapsTab = new MapPanel();
	this.tabs.addTab("Map", null, this.mapsTab, null);


        
	
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setVisible(true);
	this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    }


    private void initMenuBar() {
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");;

	fileMenu.setMnemonic(KeyEvent.VK_F);
	
	JMenuItem fileItem = new JMenuItem("New");
	fileItem.setMnemonic(KeyEvent.VK_N);
	fileItem.addActionListener(e -> System.out.println("New"));

	JMenuItem openItem = new JMenuItem("Open");
	openItem.setMnemonic(KeyEvent.VK_O);
	openItem.addActionListener(e -> System.out.println("Open"));

	JMenuItem exitItem = new JMenuItem("Exit");
	exitItem.setMnemonic(KeyEvent.VK_X);
	exitItem.addActionListener(e -> System.exit(0));

	JMenuItem saveItem = new JMenuItem("Save");
	saveItem.setMnemonic(KeyEvent.VK_S);
	saveItem.addActionListener(e -> this.save());

        fileMenu.add(fileItem);
	fileMenu.add(openItem);
	fileMenu.add(saveItem);
	fileMenu.add(new JSeparator());
	fileMenu.add(exitItem);
	
	
	menuBar.add(fileMenu);


	menuBar.add(Box.createHorizontalGlue());

	JMenu helpMenu = new JMenu("Help");

	helpMenu.setMnemonic(KeyEvent.VK_H);

	JMenuItem aboutMenuItem = new JMenuItem("About");
	aboutMenuItem.setMnemonic(KeyEvent.VK_A);
	aboutMenuItem.addActionListener(e -> this.showAbout());

	helpMenu.add(aboutMenuItem);
	menuBar.add(helpMenu);
	
	
	this.setJMenuBar(menuBar);
    }

    private void initInfoPanel() {

	JPanel insideInfoPanel = new JPanel();
	
        this.nameField = new JTextField();
	//nameField.setPlaceholder("Name");
	this.versionField = new JTextField();
	//versionField.setPlaceholder("Version");



	this.infoPanel = new JPanel();
	this.infoPanel.setLayout(new FlowLayout());
	
	insideInfoPanel = new JPanel();
	insideInfoPanel.setLayout(new GridLayout(8,1)); // new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));

	insideInfoPanel.add(new JLabel("Name"));
        insideInfoPanel.add(nameField);
	nameField.setText(this.dataManager.getName());
        insideInfoPanel.add(new JLabel("Version"));
        insideInfoPanel.add(versionField);
	versionField.setText(this.dataManager.getVersion());

        this.infoPanel.add(insideInfoPanel);
	
	this.add(this.infoPanel, BorderLayout.WEST);
	
    }


    private void showAbout() {
	new About();
    }

    private void save() {
	this.dataManager.setName(this.nameField.getText());
	this.dataManager.setVersion(this.versionField.getText());

	this.dataManager.store();
    }

}
