
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainWindow extends JFrame {

	private final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;

	private JButton button;
	private EngineDataManager dataManager;
	private JTabbedPane tabs;
	private JPanel infoPanel;
	private JTextField nameField, versionField;

	private TilesPanel tilesTab;
	private MapPanel mapTab;

	public MainWindow() {
		this.dataManager = new EngineDataManager();

		this.setTitle("Editor");

		this.setLayout(new BorderLayout());
		this.initMenuBar();
		this.initInfoPanel();

		this.tabs = new JTabbedPane();
		this.add(this.tabs, BorderLayout.CENTER);

		this.tilesTab = new TilesPanel(this.dataManager);
		this.tilesTab.listenForChanges(() -> {
			this.mapTab.processChanges();
		});
		this.tabs.addTab("Tiles", null, this.tilesTab, null);

		this.mapTab = new MapPanel(this.dataManager);
		this.mapTab.listenForChanges(() -> {
			this.tilesTab.processChanges();
		});
		this.tabs.addTab("Map", null, this.mapTab, null);

		ImageIcon img = new ImageIcon("./icon.png");
		this.setIconImage(img.getImage());

		if (Taskbar.isTaskbarSupported()) {
			Taskbar taskbar = Taskbar.getTaskbar();
			if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
				taskbar.setIconImage(img.getImage());
			}
		}

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		;

		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem fileItem = new JMenuItem("New");
		fileItem.setMnemonic(KeyEvent.VK_N);
		fileItem.addActionListener(e -> System.out.println("New"));

		JMenuItem openItem = new JMenuItem("Open");
		openItem.setMnemonic(KeyEvent.VK_O);
		openItem.addActionListener(e -> this.openFile());

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

		this.nameField = new JTextField(10);
		this.versionField = new JTextField(10);

		this.infoPanel = new JPanel();
		this.infoPanel.setBorder(BorderFactory.createMatteBorder(
                                    0, 0, 0, 1, Color.gray));
		this.infoPanel.setLayout(new FlowLayout());

		insideInfoPanel = new JPanel();
		insideInfoPanel.setLayout(new GridLayout(8, 1)); // new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));

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

	private void openFile() {
		java.awt.FileDialog fileChooser = new java.awt.FileDialog((java.awt.Frame) null);
		fileChooser.setVisible(true);

		final File[] files = fileChooser.getFiles();
		if (files.length == 0) {
			return;
		}

		File file = files[0];

		if (file.exists()) {
			this.dataManager.openFile(file.toURI().toString().replace("file:", ""));
		}

	}

}
