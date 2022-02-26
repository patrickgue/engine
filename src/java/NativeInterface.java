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


    public native void clearTiles();
    public native void addTile(String name, int flags, int r, int g, int b);
    public native void saveTile(int i, String name, int flags, int r, int g, int b);

    
    public native int getMapCount();
    public native GameMap getMap(int i);
    public native void addMap(String name, int map[][], int mapWidth, int mapHeight);
    public native void clearMaps();

    public native void store(String uri);
    public native void load(String uri);

    public native void dumpEngineDefinition();
}
