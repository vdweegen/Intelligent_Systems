import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
 *             	Adaptive Bot
 *             
 */
public class AdaptiBot {
	public static void DoTurn(PlanetWars pw) { 
		int neutralPlanets = pw.NeutralPlanets().size();
		int enemyPlanets = pw.EnemyPlanets().size();
		int friendlyPlanets = pw.MyPlanets().size();
		int totalPlanetSize = 0;
		for (Planet p : pw.NeutralPlanets()) {
			totalPlanetSize += p.GrowthRate();
		}
		
		int averagePlanetSize = Math.round(totalPlanetSize/pw.NeutralPlanets().size());
			
		/*
		 * Possibilities:
		 * 
		 * 	Aggressor => Attacks the Enemy
		 * 	Expander => Attacks a Neutral planet (Expand Territory)
		 * 	Napoleon => Try to diminish enemy growth-ratio, but choose neutral if big-win available
		 * 	Caesar => Try to diminish enemy growth-ratio
		 *  
		 */
		String thisTurnBot = AdaptiBotMap.get(neutralPlanets, enemyPlanets, friendlyPlanets, averagePlanetSize);
		
		if (thisTurnBot == null){
			DoRandomBotTurn(pw); /* If we're unsure go with Random */
		}else{
			if (thisTurnBot.equals("BullyBot")) {
				DoBullyBotTurn(pw);
			} else if (thisTurnBot.equals("RandomBot")) {
				DoRandomBotTurn(pw);
			} else if (thisTurnBot.equals("Aggressor")) {
				AggressorBot(pw);
			} else if (thisTurnBot.equals("Expander")) {
				ExpanderBot(pw);
			} else if (thisTurnBot.equals("Napoleon")) {
				NapoleonBot(pw);
			} else if (thisTurnBot.equals("Caesar")) {
				CaesarBot(pw);
			} else {
				DoRandomBotTurn(pw);
			}
		}
	}
	
	/* AggressorBot, tried to attack the enemy */
	public static void AggressorBot(PlanetWars pw){
		Planet source = null;
		Planet dest = null;
		int bestScore = Integer.MIN_VALUE;
		
		for (Planet p : pw.MyPlanets()){
			if(p.NumShips() == 1){
				continue;
			}
			for (Planet _p : pw.EnemyPlanets()){
				int score = ((p.NumShips()/2) - (_p.NumShips() + _p.GrowthRate()));
				if(score > bestScore){
					score = bestScore;
					source = p;
					dest = _p;
				}
			}
		}
		
		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}else{
			DoBullyBotTurn(pw); /* Make sure an order is actually issued */
		}
	}
	
	/* ExpanderBot => Bot that tried to expand the territory */
	public static void ExpanderBot(PlanetWars pw){
		System.err.println("[ Expander ] Growing, we shall...");
		Planet source = null;
		Planet dest = null;
		int bestScore = Integer.MIN_VALUE; /* Initializing with 0 could crash the bot */
		
		/* Loop through planets */
		for (Planet p : pw.MyPlanets()){
			if(p.NumShips() == 1){
				continue;
			}
			for (Planet _p : pw.NeutralPlanets()){
				int score = ((p.NumShips()/2)-_p.NumShips());
				
				/* Basic score, is it winnable? */
				if(score > bestScore){
					bestScore = score;
					source = p;
					dest = _p;
				}
			}
		}
		
		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}else{
			DoBullyBotTurn(pw); /* Make sure an order is actually issued */
		}
	}
	
	public static void NapoleonBot(PlanetWars pw){
		System.err.println("[ Napoleon ] Have no fear, I is here");
		Move bestMove = new Move(0,0);
		ArrayList<Move> eMoves = new ArrayList<Move>();
		ArrayList<Move> nMoves = new ArrayList<Move>();
		
		/* Save possible Moves */
		for (Planet p : pw.MyPlanets()){
			for (Planet _p : pw.NotMyPlanets()){
				if(_p.Owner() == 0){
						nMoves.add(new Move(p.PlanetID(), _p.PlanetID(), (p.NumShips()/2 - _p.NumShips() + _p.GrowthRate())));	
				}else{
					if((p.NumShips()/2 - _p.NumShips() + _p.GrowthRate()) > 0){ /* Only WinMoves */
						eMoves.add(new Move(p.PlanetID(), _p.PlanetID(), (p.NumShips()/2 - _p.NumShips() + _p.GrowthRate())));	
					}
				}
			}
		}
		
		/* Decide Best Move */
		for( Move m : eMoves ){
			if(m.getScore() > bestMove.getScore()){
				bestMove = m;
			}
		}
		
		/* Decide whether it is worth it to NOT diminish the Enemy GrowthRate */
		for( Move _m : nMoves){
			if(_m.getScore() > (bestMove.getScore())){
				bestMove = _m;
			}
		}
			
		/* If we couldn't decide, forward command to other player! */
		if (bestMove.getScore() > Integer.MIN_VALUE) {
			pw.IssueOrder(bestMove.getSrc(), bestMove.getDst());
		}else{
			DoBullyBotTurn(pw); /* Make sure an order is actually issued */
		}
	}
	
	public static void CaesarBot(PlanetWars pw){
		System.err.println("[ Caesar ] Through the mountains we come...");
		Move bestMove = new Move(0,0,Integer.MIN_VALUE);
		ArrayList<Move> eMoves = new ArrayList<Move>();
		ArrayList<Move> nMoves = new ArrayList<Move>();
		
		/* Save possible Moves */
		for (Planet p : pw.MyPlanets()){
			for (Planet _p : pw.NotMyPlanets()){
				if(_p.Owner() == 0){
					nMoves.add(new Move(p.PlanetID(), _p.PlanetID(), (p.NumShips()/2 - _p.NumShips() + _p.GrowthRate())));	
				}else{
					eMoves.add(new Move(p.PlanetID(), _p.PlanetID(), (p.NumShips()/2 - _p.NumShips() + _p.GrowthRate())));	
				}
			}
		}
		
		/* Decide Best Move */
		for( Move m : eMoves ){
			if(m.getScore() > bestMove.getScore()){
				bestMove = m;
			}
		}
    
		/* If we couldn't decide, foward command to other player! */
		if (bestMove.getScore() > Integer.MIN_VALUE) {
			pw.IssueOrder(bestMove.getSrc(), bestMove.getDst());
		}else{
			DoBullyBotTurn(pw);
		}
	}
	
	
	/* BullyBot Turn */
	public static void DoBullyBotTurn(PlanetWars pw) {
		Planet source = null;
		double sourceScore = Double.MIN_VALUE; /* Initializing with 0 could crash the bot */
		//Select my strongest planet to send ships from
		for (Planet myPlanet : pw.MyPlanets()) {
			if (myPlanet.NumShips() <= 1)
				continue;
			double score = (double) myPlanet.NumShips();
			if (score > sourceScore) {
				sourceScore = score;
				source = myPlanet;
			}
		}
		
		Planet dest = null;
		double destScore = Double.MAX_VALUE;
		//Select weakest destination planet
		for (Planet notMyPlanet : pw.NotMyPlanets()) {
			double score = (double) (notMyPlanet.NumShips());

			if (score < destScore) {
				destScore = score;
				dest = notMyPlanet;
			}
		}
		
		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}else{
			DoRandomBotTurn(pw);
		}
	}
	
	/* RandomBot Turn */
	public static void DoRandomBotTurn(PlanetWars pw) {

		Random random = new Random();
		
		Planet source = null;
		List<Planet> myPlanets = pw.MyPlanets();
		//Randomly select source planet
		if (myPlanets.size() > 0) {
			Integer randomSource = random.nextInt(myPlanets.size());
			source = myPlanets.get(randomSource);
		}
		
		Planet dest = null;
		List<Planet> allPlanets = pw.NotMyPlanets();
		
		/* Randomly Select destination Planet */
		if (allPlanets.size() > 0) {
			Integer randomTarget = random.nextInt(allPlanets.size());
			dest = allPlanets.get(randomTarget);
		}

		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}else{
			DoBullyBotTurn(pw);
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
