package com.example.test;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.Constants;
//import org.apache.xml.security.utils.DigestUtils;
import org.apache.xml.security.utils.XMLUtils;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

public class SAMLResponseModifier {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        try {
        	Init.init();
            // Load XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Important for handling namespaces
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("SAMLResponse.xml"));
            
            
         // Encode special characters in the document
            //encodeSpecialCharacters(doc);
            
         // Remove specific character sequences from the document
//            removeCharacterSequences(doc, "&#13;");
//            removeCharacterSequences(doc, "&#xD;");
            AppUtils.removeWhitespaceAndSigns(doc);

            MessageDigest md = DigestUtils.getSha1Digest();
            
         // Canonicalize XML content
            String canonicalXml = AppUtils.canonicalizeXml(doc);
         // Print the canonicalized XML
            System.out.println("Canonicalized XML:");
            System.out.println(canonicalXml);
            
            String digVal = calculateDigestOneLoginUtil(canonicalXml);
            
            System.out.println("Digest Value: " + digVal);

            // Find the Assertion element
            NodeList assertionList = doc.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
            if (assertionList.getLength() > 0) {
                Element assertionElement = (Element) assertionList.item(0);

                // Calculate digest value for the assertion
                String assertionDigest = calculateDigest(assertionElement);

                // Find the DigestValue element and replace its value
                NodeList digestValueList = doc.getElementsByTagNameNS(Constants.SignatureSpecNS, "DigestValue");
                if (digestValueList.getLength() > 0) {
                    Element digestValueElement = (Element) digestValueList.item(0);
                    digestValueElement.setTextContent(digVal);
                }
            }

            // Write the modified XML document back to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("modified_saml_response.xml"));
            transformer.transform(source, result);

            System.out.println("SAMLResponse modified successfully.");

        } catch (ParserConfigurationException | TransformerException | org.xml.sax.SAXException | java.io.IOException | XMLSecurityException e) {
            e.printStackTrace();
        }
    }
    

    
    private static String calculateDigestOneLoginUtil(String input) throws NoSuchAlgorithmException {
    	MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
  
 
    
 // Method to remove specific character sequences from the text nodes of the XML document
    private static void removeCharacterSequences(Document document, String sequenceToRemove) {
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                String text = node.getNodeValue();
                text = text.replace(sequenceToRemove, "");
                node.setNodeValue(text);
            }
        }
    }
    
    // Method to calculate SHA-256 digest value
    private static String calculateDigest(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = digest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    
    
    private static String calculateDigest(Element element) throws XMLSecurityException {
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

    
    
 // Method to encode special characters in the text nodes of the XML document
    private static void encodeSpecialCharacters(Document document) {
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                String text = node.getNodeValue();
                text = text.replace("&", "&amp;")
                           .replace("<", "&lt;")
                           .replace(">", "&gt;")
                           .replace("'", "&apos;")
                           .replace("\"", "&quot;");
                node.setNodeValue(text);
            }
        }
    }

}

