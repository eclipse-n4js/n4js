package org.eclipse.n4js.flowgraphs.tests;

public class ControlFlowTest {
	/*
	 * @Test public void checkEdgeConsistency() throws Exception { boolean verdict = true;
	 * 
	 * Iterator<MethodDeclaration> it = Java17MethodLoader.getAllMethodsIterator(); while (it.hasNext()) { Toolbox tBox
	 * = new Toolbox(); MethodDeclaration method = it.next(); tBox.programAbstraction = new ProgramAbstraction(method);
	 * TX0_MakeProgramAbstraction.compute(tBox.programAbstraction); tBox.graph =
	 * TX1_MakeGraph.compute(tBox.programAbstraction); TX2_MakeControlFlow.compute(tBox.graph);
	 * 
	 * ControlFlowEdge inconsistent = consistentCFEdges(tBox.graph.javaFeatures.getAllNodes()); if (inconsistent !=
	 * null) { System.err.println("Edge Consistency Error in " + tBox.graph.program.getName());
	 * System.err.println("  Control flow edge is inconsistent bound to start/end: " + inconsistent + "\n"); verdict =
	 * false; } }
	 * 
	 * assertTrue(verdict); }
	 * 
	 * private ControlFlowEdge consistentCFEdges(Collection<Node> nodes) { for (Node node : nodes) { for
	 * (ControlFlowEdge edge : node.getSuccessors()) { if (!edge.start.succ.contains(edge)) return edge; if
	 * (!edge.end.pred.contains(edge)) return edge; } for (ControlFlowEdge edge : node.getPredecessors()) { if
	 * (!edge.start.succ.contains(edge)) return edge; if (!edge.end.pred.contains(edge)) return edge; } }
	 * 
	 * return null; }
	 * 
	 * @Test public void checkFlowConsistency() throws Exception { boolean verdict = true;
	 * 
	 * Iterator<MethodDeclaration> it = Java17MethodLoader.getAllMethodsIterator(); while (it.hasNext()) { Toolbox tBox
	 * = new Toolbox(); MethodDeclaration method = it.next();
	 * 
	 * tBox.programAbstraction = new ProgramAbstraction(method);
	 * TX0_MakeProgramAbstraction.compute(tBox.programAbstraction); tBox.graph =
	 * TX1_MakeGraph.compute(tBox.programAbstraction); TX2_MakeControlFlow.compute(tBox.graph);
	 * 
	 * boolean findsEnd = findsEnd(tBox.graph.program.getStart(), tBox.graph.program.getEnd()); if (!findsEnd) {
	 * System.err.println("Flow Consistency Error in " + tBox.graph.program.getName() + " : " +
	 * tBox.graph.program.getStart()); verdict = false; } }
	 * 
	 * assertTrue(verdict); }
	 * 
	 * private final Set<Node> visitedNodes = new HashSet<>();
	 * 
	 * private boolean findsEnd(Node start, Node end) { if (start == end) return true; if
	 * (start.getSuccessors().isEmpty()) return false;
	 * 
	 * boolean findsEnd = true;
	 * 
	 * for (ControlFlowEdge pEdge : start.getSuccessors()) if (!pEdge.isLoop) { if (visitedNodes.contains(pEdge.end))
	 * System.out.println("Node already visited: " + pEdge);
	 * 
	 * findsEnd &= findsEnd(pEdge.end, end); }
	 * 
	 * return findsEnd; }
	 * 
	 * @Test public void checkPartialEdgeConsistency() throws Exception { boolean verdict = true;
	 * 
	 * Iterator<MethodDeclaration> it = Java17MethodLoader.getAllMethodsIterator(); while (it.hasNext()) {
	 * MethodDeclaration method = it.next();
	 * 
	 * Toolbox tBox = new Toolbox(); tBox.programAbstraction = new ProgramAbstraction(method);
	 * TX0_MakeProgramAbstraction.compute(tBox.programAbstraction); tBox.graph =
	 * TX1_MakeGraph.compute(tBox.programAbstraction); TX2_MakeControlFlow.compute(tBox.graph);
	 * TX3_MakeOpEdges.compute(tBox.graph); tBox.graphSystem = TX4_MakeGraphSystem.compute(tBox.graph);
	 * 
	 * for (PartialGraph pg : tBox.graphSystem.partialGraphs) { ControlFlowEdge inconsistent =
	 * consistentCFEdges(pg.nodeMapping.values()); if (inconsistent != null) {
	 * System.err.println("Edge Consistency Error in " + tBox.graph.program.getName()); System.err
	 * .println("  Control flow edge is inconsistent bound to start/end: " + inconsistent + "\n"); verdict = false; } }
	 * }
	 * 
	 * assertTrue(verdict); }
	 * 
	 * @Test public void checkPartialFlowConsistency() throws Exception { boolean verdict = true;
	 * 
	 * Iterator<MethodDeclaration> it = Java17MethodLoader.getAllMethodsIterator(); while (it.hasNext()) {
	 * MethodDeclaration method = it.next();
	 * 
	 * Toolbox tBox = new Toolbox(); tBox.programAbstraction = new ProgramAbstraction(method);
	 * TX0_MakeProgramAbstraction.compute(tBox.programAbstraction); tBox.graph =
	 * TX1_MakeGraph.compute(tBox.programAbstraction); TX2_MakeControlFlow.compute(tBox.graph);
	 * TX3_MakeOpEdges.compute(tBox.graph); tBox.graphSystem = TX4_MakeGraphSystem.compute(tBox.graph);
	 * 
	 * for (PartialGraph pg : tBox.graphSystem.partialGraphs) { LinkedList<Node> path = getPath(pg, true); Node endNode
	 * = path.getLast(); if (endNode != pg.endNode) { System.err.println("Flow Consistency Error (forward) in " +
	 * tBox.graph.program.getName() + ";\n  with: " + pg.startNode); verdict = false; } path = getPath(pg, false); Node
	 * startNode = path.getLast(); if (startNode != pg.startNode) {
	 * System.err.println("Flow Consistency Error (backward) in " + tBox.graph.program.getName() + ";\n  with: " +
	 * pg.startNode); verdict = false; } } }
	 * 
	 * assertTrue(verdict); }
	 * 
	 * private LinkedList<Node> getPath(PartialGraph pg, boolean forwards) { LinkedList<Node> path = new LinkedList<>();
	 * Node n = (forwards) ? pg.startNode : pg.endNode;
	 * 
	 * while (n != null) { path.add(n); List<ControlFlowEdge> nexts = (forwards) ? n.succ : n.pred;
	 * List<ControlFlowEdge> tmpE = new LinkedList<>(); for (ControlFlowEdge e : nexts) { if (!e.isLoop) tmpE.add(e); }
	 * 
	 * if (tmpE.size() == 0) n = null;
	 * 
	 * if (tmpE.size() == 1) { ControlFlowEdge e = nexts.get(0); n = (forwards) ? e.end : e.start; }
	 * 
	 * if (tmpE.size() > 1) throw new RuntimeException("Path must not fork."); }
	 * 
	 * return path; }
	 */
}
