#ifndef CAESER 
#define CAESER

#include <string>

namespace Security {

	//-------------------------------------------------
	// Caeser's Class
	//-------------------------------------------------
	class Caeser{
		private : string _abc;
		private : int _shift;

		public : Caeser(string abc, int shift){
			_abc = abc;
			_shift = shift;
		}

		public : string Encrypt(string s){
			for(int i=0 ; i < s.length(); ++i){
				for(int j=0 ; j < _abc.length() ; ++j){
					if(s[i]==_abc[j]){
						s[i]=_abc[(j+_shift)%_abc.length()];
						break;
					}
				}
			}
			return s;
		}


		public : string Decrypt(string s){
			for(int i=0 ; i < s.length(); ++i){
				for(int j=0 ; j < _abc.length() ; ++j){
					if(s[i]==_abc[j]){
						s[i]=_abc[(j-_shift+_abc.length())%_abc.length()];
						break;
					}
				}
			}
			return s;
		}


	};
}

#endif

