#include <stdio.h>
#include <iostream>
#include <ctime>
#include "util/treeMap.cpp"
#include "util/stack.cpp"
#include "util/queue.cpp"
#include "util/linkedList.cpp"
#include "lang/socketDatagram.cpp"

#include <string>
#include <sstream>

using namespace util;
using namespace std;
using namespace lang;

    class IntComp : public Comparator<int>
    {
       // Returns the compare value.
        public: virtual int compare(int obj1, int obj2){
           // cout << "#" <<obj2-obj1 << endl; 
            return obj2-obj1;
        }
    }; 

    void my_free(int * v, void * args){
        delete v;
    }


    void my_free2(Entry<int,int*> * v, void * args){
        delete v->getValue();
    }


    void my_print(int * t, void * args){
        cout << " | " << *t;
    }


int count_args(...){
    return 0;
}

int main(){
    Iterator<int*> * it;

    // ================
    // TREE-MAP EXAMPLE
    // ================
    IntComp ic = IntComp();
    TreeMap<int,int*> * arvore = new TreeMap<int,int*>(&ic);
    arvore->put(5,new int(5));
    arvore->put(4,new int(4));
    arvore->put(11,new int(11));
    arvore->put(8,new int(8));
    arvore->put(2,new int(2));
    arvore->put(6,new int(6));
    arvore->put(1,new int(1));
    arvore->put(10,new int(10));
    arvore->put(3,new int(3));
    arvore->put(7,new int(7));
    delete arvore->remove(4);
   
    cout << "TreeMap: \t";
    it = arvore->iterator();
    iterate(int*,i,it)
        cout << " | " << *i;
    delete it;
    cout << " | " << endl;
    
    delete arvore->clear(my_free2,NULL);

    // =============
    // STACK EXAMPLE
    // =============
    Stack<int*> * pilha = new Stack<int*>();
    pilha->push(new int(1));
    pilha->push(new int(2));
    pilha->push(new int(3));
    pilha->push(new int(4));
    pilha->push(new int(5));
    pilha->push(new int(6));
    pilha->push(new int(7));
    delete pilha->pop();

    cout << "Stack: \t\t";
    it = pilha->iterator();
    iterate(int*,i,it)
        cout << " | " << *i;
    delete it;
    cout << " | " << endl;
    
    delete pilha->clear(my_free, NULL);

    // =============
    // QUEUE EXAMPLE
    // =============
    Queue<int*> * fila = new Queue<int*>();
    fila->add(new int(1));
    fila->add(new int(2));
    fila->add(new int(3));
    fila->add(new int(4));
    fila->add(new int(5));
    fila->add(new int(6));
    fila->add(new int(7));   
    delete fila->remove();

    cout << "Queue: \t\t";
    it = fila->iterator();
    iterate(int*,i,it)
        cout << " | " << *i;
    delete it;
    cout << " | " << endl;
    
    delete fila->clear(my_free, NULL);



    // =============
    // LINKED EXAMPLE
    // =============
    LinkedList<int*> * lista = new LinkedList<int*>();
    lista->add(new int(1));
    lista->add(new int(2));
    lista->add(new int(3));
    lista->add(new int(4));
    lista->add(new int(5));
    lista->add(new int(6));
    lista->add(new int(7));   
    delete lista->remove(2);

    cout << "LinkedList: \t";
    it = lista->iterator();
    iterate(int*,i,it)
        cout << " | " << *i;
    delete it;
    cout << " | " << endl;
    
    delete lista->clear(my_free, NULL);

    return 0;
}