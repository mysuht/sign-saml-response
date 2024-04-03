package com.example.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.xml.security.Init;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AppUtils {
	
	public static String calculateNewDigestValue(String samlResponse) {
        try {
            // Parse the SAMLResponse string into a DOM document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(samlResponse.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);

            // Extract the Assertion element
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//Assertion");
            Element assertionElement = (Element) expr.evaluate(doc, XPathConstants.NODE);

            // Canonicalize the Assertion element
            Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            canon.canonicalizeSubtree(assertionElement, bos);
            byte[] canonBytes = bos.toByteArray();

            // Compute SHA-1 hash
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digestBytes = md.digest(canonBytes);

            // Base64 encode the hash value
            String newDigestValue = Base64.getEncoder().encodeToString(digestBytes);

            return newDigestValue;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	// Method to remove whitespace characters and signs from the text nodes of the XML document
    public static void removeWhitespaceAndSigns(Document document) {
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                String text = node.getNodeValue();
                text = text.replaceAll("\\s+", ""); // Remove whitespace characters
                text = text.replaceAll("\\p{Punct}", ""); // Remove signs (punctuation)
                node.setNodeValue(text);
            }
        }
    }
	
//	public static String calculateNewDigestValue(String samlResponse) {
//        try {
//            // Parse the SAMLResponse string into a DOM document
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setNamespaceAware(true);
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            ByteArrayInputStream input = new ByteArrayInputStream(samlResponse.getBytes(StandardCharsets.UTF_8));
//            Document doc = builder.parse(input);
//
//            // Extract the Assertion element
//            XPathFactory xpathFactory = XPathFactory.newInstance();
//            XPath xpath = xpathFactory.newXPath();
//            XPathExpression expr = xpath.compile("//Assertion");
//            Element assertionElement = (Element) expr.evaluate(doc, XPathConstants.NODE);
//
//            // Canonicalize the Assertion element
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
//            byte[] canonBytes = canon.canonicalizeSubtree(assertionElement);
//
//            // Compute SHA-1 hash
//            MessageDigest md = MessageDigest.getInstance("SHA-1");
//            byte[] digestBytes = md.digest(canonBytes);
//
//            // Base64 encode the hash value
//            String newDigestValue = Base64.getEncoder().encodeToString(digestBytes);
//
//            return newDigestValue;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
	
	public static String canonicalizeXml(Document document) throws CanonicalizationException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Canonicalizer canonicalizer = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
            canonicalizer.canonicalizeSubtree(document, outputStream);
            return outputStream.toString();
        } catch (Exception e) {
            throw new CanonicalizationException("Error canonicalizing XML", e);
        }
    }

    public static String replaceDigestValue(String samlResponse, String newDigestValue) {
        try {
            // Replace existing digest value in the SAMLResponse string
            String oldDigestValue = getExistingDigestValue(samlResponse);
            if (oldDigestValue != null) {
                return samlResponse.replace(oldDigestValue, newDigestValue);
            } else {
                System.out.println("Failed to find existing digest value.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getExistingDigestValue(String samlResponse) {
        try {
            // Parse the SAMLResponse string into a DOM document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(samlResponse.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);

            // Extract the digest value
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//ds:DigestValue");
            String digestValue = (String) expr.evaluate(doc, XPathConstants.STRING);

            return digestValue;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
//	public static String calculateDigestValue(String signedSAMLAssertion) {
//        try {
//            // Parse the signed SAML assertion into a DOM document
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setNamespaceAware(true);
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            ByteArrayInputStream input = new ByteArrayInputStream(signedSAMLAssertion.getBytes(StandardCharsets.UTF_8));
//            org.w3c.dom.Document doc = builder.parse(input);
//
//            // Canonicalize the XML document
//            Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
//            byte[] canonBytes = canon.canonicalizeSubtree(doc);
//
//            // Compute SHA-1 hash
//            MessageDigest md = MessageDigest.getInstance("SHA-1");
//            byte[] digestBytes = md.digest(canonBytes);
//
//            // Base64 encode the hash value
//            String digestValue = Base64.getEncoder().encodeToString(digestBytes);
//
//            return digestValue;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

	
	 public static void calculateDigest(Document doc) {
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
	 
	public static String calculateDigest(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
	
	public static String calculateDigest(Element element) throws XMLSecurityException  {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(element), new StreamResult(outputStream));
            byte[] xmlBytes = outputStream.toByteArray();
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = digest.digest(xmlBytes);
            return AppUtils.bytesToHex(messageDigest);
        } catch (Exception e) {
            throw new XMLSecurityException("Error calculating digest", e);
        }
    }
	
	// Method to convert byte array to hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
