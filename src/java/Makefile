JC=javac

OUT=dist
SRCS=Main.java MainWindow.java NativeInterface.java About.java EngineDataManager.java Tile.java TilesPanel.java
CLASS=$(SRCS:%.java=$(OUT)/%.class)

JCFLAGS=-d $(OUT)

all:jni $(CLASS)

$(CLASS): $(OUT)/%.class: %.java
	$(JC) $(JCFLAGS) $<

jni:NativeInterface.h

NativeInterface.h:
	javac -h . NativeInterface.java
	rm NativeInterface.class Tile.class


run:
	java -cp dist -Djava.library.path=./dist Main
clean:
	rm -f dist/*.class NativeInterface.h
