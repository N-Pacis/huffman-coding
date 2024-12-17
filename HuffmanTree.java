import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Program that implements Huffman Coding algorithm using Priority Queues and
 * Trees
 * 
 * @author Pacis Nkubito
 */
public class HuffmanTree {

    private PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
    public HuffmanNode root;

    /**
     * Constructor that takes an array of integers and builds a Huffman Tree
     * 
     * @param count an array of integers representing the frequency of each
     *              character
     *              in the input file
     * 
     */
    public HuffmanTree(int[] count) {
        // Add all characters with a frequency greater than 0 to the priority queue
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                HuffmanNode node = new HuffmanNode(count[i], i);
                priorityQueue.add(node);
            }
        }

        // Add the end of file character to the priority queue
        HuffmanNode eofNode = new HuffmanNode(1, count.length);
        priorityQueue.add(eofNode);

        buildTree();

    }

    /**
     * Constructor that takes a Scanner object and builds a Huffman Tree
     * 
     * @param input a Scanner object that reads the contents of a .code file
     * 
     */
    public HuffmanTree(Scanner input) {
        root = new HuffmanNode(0);

        // read the file as long as there is a next line
        while (input.hasNextLine()) {
            int n = Integer.parseInt(input.nextLine());
            String codes = input.nextLine();

            // recursively create the node
            createTreeNode(root, codes, n, 0);
        }
    }

    /**
     * Helper method that creates a tree node
     * 
     * @param currentRoot the current root of the tree
     * @param codes       the path to the character
     * @param character   the character to be added to the tree
     * @param i           the index of the character in the path
     * 
     */
    private void createTreeNode(HuffmanNode currentRoot, String codes, int character, int index) {
        if (index == codes.length()) {
            currentRoot.character = character;
            return;
        }

        if (codes.charAt(index) == '0') {
            if (currentRoot.left == null) {
                currentRoot.left = new HuffmanNode(0);
            }
            createTreeNode(currentRoot.left, codes, character, index + 1);
        } else {
            if (currentRoot.right == null) {
                currentRoot.right = new HuffmanNode(0);
            }
            createTreeNode(currentRoot.right, codes, character, index + 1);
        }
    }

    /**
     * Helper method that builds the Huffman Tree
     */
    private void buildTree() {
        // if the priority queue has at least 2 nodes, create a new node by combining
        // the two nodes with the smallest frequency
        while (priorityQueue.size() > 1) {
            HuffmanNode node1 = priorityQueue.poll();
            HuffmanNode node2 = priorityQueue.poll();

            HuffmanNode newNode = new HuffmanNode(node1.frequency + node2.frequency);
            newNode.left = node1;
            newNode.right = node2;

            priorityQueue.add(newNode);
        }

        // if the priority queue has only one node, set the root to that node
        root = priorityQueue.poll();
    }

    /**
     * Helper method that prints the Huffman Tree
     * 
     * @param currentRoot the current root of the tree
     * @param currentPath the path to the character
     */
    public void printHuffmanTree(PrintStream output, HuffmanNode currentRoot, String currentPath) {
        if (currentRoot == null) {
            return;
        }

        if (currentRoot.left == null && currentRoot.right == null) {
            output.println(currentRoot.character);
            output.println(currentPath);
            return;
        }

        printHuffmanTree(output, currentRoot.left, currentPath + "0");
        printHuffmanTree(output, currentRoot.right, currentPath + "1");
    }

    /**
     * Method that writes the contents of the tree to a PrintStream object
     * 
     * @param output a PrintStream object that writes the contents of the tree to a
     *               file
     */
    public void write(PrintStream output) {
        printHuffmanTree(output,root, "");
    }

    /**
     * Method that decodes the contents of a file
     * 
     * @param input  a BitInputStream object that reads the contents of a file
     * @param output a PrintStream object that writes the contents of the file to a
     *               new file
     * @param eof    the end of file character
     * 
     */
    public void decode(BitInputStream input, PrintStream output, int eof) {
        HuffmanNode current = root;

        while (true) {
            int bit = input.readBit();
            if (bit == -1) {
                break;
            }

            if (bit == 0) {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                if (current.character == eof) {
                    break;
                }
                output.write(current.character);
                current = root;
            }
        }
    }
}

/**
 * Class that represents a node in a Huffman Tree
 * 
 * @author Pacis Nkubito
 * @author Charlie Ratliff
 * 
 */
class HuffmanNode implements Comparable<HuffmanNode> {
    public int frequency;
    public int character;

    public HuffmanNode left;
    public HuffmanNode right;

    /**
     * Constructor that takes an integer and a character
     * 
     * @param frequency the frequency of the character
     * @param character the character
     * 
     */
    public HuffmanNode(int frequency, int character) {
        this.frequency = frequency;
        this.character = character;

        left = null;
        right = null;
    }

    /**
     * Constructor that takes an integer
     * 
     * @param frequency the frequency of the character
     * 
     */
    public HuffmanNode(int frequency) {
        this.frequency = frequency;

        left = null;
        right = null;
    }

    /**
     * Method that compares two HuffmanNodes
     * 
     * @param o the HuffmanNode to be compared
     * 
     */
    public int compareTo(HuffmanNode o) {
        int diff = this.frequency - o.frequency;
        return diff;
    }
}