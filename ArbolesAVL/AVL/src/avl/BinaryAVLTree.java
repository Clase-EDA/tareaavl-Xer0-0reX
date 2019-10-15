/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

import java.util.ArrayList;

/**
 *
 * @author Xer0
 */
public class BinaryAVLTree <T extends Comparable> {
    protected int cont;
    protected BinaryAVLTreeNode<T> raiz, temp1;

    public boolean insert(T elem) {
        if (contains(elem)) {
            return false;
        } else {
            raiz = insert(elem, raiz);
            actualizaFe(raiz);
            cont++;
            return true;
        }
    }

    private BinaryAVLTreeNode<T> insert(T elemento, BinaryAVLTreeNode<T> actual) {
        if (actual == null) {
            actual = new BinaryAVLTreeNode<>(elemento);
            actual.setFe(height(actual.right) - height(actual.left));
        } else {
            if (elemento.compareTo(actual.elem) < 0) {
                actual.left = insert(elemento, actual.left);
                if (height(actual.left) - height(actual.right) == 2) {
                    if (elemento.compareTo(actual.left.elem) < 0) {
                        actual = rotateLeft(actual);
                    } else {
                        actual = rotateDoubleLeft(actual);
                    }
                }
            } else {
                actual.right = insert(elemento, actual.right);
                if (height(actual.right) - height(actual.left) == 2) {
                    if (elemento.compareTo(actual.right.elem) > 0) {
                        actual = rotateRight(actual);
                    } else {
                        actual = rotateDoubleRight(actual);
                    }
                }
            }
        }
        actual.height = Math.max(height(actual.left), height(actual.right)) + 1;
        actual.setFe(height(actual.right) - height(actual.left));
        return actual;
    }

    public BinaryAVLTreeNode balancea(T elemento, BinaryAVLTreeNode<T> actual) {
        if (actual == null) {
            return actual;
        } else {
            if (elemento.compareTo(actual.elem) < 0) {
                actual.left = balancea(elemento, actual.left);
                if (actual.fe == -2) {
                    if (actual.right == null) {
                        actual = rotateLeft(actual);
                    } else {
                        actual = rotateDoubleLeft(actual);
                    }
                }
                if (actual.fe == 2) {
                    if (actual.left == null) {
                        actual = rotateRight(actual);
                    } else {
                        actual = rotateDoubleRight(actual);
                    }
                }
            } else {
                actual.right = balancea(elemento, actual.right);
                if (actual.fe == -2) {
                    if (actual.right == null) {
                        actual = rotateLeft(actual);
                    } else {
                        actual = rotateDoubleLeft(actual);
                    }
                }
                if (actual.fe == 2) {
                    if (actual.left == null) {
                        actual = rotateRight(actual);
                    } else {
                        actual = rotateDoubleRight(actual);
                    }
                }
            }
        }
        actual.height = Math.max(height(actual.left), height(actual.right)) + 1;
        actual.setFe(height(actual.right) - height(actual.left));
        return actual;
    }

    public boolean remove(T elem) {
        if (!contains(elem)) {
            return false;
        }
        BinaryAVLTreeNode<T> actual, papa = raiz, temp;
        actual = raiz; 
        while (actual.getElem().compareTo(elem) != 0) {
            papa = actual;
            if (elem.compareTo(actual.getElem()) <= 0) {
                actual = actual.getLeft();
            } else {
                actual = actual.getRight();
            }
        }
        if (actual == raiz && (actual.getLeft() == null || actual.getRight() == null)) {//caso especial
            if (raiz.getLeft() != null) {
                raiz = raiz.getLeft();
            } else {
                raiz = raiz.getRight();
            }
            cont--;
            return true;
        }
        if (actual.getLeft() == null && actual.getRight() == null) {//caso hoja
            if (actual.elem.compareTo(papa.elem) < 0) {
                papa.setLeft(null);
            } else {
                papa.setRight(null);
            }
        } else {
            if (!(actual.getLeft() != null && actual.getRight() != null)) {//un hijo
                papa.cuelga(actual.getLeft());
                papa.cuelga(actual.getRight());
            } else {
                temp = actual;
                actual = actual.getRight();
                papa = actual;
                while (actual.getLeft() != null) {
                    papa = actual;
                    actual = actual.getLeft();
                }
                temp.setElem(actual.getElem());
                if (papa == actual) {
                    temp.setRight(actual.getRight());
                } else {
                    papa.setLeft(actual.getRight());
                }
            }
            cont--;
        }

        actualizaFe(raiz);
        balancea(elem, raiz);
        actualizaFe(raiz);
        return true;
    }

    public void actualizaFe(BinaryAVLTreeNode<T> actual) {
        if (actual == null) {
            return;
        }
        if (actual.left != null) {
            actualizaFe(actual.left);
        }
        if (actual.right != null) {
            actualizaFe(actual.right);
        }
        actual.setFe(height(actual.right) - height(actual.left));
    }

    public BinaryAVLTreeNode<T> rotateLeft(BinaryAVLTreeNode<T> x1) {
        BinaryAVLTreeNode<T> x2 = x1.left;
        x1.left = x2.right;
        x2.right = x1;
        x1.height = Math.max(height(x1.left), height(x1.right)) + 1;
        x2.height = Math.max(height(x2.left), x1.height) + 1;
        return (x2);
    }

    public BinaryAVLTreeNode<T> rotateDoubleLeft(BinaryAVLTreeNode<T> x1) {
        x1.left = rotateRight(x1.left);
        return rotateLeft(x1);
    }

    public BinaryAVLTreeNode<T> rotateRight(BinaryAVLTreeNode<T> x1) {
        BinaryAVLTreeNode<T> x2 = x1.right;
        x1.right = x2.left;
        x2.left = x1;
        x1.height = Math.max(height(x1.left), height(x1.right)) + 1;
        x2.height = Math.max(height(x2.right), x1.height) + 1;
        return (x2);
    }

    public BinaryAVLTreeNode<T> rotateDoubleRight(BinaryAVLTreeNode<T> x1) {
        x1.right = rotateLeft(x1.right);
        return rotateRight(x1);
    }

    public int height(BinaryAVLTreeNode<T> actual) {
        if (actual == null) {
            return -1;
        } else {
            return actual.height;
        }
    }

    public boolean contains(T elem) {
        return contains(elem, raiz);
    }

    private boolean contains(T elem, BinaryAVLTreeNode<T> actual) {
        if (actual == null) {
            return false;
        }
        if (elem.compareTo(actual.elem) < 0) {
            return contains(elem, actual.left);
        }
        if (elem.compareTo(actual.elem) > 0) {
            return contains(elem, actual.right);
        }
        return true;
    }

    public void imprime() {
        StringBuilder sb = new StringBuilder();
        ArrayList<BinaryAVLTreeNode<T>> aux = new ArrayList<>();
        ArrayList<T> lista = new ArrayList<>();
        ArrayList lista2 = new ArrayList<>();
        aux.add(raiz);
        BinaryAVLTreeNode<T> temp;
        while (!aux.isEmpty()) {
            temp = aux.remove(0);
            lista.add(temp.getElem());
            lista2.add(temp.getFe());
            if (temp.getLeft() != null) {
                aux.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                aux.add(temp.getRight());
            }
        }
        while (lista.iterator().hasNext()) {
            System.out.println(lista.remove(0));
            System.out.println(lista2.remove(0));
        }
    }
    
     public static void main(String[] args) {
        // TODO code application logic here
    BinaryAVLTree a= new BinaryAVLTree();
    a.insert(10);
    a.insert(5);
    a.insert(15);
    a.imprime();
    a.insert(25);
    a.insert(35);
    a.imprime();
     
     }
}
