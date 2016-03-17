package huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static huffman.FrequencyTable.EOF;

/**
 * Huffman Compression class.
 *
 * Uses static Huffman coding to compress an input file to an output file. Use HuffmanDecompress to decompress.
 * Uses 257 symbols - 256 for byte values and 1 for EOF.
 * The compressed file format contains the code length of each symbol under a canonical code, followed by the Huffman-coded data.
 */
public final class HuffmanCompress {
	
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.err.println("Usage: java HuffmanCompress InputFile OutputFile");
			System.exit(1);
			return;
		}
		
  	File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);
		
		FrequencyTable freq = getFrequencies(inputFile);
		CodeTree code = freq.buildCodeTree();
		CanonicalCode canonCode = new CanonicalCode(code);
		code = canonCode.toCodeTree();  // Replace code tree with canonical one. For each symbol, the code value may change but the code length stays the same.
		
		// Read input file again, compress with Huffman coding, and write output file
		InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
		BitOutputStream out = new BitOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
		HuffmanEngine engine = new HuffmanEngine();
  	engine.writeCodeAndCompress(canonCode, code, in, out);
	}
	
	
	private static FrequencyTable getFrequencies(File file) throws IOException {
		FrequencyTable freq = new FrequencyTable();
		InputStream input = new BufferedInputStream(new FileInputStream(file));
		try {
			while (true) {
				int b = input.read();
				if (b == -1)
					break;
				freq.increment(b);
			}
		} finally {
			input.close();
		}
		freq.increment(EOF);  // EOF symbol gets a frequency of 1
		return freq;
	}
	
	

}
