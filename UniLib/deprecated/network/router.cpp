#include "network.hpp"

namespace network
{
  
    Router::Router(string hostname, int numInterfaces)
    {	
    	_numInterfaces = numInterfaces;
    	_hostname = hostname;
    	_interface = new Router*[numInterfaces];
        _ospf = NULL;
    }

    void Router::Connect(Router * r1, int int1, Router * r2, int int2)
    {	
    	if(r1->_interface[int1]==NULL)
    		r1->_interface[int1] = r2;
    	else
    		cout << "[ERROR] Interface " << int1 << " of Router " << r1->_hostname << " already used!" << endl;

    	if(r2->_interface[int2]==NULL)
    		r2->_interface[int2] = r1;
    	else
    		cout << "[ERROR] Interface " << int2 << " of Router " << r2->_hostname << " already used!" << endl;    	
    }

    void Router::Connect(Router * r1, Router * r2)
    {	
    	int int1=0, int2=0;
    	for(int1=0; int1< r1->_numInterfaces && r1->_interface[int1]!=NULL ; ++int1);
    	for(int2=0; int2< r2->_numInterfaces && r2->_interface[int2]!=NULL ; ++int2);
    	Connect(r1,int1,r2,int2);
    }

     void Router::NeighborPrint()
    {	
    	cout << "=== "<<_hostname << " neighbors ==="<<endl;
 		for(int i=0; i < _numInterfaces ; ++i){
 			if (_interface[i]!=NULL)
 			{
 				cout << "ether" << i << " => " << _interface[i]->_hostname << endl;
 			}
 		}
		cout << endl;
    }   

    void Router::EnableOSPF(IPv4 routerID){
        _ospf = new OSPF(routerID);
    }

}
