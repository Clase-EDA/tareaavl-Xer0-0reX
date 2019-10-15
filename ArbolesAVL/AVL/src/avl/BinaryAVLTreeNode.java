/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

/**
 *
 * @author rosendo
 */
public class BinaryAVLTreeNode<T extends Comparable> {
    protected T elem;
    protected BinaryAVLTreeNode<T> left, right, papa;
    protected int fe;
    protected int height;

    BinaryAVLTreeNode(T obj) {
        elem = obj;
        left = null;
        right = null;
    }

    BinaryAVLTreeNode(T obj, BinaryAVLTreeNode left, BinaryAVLTreeNode right) {
        elem = obj;
        this.left = left;
        this.right = right;
    }

    public void cuelga(BinaryAVLTreeNode<T> actual) {
        if (actual == null) {
            return;
        }
        if (actual.getElem().compareTo(elem) <= 0) {
            left = actual;
        } else {
            right = actual;
        }
        actual.setPapa(this);
    }

    public T getElem() {
        return elem;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public BinaryAVLTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryAVLTreeNode<T> left) {
        this.left = left;
    }

    public BinaryAVLTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryAVLTreeNode<T> right) {
        this.right = right;
    }

    public BinaryAVLTreeNode<T> getPapa() {
        return papa;
    }

    public void setPapa(BinaryAVLTreeNode<T> papa) {
        this.papa = papa;
    }

    @Override
    public String toString() {
        return "elem=" + elem + ", height=" + height;
    }

    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }

    

}
