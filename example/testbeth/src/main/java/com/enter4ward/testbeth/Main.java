package com.enter4ward.testbeth;

import com.enter4ward.graphbeth.Alternative;
import com.enter4ward.graphbeth.Criteria;
import com.enter4ward.graphbeth.Judgement;
import com.enter4ward.graphbeth.JudgementType;

public class Main {

	public static void main(String[] args)  {

		Criteria testCriteria = new Criteria();
		Alternative a = new Alternative("a");
		Alternative b = new Alternative("b");
		Alternative c = new Alternative("c");
		Alternative d = new Alternative("d");
		Alternative e = new Alternative("e");
		//Alternative f = new Alternative("f");

		testCriteria.addAlternative(a);
		testCriteria.addAlternative(b);
		testCriteria.addAlternative(c);
		testCriteria.addAlternative(d);
		testCriteria.addAlternative(e);
		//testCriteria.addAlternative(f);

		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, b, 2));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, c, 1));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, c, d, 1));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, d, e, 1));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, e, 4));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, d, 1,4));
		//testCriteria.addJudgement(new Judgement(JudgementType.FIXED, e, f, 0));

		testCriteria.check();
	}
	
	public static void main2(String[] args)  {

		Criteria testCriteria = new Criteria();
		Alternative a = new Alternative("a");
		Alternative b = new Alternative("b");
		Alternative c = new Alternative("c");
		Alternative d = new Alternative("d");
		Alternative e = new Alternative("e");
		//Alternative f = new Alternative("f");

		testCriteria.addAlternative(a);
		testCriteria.addAlternative(b);
		testCriteria.addAlternative(c);
		testCriteria.addAlternative(d);
		testCriteria.addAlternative(e);
		//testCriteria.addAlternative(f);

		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, e, 5));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, d, 4,5));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, c, 3));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, b, 2));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, e, 4));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, d, 1,4));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, c, 1));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, c, d, 1));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, d, e, 1));
		//testCriteria.addJudgement(new Judgement(JudgementType.FIXED, e, f, 0));

		testCriteria.check();
	}

}
