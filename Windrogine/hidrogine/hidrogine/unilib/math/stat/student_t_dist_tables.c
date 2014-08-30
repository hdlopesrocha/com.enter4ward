////////////////////////////////////////////////////////////////////////////////
// File: student_t_dist_tables.c                                              //
// Routine(s):                                                                //
//    Student_t_Distribution_Tables                                           //
////////////////////////////////////////////////////////////////////////////////

#include <math.h>                    // required for log() and exp()

//                         Externally Defined Routines                        //
extern double Ln_Beta_Function(double a, double b);
extern double Beta_Distribution(double x, double a, double b);
                                    
////////////////////////////////////////////////////////////////////////////////
// void Student_t_Distribution_Tables( int n, double start, double delta,     //
//               int nsteps, double *density, double* distribution_function)  //
//                                                                            //
//  Description:                                                              //
//     The Student-t distribution is the Pr[T < x] which equals the integral  //
//     from -inf to x of the density                                          //
//            1/ [n^(1/2)* B(1/2,n/2)] * (1 + x^2/n)^(-(n+1)/2)               //
//     where n >= 1 and B(,) is the (complete) beta function.                 //
//     The integer n is called the number of degrees of freedom.              //
//     This routine returns the probability density in the array density[]    //
//     where                                                                  //
//            density[i] = f(start + i * delta), i = 0,...,nsteps             //
//     and the distribution function in the array distribution_function[]     //
//     where                                                                  //
//     distribution_function[i] = F(start + i * delta), i = 0,...,nsteps.     //
//     Note the size of the arrays density[] and distribution_function[] must //
//     equal or exceed nsteps + 1.                                            //
//                                                                            //
//  Arguments:                                                                //
//     int    n                                                               //
//        The number of degrees of freedom, n >= 1.                           //
//     double start                                                           //
//        The initial point to start evaluating f(x) and F(x).                //
//     double delta                                                           //
//        The step size between adjacent evaluation points of f(x) and F(x).  //
//     int    nsteps                                                          //
//        The number of steps.                                                //
//     double density[]                                                       //
//        The value of f(start + i * delta), i = 0,...,nsteps.                //
//     double distribution_function[]                                         //
//        The value of F(start + i * delta), i = 0,...,nsteps.                //
//                                                                            //
//  Return Values:                                                            //
//     void                                                                   //
//                                                                            //
//  Example:                                                                  //
//     #define N                                                              //
//     double start, delta;                                                   //
//     double density[N+1];                                                   //
//     double distribution_function[N+1];                                     //
//     int n;                                                                 //
//                                                                            //
//     Student_t_Distribution_Tables(n, start, delta, N, density,             //
//                                                   distribution_function);  //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

void Student_t_Distribution_Tables(int n, double start, double delta,
                    int nsteps, double *density, double* distribution_function)
{
   double x = start;
   double a = (double) n / 2.0;
   double a1 = (double) (n + 1) / 2.0;
   double rn = 1.0 / (double) n;
   double beta;
   double ln_temp = 0.5*log((double)n) + Ln_Beta_Function(a, 0.5);
   int i;

   for (i = 0; i <= nsteps; i++) {
      density[i] = exp( -a1 * log(1.0 + rn * x * x) - ln_temp);
      beta = Beta_Distribution( 1.0 / (1.0 + x * x / n), a, 0.5);
      if ( x > 0.0 ) distribution_function[i] = 1.0 - 0.5 * beta;
      else if ( x < 0.0) distribution_function[i] = 0.5 * beta;
      else distribution_function[i] = 0.5;
      x += delta;
   }
}
