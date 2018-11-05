import java.util.NoSuchElementException;

/**
 * Your implementation of a linked deque.
 *
 * @author Le-En Huang
 * @userid ehuang42
 * @GTID 903303142
 * @version 1.0
 */
public class LinkedDeque<T> {
    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't insert empty data.");
        } else if (size == 0) {
            head = new LinkedNode<>(data);
            tail = head;
            size++;
        } else {
            LinkedNode<T> newNode = new LinkedNode<>(data);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }

    }

    /**
     * Adds the data to the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't insert empty data.");
        } else if (size == 0) {
            addFirst(data);
        } else {
            LinkedNode<T> newNode = new LinkedNode<>(data);
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes the data at the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        } else if (size == 1) {
            LinkedNode<T> temp = head;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        } else {
            LinkedNode<T> oldHead = head;
            LinkedNode<T> temp = head.getNext();
            head.setNext(null);
            temp.setPrevious(null);
            head = temp;
            size--;
            return oldHead.getData();
        }
    }

    /**
     * Removes the data at the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        } else if (size == 1) {
            return removeFirst();
        } else {
            LinkedNode<T> oldTail = tail;
            LinkedNode<T> temp = tail.getPrevious();
            tail.setPrevious(null);
            temp.setNext(null);
            tail = temp;
            size--;
            return oldTail.getData();
        }
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     * 
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}