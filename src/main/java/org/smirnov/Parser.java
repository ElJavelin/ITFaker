package org.smirnov;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Parser {

    String [][] dataArr = new String[15][5000];
    String [] tagArr = new String[15];
    int [] sizeArr = new int[19];

    public void xmlParser(String locale)  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            try {
                try {
                    document = builder.parse(new File("D:\\Faker\\src\\" + locale +"_Data.xml"));
                }catch (FileNotFoundException fio){
                    throw new IllegalArgumentException();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        fillTagArray(document.getDocumentElement().getElementsByTagName("document_tags"));
        for (int i = 0 ; i < 15; i++){
            NodeList nodeList = document.getDocumentElement().getElementsByTagName(tagArr[i]);
            parseField(nodeList, i);
        }
    }

    private void fillTagArray(NodeList nodeList){
        Node curValues = nodeList.item(0);
        NamedNodeMap attributes = curValues.getAttributes();
        String[] valArr = attributes.getNamedItem("value").getNodeValue().split(",");
        for (int i = 0 ; i < valArr.length; i++){
            tagArr[i] = valArr[i].trim();
        }
    }

    private void parseField(NodeList nodeList, int tagIndex){
        Node curValues = nodeList.item(0);
        NamedNodeMap attributes = curValues.getAttributes();
        String[] valArr = attributes.getNamedItem("value").getNodeValue().split(",");
        sizeArr[tagIndex] = valArr.length;
        for (int j = 0; j < valArr.length; j++){
            dataArr[tagIndex][j] = valArr[j].trim();
        }
    }

    public String[][] getDataArr() {
        return dataArr;
    }

    public int[] getSizeArr() {
        return sizeArr;
    }
}
