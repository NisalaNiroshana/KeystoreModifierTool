import org.xml.sax.SAXException;
import pojos.KeyStoreChange;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class KeyStoreChangeTool {
    private final static Logger LOGGER = Logger.getLogger(KeyStoreChangeTool.class.getName());
    static Map<String, String> map;

    public static void main(String[] args) {
        executeTool();
    }

    private static void executeTool() {
        KeyStoreChange keyStoreChange;
        KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
        map = new HashMap();
        byte[] decryptedValue;
        try {
            keyStoreChange = readConfigXML();
            LOGGER.info("Started the decrypting and encrypting process");
            for (int i = 0; i < keyStoreChange.getEncryptedValues().getEncryptedValue().size(); i++) {
                decryptedValue = keyStoreUtils.decrypt(decode(
                        keyStoreChange.getEncryptedValues().getEncryptedValue().get(i).getContent()),
                        keyStoreChange.getKeyStores().getNewKeyStorePath(),
                        keyStoreChange.getKeyStores().getNewKeyStorePassword(),
                        keyStoreChange.getKeyStores().getNewKeyStoreAlias());

                String encryptedValue = keyStoreUtils.encrypt(decryptedValue,
                        keyStoreChange.getKeyStores().getOldKeyStorePath(),
                        keyStoreChange.getKeyStores().getOldKeyStorePassword(),
                        keyStoreChange.getKeyStores().getOldKeyStoreAlias());

                map.put(keyStoreChange.getEncryptedValues().getEncryptedValue().get(i).getId(), encryptedValue);
            }
            LOGGER.info("Completed the decrypting and encrypting process successfully");
            generateOutput();
        } catch (Exception e) {
            LOGGER.severe("Error in executing the tool. " + e.getCause());
        }
    }

    private static KeyStoreChange readConfigXML() throws KeyChangeException, IOException {
        try {
            LOGGER.info("Started reading the configuration file - keystoreChange.xml");
            Files.createFile(Paths.get("kk.txt"));
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(KeyChangeConstants.KEYSTORE_CHANGE_XSD));
            JAXBContext jaxbContext = JAXBContext.newInstance(KeyStoreChange.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setSchema(schema);
            File file = new File(KeyChangeConstants.KEYSTORE_CHANGE_XML);
            KeyStoreChange keyStoreChange = (KeyStoreChange) jaxbUnmarshaller.unmarshal(file);
            LOGGER.info("Completed reading the configuration file - keystoreChange.xml successfully");
            return keyStoreChange;
        } catch (JAXBException e) {
            throw new KeyChangeException("Error in reading the configuration XML file. " + e.getCause());
        } catch (SAXException e) {
            throw new KeyChangeException("Error in reading the configuration XML file. " + e.getCause());
        }
    }

    private static byte[] decode(String encryptedValue) {
        return org.apache.axiom.om.util.Base64.decode(encryptedValue);
    }

    private static void generateOutput() throws IOException {
        LOGGER.info("Started generating the output file - newEncryptedValues.xml");
        Path path = Paths.get(KeyChangeConstants.GENERATED_OUTPUT_FILE);
        Files.deleteIfExists(path);
        path = Files.createFile(path);

        Files.write(path, ("<NewEncryptedValues>\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(path, () -> map.entrySet().stream()
                .<CharSequence>map(e -> "\t<NewEncryptedValue id = \"" + e.getKey() + "\">" + e.getValue() +
                        "</NewEncryptedValue>")
                .iterator(), StandardOpenOption.APPEND);

        Files.write(path, ("</NewEncryptedValues>").getBytes(), StandardOpenOption.APPEND);
        LOGGER.info("Completed  generating the output file - newEncryptedValues.xml successfully");
    }
}
