package com.enter4ward.testbeth;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.enter4ward.graphbeth.Alternative;
import com.enter4ward.graphbeth.Criteria;
import com.enter4ward.graphbeth.Judgement;
import com.enter4ward.graphbeth.JudgementType;

public class CallServlet extends HttpServlet {

	private static final long serialVersionUID = 892499459941707335L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletInputStream is = req.getInputStream();
		byte[] bytes = IOUtils.toByteArray(is);
		is.close();

		Criteria testCriteria = new Criteria();
		String text = new String(bytes);
		System.out.println("#########" + text);
		JSONObject root = new JSONObject(text);

		Map<String, Alternative> alternatives = new TreeMap<String, Alternative>();
		{
			Iterator<?> it = root.getJSONObject("alternatives").keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Alternative a = new Alternative(key);
				testCriteria.addAlternative(a);
				alternatives.put(key, a);
			}
		}
		{
			JSONObject jJuds = root.getJSONObject("judgements");
			Iterator<?> it1 = jJuds.keys();
			while (it1.hasNext()) {
				String from = (String) it1.next();
				JSONObject jJuds2 = jJuds.getJSONObject(from);

				Iterator<?> it2 = jJuds2.keys();
				while (it2.hasNext()) {
					String to = (String) it2.next();
					JSONObject jJuds3 = jJuds2.getJSONObject(to);
					Alternative aFrom = alternatives.get(from);
					Alternative aTo = alternatives.get(to);
					if (aFrom != null && aTo != null) {
						testCriteria.addJudgement(new Judgement(JudgementType.FIXED, aFrom, aTo,
								(float) jJuds3.getDouble("min"), (float) jJuds3.getDouble("max")));
					}

				}

			}
		}

		JSONObject obj = testCriteria.check();

		resp.setContentType("text/html; charset=utf-8");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().println(obj.toString());

	}

}
