package Util;

import java.util.LinkedList;

public class FIFOQueue<E> extends LinkedList<E> implements Queue<E> {
  private static final long serialVersionUID = 1;

  public FIFOQueue() {
    super();
  }

  public boolean isEmpty() {
    return 0 == size();
  }

  public E pop() {
    return poll();
  }

  public void push(E element) {
    this.addLast(element);
  }

  public Queue<E> insert(E element) {
    if (offer(element)) {
      return this;
    }
    return null;
  }
}