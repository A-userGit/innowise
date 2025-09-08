package org.innowise.javacore.collection;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for linked list task")
public class CustomLinkedListUnitTest {

  @DisplayName("Test for size function")
  @ParameterizedTest
  @ValueSource(ints = {0, 5, 10})
  public void testSize(int amount) {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i < amount; i++) {
      customLinkedList.addFirst(i);
    }
    assertEquals(amount, customLinkedList.size());
  }

  @Test
  @DisplayName("Test for addFirst and getFirst functions")
  public void testAddGetFirst() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i < 5; i++) {
      customLinkedList.addFirst(i);
    }
    customLinkedList.addFirst(20);
    assertEquals(20, customLinkedList.getFirst());
  }

  @Test
  @DisplayName("Test for addFirst to empty List function")
  public void testAddFirstToEmpty() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    customLinkedList.addFirst(20);
    assertEquals(20, customLinkedList.getFirst());
  }

  @Test
  @DisplayName("Test for addLast and getLast functions")
  public void testAddGetLast() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i < 5; i++) {
      customLinkedList.addFirst(i);
    }
    customLinkedList.addLast(20);
    assertEquals(20, customLinkedList.getLast());
  }

  @Test
  @DisplayName("Test for get function")
  public void testGet() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i <= 5; i++) {
      customLinkedList.addFirst(i);
    }
    assertEquals(4, customLinkedList.get(1));
    assertEquals(3, customLinkedList.get(2));
    assertEquals(2, customLinkedList.get(3));
  }

  @Test
  @DisplayName("Test for get function with out of bounds value")
  public void testGetOutOfBounds() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i <= 5; i++) {
      customLinkedList.addFirst(i);
    }
    assertThrows(IndexOutOfBoundsException.class, () -> customLinkedList.get(6));
    CustomLinkedList<Integer> emptyList = new CustomLinkedList<>();
    assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(0));
  }

  @Test
  @DisplayName("Test for add by index function")
  public void testAdd() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i <= 5; i++) {
      customLinkedList.addFirst(i);
    }
    customLinkedList.add(3, 56);
    assertEquals(56, customLinkedList.get(3));
  }

  @Test
  @DisplayName("Test for remove function")
  public void testRemove() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i <= 5; i++) {
      customLinkedList.addFirst(i);
    }
    customLinkedList.remove(2);
    assertEquals(2, customLinkedList.get(2));
  }

  @Test
  @DisplayName("Test for removeFirst and RemoveLast functions")
  public void testRemoveFirstLast() {
    CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
    for (int i = 0; i <= 5; i++) {
      customLinkedList.addFirst(i);
    }
    customLinkedList.removeFirst();
    customLinkedList.removeLast();
    assertEquals(4, customLinkedList.getFirst());
    assertEquals(1, customLinkedList.getLast());
    assertEquals(4, customLinkedList.size());
  }
}
