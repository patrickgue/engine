import java.util.List;
import java.util.ArrayList;


public class EngineDataManager {
    private NativeInterface inf;
    private String name, version;
    private List<Tile> tiles;
    
    public EngineDataManager() {
	this.inf = new NativeInterface();
	this.initEngineData();
    }

    private void initEngineData() {
	this.inf.initExample();
	this.name = this.inf.getName();
	this.version = this.inf.getVersion();
	this.tiles = new ArrayList<Tile>();
	int count = this.inf.getTileCount();
	for (int i = 0; i < count; i++) {
	    this.tiles.add(this.inf.getTile(i));
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
    }

    public List<Tile> getTiles() {
	return this.tiles;
    }

    public void setTiles(List<Tile> tiles) {
	this.tiles = tiles;
    }
}
