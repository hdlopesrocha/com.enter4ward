package com.enter4ward.graphbeth;

import java.io.File;
import java.net.URL;

public class Main {

	public static void main(String[] args) {
        
       ClassLoader cl = Main.class.getClassLoader();
       URL url = cl.getResource("library");
       if(url != null) {
    	   File file = new File( url.getFile() );
//    	   System.load(file.getAbsolutePath());
    	   System.setProperty("java.library.path", file.getAbsolutePath());
       }
       String javaLibPath = System.getProperty("java.library.path");
       System.out.println("XXX: "+javaLibPath);
       
       
		Criteria testCriteria = new Criteria();
		Alternative a = new Alternative("a");
		Alternative b = new Alternative("b");
		Alternative c = new Alternative("c");
		Alternative d = new Alternative("d");
		Alternative e = new Alternative("e");

		testCriteria.addAlternative(a);
		testCriteria.addAlternative(b);
		testCriteria.addAlternative(c);
		testCriteria.addAlternative(d);
		testCriteria.addAlternative(e);
		
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, e, 5));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, d, 4,5));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, c, 3));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, b, 2));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, e, 4));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, d, 1,4));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, c, 1));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, c, d, 1));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, d, e, 1));
		
		testCriteria.check();
	}

}
