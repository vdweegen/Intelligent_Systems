import java.util.*;

/*
 RandomBot - an example bot that picks up one of his planets and send half of the ships 
 from that planet to a random target planet.

 Not a very clever bot, but showcases the functions that can be used.
 Overcommented for educational purposes.
 */
public class RandomBot {

	/*
	 * Function that gets called every turn. This is where to implement the strategies.
	 */

	public static void DoTurn(PlanetWars pw) {

		// (0) Create a random number generator
		Random random = new Random();
		System.err.println("Err... RandomBot moving out... Bleep Bleep Bleep :)");
		// (1) Pick one of my planets at random.
		Planet source = null;

		// (1a) Take the list of my planets
		List<Planet> myPlanets = pw.MyPlanets();

		// (1b) If the list is not empty:
		if (myPlanets.size() > 0) {

			// (1c) Pick a random integer in [0, number_of_my_planets]
			Integer randomSource = random.nextInt(myPlanets.size());

			// (1d) Pick a random planet as source
			source = myPlanets.get(randomSource);
		}

		// (2) Pick a target planet at random
		Planet dest = null;

		// (2a) Take the list of all planets
		List<Planet> allPlanets = pw.Planets();

		// Remove our 'source' planet from the list: using the same planet as 'source' and 'dest' is an invalid turn.
		// As a result, you will skip this turn without any action.
		allPlanets.remove(source);
		if (allPlanets.size() > 0) {

			// (2b) Pick a random integer in [0, number_of_all_planets]
			Integer randomTarget = random.nextInt(allPlanets.size());

			// (2c) Pick a random planet as target
			dest = allPlanets.get(randomTarget);
		}

		// (3) Send half the ships from source to destination
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
