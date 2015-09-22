#include <math.h>
#include <stdio.h>
#include <fstream>
#include <string>
#include <iostream>
#include "util/TreeMap.h"
using namespace std;
using namespace unilib;


class MyNumber {
	public: int value;

	public: MyNumber(int v){
		value = v;
	}

};

void MyDelete(void * obj)
{
	delete (MyNumber*) obj;
}


void MyPrint(void * obj)
{
	MyNumber * num = (MyNumber*) obj;
    printf( "%d\n", num->value );
}

class MyComp : public Comparator {
	private: int value;

	public: Comparator * set(int v){
		value = v;
		return this;
	}

	public: int compare(void * obj){
		MyNumber * num = (MyNumber*) obj;
		return value - num->value;
	}
};


int main(int argc, char** argv)
{
	MyComp mc = MyComp();
	TreeMap * map = new TreeMap();
	map->put(new MyNumber(3),mc.set(3));
	map->put(new MyNumber(1),mc.set(1));
	map->put(new MyNumber(2),mc.set(2));
	delete (MyNumber*) map->remove(mc.set(2));
	
	map->remove(mc.set(2));
	map->iterate(&MyPrint);
	delete map->clear(MyDelete);


    return 0;
}


