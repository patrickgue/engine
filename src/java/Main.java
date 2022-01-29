
import javax.swing.UIManager;

public class Main
{

    public Main()
    {
	try
	{

	    // Use the system look and feel for the swing application
            String className = "com.sun.java.swing.plaf.motif.MotifLookAndFeel"; // UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
	}
	catch(Exception e)
	{}
	new MainWindow();
    }

    public static void main(String args[])
    {
	new Main();
    }
}
