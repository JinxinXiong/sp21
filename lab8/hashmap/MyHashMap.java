package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int M;
    private double loadFactor;
    private Set<K> set = new HashSet<>();
    private int N;

    /** Constructors */
    public MyHashMap() {
        this.M = 16;
        this.loadFactor = 0.75;

        buckets = createTable(this.M);
        for (int i = 0; i < this.M; i += 1) {
            buckets[i] = createBucket();
        }
        this.N = 0;
        this.set.clear();
    }

    public MyHashMap(int initialSize) {
        this.M = initialSize;
        this.loadFactor = 0.75;

        buckets = createTable(this.M);
        this.N = 0;
        this.set.clear();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.M = initialSize;
        this.loadFactor = maxLoad;

        buckets = createTable(this.M);
        this.N = 0;
        this.set.clear();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        this.N = 0;
        this.set.clear();
        this.buckets = createTable(this.M);
    }

    @Override
    public boolean containsKey(K key) {
        return set.contains(key);
    }

    @Override
    public V get(K key) {
        if (! containsKey(key)) {
            return null;
        }
        int hashcode = Math.floorMod(key.hashCode(), this.M);
        Collection<Node> bucket = buckets[hashcode];
        for (Node tmp: bucket) {
            if (key.equals(tmp.key)) {
                return tmp.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.N;
    }

    @Override
    public void put(K key, V value) {
        int hashcode = Math.floorMod(key.hashCode(), this.M);
        Collection<Node> bucket = buckets[hashcode];
        Node newNode = new Node(key, value);
        if (! containsKey(key)) {
            if (bucket == null) {
                Collection<Node> newBucket = createBucket();
                newBucket.add(newNode);
                buckets[hashcode] = (Collection<Node>) newBucket;
            } else {
                bucket.add(newNode);
            }
            this.set.add(key);
            this.N += 1;
        } else {
            for (Node tmp: bucket) {
                if (key.equals(tmp.key)) {
                    tmp.value = value;
                }
            }
        }
    }

    @Override
    public Set<K> keySet() {
        return set;
    }

    @Override
    public V remove(K key) {
        if (! containsKey(key)) {
            return null;
        }
        int hashcode = Math.floorMod(key.hashCode(), this.M);
        Collection<Node> bucket = buckets[hashcode];
        V retValue = null;
        for (Node tmp: bucket) {
            if (key.equals(tmp.key)) {
                retValue = tmp.value;
                bucket.remove(key);
                this.set.remove(key);
                this.N -= 1;
                return retValue;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
