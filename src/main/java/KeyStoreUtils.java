import org.apache.axiom.om.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import pojos.KeyStoreChange;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.*;


public class KeyStoreUtils {

    static KeyStoreChange keyStoreChange;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static KeyStore getKeyStore(final File keystoreFile,
                                        final String password, final String keyStoreType)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException, NoSuchProviderException, NoSuchPaddingException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            KeyChangeException {
        if (null == keystoreFile) {
            throw new IllegalArgumentException("Keystore url may not be null");
        }

        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        return keystore;
    }

    protected static String encrypt(byte[] plainTextBytes, String keyPath, String keyPass, String keyAlias) throws
            Exception {
        KeyStore keyStore = getKeyStore(new File(keyPath), keyPass, KeyChangeConstants.KEY_STORE_TYPE);

        loadKeyStore(keyStore, keyPath, keyPass);
        // Get Cipher instance.
        Cipher cipher = getCypherInstance();
        // Get public key.
        PublicKey publicKey = getPublicKey(keyStore, keyAlias);
        // Initialize cipher instance for encryption mode.
        initCipherForEncryption(cipher, publicKey);
        // Encrypt data.
        byte[] encryptedValue = cipherDoFinal(cipher, plainTextBytes);
        return Base64.encode(encryptedValue);
    }

    protected static byte[] decrypt(byte[] cipherText, String keyPath, String keyPass, String keyAlias)
            throws Exception {
        // Get key store.
        KeyStore keyStore = getKeyStore(new File(keyPath), keyPass, "JKS");

        loadKeyStore(keyStore, keyPath, keyPass);
        // Get Cipher instance.
        Cipher cipher = getCypherInstance();
        // Get private key.
        PrivateKey privateKey = getPrivateKey(keyStore, keyPass, keyAlias);
        // Initialize cipher instance for decryption mode.
        initCipherForDecryption(cipher, privateKey);
        // Decrypt data.
        byte[] decryptedValue = cipherDoFinal(cipher, cipherText);
        return decryptedValue;
    }

    private static void initCipherForEncryption(Cipher cipher, PublicKey publicKey) throws
            KeyChangeException {
        try {
            cipher.init(KeyChangeConstants.OPERATION_MODE_ENCRYPTION, publicKey);
        } catch (InvalidKeyException e) {
            throw new KeyChangeException("Invalid Keys (invalid encoding, wrong length, uninitialized) in certificate.",
                    e);
        }
    }


    private static void loadKeyStore(KeyStore keyStore, String keyPath, String keyPass) throws KeyChangeException {
        String file = new File(keyPath).getAbsolutePath();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            // fileInputStream cannot be null since keyPath is not null and file is not null.
            keyStore.load(fileInputStream, keyPass.toCharArray());
        } catch (FileNotFoundException e) {
            throw new KeyChangeException("Attempt to open the file failed in:" + keyPath, e);
        } catch (CertificateException e) {
            throw new KeyChangeException("Certificate issue in: " + keyPath, e);
        } catch (NoSuchAlgorithmException e) {
            throw new KeyChangeException("Cryptographic algorithm is requested but is not available in the "
                    + "environment.", e);
        } catch (IOException e) {
            throw new KeyChangeException("Interrupted I/O operations when reading key store.", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {

                }
            }
        }
    }

    private static Cipher getCypherInstance() throws KeyChangeException {
        try {
            return Cipher.getInstance(KeyChangeConstants.CIPHER_TRANSFORMATION_METHOD,
                    KeyChangeConstants.CIPHER_PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new KeyChangeException("Cryptographic algorithm is requested but is not available in the "
                    + "environment.", e);
        } catch (NoSuchProviderException e) {
            throw new KeyChangeException("Security provider for BC is not in the environment.", e);
        } catch (NoSuchPaddingException e) {
            throw new KeyChangeException("Padding mechanism is not available in environment.", e);
        }
    }

    private static PublicKey getPublicKey(KeyStore keyStore, String keyAlias) throws KeyChangeException {
        java.security.cert.Certificate[] certificateArray;
        try {
            certificateArray = keyStore.getCertificateChain(keyAlias);
        } catch (KeyStoreException e) {
            throw new KeyChangeException("No provider supports a KeyStore Service provider interface implementation "
                    + "for JKS in key store.", e);
        }
        if (certificateArray != null && certificateArray.length > 0) {
            return certificateArray[0].getPublicKey();
        } else {
            throw new KeyChangeException("There are no certificates in key store.");
        }
    }

    private static PrivateKey getPrivateKey(KeyStore keyStore, String keyPass, String keyAlias)
            throws KeyChangeException {
        try {
            return (PrivateKey) keyStore.getKey(keyAlias, keyPass.toCharArray());
        } catch (KeyStoreException e) {
            throw new KeyChangeException("No provider supports a KeyStore Service provider interface implementation "
                    + "for JKS in key store.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new KeyChangeException("Cryptographic algorithm is requested but is not available in the "
                    + "environment.", e);
        } catch (UnrecoverableKeyException e) {
            throw new KeyChangeException("Key store cannot be recovered.", e);
        }
    }

    private static void initCipherForDecryption(Cipher cipher, PrivateKey privateKey) throws
            KeyChangeException {
        try {
            cipher.init(KeyChangeConstants.OPERATION_MODE_DECRYPTION, privateKey);
        } catch (InvalidKeyException e) {
            throw new KeyChangeException("Invalid Keys (invalid encoding, wrong length, uninitialized) in certificate.",
                    e);
        }
    }

    private static byte[] cipherDoFinal(Cipher cipher, byte[] byteStream) throws KeyChangeException {
        try {
            return cipher.doFinal(byteStream);
        } catch (IllegalBlockSizeException e) {
            throw new KeyChangeException("Length of data(byte[]) provided to the cipher is incorrect.", e);
        } catch (BadPaddingException e) {
            throw new KeyChangeException("Input data(byte[]) not padded properly.", e);
        }
    }
}