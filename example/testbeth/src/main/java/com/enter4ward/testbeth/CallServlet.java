package com.enter4ward.testbeth;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONObject;

import com.enter4ward.graphbeth.Alternative;
import com.enter4ward.graphbeth.Criteria;
import com.enter4ward.graphbeth.Judgement;
import com.enter4ward.graphbeth.JudgementType;
 
public class CallServlet extends AbstractHandler {
 
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, a, b, 2f));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, c, 1f));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, c, d, 1f));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, d, e, 1f));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, e, 4f));
		testCriteria.addJudgement(new Judgement(JudgementType.FIXED, b, d, 1f,4f));
		JSONObject obj = testCriteria.check();
    	
    	
    	
    	response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(obj.toString());
        baseRequest.setHandled(true);
    }
 
   
}
