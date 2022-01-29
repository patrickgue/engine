JC=javac

OUT=dist
SRCS=Main.java MainWindow.java NativeInterface.java
CLASS=$(SRCS:%.java=$(OUT)/%.class)

JCFLAGS=-d $(OUT)

all:jni $(CLASS)

$(CLASS): $(OUT)/%.class: %.java
	$(JC) $(JCFLAGS) $<

jni:NativeInterface.h

NativeInterface.h:
	javac -h . NativeInterface.java
	rm NativeInterface.class


run:
	java -cp dist -Djava.library.path=./dist Main
clean:
	rm -f dist/*.class
