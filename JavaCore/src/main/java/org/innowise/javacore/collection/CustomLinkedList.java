package org.innowise.javacore.collection;

public class CustomLinkedList<T> {

  private ListNode<T> head;

  public long size() {
    long size = 0;
    ListNode<T> current = head;
    while (current != null) {
      size++;
      current = current.getNext();
    }
    return size;
  }

  public void addFirst(T element) {
    if (head == null) {
      head = new ListNode<>(element);
    } else {
      ListNode<T> newHead = new ListNode<>(element);
      newHead.setNext(head);
      head.setPrevious(newHead);
      head = newHead;
    }
  }

  public void addLast(T element) {
    ListNode<T> current = head;
    ListNode<T> previous = null;
    while (current != null) {
      previous = current.getPrevious();
      current = current.getNext();
    }
    ListNode<T> newNode = new ListNode<>(element);
    if (previous != null) {
      previous.setNext(newNode);
      newNode.setPrevious(previous);
    }
  }

  public void add(long index, T element) {
    ListNode<T> newNode = new ListNode<>(element);
    if (index == 0 && head != null) {
      newNode.setNext(head);
      head.setPrevious(newNode);
      head = newNode;
      return;
    }
    ListNode<T> nodeAtIndex = getNode(index);
    newNode.setPrevious(nodeAtIndex.getPrevious());
    if (nodeAtIndex.getPrevious() != null) {
      nodeAtIndex.getPrevious().setNext(newNode);
    }
    newNode.setNext(nodeAtIndex);
    nodeAtIndex.setPrevious(newNode);

  }

  public T getFirst() {
    if (head == null) {
      return null;
    }
    return head.getData();
  }

  public T getLast() {
    ListNode<T> lastNode = getLastNode();
    if (lastNode != null) {
      return lastNode.getData();
    }
    return null;
  }

  public T get(long index) {
    return getNode(index).getData();
  }

  public void removeFirst() {
    if (head == null) {
      return;
    }
    head = head.getNext();
    head.setPrevious(null);
  }

  public void removeLast() {
    ListNode<T> lastNode = getLastNode();
    if (lastNode == null) {
      return;
    }
    ListNode<T> previous = lastNode.getPrevious();
    if (previous != null) {
      previous.setNext(null);
    }
    lastNode.setPrevious(null);
  }

  public void remove(long index) {
    ListNode<T> node = getNode(index);
    ListNode<T> previous = node.getPrevious();
    ListNode<T> next = node.getNext();
    if (previous != null) {
      previous.setNext(next);
      node.setPrevious(null);
    }
    if (next != null) {
      next.setPrevious(previous);
      node.setNext(null);
    }
  }

  private ListNode<T> getNode(long index) {
    if (head == null) {
      throw new IndexOutOfBoundsException(index);
    }
    ListNode<T> current = head;
    long pos = 0;
    while (current != null && pos < index) {
      current = current.getNext();
      pos++;
    }
    if (pos < index || current == null) {
      throw new IndexOutOfBoundsException(index);
    }
    return current;
  }

  private ListNode<T> getLastNode() {
    if (head == null) {
      return null;
    }
    ListNode<T> current = head;
    while (current.getNext() != null) {
      current = current.getNext();
    }
    return current;
  }
}
