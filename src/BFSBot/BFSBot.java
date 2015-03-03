import java.util.*;

/* INTELLIGENT SYSTEMS 2014-2015
 * Planet Wars Assignment
 * Deadline: Thursday 29 Januari 23:59
 * 
 * Name(s) : Cas van der Weegen [2566388]
 *            Meera Dasai [2056631]
 *            Janita Nabibaks [2516913]
 * 
 * Group(s) : Final Project Group 26
 *              Practicum Group D
 *              Working Group 2
 *             
 *              Bot implementing Breadth First Search
 *             
 *              State Space Problem:
 *             
 *              Initial State: I have x planets, enemy has y planets,
 *                    there are z neutral planets.
 *             
 *              Goal Test: Planet Conquered
 *              
 *              Successor Function: Send half of the ships of one of
 *                    my planets to a neutral or enemy planet
 *             
 *              Cost Function: Steps taken to conquer a planet
 *             
 */
public class BFSBot {
  
  /* BreadthFirstSearch */
  static TreePath bfs(Tree root) {
   Queue queue = new Queue();
   queue.add(root);
   int source = 0;
   int dest = 0;
   while (!queue.isEmpty()) {
    Tree node = (Tree) queue.get();
    if(node.score == 1){
     System.err.println("Found Move"+node.parent+"=>"+node.id);
     source = node.parent;
     dest = node.id;
     break;
    }
    queue.addAll(node.children);
   }
   return new TreePath(source,dest);
  }
    
  public static void DoTurn(PlanetWars pw) {
    /* This is our root Tree, it hold all the possible decisions */
      Tree root = new Tree(0, 0, 0);
    
      /* Initialize Planet Objects */
      Planet source = null; Planet dest = null;
      
      /* Construct the Decision Tree */
      for (Planet p : pw.MyPlanets()) {
        /* My planets are children of the Root */
        Tree tmp = root.add(p.PlanetID(), 0);
        
          /* Contruct children */
          for (Planet _p : pw.NotMyPlanets()) {
            /* Conquerable planets get 1, Others get 0 */
            tmp.add(_p.PlanetID(), (((p.NumShips()/2) > _p.NumShips()) ? 1 :
            0));
          }
      }
      
      /* Apply BFS to get best planet to attack */
      TreePath bestPath = bfs(root); dest = pw.GetPlanet(bestPath.dst);
      source = pw.GetPlanet(bestPath.src);

    if (source != null && dest != null) {
      pw.IssueOrder(source, dest);
    }
  }

  public static void main(String[] args) {
    String line = ""; String message = ""; int c; try {
      while ((c = System.in.read()) >= 0) {
        switch (c) { case '\n':
          if (line.equals("go")) {
            PlanetWars pw = new PlanetWars(message); DoTurn(pw);
            pw.FinishTurn(); message = "";
          } else {
            message += line + "\n";
          } line = ""; break;
        default:
          line += (char) c; break;
        }
      }
    } catch (Exception e) {

    }
  }
}

class TreePath{
    public int src; public int dst;
    
    public TreePath(int _src, int _dst){
        src = _src; dst = _dst;
    }
}
