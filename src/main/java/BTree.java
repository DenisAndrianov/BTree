

public class BTree<T extends Comparable> {
    final int M;
    Node root;
    BTree(int m){
        this.M = m;
        root = new Node();
    }


    private class Point {
        T value;
        Node left;
        Node right;
        Point(T value) {
            this.value = value;
        }
        void setLinks(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

    }


    public class Node {
        int capacity = M;
        int size = 0;
        private Object[] arr = new Object[capacity];
        Node parent = null;
        boolean lower = true;
        void removeHalf ()   {
            for (int i = (int)capacity/2; i < capacity; i++) {
                arr[i] = null;
                size--;
            }
        }
        Node getLeft (int index) {
            Node left = ((Point)arr[index]).left;
            return left;
        }
        Node getRight (int index) {
            Node right = ((Point)arr[index]).right;
            return right;
        }
        T getValue(int index)   {
            T value = ((Point) arr[index]).value;
            return value;
        }
        public void print ()  {
            for (int i = 0; i < size; i++) {
                System.out.print(" ["+getValue(i)+"] ");
            }
            if (root == this)   {
                System.out.println("root");
            }   else    {
                System.out.println();
            }
        }
        Point getPoint (int index)  {
            return (Point)arr[index];
        }
        Node (Point p)  {
            parent = null;
            arr[size] = p;
            size++;
            lower = false; //false так как создается с помощью дробления и передается на уровень вверх со средним значением разбитого нода.
        }
        Node(Node node) {
            parent = node.parent;
            for (int i = 0; i <(int)capacity/2; i++) {
                arr[i]=node.arr[capacity-1-i];
                size++;
                sort();
            }

        }
        Node(){}
        void add(Point p) {
            arr[size] = p;
            size++;
            sort();

        }
        void add(T value)   {
            arr[size] = new Point(value);
            size++;
            sort();
        }
        void sort () { //bubblesort
            for (int i = size-1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (getPoint(j).value.compareTo(getPoint(j+1).value) == 1)  {
                        Object tmp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = tmp;
                    }
                }
            }
            for (int i = 0; i < size-1; i++) {
                getPoint(i+1).left=getPoint(i).right;
            }
        }
    }

    public void add(T value)  {
        add (value, root);
    }

    private void add (T value, Node node)   {
        node.print();
        if (node.lower) {
            if (node.size == node.capacity) {
                broke(node);
                add(value);
            }   else    {
                for (int i = 0; i < node.size; i++) {
                    if (value.compareTo(node.getValue(i)) == 0) {
                        System.out.println("Значение уже есть в дереве");
                        return;
                    }
                }
                node.add(value);
            }
        }   else    {
            for (int i = 0; i < node.size; i++) {
                if (value.compareTo(node.getValue(i)) == -1)    {
                    add(value, node.getLeft(i));
                    break;
                }
                if (i == node.size - 1) {
                    add(value, node.getRight(i));
                    break;
                }
            }
        }
    }

    private void broke(Node node) {
        if (node.parent == null)    {
            Node buf = new Node (node.getPoint((int)node.capacity/2));
            root = buf;
            node.parent = buf;
            buf.getPoint(0).setLinks(node, new Node(node));
            node.removeHalf();
        } else {
            if (node.parent.size == node.parent.capacity)   {
                broke (node.parent);
                broke (node);
            }   else {
                node.getPoint((int) node.capacity / 2).setLinks(node, new Node(node));
                node.parent.add(node.getPoint((int) node.capacity / 2));
                node.removeHalf();
            }
        }
    }

    private void remove ()  {

    }
}

