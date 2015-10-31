#include "Request.hpp"

namespace http {

	// trim from start
	static inline std::string &ltrim(std::string &s) {
	        s.erase(s.begin(), std::find_if(s.begin(), s.end(), std::not1(std::ptr_fun<int, int>(std::isspace))));
	        return s;
	}

	// trim from end
	static inline std::string &rtrim(std::string &s) {
	        s.erase(std::find_if(s.rbegin(), s.rend(), std::not1(std::ptr_fun<int, int>(std::isspace))).base(), s.end());
	        return s;
	}

	// trim from both ends
	static inline std::string &trim(std::string &s) {
	        return ltrim(rtrim(s));
	}



	Request::Request(std::string m, std::string f, std::string q, std::string v){
		method = m;
		file = f;
		query = q;
		version = v;
	}

	Request::Request(InputStream &in){
		std::string line = "";
		std::string temp, key , value, values;

		if(in.readLine(line)!=-1){
		 	std::stringstream ss_line(line);		   	
			getline(ss_line,method,' ');
			std::string url;
			getline(ss_line,url,' ');
			getline(ss_line,version,' ');
		   	std::stringstream ss_url(url);
			getline(ss_url, file, '?');
			getline(ss_url, query, '\n');
			ss_url.clear();
			ss_line.clear();
			line.clear();
		}


	   	std::stringstream ss_attr(query);
		while(getline(ss_attr, temp, '&')){				
	   		std::stringstream ss_equal(temp);
			getline(ss_equal, key, '=');
			getline(ss_equal, value, '=');
			fields[key].push_back(value);
			ss_equal.clear();
		}
		ss_attr.clear();



		while(in.readLine(line)>0){
		 	std::stringstream ss_line(line);		   	
			getline(ss_line,key,':');
			getline(ss_line,values,':');


		 	std::stringstream ss_values(values);		   	
			while (getline(ss_values, value, ',')){
				value = trim(value);
				headers[key].push_back(value);
			}



			line.clear();
		}


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

