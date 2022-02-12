all:engine editor


engine:
	$(MAKE) -C src/c test
	./src/c/test

editor:jni
	./src/java/dist/editor

jni:
	$(MAKE) -C src/java jni
	$(MAKE) -C src/c jni
	$(MAKE) -C src/java

clean:
	make -C src/c clean
	make -C src/java clean
