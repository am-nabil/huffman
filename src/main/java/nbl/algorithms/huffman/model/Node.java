package nbl.algorithms.huffman.model;

public class Node {

    private final Long frequence;
    private final String character;
    private final Node left;
    private final Node right;

    public Node(String character, Long frequence, Node left, Node right) {
        this.character = character;
        this.frequence = frequence;
        this.left = left;
        this.right = right;
    }

    public Node(String character, Long frequence) {
        this(character, frequence, null, null);
    }

    public Long getFrequence() {
        return frequence;
    }

    public String getCharacter() {
        return character;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj.getClass().equals(this.getClass()) &&
                this.character.equals(((Node) obj).getCharacter()) &&
                this.frequence.equals(((Node) obj).getFrequence());
    }
}
