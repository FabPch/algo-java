package org.algo.graph.huffman;

public class HuffmanNode implements Comparable{
    public int frequency;
    public char letter;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.left = left;
        this.right = right;
        this.frequency = left.frequency + right.frequency;
        this.letter = '-';
    }

    public HuffmanNode(int frequency, char letter) {
        this.frequency = frequency;
        this.letter = letter;
    }

    @Override
    public int compareTo(Object o) {
        return this.frequency - ((HuffmanNode) o).frequency;
    }

    public boolean isLeaf() {
        return left == null && right == null && Character.isLetter(letter);
    }
}
