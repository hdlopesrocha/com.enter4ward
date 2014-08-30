#include "network.hpp"
#include <string.h>
#include <stdio.h>


namespace network
{
    IPv4::IPv4()
    {	
    	_ip[0] = _ip[1] = _ip[2] = _ip[3] = 0;
    }

  
    IPv4::IPv4(string ip)
    {	
		char * pch = strtok ((char*)ip.c_str(),".");
		int i=0;
		while (pch != NULL)
		{
			_ip[i++] = atoi(pch);
			pch = strtok (NULL, ".");
		}
	}


    IPv4::IPv4(char ip0,char ip1,char ip2,char ip3)
    {	
    	_ip[0] = ip0;
    	_ip[1] = ip1;
    	_ip[2] = ip2;
    	_ip[3] = ip3;
    }

    string IPv4::ToString(){
    	char ret[32];
    	sprintf(ret,"%d.%d.%d.%d\0", _ip[0],_ip[1],_ip[2],_ip[3]);
    	return string(ret);
    }

    bool IPv4::Equal(IPv4 ip1, IPv4 ip2){
        return ip1._ip[0] == ip2._ip[0] && ip1._ip[1] == ip2._ip[1] && ip1._ip[2] == ip2._ip[2] && ip1._ip[3] == ip2._ip[3]; 
    }

    IPv4 IPv4::XOR(IPv4 ip1, IPv4 ip2){
        return IPv4(ip1._ip[0] ^ ip2._ip[0] , ip1._ip[1] ^ ip2._ip[1] , ip1._ip[2] ^ ip2._ip[2] , ip1._ip[3] ^ ip2._ip[3]); 
    }

    IPv4 IPv4::AND(IPv4 ip1, IPv4 ip2){
        return IPv4(ip1._ip[0] & ip2._ip[0] , ip1._ip[1] & ip2._ip[1] , ip1._ip[2] & ip2._ip[2] , ip1._ip[3] & ip2._ip[3]); 
    }

}
