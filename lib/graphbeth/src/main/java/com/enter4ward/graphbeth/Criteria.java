package com.enter4ward.graphbeth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Criteria {

	private List<Judgement> judgements = new ArrayList<Judgement>();
	private List<Alternative> alternatives = new ArrayList<Alternative>();

	public Criteria() {

	}

	public void addAlternative(Alternative alternative) {
		alternatives.add(alternative);
	}

	public void addJudgement(Judgement judgement) {
		judgements.add(judgement);
	}

	private float round(double x) {
		return Math.round(x * 1000) / 1000f;
	}

	public JSONObject check() {
		Graph graph = new Graph(judgements);
		JSONObject result = new JSONObject();

		if (!graph.hasCycle()) {

			Map<String, Integer> variables = new TreeMap<String, Integer>();
			Solver solver = new Solver(alternatives.size() * 2);

			// #region CREATE_VARIABLES
			for (Alternative a : alternatives) {
				int aMin = solver.createVariable();
				int aMax = solver.createVariable();

				variables.put(a.getId() + "-", aMin);
				variables.put(a.getId() + "+", aMax);

				solver.composeEquation(aMin, -1);
				solver.createEquation(ConstraintType.LE, 0);

				solver.composeEquation(aMin, 1);
				solver.composeEquation(aMax, -1);
				solver.createEquation(ConstraintType.LE, 0);
			}

			System.out.println("SCORES");
			for (Alternative a : alternatives) {
				int aMin = variables.get(a.getId() + "-");
				int aMax = variables.get(a.getId() + "+");
				for (Alternative b : alternatives) {
					if (a != b) {
						int bMax = variables.get(b.getId() + "+");
						Interval s = graph.pathSize(a, b, null);
						if (s != null) {
							Float min = s.getMin();
							Float max = s.getMax();
							System.out.println(a.getId() + "->" + b.getId() + "=" + min + "/" + max);

							solver.composeEquation(aMin, -1);
							solver.composeEquation(bMax, 1);
							solver.createEquation(ConstraintType.LE, -min);

							solver.composeEquation(aMax, -1);
							solver.composeEquation(bMax, 1);
							solver.createEquation(ConstraintType.LE, -max);

							// solver.composeEquation(aMin, 1);
							// solver.composeEquation(aMax, -1);
							// solver.createEquation(ConstraintType.LE, min - max);

							Judgement j = graph.get(a, b);
							if (j == null) {
								j = graph.get(b, a);
							}
							if (j == null) {
								merge(new Judgement(JudgementType.DYNAMIC, a, b, min, max), graph, "SC1");
							}
						}

					}
				}
			}

			System.out.println("FINAL RESULT");
			for (Judgement j : judgements) {
				System.out.println(j.toString());
			}

			JSONObject judgements = new JSONObject();

			if (solver.solve()) {
				Map<Alternative, Interval> scale = new TreeMap<Alternative, Interval>();

				System.out.println("SCALE");
				JSONObject jScale = new JSONObject();
				for (Alternative a : alternatives) {

					// Building scale
					float min = round(solver.getDecision(variables.get(a.getId() + "-")));
					float max = round(solver.getDecision(variables.get(a.getId() + "+")));
					scale.put(a, new Interval(min, max));

					JSONArray array = new JSONArray();
					array.put(min);
					array.put(max);
					jScale.put(a.getId(), array);
				}

				//graph = scoreCompleter(graph, scale);
				graph.enforce();

				for (Alternative a : alternatives) {
					// Building judgements
					JSONObject fObj = new JSONObject();
					for (Judgement j : graph.getFrom(a)) {
						JSONObject tObj = new JSONObject();
						float jMin = round(j.getMin());
						float jMax = round(j.getMax());

						tObj.put("min", jMin);
						tObj.put("max", jMax);
						tObj.put("type", j.getJudgementType());

						JSONArray sugs = new JSONArray();
						for (Interval i : j.getSuggestions()) {
							sugs.put(new JSONArray().put(i.getMin()).put(i.getMax()));
						}
						tObj.put("sug", sugs);

						fObj.put(j.getTo().getId().toString(), tObj);
					}
					judgements.put(a.getId().toString(), fObj);

				}

				result.put("scale", jScale);
				result.put("judgements", judgements);

			}

		} else {
			System.out.println("Cycle detected!");
		}

		
		System.out.println("RES: "+result.toString());
		return result;

	}

	public Graph scoreCompleter(Graph graph, Map<Alternative, Interval> scale) {
		for (Judgement j : new ArrayList<Judgement>(judgements)) {
			if (j.getJudgementType().equals(JudgementType.DYNAMIC)) {
				judgements.remove(j);
			}
		}

		graph = new Graph(judgements);

		Alternative[] alts = new Alternative[alternatives.size()];
		alternatives.toArray(alts);

		for (int i = 0; i < alts.length; ++i) {
			Alternative a = alts[i];
			for (int j = i + 1; j < alts.length; ++j) {
				Alternative b = alts[j];
				Judgement jud = graph.get(a, b);
				if (jud == null) {
					jud = graph.get(b, a);
				}
				if (jud == null) {
					float aMin = scale.get(a).getMin();
					float aMax = scale.get(a).getMax();
					float bMin = scale.get(b).getMin();
					float bMax = scale.get(b).getMax();

					if (aMin - bMax > 0) {
						Collection<Judgement> tos = graph.getTo(b);
						Collection<Judgement> froms = graph.getFrom(b);
						if (!tos.isEmpty() || !froms.isEmpty()) {
							merge(new Judgement(JudgementType.DYNAMIC, a, b, aMin - bMax, aMax - bMin), graph, "SC1");
						}
					} else if (bMin - aMax > 0) {
						Collection<Judgement> tos = graph.getTo(a);
						Collection<Judgement> froms = graph.getFrom(a);
						if (!tos.isEmpty() || !froms.isEmpty()) {
							merge(new Judgement(JudgementType.DYNAMIC, b, a, bMin - aMax, bMax - aMin), graph, "SC2");
						}
					}
				}

			}
		}
		return graph;
	}

	private boolean merge(Judgement j, Graph graph, String rule) {
		boolean changed = false;
		Judgement k = graph.get(j.getFrom(), j.getTo());

		if (k == null) {
			addJudgement(j);
			graph.add(j);
			changed |= true;
		} else if (k.getJudgementType() == JudgementType.DYNAMIC) {
			try {
				changed |= k.merge(j);
			} catch (MergeException e) {
				e.printStackTrace();
			}
		}
		if (changed) {
			System.out.println(rule + " | " + j.toString());
		}

		return changed;
	}

}
