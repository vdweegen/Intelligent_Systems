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
 *            'Move' Helper Class
 *             
 */

public class Move {
  /* Some private variables, get/set with GetSetters */
  int src; int dst; double alpha; int score;
  
  /* constructor */
  public Move(int _src, int _dst) {
    src = _src; dst = _dst;
  }
  
  /* constructor 2 */
  public Move(int _src, int _dst, double _alpha){
    src = _src; dst = _dst; alpha = _alpha;
  }
  
  public Move(int _src, int _dst, int _score){
   src = _src;
    dst = _dst; score = _score;
  }
}
