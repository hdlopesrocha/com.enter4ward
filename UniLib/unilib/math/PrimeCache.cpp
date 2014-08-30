#ifndef unilib_math_PrimeCache
#define unilib_math_PrimeCache

using namespace std;
#include <iostream>
#include <cmath>
#include <cstdlib>



namespace unilib {
	//-------------------------------------------------
	// PrimeCache's Class
	//-------------------------------------------------


    class PrimeCache
    {
        private: long * _vec;
        private: long _max;


		public: PrimeCache(long max){
			_max = max;
			_vec = NthPrimeStack(max);
		}

		long Get(long i){
			return _vec[i%_max];
		}

		long * NthPrimeStack(long n){
			long * stk = new long[n];
			long s=0;
			stk[s++] = 1;
			bool isPrime;
			long num = 1;
			while(n!=0){
				isPrime = true;

				for (long i = 2; i <= sqrt((float)num); ++i)
				{
					if((num%i)==0){
						isPrime=false;
						break;
					}
				}

				if(isPrime){--n;stk[s++] = num;}
				if(n!=0)	++num;
			}
			return stk;
		}

		void Print(){
			for(int i =0 ; i < _max ; ++i)
				cout << _vec[i] << endl;

		}
	};
}


#endif
