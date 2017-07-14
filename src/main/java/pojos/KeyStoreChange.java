package pojos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KeyStoreChange")
public class KeyStoreChange {
    EncryptedValues encryptedValues;
    KeyStores keyStores;

    public KeyStoreChange() {
    }

    public KeyStoreChange(EncryptedValues encryptedValues, KeyStores keyStores) {
        super();
        this.encryptedValues = encryptedValues;
        this.keyStores = keyStores;
    }

    @XmlElement(name = "EncryptedValues")
    public EncryptedValues getEncryptedValues() {
        return encryptedValues;
    }

    public void setEncryptedValues(EncryptedValues encryptedValues) {
        this.encryptedValues = encryptedValues;
    }

    @XmlElement(name = "KeyStores")
    public KeyStores getKeyStores() {
        return keyStores;
    }

    public void setKeyStores(KeyStores keyStores) {
        this.keyStores = keyStores;
    }
}
