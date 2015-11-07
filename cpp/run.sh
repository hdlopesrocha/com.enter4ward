rm server
g++ -g -std=c++11 http/*.cpp ws.cpp -o server -lpthread -lIL
./server
