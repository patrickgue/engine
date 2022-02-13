
import javax.swing.UIManager;

public class Editor
{

    public Editor()
    {
	try
	{
	    
            String className = "com.sun.java.swing.plaf.motif.MotifLookAndFeel"; //
	    //String className = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	    //String className = UIManager.getSystemLookAndFeelClassName(); // UIManager.getCrossPlatformLookAndFeelClassName();
	    System.out.println(className);
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
