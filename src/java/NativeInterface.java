public class NativeInterface
{
    static {
	System.loadLibrary("engine");
    }

    public native void sayHello();

}
