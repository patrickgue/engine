import java.awt.Color;

public class Tile {
    private String name;
    private int flags, r, g, b;

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

    public Color getColor() {
	return new Color(this.r, this.g, this.b);
    }

    public void setColor(Color c) {
	this.r = c.getRed();
	this.g = c.getGreen();
	this.b = c.getBlue();
    }

    public int getR() {
	return this.r;
    }

    public int getG() {
	return this.g;
    }

    public int getB() {
	return this.b;
    }

}
