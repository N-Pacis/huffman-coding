# Huffman Coding

Huffman coding is a compression algorithm that is used to reduce the size of data without losing any information. It works by assigning variable-length codes to characters based on their frequencies. Characters that occur more frequently are assigned shorter codes, while those that occur less frequently are assigned longer codes. This results in a compressed representation of the data that can be efficiently stored and transmitted.

## Implementation
The algorithm first reads the input file and counts the frequency of each character. Using these frequencies, it build a Huffman tree where each node represents a character and its frequency. The tree is then constructed by repeatedly combining the two nodes with the smallest frequencies until only one node remains, which becomes the root of the tree. The algoritm then generates the Huffman codes for each character by traversing the tree and writing these codes to a file.

For the decompression process, The algorithm reads the encoded file and the corresponding code file to reconstruct the Huffman tree. Using this tree, it decodes the compressed data by traversing the tree according to the bits read from the encoded file. The decoded characters are then written to an output file, effectively restoring the original data.