#ifndef NRYXCIPHER
#define NRYXCIPHER
#include <iostream>
#include <string>
#include "../util/stack.cpp"
#include "../framework/maths.hpp"


#include <math.h>
#include <cstdlib>
#include <fstream>

using namespace std;
using namespace util;
using namespace framework;
#define BLKSIZE 1991
#define ENCRYPT 1
#define DECRYPT 2
#define TOGGLE 3



namespace Security {



	//-------------------------------------------------
	// NryxCipher's Class
	//-------------------------------------------------

	// Encrypts files and strings using a key and some prime numbers- by Henrique Rocha

	class NryxCipher{
		private : string _key;
		private : PrimeCache * _primes;

		public : NryxCipher(string key){
			_key = key;
			long max = 0;
			for (long i = 0; i < key.length() ; ++i){
				if((long)key[i]>max)
					max = (long)key[i];
			}
			_primes = new PrimeCache(max+128);
		}

		public : void CipherString(char * s, long size, int mode){
			for(long i=0 ; i < size; ++i){
				long p = _primes->Get(_key[i%_key.length()]+i);
				if(mode==TOGGLE)
					s[i]^= p;
				else
					s[i]=MathHelper::Mod(s[i] +(mode==DECRYPT? p:-p) ,256);
			}
		}


		public : void CipherFile(string from, string to, int mode){
			char * blk = new char[BLKSIZE+1+256];
			long bytes_read=0;
			int i=0;

    		ofstream output(to.c_str(), ios::out);
			ifstream input(from.c_str());
			if (input.is_open())
			{
				while(true){
					bytes_read = input.readsome(blk, (int) _primes->Get(_key[i++%_key.length()]+i));
					blk[bytes_read]='\0';
			   		CipherString(blk,bytes_read,mode);
			   		output.write(blk,input.gcount());

			   		if(bytes_read<=0)
			   			break;
		   		}
		    	input.close();
		    	output.close();
		    }
		  	else cout << "Unable to open file"; 
		  	delete blk;
		}

	};
}

#endif

