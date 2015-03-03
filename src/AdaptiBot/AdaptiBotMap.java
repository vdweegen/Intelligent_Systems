import java.util.*;

/* An adaptivity map implementation.
 * 
 * In this example, we provide two environment parameters (number of neutral planets, average growth ratio of neutral planets), and we match them to a specific bot. 
 * The first upper left element of the botValue can be read as: "given that the map has 0 neutral planets
 * and that the average growth rate of the neutral planets in the map is 0, then use the RandomBot"; the next element to the right: "given
 * 0 neutral planets and an average growth of 1, use BullyBot; one down the upper left: given 1 neutral planet and average growth
 * 0, use RandomBot". The given example is random and does not have to be smart at all. We recommend start editing this array to 
 * get used to adaptivity and discover which other features would make your bot more smartly adaptive. * 
 */

public class AdaptiBotMap {
	
	private static HashMap<List<Integer>, String> map = new HashMap<List<Integer>, String>();
	private static final int MAX_NEUTRAL_PLANETS = 25;
	private static final int MAX_PLANET_SIZE = 5;
	
	
	/*
	 * X => Average Growth Ratio
	 * Y => Neutral Planets on Map
	 */
	
	
	//average growth ratio of				0			1			2			3			4			5
	private static String[] botValue = {"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot", // 0 neutral planets on the map
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot", // 1 neutral planet
										"BullyBot", "RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", // 2 neutral planets
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "RandomBot",// ...
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "RandomBot", "RandomBot", "BullyBot", "RandomBot",
										"RandomBot", "BullyBot", "BullyBot", "BullyBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "RandomBot", "RandomBot",
										"BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "RandomBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"BullyBot", "BullyBot", "RandomBot", "RandomBot", "BullyBot", "RandomBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "BullyBot", "BullyBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "RandomBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "RandomBot", "BullyBot",
										"RandomBot", "BullyBot", "RandomBot", "RandomBot", "BullyBot", "RandomBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"BullyBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "RandomBot",
										"RandomBot", "BullyBot", "RandomBot", "RandomBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"BullyBot", "RandomBot", "BullyBot", "RandomBot", "BullyBot", "RandomBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"RandomBot", "BullyBot", "BullyBot", "RandomBot", "BullyBot", "BullyBot",
										"BullyBot", "RandomBot", "BullyBot", "BullyBot", "BullyBot", "BullyBot"}; // 25
	
	
	//Use the botvalue table and rewrite it into our mapping variable
	static {
		for (int numberNeutralPlanets = 0; numberNeutralPlanets <= MAX_NEUTRAL_PLANETS; numberNeutralPlanets++) {
			for (int planetSize = 0; planetSize <= MAX_PLANET_SIZE; planetSize++) {
				put(numberNeutralPlanets, planetSize, botValue[numberNeutralPlanets*(MAX_PLANET_SIZE+1) + planetSize]);
			}
			
		}
	}
	
	
	/**
	 * Store value in hashmap, so we can more easily retrieve name of bot for a set of environment characteristics
	 * 
	 * @param neutralPlanets
	 * @param planetsSize
	 * @param botName
	 */
	public static void put(int neutralPlanets, int planetsSize, String botName) {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		keys.add(neutralPlanets);
		keys.add(planetsSize);
		map.put(keys, botName);
	}
	
	/**
	 * Get the bot name for these environment characteristics
	 * 
	 * @param neutralPlanets
	 * @param planetsSize
	 * @return
	 */
	public static String get(int neutralPlanets, int planetsSize) {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		keys.add(neutralPlanets);
		keys.add(planetsSize);
		return map.get(keys);
	}
	
	public static String get(int neutralPlanets, int enemyPlanets, int friendlyPlanets, int planetsSize){
		/* Special Cases */
		if((enemyPlanets == 1) && (neutralPlanets == 0)){
			return "BullyBot"; /* KillShot */
		}
		if((enemyPlanets < 3*friendlyPlanets) && neutralPlanets == 0){
			return "Aggressor"; /* Finish him off */
		}
		
		/* NO MORE NEUTRAL PLANETS */
		if(neutralPlanets == 0){
			/* (E > F) && (F > N) */
			if(enemyPlanets > friendlyPlanets){
				return "Caesar"; /* My Position is.. Mweh */
			}else if(friendlyPlanets > enemyPlanets){
				return "Aggressor"; /* My position is strongest, so attack enemy */
			}else if(friendlyPlanets == enemyPlanets){
				return "BullyBot"; /* Let environment Decide */
			}
		}else{
			if(enemyPlanets > friendlyPlanets){
				if(friendlyPlanets > neutralPlanets){
					return "Caesar"; /* Mweh */
				}else if(friendlyPlanets < neutralPlanets){
					return "Napoleon"; /* Really Weak */
				}else if(friendlyPlanets == neutralPlanets){
					return "Expander"; /* Weak position, should expand */
				}
			}else if(friendlyPlanets > enemyPlanets){
				if(enemyPlanets > neutralPlanets){
					return "Caesar"; /* Strong Position */
				}else if(enemyPlanets < neutralPlanets){
					return "Expander"; /* Really Strong */
				}else if(enemyPlanets == neutralPlanets){
					return get(neutralPlanets, planetsSize); /* Mweh */
				}else{
					return get(neutralPlanets, planetsSize);
				}
			}else if(friendlyPlanets == enemyPlanets){
				if(friendlyPlanets > neutralPlanets){
					return "Napoleon"; /* Try to Expand */
				}else if(friendlyPlanets < neutralPlanets){
					return "Expander"; /* Expand Fast! */
				}else{
					return "Aggressor";
				}
			}
		}
		return null;
	}
	
}
