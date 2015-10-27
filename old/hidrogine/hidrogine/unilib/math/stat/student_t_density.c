////////////////////////////////////////////////////////////////////////////////
// File: student_t_density.c                                                  //
// Routine(s):                                                                //
//    Student_t_Density                                                       //
////////////////////////////////////////////////////////////////////////////////

#include <math.h>                    // required for log() and exp()
#include "ln_beta_function.c"
//                         Externally Defined Routines                        //
extern double Ln_Beta_Function(double a, double b);

////////////////////////////////////////////////////////////////////////////////
// double Student_t_Density( double x, int n )                                //
//                                                                            //
//  Description:                                                              //
//     The density of the Student-t distribution is                           //
//            1/ [n^(1/2)* B(1/2,n/2)] * (1 + x^2/n)^(-(n+1)/2)               //
//     where n >= 1 and B(,) is the (complete) beta function.                 //
//                                                                            //
//  Arguments:                                                                //
//     double x   The upper limit of the integral of the density given above. //
//     int    n   The number of degrees of freedom.                           //
//                                                                            //
//  Return Values:                                                            //
//     1/ [n^(1/2)* B(1/2,n/2)]*(1 + x^2/n)^(-(n+1)/2) evaluated at x and n.  //
//                                                                            //
//  Example:                                                                  //
//     double p, x;                                                           //
//     int    n;                                                              //
//                                                                            //
//     p = Student_t_Density(x, n);                                           //
////////////////////////////////////////////////////////////////////////////////

double Student_t_Density( double x, int n )
{
   double ln_density;

   ln_density = -(double)(n+1)/2.0 * log(1.0 + x * x /(double)n)
                - 0.5*log((double)n)
                - Ln_Beta_Function(0.5 * (double)n, 0.5);

   return exp(ln_density);
}
