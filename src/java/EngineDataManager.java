import java.util.List;
import java.util.ArrayList;

public class EngineDataManager {
    private NativeInterface inf;
    private String name, version;
    private List<Tile> tiles;
    private List<GameMap> maps;
    
    public EngineDataManager() {
	this.inf = new NativeInterface();
	this.initEngineData();
    }

    private void initEngineData() {
	this.inf.initExample();
	this.name = this.inf.getName();
	this.version = this.inf.getVersion();
	this.tiles = new ArrayList<Tile>();
	this.maps = new ArrayList<GameMap>();
	int count = this.inf.getTileCount();
	for (int i = 0; i < count; i++) {
	    this.tiles.add(this.inf.getTile(i));
	}
	int mapsCount = this.inf.getMapCount();
	for (int i = 0; i < mapsCount; i++) {
	    GameMap map = this.inf.getMap(i);
	    this.maps.add(map);
	}
    }


    public String getName() {
	return this.name;
    }

    public String getVersion() {
	return this.version;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setVersion(String version) {
	this.version = version;
    }

    public void store() {
	this.inf.setNameAndVersion(this.name, this.version);
	this.inf.clearTiles();
	for (Tile t : this.tiles) {
	    this.inf.addTile(t.getName(), t.getFlags(), t.getR(), t.getG(), t.getB());
	}
	this.inf.clearMaps();
	for (GameMap m : this.maps) {
	    this.inf.addMap(m.getName(), m.getMap(), m.getMapWidth(), m.getMapHeight());
	}
	this.inf.dumpEngineDefinition();
	this.inf.store("/tmp/test.def");
    }

    public List<Tile> getTiles() {
	return this.tiles;
    }

    public void setTiles(List<Tile> tiles) {
	this.tiles = tiles;
    }

    public List<GameMap> getMaps() {
	return this.maps;
    }

    public void setMaps(List<GameMap> maps) {
	this.maps = maps;
    }

    public void openFile(String path) {
	System.out.println(path);
    }

    public boolean isTileIndexUsedInMaps(int i) {
	for (GameMap map : this.maps) {
	    for (int y = 0; y < map.getMapHeight(); y++) {
		for (int x = 0; x < map.getMapWidth(); x++) {
		    if (map.getMap()[x][y] == i) {
			return true;
		    }
		}
	    }
	}
	return false;
    }
}
