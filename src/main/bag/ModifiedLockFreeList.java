package edu.vt.ece.hw5.bag;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class ModifiedLockFreeList<T> {
    Node head;
    Node tail;

    public ModifiedLockFreeList() {
        this.head = new Node(Integer.MIN_VALUE);
        this.tail = new Node(Integer.MAX_VALUE);
        while (!head.next.compareAndSet(null, tail, false, false)) ;
    }

    public boolean add(T item, int key) {
        //int key = item.hashCode();
        while (true) {
            Window window = find(head, key);
            Node pred = window.pred, curr = window.curr;
            Node node = new Node(item,key);
            node.next = new AtomicMarkableReference<Node>(curr, false);
            if (pred.next.compareAndSet(curr, node, false, false))
            {
                                return true;
            }
        }
    }

    public boolean remove(int key) {
        //int key = item.hashCode();
        boolean snip;
        while (true) {
            Window window = find(head, key);
            Node pred = window.pred, curr = window.curr;
            if (curr.key != key) {
                return false;
            } else {
                Node succ = curr.next.getReference();
                snip = curr.next.attemptMark(succ, true);
                if (!snip)
                    continue;
                pred.next.compareAndSet(curr, succ, false, false);
                return true;
            }
        }
    }

    public T contains(int key) {
        //int key = item.hashCode();
        // find predecessor and curren entries
        Window window = find(head, key);
        Node pred = window.pred, curr = window.curr;
        if (curr.key == key)
        {
            return curr.item;
        }
        return null;
    }

    private class Node {
        T item;
        int key;
        AtomicMarkableReference<Node> next;

        Node(T item, int key) {      // usual constructor
            this.item = item;
            this.key = key;
            this.next = new AtomicMarkableReference<Node>(null, false);
        }

        Node(int key) { // sentinel constructor
            this.item = null;
            this.key = key;
            this.next = new AtomicMarkableReference<Node>(null, false);
        }
    }

    class Window {
        public Node pred;
        public Node curr;

        Window(Node pred, Node curr) {
            this.pred = pred;
            this.curr = curr;
        }
    }

    public Window find(Node head, int key) {
        Node pred = null, curr = null, succ = null;
        boolean[] marked = {false}; // is curr marked?
        boolean snip;
        retry:
        while (true) {
            pred = head;
            curr = pred.next.getReference();
            while (true) {
                succ = curr.next.get(marked);
                while (marked[0]) {           // replace curr if marked
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if (!snip) continue retry;
                    curr = pred.next.getReference();
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key)
                    return new Window(pred, curr);
                pred = curr;
                curr = succ;
            }
        }
    }
}
