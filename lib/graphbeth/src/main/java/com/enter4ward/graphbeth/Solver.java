package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.List;

import com.joptimizer.optimizers.LPOptimizationRequest;
import com.joptimizer.optimizers.LPPrimalDualMethod;

enum ConstraintType {
	EQ, LE, GE
};

class Solver {
	private int varIndex = 0;
	private double[] values;
	private int _numVars;
	private double[] _result;

	
	//      Ax = b,   
	private List<double[]> A = new ArrayList<double[]>(); 
	private List<Double> b = new ArrayList<Double>(); 

	
	public Solver(int numVars) {		
	     values = new double[numVars];
         _numVars = numVars;
     
	}
	
	public int createVariable() {
		return varIndex++;
	}

	public void composeEquation(int i, double value) {
		values[i] += value;
	}

	public void createEquation(ConstraintType type, double value) {
		
		if(type==ConstraintType.GE){
			value = -value;
			for(int i=0; i < values.length; ++i){
				values[i]=-values[i];
			}
		}
		
		for(int i=0; i < values.length; ++i){
			System.out.print("\t"+values[i]);
		}		
		System.out.println("\t<=\t"+value);

		A.add(values);
		b.add(value);
		values = new double[_numVars];

	}

	public boolean solve()  {
		double [] c = new double[_numVars];
		double [] ub = new double[_numVars];
		double [] lb = new double[_numVars];
		
		
		for(int i=0; i<c.length ; ++i){
			c[i]=1;
			lb[i]=0;
			ub[i]=Float.MAX_VALUE;
		}
		

		double [][] G  = new double[A.size()][];
		for(int i=0; i < G.length; ++i){
			G[i]=A.get(i);
		}
		
		double [] h = new double[b.size()];
		for(int i=0;i<h.length;++i){
			h[i]=b.get(i);
		}
		
		LPOptimizationRequest or = new LPOptimizationRequest();
	
		or.setC(c);
		or.setG(G);
		or.setH(h);
		or.setLb(lb);
		or.setUb(ub);
		or.setDumpProblem(true); 
		
		//optimization
		LPPrimalDualMethod opt = new LPPrimalDualMethod();
		
		opt.setLPOptimizationRequest(or);
		try {
			if(opt.optimize()==0){
				_result = opt.getOptimizationResponse().getSolution();
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}


	public double getDecision(int i) {

		return _result[i];
	}
}
