package com.thinkingtop.kaas.etl.validator;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkingtop.kaas.etl.reader.PropertiesReader;
import com.thinkingtop.kaas.etl.stream.StreamUtil;

public class ValidateXML {
	private static final Logger logger = LoggerFactory.getLogger(ValidateXML.class.getName());

	public boolean validate() {
		try {
			String strLang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(strLang);
/*
 * if the mid.xsd file is under the directory of "src/main/resources"(native directory)
 * use "InputStream isSchema = getClass().getResourceAsStream("/mid.xsd");" to get the stream easily.
 * 
 * 
 */
			InputStream isSchema = getClass().getResourceAsStream(PropertiesReader.getProperty("schema"));
			StreamSource ss = new StreamSource(isSchema);
			Schema schema = factory.newSchema(ss);

			Validator validator = schema.newValidator();

			/*
			 * otherwise, the mid.xml is not a native file, we have to use URL address to get the stream.
			 *  
			 */
			InputStream isXML = StreamUtil.getEtlXMLStream();

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
