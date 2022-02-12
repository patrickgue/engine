
import javax.swing.UIManager;

public class Editor
{

    public Editor()
    {
	try
	{

	    // Use the system look and feel for the swing application
            String className = "com.sun.java.swing.plaf.motif.MotifLookAndFeel"; // 
	    //String className = UIManager.getSystemLookAndFeelClassName(); // UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
	}
	catch(Exception e)
	{}
	new MainWindow();
    }

    public static void main(String args[])
    {
	new Editor();
    }
}
