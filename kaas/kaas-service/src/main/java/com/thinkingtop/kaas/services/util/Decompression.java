package com.thinkingtop.kaas.services.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.thinkingtop.kaas.services.algorithm.SevenZip.Compression.LZMA.Decoder;
import com.thinkingtop.kaas.services.algorithm.SevenZip.Compression.LZMA.Encoder;
import com.thinkingtop.kaas.services.algorithm.SevenZip.LzmaAlone.CommandLine;

public class Decompression {
	public static void main(String[] args) {
		new Decompression().DecompressionKaas("/home/roadahead/workspace/newarch/kaas/dist/data/out/scheme1.kaas", "/home/roadahead/workspace/newarch/kaas/dist/data/out/scheme");
	}
	
	public void DecompressionKaas(String fileName, String outputPath){
		String outZip =  outputPath+".zip"; 
		try {
			// 执行解压
			String[] path7z = {"d",fileName,outZip};
			package7zip(path7z);
			decompress(outZip, outputPath);
			System.out.println("Extracting  OK!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Extracting File Failed!");
			dealError(outputPath);
			System.out.println("Installing Portal Failed");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		File f = new File(outZip);
		while(f.exists()){
			f.delete();
		}
	}
	
	static void PrintHelp() {
		System.out
				.println("\nUsage:  LZMA <e|d> [<switches>...] inputFile outputFile\n"
						+ "  e: encode file\n"
						+ "  d: decode file\n"
						+ "  b: Benchmark\n"
						+ "<Switches>\n"
						+
						// "  -a{N}:  set compression mode - [0, 1], default: 1 (max)\n"
						// +
						"  -d{N}:  set dictionary - [0,28], default: 23 (8MB)\n"
						+ "  -fb{N}: set number of fast bytes - [5, 273], default: 128\n"
						+ "  -lc{N}: set number of literal context bits - [0, 8], default: 3\n"
						+ "  -lp{N}: set number of literal pos bits - [0, 4], default: 0\n"
						+ "  -pb{N}: set number of pos bits - [0, 4], default: 2\n"
						+ "  -mf{MF_ID}: set Match Finder: [bt2, bt4], default: bt4\n"
						+ "  -eos:   write End Of Stream marker\n");
	}

	
	public void package7zip(String[] args) throws Exception {
		System.out.println("\nLZMA (Java) 4.61  2008-11-23\n");

		if (args.length < 1) {
			PrintHelp();
			return;
		}

		CommandLine params = new CommandLine();
		if (!params.Parse(args)) {
			System.out.println("\nIncorrect command");
			return;
		}

		if (params.Command == CommandLine.kBenchmak) {
			int dictionary = (1 << 21);
			if (params.DictionarySizeIsDefined)
				dictionary = params.DictionarySize;
			if (params.MatchFinder > 1)
				throw new Exception("Unsupported match finder");
			com.thinkingtop.kaas.services.algorithm.SevenZip.LzmaBench.LzmaBenchmark(params.NumBenchmarkPasses,
					dictionary);
		} else if (params.Command == CommandLine.kEncode
				|| params.Command == CommandLine.kDecode) {
			java.io.File inFile = new java.io.File(params.InFile);
			java.io.File outFile = new java.io.File(params.OutFile);

			java.io.BufferedInputStream inStream = new java.io.BufferedInputStream(
					new java.io.FileInputStream(inFile));
			java.io.BufferedOutputStream outStream = new java.io.BufferedOutputStream(
					new java.io.FileOutputStream(outFile));

			boolean eos = false;
			if (params.Eos)
				eos = true;
			if (params.Command == CommandLine.kEncode) {
				Encoder encoder = new Encoder();
				if (!encoder.SetAlgorithm(params.Algorithm))
					throw new Exception("Incorrect compression mode");
				if (!encoder.SetDictionarySize(params.DictionarySize))
					throw new Exception("Incorrect dictionary size");
				if (!encoder.SetNumFastBytes(params.Fb))
					throw new Exception("Incorrect -fb value");
				if (!encoder.SetMatchFinder(params.MatchFinder))
					throw new Exception("Incorrect -mf value");
				if (!encoder.SetLcLpPb(params.Lc, params.Lp, params.Pb))
					throw new Exception("Incorrect -lc or -lp or -pb value");
				encoder.SetEndMarkerMode(eos);
				encoder.WriteCoderProperties(outStream);
				long fileSize;
				if (eos)
					fileSize = -1;
				else
					fileSize = inFile.length();
				for (int i = 0; i < 8; i++)
					outStream.write((int) (fileSize >>> (8 * i)) & 0xFF);
				encoder.Code(inStream, outStream, -1, -1, null);
			} else {
				int propertiesSize = 5;
				byte[] properties = new byte[propertiesSize];
				if (inStream.read(properties, 0, propertiesSize) != propertiesSize)
					throw new Exception("input .lzma file is too short");
				Decoder decoder = new Decoder();
				if (!decoder.SetDecoderProperties(properties))
					throw new Exception("Incorrect stream properties");
				long outSize = 0;
				for (int i = 0; i < 8; i++) {
					int v = inStream.read();
					if (v < 0)
						throw new Exception("Can't read stream size");
					outSize |= ((long) v) << (8 * i);
				}
				if (!decoder.Code(inStream, outStream, outSize))
					throw new Exception("Error in data stream");
			}
			outStream.flush();
			outStream.close();
			inStream.close();
		} else
			throw new Exception("Incorrect command");
		return;
	}
	
	
	private void decompress(String fileName, String outputPath)
			throws IOException {

		if (!outputPath.endsWith(File.separator)) {
			outputPath += File.separator;
		}

		ZipFile jf = new ZipFile(fileName);

		for (Enumeration e = jf.entries(); e.hasMoreElements();) {
			ZipEntry je = (ZipEntry) e.nextElement();
			String outFileName = outputPath + je.getName();
			File f = new File(outFileName);
			System.out.println(f.getAbsolutePath());

			// 创建该路径的目录和所有父目录
			makeSupDir(outFileName);

			// 如果是目录，则直接进入下一个循环
			if (f.isDirectory()) {
				continue;
			}

			InputStream in = null;
			OutputStream out = null;

			try {
				in = jf.getInputStream(je);
				out = new BufferedOutputStream(new FileOutputStream(f));
				byte[] buffer = new byte[2048];
				int nBytes = 0;
				while ((nBytes = in.read(buffer)) > 0) {
					out.write(buffer, 0, nBytes);
				}
			} catch (IOException ioe) {
				throw ioe;
			} finally {
				try {
					if (null != out) {
						out.flush();
						out.close();
					}
				} catch (IOException ioe) {
					throw ioe;
				} finally {
					if (null != in) {
						in.close();
					}
				}
			}
		}
	}
	
	private void makeSupDir(String outFileName) {
		// 匹配分隔符
		Pattern p = Pattern.compile("[/\\" + File.separator + "]");
		Matcher m = p.matcher(outFileName);
		// 每找到一个匹配的分隔符，则创建一个该分隔符以前的目录
		while (m.find()) {
			int index = m.start();
			String subDir = outFileName.substring(0, index);
			File subDirFile = new File(subDir);
			if (!subDirFile.exists())
				subDirFile.mkdir();
		}
	}
	
	private void clean(String path) throws IOException {
		File file = new File(path);
		// 如果该路径不存在
		if (!file.exists()) {
			System.out.println(path + " Not Exist!");
		} else {
			// 如果是目录，则递归删除
			if (file.isDirectory()) {
				String[] fileNames = file.list();

				if (null == fileNames) {
					throw new IOException("IO ERROR While Deleting Files");
				}
				// 如果是空目录则直接删除
				else if (fileNames.length == 0) {
					file.delete();
				} else {
					for (String fileName : fileNames) {
						File subFile = new File(fileName);
						clean(path + File.separator + subFile);
					}
					System.out.println(file.getAbsolutePath());
					// 最后删除父目录
					file.delete();

				}
			}
			// 如果是文件，则直接删除
			else {
				System.out.println(file.getAbsolutePath());
				file.delete();
			}
		}
	}
	private void dealError(String outputPath) {
		// 删除已解压的文件
		System.out.println("Start Deleting Files......");
		try {
			clean(outputPath);
			System.out.println("Deleting Files OK!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Deleting Files Failed!");
		}
	}
}
