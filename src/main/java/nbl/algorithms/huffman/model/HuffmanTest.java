package nbl.algorithms.huffman.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanTest {

    private static final String ORIGINAL_TEXT = "abbccccdddddddeeeeeeeeefffffffffffffgggggggggggggggg";
    private static final String ENCODED_TEXT = "10100101011010110111011101110111001001001001001001000000000000000000000101010101010101010101010111111111111111111111111111111111";

    private static final String ORIGINAL_TEXT_2 = "tititoto";
    private static final String ENCODED_TEXT_2 = "010010011011";

    private static final String ORIGINAL_TEXT_3 = "t";
    private static final String DECODED_TEXT_3 = "";

    @Test
    public void testCharactersFrequence() {
        List<Node> x = Huffman.createNodes(ORIGINAL_TEXT);
        assert x.contains(new Node(String.valueOf('a'), Long.valueOf(1)));
        assert x.contains(new Node(String.valueOf('b'), Long.valueOf(2)));
        assert x.contains(new Node(String.valueOf('f'), Long.valueOf(13)));
    }

    @Test
    public void testHuffmanTreeConstruction() {
        String text = ORIGINAL_TEXT;
        Node treeRoot = Huffman.constructHuffmanTree(Huffman.createNodes(text));
        assert (treeRoot.getFrequence() == text.length());
        displayTree(treeRoot);
    }

    @Test
    public void testCodesMapGeneration() {
        String text = ORIGINAL_TEXT;
        Node node = Huffman.constructHuffmanTree(Huffman.createNodes(text));
        Map<String, String> codesMap = new HashMap<>();
        Huffman.generateEncodedText(node, codesMap, "");
        codesMap.entrySet().forEach(e -> System.out.println(e.getKey().concat(" ").concat(e.getValue())));
        assert (codesMap.get("a").equals("10100"));
        assert (codesMap.get("b").equals("10101"));
        assert (codesMap.get("c").equals("1011"));
        assert (codesMap.get("d").equals("100"));
        assert (codesMap.get("e").equals("00"));
        assert (codesMap.get("f").equals("01"));
        assert (codesMap.get("g").equals("11"));
    }

    @Test
    public void testEncodeText() {
        testEncode(ORIGINAL_TEXT, ENCODED_TEXT);
    }

    @Test
    public void testDecodeText() {
        testDecode(ORIGINAL_TEXT);
    }


    @Test
    public void testEncodeText2() {
        testEncode(ORIGINAL_TEXT_2, ENCODED_TEXT_2);
    }

    @Test
    public void testDecodeText2() {
        testDecode(ORIGINAL_TEXT_2);
    }


    @Test
    public void testEncodeText1char() {
        testEncode(ORIGINAL_TEXT_3, DECODED_TEXT_3);
    }

    @Test
    public void testDecodeText1char() {
        testDecode(ORIGINAL_TEXT_3);
    }

    private void testEncode(String originalText, String encodedText) {
        Node node = Huffman.constructHuffmanTree(Huffman.createNodes(originalText));
        Map<String, String> mapEncodee = new HashMap<>();
        Huffman.generateEncodedText(node, mapEncodee, "");
        String code = Huffman.encodeTexte(originalText, mapEncodee);
        assert (code.equals(encodedText));
        System.out.println(code);
    }

    private void testDecode(String originalText) {
        Node node = Huffman.constructHuffmanTree(Huffman.createNodes(originalText));
        Map<String, String> codesMap = new HashMap<>();
        Huffman.generateEncodedText(node, codesMap, "");
        String encodedText = Huffman.encodeTexte(originalText, codesMap);
        String decodedText = Huffman.decodeText(node, encodedText);
        assert (decodedText.equals(originalText));
        System.out.println(decodedText);
        System.out.println(encodedText);
    }


    public void displayTree(Node node) {
        System.out.println(node.getCharacter() + " : " + node.getFrequence());
        if (node.getLeft() != null) {
            displayTree(node.getLeft());
        }
        if (node.getRight() != null) {
            displayTree(node.getRight());
        }
    }
}
