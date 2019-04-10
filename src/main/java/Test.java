import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
       BTree tree = new BTree<Integer>(4);
        for (int i = 0; i < 10000; i++) {
            tree.add(i);
        }
        System.out.println("tree.add(5);");
        tree.add(5);
        System.out.println("tree.add(5);");
        tree.add(5);


    }
}
