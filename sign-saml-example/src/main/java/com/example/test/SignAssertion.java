package com.example.test;

import java.io.ByteArrayInputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.impl.AssertionMarshaller;
import org.opensaml.saml.saml2.core.impl.ResponseMarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.onelogin.saml2.util.Constants;
import com.onelogin.saml2.util.Util;

import net.shibboleth.utilities.java.support.xml.SerializeSupport;

public class SignAssertion {


	public static void main(String[] args) {
		String astStr = null;
		PrivateKey privateKey = null;
		try {
			String pk = Util.formatPrivateKey(AppConstants.PRIVATE_KEY, false);
			
			System.out.println("privateKey >>> " + pk);
			privateKey = Util.loadPrivateKey(pk);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		X509Certificate cert = null;
		try {
			cert = Util.loadCert(AppConstants.CERTIFICATE);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("privateKey >>> " + privateKey);
//		System.out.println("cert >>> " + cert);
		
		Response response = null;
		// 1. Sign Assertion > Turn signed string back to Assertion
		AssertionMarshaller aMarshaller = new AssertionMarshaller();
		Assertion assertion = null;
		
		try {
			astStr = Util.addSign(aMarshaller.marshall(assertion), privateKey, cert, Constants.RSA_SHA1);
		} catch (XPathExpressionException | ParserConfigurationException | XMLSecurityException
				| MarshallingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		astStr = astStr.replace("&#13;", "").replace("&#xD;", "");
		
		try {
			assertion = (Assertion) stringTOobject(astStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 2. Add Assertion into Response
		response.getAssertions().add(assertion);
		// 3. Sign Response > Turn signed string back to Response
		ResponseMarshaller marshaller = new ResponseMarshaller();
		String resStr = null;
		try {
			resStr = Util.addSign(marshaller.marshall(response), privateKey, cert, Constants.RSA_SHA1);
		} catch (XPathExpressionException | ParserConfigurationException | XMLSecurityException
				| MarshallingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		astStr = astStr.replace("&#13;", "").replace("&#xD;", "");
		
		try {
			response = (Response) stringTOobject(resStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 4. To XMLString
		try {
			String samlStr = SerializeSupport.nodeToString(marshaller.marshall(response));
		} catch (MarshallingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	private static Object stringTOobject(String samlStr) throws Exception {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(new ByteArrayInputStream(samlStr.getBytes("UTF-8")));  
	    Element samlElement = document.getDocumentElement();
	    Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(samlElement);
	    return unmarshaller.unmarshall(samlElement);
	}
}
