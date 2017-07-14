package pojos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KeyStores")
public class KeyStores {
    String newKeyStorePath;
    String newKeyStorePassword;
    String newKeyStoreAlias;
    String oldKeyStorePath;
    String oldKeyStorePassword;
    String oldKeyStoreAlias;

    public KeyStores() {
    }

    public KeyStores(String newKeyStorePath, String newKeyStorePassword, String newKeyStoreAlias,
                     String oldKeyStorePath, String oldKeyStorePassword, String oldKeyStoreAlias) {
        super();
        this.newKeyStorePath = newKeyStorePath;
        this.newKeyStorePassword = newKeyStorePassword;
        this.newKeyStoreAlias = newKeyStoreAlias;
        this.oldKeyStorePath = oldKeyStorePath;
        this.oldKeyStorePassword = oldKeyStorePassword;
        this.oldKeyStoreAlias = oldKeyStoreAlias;
    }

    @XmlElement(name = "NewKeyStorePath")
    public String getNewKeyStorePath() {
        return newKeyStorePath;
    }

    public void setNewKeyStorePath(String newKeyStorePath) {
        this.newKeyStorePath = newKeyStorePath;
    }

    @XmlElement(name = "NewKeyStorePassword")
    public String getNewKeyStorePassword() {
        return newKeyStorePassword;
    }

    public void setNewKeyStorePassword(String newKeyStorePassword) {
        this.newKeyStorePassword = newKeyStorePassword;
    }

    @XmlElement(name = "NewKeyStoreKeyAlias")
    public String getNewKeyStoreAlias() {
        return newKeyStoreAlias;
    }

    public void setNewKeyStoreAlias(String newKeyStoreAlias) {
        this.newKeyStoreAlias = newKeyStoreAlias;
    }

    @XmlElement(name = "OldKeyStorePath")
    public String getOldKeyStorePath() {
        return oldKeyStorePath;
    }

    public void setOldKeyStorePath(String oldKeyStorePath) {
        this.oldKeyStorePath = oldKeyStorePath;
    }

    @XmlElement(name = "OldKeyStorePassword")
    public String getOldKeyStorePassword() {
        return oldKeyStorePassword;
    }

    public void setOldKeyStorePassword(String oldKeyStorePassword) {
        this.oldKeyStorePassword = oldKeyStorePassword;
    }

    @XmlElement(name = "OldKeyStoreKeyAlias")
    public String getOldKeyStoreAlias() {
        return oldKeyStoreAlias;
    }

    public void setOldKeyStoreAlias(String oldKeyStoreAlias) {
        this.oldKeyStoreAlias = oldKeyStoreAlias;
    }
}
