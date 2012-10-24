package com.thinkingtop.kaas.services.algorithm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.thinkingtop.kaas.services.algorithm.SevenZip.Compression.LZMA.Decoder;
import com.thinkingtop.kaas.services.algorithm.SevenZip.Compression.LZMA.Encoder;
import com.thinkingtop.kaas.services.algorithm.SevenZip.LzmaAlone.CommandLine;

import com.ibm.icu.text.SimpleDateFormat;

@Component("packageDate")
public class PackageDate {
	static Logger logger=Logger.getLogger(PackageDate.class);
	private KaasDataPath kaasDataPath;
	private AlgorithmProperties algorithmProperties;
	
	public void packageD(){
		createMeta();
		try {
			packageZip(kaasDataPath.getRDataPath(),kaasDataPath.getRDataPath()+".zip");
			String[] path7z = {"e",kaasDataPath.getRDataPath()+".zip",kaasDataPath.getRDataPath()+".kaas"};
			package7zip(path7z);
			
			File f = new File(kaasDataPath.getRDataPath());
			//deleteFolder(f);
			//logger.info("file : ---------" + f.exists());
			f = new File(kaasDataPath.getRDataPath()+".zip");
			while(f.exists()){
				f.delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFolder(File f){
		File[] fs = f.listFiles();
		for(File fc : fs){
			if(!fc.isDirectory()){
				fc.delete();
			}else{
				deleteFolder(fc);
			}
		}
		f.delete();
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
	
	public void packageZip(String inputFileName, String outputFileName)
			throws Exception {
		
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				outputFileName));
		File f = new File(inputFileName);
		packageZip(out, f, "");
		out.close();
	}

	private void packageZip(ZipOutputStream out, File f, String base)
			throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				packageZip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new JarEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int n = in.read(buffer);
			while (n != -1) {
				out.write(buffer, 0, n);
				n = in.read(buffer);
			}
			in.close();
		}
	}
	
	public int createMeta(){
		FileOutputStream fo = null;
		try {
            fo = new FileOutputStream(kaasDataPath.getRDataPath()+"/Meta",false);
            logger.info("kaasDataOutPath:-"+ kaasDataPath.getRDataPath());
            String metas ="schemeName=" + algorithmProperties.getSchemeName();
            fo.write(metas.getBytes());
            fo = new FileOutputStream(kaasDataPath.getRDataPath()+"/Meta",true);
            metas ="\r\neCommerceName=" + algorithmProperties.getECommerceName();
            fo.write(metas.getBytes());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            metas ="\r\ncreateDate=" + sdf.format(new Date());
            fo.write(metas.getBytes());
            
        } catch (Exception h) {
        	return 2;
        }finally{
        	try {
        		if(fo!=null)
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return 1;
	}
	public KaasDataPath getKaasDataPath() {
		return kaasDataPath;
	}
	@Resource(name="kaasDataPath")
	public void setKaasDataPath(KaasDataPath kaasDataPath) {
		this.kaasDataPath = kaasDataPath;
	}
	public AlgorithmProperties getAlgorithmProperties() {
		return algorithmProperties;
	}
	@Resource(name="algorithmProperties")
	public void setAlgorithmProperties(AlgorithmProperties algorithmProperties) {
		this.algorithmProperties = algorithmProperties;
	}
	
}
