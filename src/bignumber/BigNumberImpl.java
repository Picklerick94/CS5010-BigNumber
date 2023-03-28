package bignumber;

public class BigNumberImpl implements BigNumber {
    private String strNumber;
    public Node head = null;

    public Node tail = null;

    public BigNumberImpl(String strNumber) {
        String regex = "[0-9]+";
        if (!strNumber.matches(regex)) throw new IllegalArgumentException("strNumber is invalid");
        this.strNumber = strNumber;
        stringToList(this.strNumber);
    }

    public BigNumberImpl() {
        this.head = new Node(0);
        head.next = null;
    }

    public class Node {
        private int val;
        private Node next;

        Node(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public void insertFront(int val) {
        Node newNode = new Node(val);
        newNode.next = head;
        head = newNode;
    }

    public void insertLast(int val) {
        Node newNode = new Node(val);
        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    public void createNode(int val) {
        Node newNode = new Node(val);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public Node deleteLast() {
        Node newNode = new Node(0);
        if (head == null) {
            return head = newNode;
        }
        if (head.next == null) {
            return head;
        }

        Node current = head;
        Node previous = null;

        while (current.next != null) {
            previous = current;
            current = current.next;
        }
        previous.next = null;
        return head;
    }

    public Node getList() {
        return head;
    }

    public void displayList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.val + " --> ");
            current = current.next;
        }
        System.out.print("null");
        System.out.println();
    }

    public void stringToList(String text) {
        for (int i = 0; i < text.length(); i++) {
            int currNum = Character.getNumericValue(text.charAt(i));
//            insertLast(currNum);
            createNode(currNum);
        }
        this.displayList();
    }

    @Override
    public int length() {
        Node dummy = head;
        int size = 0;

        while (dummy != null) {
            size++;
            dummy = dummy.next;
        }

        return size;
    }

    @Override
    public void shiftLeft(int numOfShifts) {
        if (head != null && head.val == 0) return;
        if (numOfShifts < 0) {
            this.shiftRight(numOfShifts * -1);
        } else {
            for (int i = 0; i < numOfShifts; i++) {
                insertLast(0);
            }
        }
    }

    @Override
    public void shiftRight(int numOfShifts) {
//        if (head == null) this.copy();
        if (head != null && head.val == 0) return;

        if (numOfShifts < 0) {
            this.shiftLeft(numOfShifts * -1);
        } else {
            while (numOfShifts > 0) {
                Node newNode = new Node(0);
                if (this.length() == 1) {
                    this.head = newNode;
                }
                this.head = deleteLast();
                numOfShifts--;
            }
        }
    }

    @Override
    public void addDigit(int n) {
        boolean isDoubleDigit = (n > 9 && n < 100) || (n < -9 && n > -100);
        if (isDoubleDigit || n < 0) throw new IllegalArgumentException("Digit has to be single non-negative digit");

        Node lastNode = null;
        if (head == null) head = new Node(0);
        Node curr = head;

        while (curr.next != null) {
            if (curr.val != 9) {
                lastNode = curr;
            }
            curr = curr.next;
        }

        curr.val = curr.val + n;

        if (curr.val > 9) {
            curr.val = curr.val % 10;
            if (lastNode == null) {
                insertFront(1);
                lastNode = head.next;
            }

            while (lastNode != curr) {
                lastNode.val = (lastNode.val + 1) % 10;
                lastNode = lastNode.next;
            }
        }

        this.displayList();
    }

    @Override
    public int getDigitAt(int n) {
        if (head == null) {
            return 0;
        }

        if (n < 0 || n > this.length()) {
            throw new IllegalArgumentException("Invalid value: n = " + n);
        }

        Node mainPtr = head;
        Node refPtr = head;

        int count = 0;

        while(count <= n) {
            if(refPtr == null) {
                throw new IllegalArgumentException(n + " is greater than the number of nodes in list");
            }
            refPtr = refPtr.next;
            count++;
        }

        while (refPtr != null) {
            refPtr = refPtr.next;
            mainPtr = mainPtr.next;
        }

        return mainPtr.val;
    }

    @Override
    public BigNumber copy() {
        BigNumber copy = new BigNumberImpl();
        Node cur = head;
        Node copyOfCur = copy.getList();
        while (cur != null) {
            copyOfCur.val = cur.val;
            if(cur.next != null) {
                copyOfCur.next = new Node(0);
            }
            cur = cur.next;
            copyOfCur = copyOfCur.next;
        }
        return copy;
    }

    @Override
    public BigNumber add(BigNumber other) {
        Node start1 = new Node(0);
        start1.next = head;
        Node start2 = new Node(0);
        start2.next = other.getList();

        addPrecedingZeros(start1, start2);
        Node result = new Node(0);
        if (sumTwoNodes(start1.next, start2.next, result) == 1) {
            Node node = new Node(1);
            node.next = result.next;
            result.next = node;
        }

        Node current = result.next;
        StringBuilder ans = new StringBuilder();
        while (current != null) {
            ans.append(current.val);
            current = current.next;
        }

        return new BigNumberImpl(ans.toString());
    }

    private int sumTwoNodes(Node first, Node second, Node result) {
        if (first == null) {
            return 0;
        }
        int number = first.val + second.val + sumTwoNodes(first.next, second.next, result);
        Node node = new Node(number % 10);
        node.next = result.next;
        result.next = node;
        return number / 10;
    }

    private void addPrecedingZeros(Node start1, Node start2) {
        Node l1 = start1.next;
        Node l2 = start2.next;
        while (l1 != null && l2 != null) {
            l1 = l1.next;
            l2 = l2.next;
        }
        if (l1 == null && l2 != null) {
            while (l2 != null) {
                Node node = new Node(0);
                node.next = start1.next;
                start1.next = node;
                l2 = l2.next;
            }
        } else if (l2 == null && l1 != null) {
            while (l1 != null) {
                Node node = new Node(0);
                node.next = start2.next;
                start2.next = node;
                l1 = l1.next;
            }
        }
    }

    @Override
    public String toString() {
        Node temp = head;
        StringBuilder res = new StringBuilder();
        while (temp != null) {
            res.append(temp.val);
            temp = temp.next;
        }
        String ans = res.toString();
        return ans;
    }

    @Override
    public int compareTo(BigNumber other) {
        int len1 = this.length();
        int len2 = other.length();

        if (len1 < len2) {
            return -1;
        } else if (len1 > len2) {
            return 1;
        } else if (len1 == len2) {
            for (int i = len1-1; i >= 0; i--) {
                if (this.getDigitAt(i) == other.getDigitAt(i)) {
                    continue;
                } else if (this.getDigitAt(i) > other.getDigitAt(i)) {
                    return 1;
                } else if (this.getDigitAt(i) < other.getDigitAt(i)) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
