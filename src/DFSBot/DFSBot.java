/* INTELLIGENT SYSTEMS 2014-2015
 * Planet Wars Assignment
 * Deadline: Thursday 29 Januari 23:59
 * 
 * Name(s) :	Cas van der Weegen [2566388]
 *           	Meera Dasai [2056631]
 *           	Janita Nabibaks [2516913]
 * 
 * Group(s) :	Final Project Group 26
 *             	Practicum Group D
 *             	Working Group 2
 *             
 *             	Bot implementing Depth First Search
 *             
 *             	State Space Problem:
 *             
 *             	Initial State: I have x planets, enemy has y planets,
 *             				there are z neutral planets. 
 *             
 *            	Goal Test: Optimal Move
 *             	
 *             	Successor Function: Send half of the ships of one of
 *             				my planets to a neutral or enemy planet
 *             
 *             	Cost Function:
 *             
 */
public class DFSBot {
	
	/* DepthFirstSearch */
	static Path dfs(Tree root) {
		Stack stack = new Stack();
		stack.push(root);
		int maxScore = 0;
		int source = 0;
		int dest = 0;
		while (!stack.isEmpty()) {
			Tree node = (Tree) stack.pop();
			if(node.score == 1){
				System.err.println("Found Move");
				source = node.parent;
				dest = node.id;
        break;
			}
			stack.addAll(node.children);
		}
		return new Path(source,dest);
	}
	  
	public static void DoTurn(PlanetWars pw) {
		/* This is our root Tree, it hold all the possible decisions */
	    Tree root = new Tree(0, 0, 0);
	 
    	Planet source = null;
    	Planet dest = null;
    	
    	/* Construct the Decision Tree */
    	for (Planet p : pw.MyPlanets()) {
    		/* My planets are children of the Root */
    		Tree tmp = root.add(p.PlanetID(), 0);
    		
        	/* Contruct children */
        	for (Planet _p : pw.NotMyPlanets()) {
        		/* Conquerable planets get 1, Others get 0 */
        		tmp.add(_p.PlanetID(), (((p.NumShips()/2) > _p.NumShips()) ? 1 : 0));
       		}
    	}
    	
    	/* Apply DFS to get best planet to attack */
    	Path bestPath = dfs(root);
    	dest = pw.GetPlanet(bestPath._dst);
    	source = pw.GetPlanet(bestPath._src);

		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}
	}

	public static void main(String[] args) {
		String line = "";
		String message = "";
		int c;
		try {
			while ((c = System.in.read()) >= 0) {
				switch (c) {
				case '\n':
					if (line.equals("go")) {
						PlanetWars pw = new PlanetWars(message);
						DoTurn(pw);
						pw.FinishTurn();
						message = "";
					} else {
						message += line + "\n";
					}
					line = "";
					break;
				default:
					line += (char) c;
					break;
				}
			}
		} catch (Exception e) {

		}
	}
}

class Path{
    public int _src;
    public int _dst;
    public Path(int src, int dst){
        _src = src;
        _dst = dst;
    }
}
