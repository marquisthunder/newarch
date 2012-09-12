package com.thinkingtop.kaas.etl.validator;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateXML {
	private static final Logger logger = LoggerFactory.getLogger(ValidateXML.class.getName());

	public boolean validate() {
		try {
			String strLang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(strLang);

			InputStream isSchema = getClass().getResourceAsStream("/mid.xsd");
			StreamSource ss = new StreamSource(isSchema);
			Schema schema = factory.newSchema(ss);

			Validator validator = schema.newValidator();

			InputStream isXML = getClass().getResourceAsStream("/mid.xml");

			StreamSource source = new StreamSource(isXML);
			validator.validate(source);
			logger.info("Result : Valid!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Result : Invalid!");
			return false;
		}
	}

	/*public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		ValidateXML sv = new ValidateXML();
		sv.validate();
	}*/

}
