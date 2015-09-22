#ifndef unilib_math_Statistics
#define unilib_math_Statistics

using namespace std;
#include <iostream>
#include <cmath>
#include <cstdlib>
#include "MathHelper.cpp"

namespace unilib {


	struct Interval {
		public: double Min,Max;
		public: Interval(double min, double max){
			Min = min;
			Max = max;
		}
	};

	struct Line {
		public: double M,B;
		public: Line(double m, double b){
			M = m;
			B = b;
		}
	};

    class Statistics
    {
		public: static double Sum(double * v, int n){
			double result=0;
			for(int i=0; i < n ; ++i){
				result += v[i];
			}
			return result;
		}

		public: static double Mean(double * v, int n){
			return Sum(v,n)/n;
		}

		public: static double SumSquared(double * v, int n){
			double result=0;
			for(int i=0; i < n ; ++i){
				result += v[i]*v[i];
			}
			return result;
		}

		public: static double SumMultiply(double * x, double *y, int n){
			double result=0;
			for(int i=0; i < n ; ++i){
				result += x[i]*y[i];
			}
			return result;
		}

		public: static Line LinearRegression(double *x, double *y, int n){
			double xbar = Mean(x,n);
			double ybar = Mean(y,n);
			double xsqr = SumSquared(x,n);
			double slope = (SumMultiply(x,y,n)-n*xbar*ybar)/(xsqr-n*xbar*xbar);
			double intercept = ybar -slope*xbar;
			return Line(slope,intercept);
		}

		public: static double SigmaSquared(double * x , double *y , int n){
			Line line = LinearRegression(x,y,n);
			double xbar = Mean(x,n);
			double ybar = Mean(y,n);
			double xsqr = SumSquared(x,n);
			double ysqr = SumSquared(y,n);
			return (1.0/(n-2))*((ysqr-n*ybar*ybar)  -line.M*line.M*(xsqr-n*xbar*xbar));
		}

		public: static double RSquared(double * x , double *y , int n){
			double xsqr = SumSquared(x,n);
			double ysqr = SumSquared(y,n);
			double xbar = Mean(x,n);
			double ybar = Mean(y,n);
			double top = SumMultiply(x,y,n)-n*xbar*ybar;
			return (top*top)/((xsqr-n*xbar*xbar)*(ysqr-n*ybar*ybar));
		}

		public: static double TScoreM(double * x , double *y , int n){
			Line line = LinearRegression(x,y,n);
			double xsqr = SumSquared(x,n);
			double xbar = Mean(x,n);
			return line.M/sqrt(SigmaSquared(x,y,n)/(xsqr-n*xbar*xbar));
		}

		public: static double SErrorM(double * x , double *y , int n){
			Line line = LinearRegression(x,y,n);
			double xbar = Mean(x,n);
			double top =0;
			double bottom =0;

			for(int i=0; i < n ; ++i){
				double yhat = line.B + line.M*x[i];
				top += (y[i]-yhat)*(y[i]-yhat);
				bottom += (x[i]-xbar)*(x[i]-xbar);
			}
			return sqrt(top/(n-2))/sqrt(bottom);
		}

		public: static double Gamma2(double t){
			return sqrt(2*Pi/t)*pow((t/E)*sqrt(t*sinh(1/t)+1/(810*t*t*t*t*t*t)),t);
		}


		public: static double Gamma(double t){
			double res =1.0;
			for(int n=1; n < 100000 ; ++n){
				res *= pow(1+1.0/n,t)/(1+t/n);
			}
			return res/t;
		}

		public: static double TCumulative(double t,int n){




		}

		public: static Interval ConfidenceM(double * x , double *y , int n, double t)
		{
			Line line = LinearRegression(x,y,n);
			double se = SErrorM(x,y,n);
			return Interval(line.M-t*se,line.M+t*se);
		
		}



	};
}


#endif