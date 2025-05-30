/* This program contains 2 parts: (1) and (2)
   YOUR TASK IS TO COMPLETE THE PART  (2)  ONLY
 */
//(1)==============================================================
import java.util.*;
import java.io.*;

public class MyList {

    Node head, tail;

    MyList() {
        head = tail = null;
    }

    boolean isEmpty() {
        return (head == null);
    }

    void clear() {
        head = tail = null;
    }

    void fvisit(Node p, RandomAccessFile f) throws Exception {
        if (p != null) {
            f.writeBytes(p.info + " ");
        }
    }

    void ftraverse(RandomAccessFile f) throws Exception {
        Node p = head;
        while (p != null) {
            fvisit(p, f); // You will use this statement to write information of the node p to the file
            p = p.next;
        }
        f.writeBytes("\r\n");
    }

    void loadData(int k) { //do not edit this function
        String[] a = Lib.readLineToStrArray("data.txt", k);
        int[] b = Lib.readLineToIntArray("data.txt", k + 1);
        int[] c = Lib.readLineToIntArray("data.txt", k + 2);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            addLast(a[i], b[i], c[i]);
        }
    }

//===========================================================================
//(2)===YOU CAN EDIT OR EVEN ADD NEW FUNCTIONS IN THE FOLLOWING PART========
/* 
   Khong su dung tieng Viet co dau de viet ghi chu.
   Neu dung khi chay truc tiep se bao loi va nhan 0 diem
     */
    void addLast(String xPlace, int xDepth, int xType) {
        //You should write here appropriate statements to complete this function.
        if (xPlace.charAt(0) == 'B') {
            return;
        } else {
            addLast(new Box(xPlace, xDepth, xType));
        }
    }

    void addLast(Box x) {
        Node p = new Node(x);
        if (isEmpty()) {
            head = tail = p;
            return;
        }
        tail.next = p;
        tail = p;
    }

    void addFirst(Box x){
        Node p=new Node(x);
        if(isEmpty())
            return;
        else{
            p.next=head;
            head=p;
        }
    }

    void insertAfter(Node q, Box x){
        Node p=new Node(x);
        if(isEmpty())
            return;
        p.next=q.next;
        q.next=p;
        if(q==tail){
            tail=p;
        }
    }

    //You do not need to edit this function. Your task is to complete the addLast function above only.
    void f1() throws Exception {
        clear();
        loadData(1);
        String fname = "f1.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        ftraverse(f);
        f.close();
    }

//==================================================================
    void f2() throws Exception {
        clear();
        loadData(5);
        String fname = "f2.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        ftraverse(f);
        Box x, y;
        x = new Box("X", 1, 2);
        y = new Box("Y", 3, 4);
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
          Your task is to insert statements here, just after this comment,
          to complete the question in the exam paper.*/
        // add x 1st (head)
        // add y 4th
        addFirst(x);
        Node p=head;
        int count=1;
        while(p!=null && count<3){
            p=p.next;
            count++;
        }
        if(p!=null){
            insertAfter(p, y); // add y after node 3 (y is 4th elements)
        }
        //------------------------------------------------------------------------------------
        ftraverse(f);
        f.close();
    }

//==================================================================
    void f3() throws Exception {
        clear();
        loadData(9);
        String fname = "f3.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        ftraverse(f);
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
           Your task is to insert statements here, just after this comment,
           to complete the question in the exam paper.*/
        Node p=head;
        int maxDepth=p.info.depth;

        // find depth maximum depth 
        while(p!=null){
            if(p.info.depth>maxDepth){
                maxDepth=p.info.depth;
            }
            p=p.next;
        }

        // find 1st node have depth==maxDepth & change place
        p=head;
        while(p!=null){
            if(p.info.depth==maxDepth){
                p.info.place="XX";
                break; // Only change 1st node acp condition
            }
            p=p.next;
        }
        //------------------------------------------------------------------------------------
        ftraverse(f);
        f.close();
    }

//==================================================================
    void f4() throws Exception {
        clear();
        loadData(13);
        String fname = "f4.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        ftraverse(f);
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
           Your task is to insert statements here, just after this comment,
           to complete the question in the exam paper.*/
        Node p=head;
        for(int i=0; i<2; i++){
            p=p.next; // p at pos 2
        }

        // save 4 Box (info) into array
        Box[] boxes=new Box[4];
        Node tmp=p;
        for(int i=0; i<4; i++){
            boxes[i]=new Box(tmp.info.place, tmp.info.depth, tmp.info.type); // creat clone
            tmp=tmp.next;
        }

        // sort depth
        java.util.Arrays.sort(boxes, (a, b) -> a.depth - b.depth);

        // retail info for 4 node
        tmp=p;
        for(int i=0; i<4; i++){
            tmp.info=boxes[i];
            tmp=tmp.next;
        }
        //------------------------------------------------------------------------------------
        ftraverse(f);
        f.close();
    }
}
