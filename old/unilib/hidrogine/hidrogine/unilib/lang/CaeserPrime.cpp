#ifndef CAESERPRIME
#define CAESERPRIME
#include <iostream>
#include <string>
#include "../Collection/Stack.cpp"
#include "../Math/Math.cpp"
#include <math.h>
#include <cstdlib>
using namespace std;
using namespace Collection;
using namespace Maths;


namespace Security {

	//-------------------------------------------------
	// Caeser's Class
	//-------------------------------------------------

	// Encrypts text with a given alphabet - by Henrique Rocha

	class CaeserPrime{
		private : string _abc;
		private : long _shift;


		public : CaeserPrime(string abc, long shift){
			_abc = abc;
			_shift = shift;
		}

		public : string Encrypt(string s){
			Stack<long> * stk = Math::NthPrimeStack(s.length()+_shift);


			for(long i=0 ; i < s.length(); ++i){
				for(long j=0 ; j < _abc.length() ; ++j){
					if(s[i]==_abc[j]){
						long * p = stk->Pop();
						s[i]=_abc[(j+*p)%_abc.length()];
						delete p;
						break;
					}
				}
			}
			delete stk->Delete();
			return s;
		}


		public : string Decrypt(string s){
			Stack<long> * stk = Math::NthPrimeStack(s.length()+_shift);

			for(long i=0 ; i < s.length(); ++i){
				for(long j=0 ; j < _abc.length() ; ++j){
					if(s[i]==_abc[j]){
						long * p = stk->Pop();
						long lol = (j-*p+_abc.length());
						delete p;

						s[i]=_abc[Math::Mod(lol,_abc.length())];
						break;
					}
				}
			}
			delete stk->Delete();
			return s;
		}


	};
}

#endif

