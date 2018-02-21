all:
	gcc -c -I/opt/jdk-9.0.1/include/ -I/opt/jdk-9.0.1/include/linux/ -fPIC native.c -o native.o
	mkdir -p ../resources/net/gudenau/lib/largebuffers/implementation/
	gcc native.o -shared -o ../resources/net/gudenau/lib/largebuffers/implementation/LargeBuffers.so

clean:
	rm -vfr native.o ../resources/net/gudenau/lib/largebuffers/implementation/LargeBuffers.so
