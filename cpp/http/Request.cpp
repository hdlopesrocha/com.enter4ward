#include "Request.hpp"

namespace http {
	Request::Request(std::string m, std::string f, std::string q, std::string v){
		method = m;
		file = f;
		query = q;
		version = v;
	}

	void Request::dump(){
		std::cout << method << " "<< file << std::endl;
		std::cout << "=== fields ==="<< std::endl;
		for (auto i : fields){
    		for (auto j : i.second){
    			std::cout << "{" << i.first << "," << j << "}" << std::endl;
    		}
		}

		std::cout << "=== headers ==="<< std::endl;
		for (auto i : headers){
    		for (auto j : i.second){
    			std::cout << "{" << i.first << "," << j << "}" << std::endl;
    		}
		}

	}
}

