/* INTELLIGENT SYSTEMS 2014-2015
 * Planet Wars Assignment
 * Deadline: Thursday 29 Januari 23:59
 * 
 * Name(s) :	Cas van der Weegen [2566388]
 *           	Meera Dasai [2056631]
 *           	Janita Nabibaks [2516913]
 * 
 * Group(s) :	Final Project Group 26
 *            Practicum Group D
 *            Working Group 2
 *             
 *             	'Move' helper class
 *             
 */
public class Move {
	/* Some private variables, get/set with GetSetters */
	private int src;
	private int dst;
	private int score;
	
	/* constructor */
	public Move(int src, int dst) {
		this.setSrc(src);
		this.setDst(dst);
		this.setScore(-1);
	}
	
	/* constructor 2 */
	public Move(int src, int dst, int score){
		this.setSrc(src);
		this.setDst(dst);
		this.setScore(score);
	}
	
	/* score Getter */
	public int getScore(){
		return score;
	}

	/* score Setter */
	public void setScore(int score){
		this.score = score;
	}
	
	/* source Getter */
	public int getSrc() {
		return src;
	}

	/* source Setter */
	public void setSrc(int src) {
		this.src = src;
	}

	/* dst Getter */
	public int getDst() {
		return dst;
	}

	/* dst Setter */
	public void setDst(int dst) {
		this.dst = dst;
	}
}