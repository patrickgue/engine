JC=javac

OUT=dist
SRCS=Editor.java MainWindow.java NativeInterface.java About.java EngineDataManager.java Tile.java GameMap.java TilesPanel.java MapPanel.java Alert.java EditPanel.java
CLASS=$(SRCS:%.java=$(OUT)/%.class)

JCFLAGS+=-d $(OUT)

all:jni $(CLASS)

$(CLASS): $(OUT)/%.class: %.java
	$(JC) $(JCFLAGS) $<

jni:NativeInterface.h

NativeInterface.h:
	rm -f NativeInterface.h
	javac -h . NativeInterface.java
	rm -f NativeInterface.class Tile.class GameMap.class


run:
	java -cp dist -Djava.library.path=./dist Editor
clean:
	rm -f dist/*.class NativeInterface.h
