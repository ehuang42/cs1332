import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author Le-En Huang
 * @userid ehuang42
 * @GTID 9033303142
 * @version 2.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data set is null.");
        }
        for (T el : data) {
            if (data == null) {
                throw new IllegalArgumentException("Some data is empty.");
            } else {
                add(el);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to be added
     * @throws IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can't be empty.");
        } else if (size == 0) {
            root = new BSTNode<>(data);
            size++;
        } else {
            root = addH(data, root);
        }
    }

    /**
     * Add data to the correct location in the tree with recursion.
     *
     * @param data the data to be added.
     * @param node the node pointer of traversal.
     * @return the node being passed in.
     */
    private BSTNode<T> addH(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(addH(data, node.getLeft()));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(addH(data, node.getRight()));
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can't be empty.");
        } else {
            BSTNode<T> dummy = new BSTNode<>(null);
            root = removeH(data, root, dummy);
            size--;
            return dummy.getData();
        }
    }

    /**
     * Helper method that removes the data from the tree with recursion.
     *
     * @param data the data to remove from the tree.
     * @param node the node pointer of traversal.
     * @param dummy the node that holds the data of the removed node.
     * @return the node being passed in
     * @throws NoSuchElementException if the data is not found in the tree
     */

    private BSTNode<T> removeH(T data, BSTNode<T> node, BSTNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("Data not found in the tree.");
        } else {
            if (node.getData().equals(data)) {
                dummy.setData(node.getData());
                if (node.getRight() == null && node.getLeft() == null) {
                    return null;
                } else if (node.getRight() == null) {
                    return node.getLeft();
                } else if (node.getLeft() == null) {
                    return node.getRight();
                } else {
                    BSTNode<T> temp = new BSTNode<>(node.getData());
                    node.setRight(succH(node.getRight(), temp));
                    node.setData(temp.getData());
                }
            } else if (data.compareTo(node.getData()) < 0) {
                node.setLeft(removeH(data, node.getLeft(), dummy));
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(removeH(data, node.getRight(), dummy));
            }
        }
        return node;
    }

    /**
     * Helper method that finds the successor node and stores its data
     * with recursion.
     *
     * @param node the node pointer of traversal.
     * @param temp the node that holds the data of the successor node.
     * @return the node being passed in
     * @throws java.util.NoSuchElementException if the data is not found
     * in the tree
     */
    private BSTNode<T> succH(BSTNode<T> node, BSTNode<T> temp) {
        if (node.getLeft() == null) {
            temp.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(succH(node.getLeft(), temp));
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can't be empty.");
        }
        return getH(data, root);
    }

    /**
     * Helper method that returns the data in the tree matching the parameter
     * passed in with recursion.
     *
     * @param data the data to search for in the tree.
     * @param node the node pointer for traversal.
     * @return a self call or data in the tree equal to the parameter.
     * @throws java.util.NoSuchElementException if the data is not found.
     */
    private T getH(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("The data isn't found.");
        } else if (node.getData().equals(data)) {
            return node.getData();
        } else {
            if (node.getData().compareTo(data) > 0) {
                return getH(data, node.getLeft());
            } else {
                return getH(data, node.getRight());
            }
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     * <p>
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can't be empty.");
        }
        return searchH(data, root);
    }

    /**
     * Helper method that returns whether or not data equivalent to the given
     * parameter is contained within the tree.
     *
     * @param data the data to search for in the tree.
     * @param node the node pointer of the traversal
     * @return whether or not the parameter is contained within the tree.
     */
    private boolean searchH(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        } else if (node.getData().equals(data)) {
            return true;
        } else {
            if (node.getData().compareTo(data) > 0) {
                return searchH(data, node.getLeft());
            } else {
                return searchH(data, node.getRight());
            }
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {

        List<T> lst = new ArrayList<>();
        preorderH(root, lst);
        return lst;
    }

    /**
     * Helper method for preorder traversal.
     *
     * @param node the node pointer for traversal.
     * @param lst the list that stores the traversed data.
     * @return a list of preorder traversal of the tree
     */
    private void preorderH(BSTNode<T> node, List<T> lst) {
        if (node != null) {
            lst.add(node.getData());
            preorderH(node.getLeft(), lst);
            preorderH(node.getRight(), lst);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> lst = new ArrayList<>();
        return inorderH(root, lst);
    }

    /**
     * Helper method for inorder traversal.
     *
     * @param node the node pointer of the traversal.
     * @param lst the list that stores the traversed data.
     * @return a list of inorder traversal of the tree
     */
    private List<T> inorderH(BSTNode<T> node, List<T> lst) {
        if (node != null) {
            inorderH(node.getLeft(), lst);
            lst.add(node.getData());
            inorderH(node.getRight(), lst);
        }
        return lst;
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> lst = new ArrayList<>();
        return postorderH(root, lst);

    }

    /**
     * Helper method for post traversal.
     *
     * @param node the node pointer of the traversal.
     * @param lst the list that stores the traversed data.
     * @return a list of postorder traversal of the tree.
     */
    private List<T> postorderH(BSTNode<T> node, List<T> lst) {
        if (node != null) {
            postorderH(node.getLeft(), lst);
            postorderH(node.getRight(), lst);
            lst.add(node.getData());
        }
        return lst;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     * <p>
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> myQueue = new LinkedList<>();
        List<T> mylst = new ArrayList<>();
        return levelorderH(root, myQueue, mylst);
    }

    /**
     * Helps generate a level-order traversal of the tree.
     *
     * @param node the node pointer of the traversal.
     * @param myQueue the linked list that temporarily holds data.
     * @param mylst the list that contains the traversed data.
     * @return a list of level order traversal of the tree.
     */
    private List<T> levelorderH(BSTNode<T> node,
                                Queue<BSTNode<T>> myQueue, List<T> mylst) {
        if (node == root) {
            myQueue.add(root);
        }

        while (!myQueue.isEmpty()) {
            BSTNode<T> el = myQueue.remove();
            if (el != null) {
                mylst.add(el.getData());
                myQueue.add(el.getLeft());
                myQueue.add(el.getRight());
            }
        }
        return mylst;
    }

    /**
     * Clears the tree.
     * <p>
     * Should run in O(1).
     */
    public void clear() {
        if (size != 0) {
            root.setLeft(null);
            root.setRight(null);
            root = null;
            size = 0;
        }
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     * <p>
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return max(heightH(root.getLeft()), heightH(root.getRight())) + 1;
    }

    /**
     * Helps calculate and return the height of the root of the tree.
     *
     * @param node the node pointer of the traversal
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int heightH(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else if (node.getRight() == null && node.getLeft() == null) {
            return 0;
        } else if (node.getLeft() == null) {
            return heightH(node.getRight()) + 1;
        } else if (node.getRight() == null) {
            return heightH(node.getLeft()) + 1;
        } else {
            return max(heightH(node.getLeft()), heightH(node.getRight())) + 1;
        }
    }

    /**
     * Helps calculate the bigger int of the two.
     *
     * @param a the int passed in for comparison.
     * @param b the int passed in for comparison.
     * @return the greater int of the two
     */
    private int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
