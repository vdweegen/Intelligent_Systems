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
 *      Usage: Holds the 'Stack' support class */

public class Stack {
    Vector<Object> stack = new Vector<Object>();
    
    /* Push Object onto the Stack */
    void push(Object object) {
      stack.add(object);
    }
    
    /* Pop Object from the Stack */
    Object pop() {
      return stack.remove(stack.size()-1);
    }
    
    /* Push collection of Objects */
    void addAll(Collection<?> collection) {
      stack.addAll(collection);
    }
    
    /* Test if stack is empty */
    boolean isEmpty() {
        return stack.isEmpty();
    }
}