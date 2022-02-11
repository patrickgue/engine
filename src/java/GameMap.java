public class GameMap {
    private String name;
    private int map[][];
    private int mapWidth, mapHeight;

    public GameMap() {
	this.name = "";
	this.map = new int[32][32];
	this.mapWidth = this.mapHeight = 32;
    }

    public GameMap(String name, int map[][], int mapWidth, int mapHeight) {
	this.name = name;
	this.map = map;
	this.mapWidth = mapWidth;
	this.mapHeight = mapHeight;
    }


    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }


    public int[][] getMap() {
	return this.map;
    }

    public void setMap(int map[][]) {
	this.map = map;
    }
    
    public int getMapWidth() {
	return this.mapWidth;
    }

    public void setMapWidth(int mapWidth) {
	this.mapWidth = mapWidth;
    }

public int getMapHeight() {
	return this.mapHeight;
    }

    public void setMapHeight(int mapHeight) {
	this.mapHeight = mapHeight;
    }

}
