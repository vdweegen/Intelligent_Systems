import java.util.*;
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
 *      Usage: Holds the 'Tree' support class
 */

public class Tree {
	int id;
	int score;
	int parent;
	Vector<Tree> children;
	
	/** Constructor to create a node containing the given value. */
	Tree(int parent, int id, int score) {   
	    this.parent = parent;
     this.id = id;
	    this.score = score;
	    children = new Vector<Tree>();
	}
	
	/** Add a node containing the given value as a child of this node. */
	Tree add(int id, int score) {
	    return add(new Tree(this.id, id, score));
	}
	
	/** Adds the given tree as a child of this node. */
	Tree add(Tree tree) {
	    children.add(tree);
	    return tree;
	}
}