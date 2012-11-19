package test;

import java.util.List;

import org.springframework.schema.beans.KaasService;

import com.thinkingtop.kaasservice.ExclusiveKeyService;

public class Test {
	public static void main(String[] args) {
		KaasService ka = new KaasService();
		ExclusiveKeyService eks = ka.getExclusiveKeyServicePort();
		List<String> a = eks.test("afdsa");
		System.out.println(eks.test("afdsa"));
	}
}
