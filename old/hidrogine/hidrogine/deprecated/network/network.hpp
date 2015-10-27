#ifndef NETWORK
#define NETWORK



namespace network {
	class Router;
	class IPv4;
	class IPv4Header;
	class OSPF;
	class RIP;
}

#include <string>
#include <math.h>
#include <cstdlib>
#include <iostream>
#include <sstream>
#include <vector>

using namespace std;

namespace network {

	class IPv4
	{
		public: unsigned char _ip[4];
		public: IPv4();
		public: IPv4(string ip);
		public: IPv4(char ip0,char ip1,char ip2,char ip3);
		public: string ToString();
		public: static bool Equal(IPv4 ip1, IPv4 ip2);
    	public: static IPv4 XOR(IPv4 ip1, IPv4 ip2);
    	public: static IPv4 AND(IPv4 ip1, IPv4 ip2);
    };


	class IPv4Header
	{
		public:	char * _header;
		public: IPv4Header();

	};


	class OSPF
	{
		public: IPv4 _routerID;
		public: OSPF();
		public: OSPF(IPv4 routerID);
		public: void SetRouterID(IPv4 routerID);

	};


	class Router{
        public: string _hostname;
        public: Router ** _interface;
        public: int _numInterfaces;
        public: OSPF * _ospf;

        public: Router(string hostname, int numInterfaces); 
        public: static void Connect(Router * r1, int int1, Router * r2, int int2);
        public: static void Connect(Router * r1, Router * r2);
        public: void EnableOSPF(IPv4 routerID);
        public: void NeighborPrint();
	};

	class RIP
	{
		public: RIP();

	};






}
#endif