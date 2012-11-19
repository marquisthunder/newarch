package com.thinkingtop.kaasservice;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class KaasServiceTest {

	@Test
	public void test() {
		KaasService ka = new KaasService();
		ExclusiveKeyService eks = ka.getExclusiveKeyServicePort();
		List<String> a = eks.test("afdsa");
		System.out.println(eks.test("afdsa"));
	}

}
