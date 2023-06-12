public class BinaryTreeNode {
    private final int value; // Значення вузла
    private BinaryTreeNode leftChild; // Лівий дочірній вузол
    private BinaryTreeNode rightChild; // Правий дочірній вузол

    public BinaryTreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public BinaryTreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BinaryTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public BinaryTreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BinaryTreeNode rightChild) {
        this.rightChild = rightChild;
    }
}
