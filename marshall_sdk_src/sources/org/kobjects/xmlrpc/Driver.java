package org.kobjects.xmlrpc;

import java.io.FileReader;
import org.kobjects.xml.XmlReader;

/* JADX INFO: loaded from: classes2.dex */
public class Driver {
    public static void main(String[] strArr) throws Exception {
        new XmlRpcParser(new XmlReader(new FileReader(strArr[0]))).parseResponse();
    }
}
