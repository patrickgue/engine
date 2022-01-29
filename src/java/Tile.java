public class Tile {
    private String name;
    private int flags;

    public Tile(String name, int flags) {
	this.name = name;
	this.flags = flags;
    }

    public Tile() {
	this.name = "";
	this.flags = 0;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getFlags() {
	return this.flags;
    }

    public void setFlags(int flags) {
	this.flags = flags;
    }

}
