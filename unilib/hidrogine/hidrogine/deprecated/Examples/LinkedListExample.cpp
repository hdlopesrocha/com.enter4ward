#include <stdio.h>
#include <iostream>
#include <ctime>
#include "../Collection/LinkedList.cpp"
#include <string>


using namespace Collection;
using namespace std;

int main(){

    LinkedList<int> * lst = new LinkedList<int>();
    Iterator<int> * it;

    lst->Add(new int(0));
    lst->Add(new int(1));
    lst->Add(new int(2));
    lst->Add(new int(3));
    lst->Add(new int(4));

    delete lst->Remove();

    cout    << "Forward"<<endl;
	it = lst->GetForwardIterator();
	while(it->IsValid()){
	  	cout << *(it->GetValue())<<"\n";
	   	it->Next();
	}
    delete it;

    cout    << "Backward"<<endl;
    it = lst->GetBackwardIterator();
    while(it->IsValid()){
        cout << *(it->GetValue())<<"\n";
        it->Prev();
    }
    delete it;

    delete lst->Delete();

    return 0;
} 
