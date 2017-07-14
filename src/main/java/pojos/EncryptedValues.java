package pojos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "EncryptedValues")
public class EncryptedValues {
    List <EncryptedValue>encryptedValue;

    public EncryptedValues() {
    }

    public EncryptedValues(List<EncryptedValue> encryptedValue) {
        this.encryptedValue = encryptedValue;
    }

    @XmlElement(name = "EncryptedValue")
    public List<EncryptedValue> getEncryptedValue() {
        return encryptedValue;
    }

    public void setEncryptedValue(List<EncryptedValue> encryptedValue) {
        this.encryptedValue = encryptedValue;
    }
}
