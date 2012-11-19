package com.thinkingtop.kaas.server.upload;

import com.thinkingtop.kaas.services.algorithm.SevenZip.Decompression;

public class De {
	public static void main(String[] args) {
		new Decompression().DecompressionKaas("../dist/scheme1.kaas","../dist/out/scheme");
	}
}
