package com.enter4ward.graphbeth;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;

enum ConstraintType {
	EQ, LE, GE
};

class Solver {
	private LpSolve solver;
	private int varIndex = 1;
	private double[] values;
	private int _numVars;
	private boolean _first = true;
	private double[] _result;

	public Solver(int numVars) {
	     try {
			solver = LpSolve.makeLp(0, numVars);
			solver.setVerbose(0);
		} catch (LpSolveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	     values = new double[numVars + 1];
         _numVars = numVars;
     
	     
	}
	
	public int AddDecision() {
		try {
			solver.setColName(varIndex, "x" + varIndex);
		} catch (LpSolveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return varIndex++;
	}

	public void AddConstraint(int i, double value) {
		values[i] += value;
	}

	public void AddConstraints(ConstraintType type, double value) {
		if (_first) {
			solver.setAddRowmode(true);
			_first = false;
		}

		int ltype = (type == ConstraintType.EQ) ? LpSolve.EQ: (type == ConstraintType.GE) ? LpSolve.GE : LpSolve.LE;
		try {
			solver.addConstraintex(values.length, values, null, ltype, value);
		} catch (LpSolveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		values = new double[_numVars + 1];

	}

	public boolean Solve() throws LpSolveException {
		_result = new double[_numVars];

		solver.setAddRowmode(false);
		/* set the objective in lpsolve */
		for (int i = 1; i <= _numVars; ++i) {
			values[i] = 1;
		}
		solver.setObjFnex(values.length, values, null);

		solver.setMinim();
		int ret = solver.solve();
		solver.getVariables( _result);

		return (ret == LpSolve.OPTIMAL);

	}

	public void WriteFile(String filename) {
		try {
			solver.writeLp(filename + ".lp");
		} catch (LpSolveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double GetDecision(int i) {

		return _result[i - 1];
	}
}
