package bstmap;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    private boolean containsKeyHelper(K key, Node p) {
        if (p == null) {
            return false;
        }
        if (key.compareTo(p.key) == 0) {
            return true;
        } else if (key.compareTo(p.key) < 0) {
            return containsKeyHelper(key, p.left);
        } else {
            return containsKeyHelper(key, p.right);
        }
    }

    @Override
    public boolean containsKey(K key) {
//        return containsKeyHelper(key, this.root);
        return set.contains(key);
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
//        throw new UnsupportedOperationException();
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
//        throw new UnsupportedOperationException();
        return getHelper(key, this.root);
    }


    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
//        throw new UnsupportedOperationException();
        if (p == null) {
            return new Node(key, value);
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    private Node search_by_key(K key) {
        Node p = this.root;
        while (p.key != key) {
            if (key.compareTo(p.key) < 0) {
                p = p.left;
            } else if (key.compareTo(p.key) > 0) {
                p = p.right;
            }
        }
        return p;
    }
    @Override
    public void put(K key, V value) {
        if (! containsKey(key)) {
            this.root = putHelper(key, value, this.root);
        } else {
            Node p = search_by_key(key);
            p.value = value;
        }
        set.add(key);
        this.size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
//        throw new UnsupportedOperationException();
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private Set<K> set = new HashSet<>();
    private void traverse(Node p) {
        if (p == null) {
            return;
        }
        set.add(p.key);
        traverse(p.left);
        traverse(p.right);
    }
    @Override
    public Set<K> keySet() {
//        throw new UnsupportedOperationException();
//        Set<K> set = new HashSet<>();
//        set.clear();
//        traverse(this.root);
//        return set;
        return set;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */

    // if the tree contains the key, find the parent of the key in the tree rooted by p
    private Node parent_of_key (K key, Node p, Node parent) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return parent;
        }

        if (key.compareTo(p.key) < 0) {
            return parent_of_key(key, p.left, p);
        } else {
            return parent_of_key(key, p.right, p);
        }
    }

    private Node most_right (Node p) {
        if (p == null) {
            return null;
        }
        if (p.right == null) {
            return p;
        } else {
            return most_right(p.right);
        }
    }

    private Node most_left (Node p) {
        if (p == null) {
            return null;
        }
        if (p.left == null) {
            return p;
        } else {
            return most_left(p.left);
        }
    }
    @Override
    public V remove(K key) {
//        throw new UnsupportedOperationException();
        if (!containsKey(key)) {
            return null;
        }

        Node p = search_by_key(key);
        V ret = p.value;
        Node parent = parent_of_key(key, this.root, null);
        Node left_child = p.left;
        Node right_child = p.right;
        // has no child
        if (left_child == null && right_child == null) {
            if (parent != null){
                if (parent.left == p) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                this.root = null;
            }
        }

        // has only one child
        if (left_child == null | right_child == null) {
            Node child;
            if (left_child != null) {
                child = left_child;
            } else {
                child = right_child;
            }

            if (parent != null) {
                if (parent.left == p) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
            } else {
                this.root = child;
            }
        }

        // has two child
        if (left_child != null && right_child != null) {
            Node new_root = most_right(p.left);

            remove(new_root.key);
            left_child = p.left;
            right_child = p.right;

            if (parent != null) { // p is the root
                if (parent.left == p) {
                    parent.left = new_root;
                } else {
                    parent.right = new_root;
                }
            } else {
                this.root = new_root;
            }
            new_root.left = left_child;
            new_root.right = right_child;
            set.add(new_root.key);
        }
        set.remove(p.key);
        size -= 1;
        return ret;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
//        throw new UnsupportedOperationException();
        Node p = search_by_key(key);
        if (p.value != value) {
            return null;
        } else {
            return remove(key);
        }
    }

    @Override
    public Iterator<K> iterator() {
        Set<K> set = keySet();
        return set.iterator();
    }


    public void printInOrder(Node p) {
        if (p == null) {
            return;
        }
        printInOrder(p.left);
        System.out.print(p.key + " ");
        printInOrder(p.right);
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        bstmap.put("zebra", 20);
        bstmap.put("aaaa", 0);
        bstmap.put("ab", 1);
        bstmap.put("fash", 2);

//        bstmap.remove("hello");
        System.out.println(bstmap.keySet());

        for (String s : bstmap) {
            System.out.println(s);
        }

        bstmap.printInOrder(bstmap.root);
    }
}
