#include <iostream>
#include "unilib/lang/NryxCipher.cpp"
#include <string>
#include <sstream>
#include <csignal>
#include <iomanip>
//#include "unilib/math/stat/student_t_density.c"


using namespace std;
using namespace unilib;


int main(){
/*
	cout << setprecision(10);
	double x[] = {15,14,12,14,12,11,11,10,12,13};
	double y[] = {4.3,4.4,5.3,4.6,5.5,5.9,5.7,6.2,5.2,5.0};
	Line line = Statistics::LinearRegression(x,y,10);
	cout << "y = " << line.M << "x + " << line.B << endl;
	cout << "S² = " << Statistics::SigmaSquared(x,y,10) << endl;
	cout << "R² = " << Statistics::RSquared(x,y,10) << endl;
	cout << "TScoreM = " << Statistics::TScoreM(x,y,10) << endl;
	cout << "SErrorM = " << Statistics::SErrorM(x,y,10) << endl;

	Interval inter = Statistics::ConfidenceM(x,y,10,1.8600);
	cout << "[ " << inter.Min << " , " << inter.Max << " ]"<< endl;
	cout << setprecision(13) << "sqrt(PI)="<<sqrt(PI)<<endl;

	cout << setprecision(13) << "Gamma="<<Statistics::Gamma2(0.5)<<endl;
	//XmlDocument * doc = new XmlDocument("test.xml"); 
*/

	NryxCipher * cipher = new NryxCipher("qwerty");
	cipher->CipherFile("test.xml","test.f1",ENCRYPT);
	cipher->CipherFile("test.f1","test.f2",DECRYPT);


	return 0;
}

