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
 *        PSEUDOCODE
 *
 *              function minimax(node, depth, isMax)
 *          if depth = 0 or node is a terminal node
 *              return the heuristic value of node
 *          if isMax
 *              bestValue := -∞
 *              for each child of node
 *                  eval := minimax(child, depth - 1, FALSE)
 *                  bestValue := max(bestValue, eval)
 *              return bestValue
 *          else
 *              bestValue := +∞
 *              for each child of node
 *                  eval := minimax(child, depth - 1, TRUE)
 *                  bestValue := min(bestValue, eval)
 *              return bestValue
 *             
 */
public class MinMaxBot {
  private static int depth;
  
  /* MiniMax */
  public static Move minimax(PlanetWars pw, int d, Move bestMove, boolean isMax)
  {
    boolean firstMove = false; if(depth == d) /* Mean FirstMove */
      firstMove = true;
    
    Move eval = null; int bestValue;
    
    /* Get the possible moves for Player 1 */
    ArrayList<Move> moves = pw.getMoves(((isMax) ? 1 : 2));
    
    /* Save best move based on score and start recursion */
    for (Move move : moves) {
      if(move.score > bestMove.score){
        bestMove = move;
      }

      /* Clone the PlanetWars Object */
      PlanetWars clone = (PlanetWars) pw.clone(); if(!firstMove && isMax){
        clone.simulateGrowth();
      }
        
      /* It is Max' turn */
      if(isMax == true){
        bestValue = Integer.MIN_VALUE;
        /* Call self while decrementing the depth by 1 */
        if(d == 0){
          int score = (clone.Planets().get(move.src).NumShips() -
          clone.Planets().get(move.dst).NumShips()); eval = new Move(move.src,
          move.dst, score);
        }else{
          clone.simulateIssueOrder(move.src, move.dst); eval = minimax(clone,
          d-1, move, false);
        }
        /* Evaluate */
        if(eval.score > bestValue){
          bestValue = eval.score; bestMove = move;
        }
        /* It is Min' turn */
      }else{
        bestValue = Integer.MAX_VALUE; if(d == 0){
          int score = (clone.Planets().get(move.src).NumShips() -
          clone.Planets().get(move.dst).NumShips()); eval = new Move(move.src,
          move.dst, score);
        }else{
          clone.simulateIssueOrder(move.src, move.dst); eval = minimax(clone,
          d-1, move, true);
        }
        /* Evaluate */
        if(eval.score < bestValue){
          bestValue = eval.score; bestMove = move;
        }
      }
    } return bestMove;
  }
  
  /* Doturn function, gets called each turn */
  public static void DoTurn(PlanetWars pw) {

    Planet source = null; Planet dest = null; depth = 2;
      
    /* Call to (recursive) Minimax */
    Move bestMove = minimax(pw, depth, new Move(0,0,0), true);
    
    /* Extract source and destination */
    source = pw.GetPlanet(bestMove.src); dest = pw.GetPlanet(bestMove.dst);
    
    /* Print */
    System.err.println("[ MinMaxBot ] Moving from: "+bestMove.src+ " to "
                       +bestMove.dst);
    
    /* Issue Order */
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
      e.printStackTrace();
    }
  }
}

class MoveList{
  public int parent; ArrayList<Move> moves; public MoveList(int parent,
  ArrayList<Move> _moves){
    moves = _moves;
    
  }
}
