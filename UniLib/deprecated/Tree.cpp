#define DEBUG

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <ctime>
#include "framework/util.hpp"
#include <string>
#include <sstream>

using namespace std;
using namespace framework;



void freeInt(Entry<int,int*> *entry,void* lol){

	delete entry->getValue();
}

int main(int argc, char **argv){

	IntComparator * ic;
	TreeMap<int,int*> * tree =  new TreeMap<int,int*>(ic =new IntComparator());
	int n;
	srand(time(NULL));
	int i = 1000000;
	while(i--){
		n = rand()%1000000;
		tree->put(n,new int(n));
	}
	/*
	Iterator<int*> *it = tree->iterator();
	while(it->isValid()){
		cout<<*(it->getValue())<<endl;
		it->next();
	}
	
	tree->print();
	*/

	delete tree->clear( &freeInt, NULL);
	delete ic;
	return 0;
}
