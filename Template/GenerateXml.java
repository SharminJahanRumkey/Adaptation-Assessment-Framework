import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateXml {
    private DocumentBuilderFactory documentFactory;
    private DocumentBuilder dBuilder;
    private Document document;
    private InstantiateXml instantiateXml;

    public GenerateXml() {}

    public GenerateXml(String paramFilePath, String xmlFilePath)
    {
        System.out.println("hi");
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("GSN");
            document.appendChild(root);

            instantiateXml = new InstantiateXml("./"+paramFilePath);


            LoadParamsForXml SAC = new LoadParamsForXml(document, root, instantiateXml);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("./"+xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public Document getDocument() {return document; }

    public InstantiateXml getInstantiateXml() {
        return instantiateXml;
    }
}
