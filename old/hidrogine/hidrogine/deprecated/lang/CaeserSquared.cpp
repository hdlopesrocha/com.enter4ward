#ifndef CAESERSQR 
#define CAESERSQR

#include <string>

namespace Security {

	//-------------------------------------------------
	// Caeser's Class
	//-------------------------------------------------
	class CaeserSquared{
		private : string _abc;
		private : int _shift;

		public : CaeserSquared(string abc, int shift){
			_abc = abc;
			_shift = shift;
		}

		public : string Encrypt(string s){
			for(int i=0 ; i < s.length(); ++i){
				for(int j=0 ; j < _abc.length() ; ++j){
					if(s[i]==_abc[j]){
						s[i]=_abc[(i+j+_shift)%_abc.length()];
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
						s[i]=_abc[(j-i-_shift+_abc.length()*s.length())%_abc.length()];
						break;
					}
				}
			}
			return s;
		}


	};
}

#endif

