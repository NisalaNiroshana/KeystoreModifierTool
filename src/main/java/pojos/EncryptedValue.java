package pojos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "EncryptedValue")
public class EncryptedValue {
    String id;
    String content;

    public EncryptedValue() {
    }

    public EncryptedValue(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @XmlAttribute(name = "id" ,required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlValue
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
