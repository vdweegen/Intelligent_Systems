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
 *      Usage: Holds the 'Queue' support class */

public class Queue {
 Vector<Object> queue = new Vector<Object>();

 /* Enqueues an object */
 void add(Object object) {
  queue.add(object);
 }

 /* Dequeues an object */
 Object get() {
  return queue.remove(0);
 }

 /* Enqueues collection of objects */
 void addAll(Collection<?> collection) {
  queue.addAll(collection);
 }

 /* Test if queue is empty */
 boolean isEmpty() {
  return queue.isEmpty();
 }
}