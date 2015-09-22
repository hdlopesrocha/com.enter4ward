#include "network.hpp"
#include <string.h>
#include <stdio.h>


namespace network
{
  
    OSPF::OSPF()
    {	
	}


    OSPF::OSPF(IPv4 routerID)
    {	
    	_routerID = routerID;   
	}	

    void OSPF::SetRouterID(IPv4 routerID){
        _routerID = routerID;        
    }

        

}
