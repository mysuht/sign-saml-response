package com.example.test;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.Constants;
//import org.apache.xml.security.utils.DigestUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.keyinfo.impl.X509KeyInfoGeneratorFactory;
import org.opensaml.xmlsec.signature.support.SignatureConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.xml.security.Init;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import com.onelogin.saml2.util.Util;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class DigestValueCalculator {
	public static void main(String[] args) {
        try {
        	
        	Init.init();
        	
            // Canonicalize the XML content
            String canonicalXml = canonicalizeXml(AppConstants.XML_CONTENT);
            
            canonicalXml = canonicalXml.replace("&#13;", "").replace("&#xD;", "");
            // Calculate the digest value
            String digestValue = AppUtils.calculateNewDigestValue(canonicalXml);

            System.out.println("Digest Value: " + digestValue);
          
            PrivateKey pk = null;
            // Util.sign(canonicalXml, pk, content);
            
            Document convertStringToDocument = Util.convertStringToDocument(canonicalXml);
            AppUtils.removeWhitespaceAndSigns(convertStringToDocument);
            AppUtils.calculateDigest(convertStringToDocument);
            // URI => pfxe68cdb80-0064-dd6e-0935-0db523c76cc8
            String modifiedDocStr = Util.convertDocumentToString(convertStringToDocument);
            
            System.out.println( Base64.getEncoder().encodeToString(modifiedDocStr.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// Method to retrieve content from a URI
    private static String getContentFromUri(String uri) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
    }
    
   
	
	private static void calculateDigest(Document doc, String digestValue) {
		// Find the Assertion element
        NodeList assertionList = doc.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
        if (assertionList.getLength() > 0) {
            Element assertionElement = (Element) assertionList.item(0);

            // Calculate digest value for the assertion
            String assertionDigest = null;
            try {
            	assertionDigest = AppUtils.calculateDigest(assertionElement);
			} catch (XMLSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Find the DigestValue element and replace its value
            NodeList digestValueList = doc.getElementsByTagNameNS(Constants.SignatureSpecNS, "DigestValue");
            if (digestValueList.getLength() > 0) {
                Element digestValueElement = (Element) digestValueList.item(0);
                digestValueElement.setTextContent(assertionDigest);
            }
        }
	}
	
	

    // Method to canonicalize XML content
    private static String canonicalizeXml(String xmlContent) throws XMLSecurityException, ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8))));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(outputStream));
        return outputStream.toString();
    }

    

}

