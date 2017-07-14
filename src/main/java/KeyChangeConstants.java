/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * This class holds Constants related to key store change recovery of registry data.
 */
public final class KeyChangeConstants {

    /**
     * Key store type used.
     */
    public static final String KEY_STORE_TYPE = "JKS";

    /**
     * Cipher text transformation type.
     */
    public static final String CIPHER_TRANSFORMATION_METHOD = "RSA";

    /**
     * Cipher provider.
     */
    public static final String CIPHER_PROVIDER = "BC";

    /**
     * Cipher operation mode constant for encryption.
     */
    public static final int OPERATION_MODE_ENCRYPTION = 1;

    /**
     * Cipher operation mode constant for decryption.
     */
    public static final int OPERATION_MODE_DECRYPTION = 2;

    public static final String KEYSTORE_CHANGE_XSD = "keystoreChange.xsd";

    public static final String KEYSTORE_CHANGE_XML = "keystoreChange.xml";

    public static final String GENERATED_OUTPUT_FILE = "newEncryptedValues.xml";

}
