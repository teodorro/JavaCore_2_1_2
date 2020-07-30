package netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class App {
    public static void main(String[] args) {
        String fileName = "data.xml";
        List<Employee> employees = parseXml(fileName);
        String json = employeesToJson(employees);
        saveJson(json);
    }

    public static List<Employee> parseXml(String fileName) {
        List<Employee> employees = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            NodeList employeeNodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < employeeNodes.getLength(); i++) {
                Node employeeNode = employeeNodes.item(i);
                if (employeeNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList propNodes = employeeNode.getChildNodes();
                    Employee employee = new Employee();
                    Field[] fields = employee.getClass().getDeclaredFields();
                    for (int j = 0; j < propNodes.getLength(); j++) {
                        Node propNode = propNodes.item(j);
                        if (propNode.getNodeType() == Node.ELEMENT_NODE) {
                            Field field = Arrays.stream(fields).filter(x -> x.getName().equals(propNode.getNodeName())).findFirst().get();
                            field.set(employee, mapValueString(propNode.getTextContent(), field.getType()));
                        }
                    }
                    employees.add(employee);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static <T> T mapValueString(String valueString, Class<T> targetType){
        if (valueString == null) {
            return null;
        }
        else if(targetType.equals(String.class)) {
            return (T)valueString;
        }
        else if (targetType.equals(Integer.class)) {
            Integer i;
            try {
                i = Integer.parseInt(valueString);
            }
            catch (NumberFormatException nfe) {
                throw new RuntimeException("Failed to parse value string into Integer object. String was " + valueString + ".", nfe);
            }
            return (T)i;
        }
        else if (targetType.equals(Long.class)) {
            Long l;
            try {
                l = Long.parseLong(valueString);
            }
            catch (NumberFormatException nfe) {
                throw new RuntimeException("Failed to parse value string into Long object. String was " + valueString + ".", nfe);
            }
            return (T)l;
        }
        else {
            throw new RuntimeException("Unsupported java type " + targetType.getName() + ".");
        }
    }

    public static void saveJson(String json) {
        try(FileWriter writer = new FileWriter("data.json")){
            writer.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String employeesToJson(List<Employee> employees) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (Employee employee : employees)
            sb.append(gson.toJson(employee) + "\n");
        sb.append("]");
        return sb.toString();
    }
}

