package ru.netology;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Document {
    private static List<Employee> employees = new ArrayList<>( );

    public static List<Employee> parseXML(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
            DocumentBuilder builder = factory.newDocumentBuilder( );
            org.w3c.dom.Document document = builder.parse(file);
            NodeList nodeList = document.getDocumentElement( ).getChildNodes( );
            for (int i = 0; i < nodeList.getLength( ); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType( ) == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Integer id = Integer.parseInt(elem.getElementsByTagName("id")
                            .item(0).getChildNodes( ).item(0).getNodeValue( ));
                    String firstname = elem.getElementsByTagName("firstName")
                            .item(0).getChildNodes( ).item(0).getNodeValue( );
                    String lastname = elem.getElementsByTagName("lastName").item(0)
                            .getChildNodes( ).item(0).getNodeValue( );
                    String country = elem.getElementsByTagName("country").item(0)
                            .getChildNodes( ).item(0).getNodeValue( );
                    Integer age = Integer.parseInt(elem.getElementsByTagName("age")
                            .item(0).getChildNodes( ).item(0).getNodeValue( ));

                    employees.add(new Employee(id, firstname, lastname, country, age));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace( );
        }
        return employees;
    }


    public static void createXmlFile() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
        try {
            DocumentBuilder builder = factory.newDocumentBuilder( );
            org.w3c.dom.Document document = builder.newDocument( );
            Element staff = document.createElement("staff");
            document.appendChild(staff);
            Element employee = document.createElement("employee");
            staff.appendChild(employee);
            Element id = document.createElement("id");
            id.setTextContent("1");
            employee.appendChild(id);
            Element firstName = document.createElement("firstName");
            firstName.setTextContent("John");
            employee.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            lastName.setTextContent("Smith");
            employee.appendChild(lastName);
            Element country = document.createElement("country");
            country.setTextContent("USA");
            employee.appendChild(country);
            Element age = document.createElement("age");
            age.setTextContent("25");
            employee.appendChild(age);
            Element employee1 = document.createElement("employee");
            staff.appendChild(employee1);
            Element id1 = document.createElement("id");
            id1.setTextContent("2");
            employee1.appendChild(id1);
            Element firstName1 = document.createElement("firstName");
            firstName1.setTextContent("Ivan");
            employee1.appendChild(firstName1);
            Element lastName1 = document.createElement("lastName");
            lastName1.setTextContent("Petrov");
            employee1.appendChild(lastName1);
            Element country1 = document.createElement("country");
            country1.setTextContent("RU");
            employee1.appendChild(country1);
            Element age1 = document.createElement("age");
            age1.setTextContent("23");
            employee1.appendChild(age1);
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("data.xml"));
            TransformerFactory transformerFactory = TransformerFactory.newInstance( );
            Transformer transformer = transformerFactory.newTransformer( );
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace( );
        } catch (TransformerException e) {
            e.printStackTrace( );
        }
    }
}
