package nbl.algorithms.huffman.model;

import java.util.*;
import java.util.stream.Collectors;

public class Huffman {

    /**
     * Method creating list of nodes, each node having a frequence of appearance in text
     *
     * @param text
     * @return list of Nodes
     */
    public static List<Node> createNodes(String text) {
        List<String> charactersList = text.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
        return charactersList.stream()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet().stream().map(e -> new Node(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * constructs huffman tree from given nodes
     * 1 - Sort list
     * 2 - Create PriorityQueue from the previously sorted list
     * 3 - create parent node to the first two nodes from the Queue, the parent node frequence is the sum of its children frequences.
     * 4 - Return the parent node.
     *
     * @param nodesList
     * @return The new parent node
     */
    public static Node constructHuffmanTree(List<Node> nodesList) {
        Queue<Node> tree = new PriorityQueue<>(Comparator.comparing(Node::getFrequence));
        nodesList.forEach(node -> tree.add(node));
        while (tree.size() >= 2) {
            Node leftChild = tree.poll();
            Node rightChild = tree.poll();
            tree.add(new Node(leftChild.getCharacter().concat(rightChild.getCharacter()), leftChild.getFrequence() + rightChild.getFrequence(), leftChild, rightChild));
        }
        return tree.poll();
    }

    /**
     * Method that generates a map of codes associated to each character found
     * 1 - browse recursively the tree using DFS (Depth First Search).
     * 2 - for each node concat 0 if left child, 1 if right child, insert in the map if leaf
     *
     * @param node
     * @param codesMap
     * @param codeCumul
     */
    public static void generateEncodedText(Node node, Map<String, String> codesMap, String codeCumul) {
        if (node.getLeft() == null && node.getRight() == null) {
            codesMap.put(node.getCharacter(), codeCumul);
            return;
        }
        if (node.getLeft() != null) {
            generateEncodedText(node.getLeft(), codesMap, codeCumul.concat("0"));
        }
        if (node.getRight() != null) {
            generateEncodedText(node.getRight(), codesMap, codeCumul.concat("1"));
        }
    }

    /**
     * Methode that encodes the text
     * 1 - For each character: we modify it by its equivalent in the input encoding map
     *
     * @param text     the text to encode
     * @param codesMap codes map
     * @return encoded text
     */
    public static String encodeTexte(String text, Map<String, String> codesMap) {
        String encodedText = "";
        for (int i = 0; i < text.length(); i++) {
            encodedText = encodedText.concat(codesMap.get(String.valueOf(text.charAt(i))));
        }
        return encodedText;
    }


    /**
     * Overloading the recursive method to transform the list of decoded characters into a character string
     *
     * @param root        tree root
     * @param encodedText encodedText
     * @return decodedText
     */
    public static String decodeText(Node root, String encodedText) {
        List<String> decodedCharacters = new ArrayList<>();
        decodeText(root, root, encodedText, decodedCharacters);
        return decodedCharacters.stream().reduce(String::concat).orElse(null);
    }

    /**
     * This recursive method decodes the encoded text.
     * 1 - browse the tree according to the characters of the encoded text 0: go left, 1: go right
     * 2 - If we arrive at a leaf of the tree, we consider that a character has been found and we add it to the list of decoded characters.
     * - Then start again with the rest of the encoded text from the root of the tree
     * 3 - Otherwise (we are on a parent node): We call recursively according to the entry 0 left, 1 right
     * 4 - We leave the function if there is no more text to decode
     *
     * @param root
     * @param node
     * @param encodedText
     * @param decodedCharacters
     */
    public static void decodeText(Node root, Node node, String encodedText, List<String> decodedCharacters) {
        if (node.getLeft() == null && node.getRight() == null) {
            decodedCharacters.add(node.getCharacter());
            if (encodedText.isEmpty()) {
                return;
            } else {
                decodeText(root, root, encodedText, decodedCharacters);
            }
        } else if (String.valueOf(encodedText.charAt(0)).equals("0")) {
            decodeText(root, node.getLeft(), encodedText.substring(1), decodedCharacters);
        } else if (String.valueOf(encodedText.charAt(0)).equals("1")) {
            decodeText(root, node.getRight(), encodedText.substring(1), decodedCharacters);
        }
    }
}