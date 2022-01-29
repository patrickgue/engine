CC=clang
SRCS=engine.c
OBJS=$(SRCS:.c=.o)
STATIC_LIB=libengine.a
JNI_SRCS=java_native_interface.c $(SRCS)
JNI_LIB=../java/dist/libengine.so
TEST_SRC=test.c
TEST_OBJ=test.o
TEST_LDFLAGS=-lengine -L.

JAVA_HOME=/usr/local/openjdk17

JNI_CFLAGS+=-fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/freebsd -shared


all:$(STATIC_LIB)

jni:$(JNI_LIB)

$(JNI_LIB):$(JNI_SRCS) 
	$(CC) $(CFLAGS) $(JNI_CFLAGS) -o $@ $(JNI_SRCS)


$(STATIC_LIB):$(OBJS)
	ar rcs $@ $(OBJS) 

test: $(TEST_OBJ) $(STATIC_LIB)
	$(CC) $(LDFLAGS) -o $@ $(TEST_OBJ) $(TEST_LDFLAGS)

%.o:%.c
	$(CC) $(CFLAGS) -c $^ -o $@

clean:
	rm -f $(STATIC_LIB) $(OBJS) $(JNI_LIB) $(TEST_OBJ) test
