#ifndef NRYXCIPHER
#define NRYXCIPHER
#include <iostream>
#include <string>
#include "../math/PrimeCache.cpp"
#include "../math/MathHelper.cpp"


#include <math.h>
#include <cstdlib>
#include <stdlib.h>
#include <fstream>
#include <strings.h>

using namespace std;
using namespace unilib;

#define BLKSZ 256
#define ENCRYPT 1
#define DECRYPT 2
#define TOGGLE 3



namespace unilib {



	//-------------------------------------------------
	// NryxCipher's Class
	//-------------------------------------------------

	// Encrypts files and strings using a key and some prime numbers- by Henrique Rocha

	class NryxCipher{
		private : string _key;
		private : PrimeCache * _primes;

		public : NryxCipher(string key){
			_key = key;
			long total = 0;
			for (long i = 0; i < key.length() ; ++i){
				total += key[i];
			}
			_primes = new PrimeCache(total);
		}

		public : void CipherString(char * s, long size, int mode){
			long p=0;
			for(long i=0 ; i < size; ++i){
				p += _primes->Get(_key[i%_key.length()]);
				if(mode==TOGGLE)
					s[i]^= p;
				else
					s[i]=MathHelper::Mod(s[i] +(mode==DECRYPT? p:-p) ,256);
			}
		}


		public : void CipherFile(string from, string to, int mode){
			char blk[BLKSZ];
			
			long bytes_read=0;

    		ofstream output(to.c_str(), ios::out);
			ifstream input(from.c_str());
			if (input.is_open())
			{
				while(true){
					bzero(blk,BLKSZ);
					bytes_read = input.readsome(blk, BLKSZ);
			   		CipherString(blk,bytes_read,mode);
			   		output.write(blk,input.gcount());

			   		if(bytes_read<=0)
			   			break;
		   		}
		    	input.close();
		    	output.close();
		    }
		  	else cout << "Unable to open file"; 

		}

	};
}

#endif

