#define DEBUG

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <ctime>
#include "network/network.hpp"
#include <string>
#include <sstream>

using namespace std;
using namespace network;


int main(int argc, char **argv){

	// INSTANCES OF ROUTERS
	Router * R1 = new Router("R1",4);
	Router * R2 = new Router("R2",4);
	Router * R3 = new Router("R3",4);
	Router * R4 = new Router("R4",4);
	Router * R5 = new Router("R5",4);
	Router * R6 = new Router("R6",4);

	// CONNECTING ROUTERS (automatic interface)
	Router::Connect(R1,R2);
	Router::Connect(R1,R3);
	Router::Connect(R2,R4);
	Router::Connect(R2,R3);
	Router::Connect(R5,R3);
	Router::Connect(R5,R4);
	Router::Connect(R6,R5);
	Router::Connect(R6,R4);

	// PRINTING NEIGHBORS
	R1->NeighborPrint();
	R2->NeighborPrint();
	R3->NeighborPrint();
	R4->NeighborPrint();
	R5->NeighborPrint();
	R6->NeighborPrint();

	// ENABLE OSPF AND SET ROUTER ID'S
	R1->EnableOSPF(IPv4("1.1.1.1"));
	R2->EnableOSPF(IPv4("2.2.2.2"));
	R3->EnableOSPF(IPv4("3.3.3.3"));
	R4->EnableOSPF(IPv4("4.4.4.4"));
	R5->EnableOSPF(IPv4("5.5.5.5"));
	R6->EnableOSPF(IPv4("6.6.6.6"));


	cout << IPv4::AND(R1->_ospf->_routerID, IPv4(255,255,0,0)).ToString() << endl;

	return 0;
}
