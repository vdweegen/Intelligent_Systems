import java.util.*;

/* A bit smarter kind of bot, who searches for its strongest planet and then attacks the weakest planet.
 The score is computed based on the number of ships and the inverse of the growth rate.
 */

public class BullyBot {
	public static void DoTurn(PlanetWars pw) {

		// (1) Find my strongest planet.
		Planet source = null;
		System.err.println("This is BullyBot! I Came, I saw, I bullied, I won... ;-) ");
		double sourceScore = Double.MIN_VALUE;
		for (Planet myPlanet : pw.MyPlanets()) {
			//This score is one way of defining how 'good' my planet is. 
			double score = (double) myPlanet.NumShips() / (1 + myPlanet.GrowthRate());
			if (score > sourceScore) {
				//we want to maximize the score, so store the planet with the best score
				sourceScore = score;
				source = myPlanet;
			}
		}
		// (2) Find the weakest enemy or neutral planet.
		Planet dest = null;
		double destScore = Double.MIN_VALUE;
		for (Planet notMyPlanets : pw.NotMyPlanets()) {
			//This score is one way of defining how 'bad' the other planet is. 
			double score = (double) (1 + notMyPlanets.GrowthRate()) / notMyPlanets.NumShips();
			if (score > destScore) {
				//The way the score is defined, is that the weaker a plannet is, the higher the score. 
				//So again, we want to select the planet with the best score
				destScore = score;
				dest = notMyPlanets;
			}
		}
		// (3) Attack!
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
			// Owned.
		}
	}
}
