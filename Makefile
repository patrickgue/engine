all:engine editor


engine:
	make -C src/c

editor:
	make -C src/java

jni:
	make- C src/java jni

clean:
	make -C src/c clean
	make -C src/java clean
