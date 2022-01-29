
import javax.swing.UIManager;

public class Main
{

    public Main()
    {
	try
	{
	    UIManager.setLookAndFeel(
		UIManager.getSystemLookAndFeelClassName());
	}
	catch(Exception e)
	{}
	new MainWindow();
    }

    public static void main(String args[])
    {
	
	
	new NativeInterface().sayHello();
	new Main();
    }
}
