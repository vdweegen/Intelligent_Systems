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
 *            Practicum Group D
 *            Working Group 2
 *             
 *            OtherBot [Own Implementation]
 *             
 */
public class OtherBot {
  private static boolean OnlyWinMoves;
  /* MiniMax */
  public static Move minimax(PlanetWars pw, int depth) {
    double alpha = Integer.MIN_VALUE; double beta = Integer.MAX_VALUE;
    
    /* Start with Player1 */
      Move bestMove = null; int player = 1;
      
    /* Get the possible moves for Player */
    ArrayList<Move> moves = pw.getMoves(player,OnlyWinMoves);
    
    /* Just make sure we don't crash when there are no moves because of
     * the 'OnlyWinMoves' flag */
    if(moves.size() == 0){
      moves = pw.getMoves(player, !OnlyWinMoves);
    }
    
    /* Start checking all moves */
    for(Move move : moves){
      PlanetWars clone = (PlanetWars) pw.clone();
      clone.simulateIssueOrder(move.src, move.dst); double a = minimax(clone,
      depth-1, alpha, beta, 2); move.alpha = a; if(a > alpha){
        alpha = a; bestMove = move;
      } if (beta <= alpha){
        return bestMove; /* Beta Cutoff */
      }
    }
    
    if(bestMove == null){
      System.err.println("[ OtherBot ] NO MOVES!!!!");
    } return bestMove;
  }
  
  private static double minimax(PlanetWars pw, int depth, double alpha, double
  beta, int player) {
    if(depth == 0){
      return evaluate(pw, player);
    } if(player == 1){
      pw.simulateGrowth(); /* Simulate the Growth */ double v =
      Integer.MIN_VALUE; ArrayList<Move> moves =
      pw.getMoves(player,OnlyWinMoves);
           for (Move move : moves) {
        PlanetWars clone = (PlanetWars) pw.clone();
              clone.simulateIssueOrder(move.src, move.dst);
        v = Math.max(v, minimax(clone, depth-1, alpha, beta, 2));
              move.alpha = v; alpha = Math.max(alpha, v); if (beta <= alpha) {
                break; /* Beta Cutoff */
              }
           } return v;
    }else{
      double v = Integer.MAX_VALUE; ArrayList<Move> moves =
      pw.getMoves(player,OnlyWinMoves);
           for (Move move : moves) {
        PlanetWars clone = (PlanetWars) pw.clone();
              clone.simulateIssueOrder(move.src, move.dst);
        v = Math.min(v, minimax(clone, depth-1, alpha, beta, 1));
              move.alpha = v; beta = Math.min(beta, v); if (beta <= alpha) {
                break; /* Alpha Cutoff */
              }
           } return v;
    }
  }
  
     private static double evaluate(PlanetWars clone, int player) {
       /* Eval is in the wrong order because of premature assigning, so switch
       /* */
      player = (player == 1) ? 2 : 1; double enemyShips = 0.0; double
      friendlyShips = 0.0; double enemyPlanets = 0.0; double friendlyPlanets =
      0.0;
      
      /* Enemy Ships and Planets */
      for (Planet planet : clone.EnemyPlanetsOfPlayer(player)){
        enemyShips += planet.NumShips(); enemyPlanets++;
      }
      
      /* Friendly Ships and Planets */
      for (Planet planet : clone.PlanetsOfPlayer(player)){
        friendlyShips += planet.NumShips(); friendlyPlanets++;
      }
      
      /* Neutral Planets */
      double neutralPlanets = clone.NeutralPlanets().size();
      
      
       //double score =
       //(friendlyPlanets/enemyPlanets)/neutralPlanets+(friendlyShips/enemyShips)/neutralPlanets;
       //double score = (friendlyShips/enemyShips); double score =
       //(friendlyShips/enemyShips)/(friendlyPlanets/enemyPlanets);
       double score = (friendlyShips*friendlyPlanets)/(enemyShips*enemyPlanets);
          
        return score;
       }

  public static void DoTurn(PlanetWars pw) {

    Planet source = null; Planet dest = null; int depth;
    
    if(pw.Planets().size() > 8){
      if(pw.MyPlanets().size() > 4){
        depth = 1;
      }else{
        depth = 2;
      }
      
      OnlyWinMoves = true;
    } else{
        OnlyWinMoves = false;
      depth = 4;
      
    }
    
    /* Call to (recursive) Minimax */
    Move bestMove = minimax(pw, 3);
    
    /* Extract source and destination */
    source = pw.GetPlanet(bestMove.src); dest = pw.GetPlanet(bestMove.dst);
    
    /* Print */
    System.err.println("[ OtherBot ] Moving from: "+bestMove.src+ " to "+
                       bestMove.dst);
    
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

    }
  }
}
