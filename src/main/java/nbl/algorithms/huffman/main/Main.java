package nbl.algorithms.huffman.main;

import nbl.algorithms.huffman.model.Huffman;
import nbl.algorithms.huffman.model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("-----------------------");
        System.out.println("--------HUFFMAN--------");
        System.out.println("-----------------------");
        System.out.println("Enter text to code : ");
        String text = in.nextLine();
        // We generate a hoffman tree from the text entered according to the number of occurrences
        Node huffmanTree = Huffman.constructHuffmanTree(Huffman.createNodes(text));
        //We initialize an association map: Character <-> Binary code according to huffman
        Map<String, String> codesMap = new HashMap<>();
        // We fill in the association map using the generated tree.
        Huffman.generateEncodedText(huffmanTree, codesMap, "");
        //we display the encoding associated with each character
        codesMap.entrySet().forEach(System.out::println);
        // We can now encode the input text
        String encodedText = Huffman.encodeTexte(text, codesMap);
        System.out.println("Encoded text : " + encodedText);
        // We, then, decode it using the tree
        String decodedText = Huffman.decodeText(huffmanTree, encodedText);
        System.out.println("Encoded text : " + decodedText);
    }
}
