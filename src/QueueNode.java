/**a class for making object which contains
 * character and length and other information of it**/
public class QueueNode
{
    private String huffmanPath;
    private String canonicalPath;
    private int pathLength;
    private int index;      //the index in the min heap in "sorted queue" class
    private int count;      //frequency
    private Character letter;
    private QueueNode leftChild = null, rightChild = null;      //if it has a right or left child in huffman tree
    public QueueNode(int count, Character letter)
    {
        this.count = count;
        this.letter = letter;
    }

    public QueueNode(String huffmanPath, int pathLength, Character letter)
    {
        this.huffmanPath = huffmanPath;
        this.pathLength = pathLength;
        this.letter = letter;
    }

    public QueueNode(Character letter, int count, QueueNode leftChild, QueueNode rightChild) {
        this.count = count;
        this.letter = letter;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public void setCanonicalPath(String canonicalPath) {
        this.canonicalPath = canonicalPath;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount()
    {
        return count;
    }
    public Character getLetter()
    {
        return letter;
    }

    public QueueNode getLeftChild() {
        return leftChild;
    }

    public QueueNode getRightChild() {
        return rightChild;
    }

    public boolean isLeaf()
    {
        return rightChild == null && leftChild == null;
    }

    public boolean isRightChild()
    {
        return index % 2 == 1;
    }

    public String getHuffmanPath() {
        return huffmanPath;
    }

    public String getCanonicalPath() {
        return canonicalPath;
    }

    public int getPathLength() {
        return pathLength;
    }

}
