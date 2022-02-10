public class NativeInterface
{
    static {
	System.loadLibrary("engine");
    }

    public native String sayHello(int i);

    public native void initExample();

    public native String getName();

    public native String getVersion();

    public native void setNameAndVersion(String name, String version);

    public native int getTileCount();
    public native Tile getTile(int i);

    public native void addTile(String name, int flags);
    public native void saveTile(int i, String name, int flags);
}
