import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

// Interface definition
interface DataProcessor {
    void process(String data);
}

// Simple JSON Processor
class JsonProcessor implements DataProcessor {
    @Override
    public void process(String data) {
        try {
            // Remove curly braces and split by comma
            String cleanData = data.replaceAll("[{}]", "").trim();
            String[] pairs = cleanData.split(",");

            for (String pair : pairs) {
                // Split each pair into key and value
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replaceAll("\"", "");
                    String value = keyValue[1].trim().replaceAll("\"", "");
                    System.out.println(key + ": " + value);
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing JSON: " + e.getMessage());
        }
    }
}

// XML Processor using built-in DOM parser
class XMLProcessor implements DataProcessor {
    @Override
    public void process(String data) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(data)));

            // Get root element
            Element root = doc.getDocumentElement();
            root.normalize();

            // Process all child nodes
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(node.getNodeName() + ": " + node.getTextContent());
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing XML: " + e.getMessage());
        }
    }
}

// Main class with example usage
public class Main {
    public static void main(String[] args) {
        // Test data
        String jsonData = "{\"name\": \"COLLINS\", \"age\": \"22\", \"city\": \"manchester\"}";
        String xmlData = "<?xml version=\"1.0\"?><person><name>alexia</name><age>19</age><city>London</city></person>";

        // Process JSON
        System.out.println("Processing JSON:");
        DataProcessor jsonProcessor = new JsonProcessor();
        jsonProcessor.process(jsonData);

        System.out.println("\nProcessing XML:");
        DataProcessor xmlProcessor = new XMLProcessor();
        xmlProcessor.process(xmlData);
    }
}