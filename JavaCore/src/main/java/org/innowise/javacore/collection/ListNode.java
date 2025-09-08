package org.innowise.javacore.collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListNode<T> {

  private T data;
  private ListNode<T> previous;
  private ListNode<T> next;

  public ListNode(T element) {
    data = element;
  }


}
