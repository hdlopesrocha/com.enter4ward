#ifndef UTILS
#define UTILS

#pragma once
#include <fstream>
#include <string>
#include <iostream>
using namespace std;


namespace hidrogine {
	class Utils
	{
		public: static string fileToString(string filename);
	};
}

#endif