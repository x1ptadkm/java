/* This program contains 2 parts: (1) and (2)
   YOUR TASK IS TO COMPLETE THE PART  (2)  ONLY
 */
//(1)==============================================================
import java.io.*;
import java.util.*;

class BSTree {
    
    Node root;
    
    BSTree() {
        root = null;
    }
    
    boolean isEmpty() {
        return (root == null);
    }
    
    void clear() {
        root = null;
    }
    
    void fvisit(Node p, RandomAccessFile f) throws Exception {
        if (p != null) {
            f.writeBytes(p.info + " ");
        }
    }
    
    void preOrder(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        fvisit(p, f);
        preOrder(p.left, f);
        preOrder(p.right, f);
    }
    
    void inOrder(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        inOrder(p.left, f);
        fvisit(p, f);
        inOrder(p.right, f);
    }
    
    void postOrder(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        postOrder(p.left, f);
        postOrder(p.right, f);
        fvisit(p, f);
    }
    
    void breadth(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        Queue q = new Queue();
        q.enqueue(p);
        Node r;
        while (!q.isEmpty()) {
            r = q.dequeue();
            fvisit(r, f);
            if (r.left != null) {
                q.enqueue(r.left);
            }
            if (r.right != null) {
                q.enqueue(r.right);
            }
        }
    }
    
    void loadData(int k) //do not edit this function
    {
        String[] a = Lib.readLineToStrArray("data.txt", k);
        int[] b = Lib.readLineToIntArray("data.txt", k + 1);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            insert(a[i], b[i]);
        }
    }

//===========================================================================
//(2)===YOU CAN EDIT OR EVEN ADD NEW FUNCTIONS IN THE FOLLOWING PART========
//===========================================================================
    void insert(String xOwner, int xPrice) {//You should insert here statements to complete this function
        if (xOwner.startsWith("B") || xPrice > 100) {
            return;
        }else {
            insert2(new Car(xOwner, xPrice));
        }
    }
    
    void insert2(Car x) {
        if (isEmpty()) {
            root = new Node(x);
            return;
        }
        Node father, child; // father la cha cua p
        father = null;
        child = root;

        while (child != null) {
            if (child.info.price == x.price) {
                System.out.println("The key " + x + "already exists, no insertion");
                return;
            }
            father = child;
            if (x.price < (child.info.price)) {
                child = child.left;
            } else {
                child = child.right;
            }
        }

        if (x.price < (father.info.price) ) {
            father.left = new Node(x);
        } else {
            father.right = new Node(x);
        }

    }
    
   
    
    void f1() throws Exception {/* You do not need to edit this function. Your task is to complete insert  function
        above only.
         */
        clear();
        loadData(1);
        String fname = "f1.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        preOrder(root, f);
        f.writeBytes("\r\n");
        inOrder(root, f);
        f.writeBytes("\r\n");
        f.close();
    }

//=============================================================
    void preOrder2(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        //logic
        if (p.info.price >= 3 && p.info.price <= 5) {
            fvisit(p, f);
        }
        //logic
        preOrder2(p.left, f);
        preOrder2(p.right, f);
    }
    
    void f2() throws Exception {
        clear();
        loadData(4);
        String fname = "f2.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        preOrder(root, f);
        f.writeBytes("\r\n");
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
       Your task is to insert statements here, just after this comment,
       to complete the question in the exam paper.*/
        preOrder2(root, f);
        //------------------------------------------------------------------------------------
        f.writeBytes("\r\n");
        f.close();
    }

// f.writeBytes(" k = " + k + "\r\n");
//=============================================================
    void deleByCopy(Car x) {
        if (isEmpty()) {
            return;
        }

        Node father, child; // father is father of child
        father = null;
        child = root;
        while (child != null) {
            if (child.info.price == x.price) {
                break;
            }
            father = child;
            if (x.price < (child.info.price) ) {
                child = child.left;
            } else {
                child = child.right;
            }
        }
        if (child == null) {
            return; // not found
        }

        // in here, child = nut need to remove
        // father is father of child
        // Case 1: child is leaf
        if (child.left == null && child.right == null) {
            if (father == null) { // child is root
                root = null;
            } else {
                if (child == father.left) {
                    father.left = null;
                } else {
                    father.right = null;
                }
            }
            return;
        }
        // Case 2: child have 1 child at left
        if (child.left != null && child.right == null) {
            if (father == null) { // child == root
                root = child.left;
            } else {
                if (child == father.left) {
                    father.left = child.left;
                } else {
                    father.right = child.right;
                }
            }
        }

        //// Case 3: child have 1 child at right
        if (child.left == null && child.right != null) {
            if (father == null) { // child la root
                root = child.right;
            } else {
                if (child == father.left) {
                    father.left = child.right;
                } else {
                    father.right = child.right;
                }

            }
            return;
        }

        // 1:33:00
        // Case 4: child have 2 children
        if (child.left != null && child.right != null) {
            // find most right node of left side of child node
            Node qNode = child.left;
            Node frp, rp; // frp la cha cua rp
            frp = null;
            rp = qNode;
            while (rp.right != null) {
                frp = rp;
                rp = rp.right;
            }
            // rp la node phai nhat

            // copy gia tri cua rp vao node can xoa(p)
            child.info = rp.info;

            // xoa rp di
            if (frp == null) {
                child.left = qNode.left;
            } else {
                frp.right = rp.left;
            }

        }

    }
    void breadthDelete(Node p) {
        if (p == null) {
            return;
        }
        //copy 
        Queue queue = new Queue();
        queue.enqueue(p);
        Node r;
        while (!queue.isEmpty()) {
            r = queue.dequeue();
            //logic....
            if (r.left != null && r.right != null && r.info.price <7) {
                deleByCopy(r.info);
                break;
            }

            //logic
            if (r.left != null) {
                queue.enqueue(r.left);
            }
            if (r.right != null) {
                queue.enqueue(r.right);
            }
        }
    }
    void f3() throws Exception {
        clear();
        loadData(7);
        String fname = "f3.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        breadth(root, f);
        f.writeBytes("\r\n");
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
       Your task is to insert statements here, just after this comment,
       to complete the question in the exam paper.*/
        breadthDelete(root);
        //------------------------------------------------------------------------------------
        breadth(root, f);
        f.writeBytes("\r\n");
        f.close();
    }
//=============================================================
    Node rotateRight(Node p) {
        if (p == null || p.left == null) {
            return (p);
        }
        Node q = p.left;
        p.left = q.right;
        q.right = p;
        return (q);
    }
    void breathRotate() {
        Node x = root;
        Node fatherX = null;
        Queue q = new Queue();
        q.enqueue(root);
        Node r;
        while (!q.isEmpty()) {
            r = q.dequeue();
            //logic
            if (r.left != null && r.info.price < 7) {
                x = r;
                break;
            }
            //logic
            if (r.left != null) {
                q.enqueue(r.left);
            }
            if (r.right != null) {
                q.enqueue(r.right);
            }
            fatherX = r;
        }

        boolean isLeft = false;
        if (fatherX.left == x) {
            isLeft = true;
        }

        x = rotateRight(x);

        if (isLeft) {
            fatherX.left = x;
        } else {
            fatherX.right = x;
        }
    }
    
    void f4() throws Exception {
        clear();
        loadData(10);
        String fname = "f4.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        breadth(root, f);
        f.writeBytes("\r\n");
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
       Your task is to insert statements here, just after this comment,
       to complete the question in the exam paper.*/
        breathRotate();
        //------------------------------------------------------------------------------------
        breadth(root, f);
        f.writeBytes("\r\n");
        f.close();
    }
    
}
