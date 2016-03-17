package huffman;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

// Tests HuffmanCompress coupled with HuffmanDecompress.
public class HuffmanCompressTest extends HuffmanCodingTest {

	private final HuffmanEngine engine = new HuffmanEngine();

	protected byte[] compress(byte[] b) throws IOException {
		InputStream in = new ByteArrayInputStream(b);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BitOutputStream bitOut = new BitOutputStream(out);
		
		FrequencyTable freq = new FrequencyTable();
		for (byte x: b) {
			freq.increment(x & 0xff);
		}
		freq.increment(FrequencyTable.EOF);
		CodeTree code = freq.buildCodeTree();
		CanonicalCode canonCode = new CanonicalCode(code);
		code = canonCode.toCodeTree();
		engine.writeCodeAndCompress(canonCode, code, in, bitOut);
		return out.toByteArray();
	}
	
	
	protected byte[] decompress(byte[] b) throws IOException {
		InputStream in = new ByteArrayInputStream(b);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BitInputStream bitIn = new BitInputStream(in);
		
		engine.readCodeAndDecompress(bitIn, out);
		return out.toByteArray();
	}
}
