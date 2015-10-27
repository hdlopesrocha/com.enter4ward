util:
	g++ -c unilib/util/*.cpp -g 
	ar rvs util.a *.o
	rm *.o
	
math:
	g++ -c unilib/math/*.cpp -g 
	ar rvs math.a *.o
	rm *.o	
	
graphic:
	g++ -c unilib/graphic/*.cpp -g 
	ar rvs graphic.a *.o
	rm *.o		
	
xml:
	g++ -c unilib/xml/*.cpp -g 
	ar rvs xml.a *.o
	rm *.o	
	
main:
	g++ -o Main Main.cpp -g -pthread -std=c++11
	
web:
	rm -f web
	g++ -O2 -o web WebServer.cpp -g -pthread -std=c++11	unilib/ws/*.cpp unilib/json/*.cpp
