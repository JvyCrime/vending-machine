package com.magtek.mobile.android.mtlib.config;

import android.content.Context;
import android.net.Uri;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/* JADX INFO: loaded from: classes.dex */
public class ConfigurationWS {
    private static final String METHOD_GETCONFIGURATION = "GetDeviceConfigWithAuthentication";
    private static final String NAMESPACE_GETCONFIGURATION = "http://ns.magtek.com/deviceconfig/";
    private static final int OBJECT_TYPE_CONFIGPARAMS = 3;
    private static final int OBJECT_TYPE_DEVICEINFO = 0;
    private static final int OBJECT_TYPE_READERTYPE = 2;
    private static final int OBJECT_TYPE_STATUSCODE = 1;
    private static final String SOAP_ACTION_GETCONFIGURATION = "http://ns.magtek.com/deviceconfig/GetDeviceConfigWithAuthentication";

    private void debugMsg(String str) {
    }

    public static String ReadSettings(Context context, String str) throws IOException {
        FileInputStream fileInputStreamOpenFileInput = context.openFileInput(str);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStreamOpenFileInput);
        char[] cArr = new char[fileInputStreamOpenFileInput.available()];
        inputStreamReader.read(cArr);
        String str2 = new String(cArr);
        inputStreamReader.close();
        fileInputStreamOpenFileInput.close();
        return str2;
    }

    public static void WriteSettings(Context context, String str, String str2) throws IOException {
        FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(str2, 0);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStreamOpenFileOutput);
        outputStreamWriter.write(str);
        outputStreamWriter.close();
        fileOutputStreamOpenFileOutput.close();
    }

    public String setConfigurationResponse(SoapObject soapObject, ProcessMessageResponse processMessageResponse) {
        StatusCode statusCode = null;
        try {
            ResponsePayload responsePayload = new ResponsePayload();
            ArrayOfConfigParam arrayOfConfigParam = new ArrayOfConfigParam();
            ArrayOfSCRAConfiguration arrayOfSCRAConfiguration = new ArrayOfSCRAConfiguration();
            int i = 1;
            if (soapObject.getPropertyCount() >= 1) {
                SoapObject soapObject2 = (SoapObject) soapObject.getProperty("Payload");
                if (soapObject2.getPropertyCount() >= 1) {
                    statusCode = new StatusCode((SoapObject) soapObject2.getProperty("StatusCode"));
                    if (statusCode.Number == 0) {
                        SoapObject soapObject3 = (SoapObject) soapObject2.getProperty("SCRAConfigurations");
                        if (soapObject3.getPropertyCount() >= 1) {
                            int i2 = 0;
                            while (i2 < soapObject3.getPropertyCount()) {
                                SoapObject soapObject4 = (SoapObject) soapObject3.getProperty(i2);
                                if (soapObject4.getPropertyCount() >= i) {
                                    SCRAConfigurationReaderType sCRAConfigurationReaderType = new SCRAConfigurationReaderType((SoapObject) soapObject4.getProperty("ReaderType"));
                                    SCRAConfigurationDeviceInfo sCRAConfigurationDeviceInfo = new SCRAConfigurationDeviceInfo((SoapObject) soapObject4.getProperty("DeviceInfo"));
                                    StatusCode statusCode2 = new StatusCode((SoapObject) soapObject4.getProperty("StatusCode"));
                                    SoapObject soapObject5 = (SoapObject) soapObject4.getProperty("ConfigParams");
                                    if (soapObject5.getPropertyCount() >= i) {
                                        for (int i3 = 0; i3 < soapObject5.getPropertyCount(); i3++) {
                                            arrayOfConfigParam.add(new ConfigParam((SoapObject) soapObject5.getProperty(i3)));
                                        }
                                    }
                                    SCRAConfiguration sCRAConfiguration = new SCRAConfiguration();
                                    sCRAConfiguration.StatusCode = statusCode2;
                                    sCRAConfiguration.DeviceInfo = sCRAConfigurationDeviceInfo;
                                    sCRAConfiguration.ReaderType = sCRAConfigurationReaderType;
                                    sCRAConfiguration.ConfigParams = arrayOfConfigParam;
                                    arrayOfSCRAConfiguration.add(sCRAConfiguration);
                                }
                                i2++;
                                i = 1;
                            }
                        }
                        responsePayload.Version = soapObject2.getPrimitivePropertyAsString("Version");
                    }
                }
                responsePayload.StatusCode = statusCode;
                responsePayload.SCRAConfigurations = arrayOfSCRAConfiguration;
                processMessageResponse.Payload = responsePayload;
            }
            return "OK";
        } catch (Exception e) {
            return "Error: Setting Configuration Result:" + e.getMessage();
        }
    }

    public String setConfigurationResponse(String str, ProcessMessageResponse processMessageResponse) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(str)));
            document.getDocumentElement().normalize();
            return getConfigParams(document, processMessageResponse);
        } catch (Exception e) {
            return "Error: Setting Configuration Result:" + e.getMessage();
        }
    }

    public String getConfiguration(String str, String str2, String str3, ProcessMessageRequest processMessageRequest, ProcessMessageResponse processMessageResponse, int i) {
        String configurationXML;
        String str4 = "OK";
        debugMsg("+ getConfiguration");
        try {
            configurationXML = getConfigurationXML(str, str2, str3, processMessageRequest, processMessageResponse, i);
        } catch (Exception e) {
            e = e;
        }
        try {
            if (configurationXML.startsWith("Error:")) {
                str4 = configurationXML;
            }
        } catch (Exception e2) {
            e = e2;
            str4 = configurationXML;
            if (e.getMessage() != null) {
                str4 = "Error: getConfiguration :" + e.getMessage();
            }
        }
        debugMsg("- getConfiguration");
        return str4;
    }

    public String getConfigurationXML(String str, String str2, String str3, ProcessMessageRequest processMessageRequest, ProcessMessageResponse processMessageResponse, int i) {
        SoapObject soapObject = new SoapObject(NAMESPACE_GETCONFIGURATION, METHOD_GETCONFIGURATION);
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(110);
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("Request");
        propertyInfo.setValue(processMessageRequest);
        propertyInfo.setType(processMessageRequest.getClass());
        soapObject.addProperty(propertyInfo);
        PropertyInfo propertyInfo2 = new PropertyInfo();
        propertyInfo2.setName("Username");
        propertyInfo2.setValue(str);
        propertyInfo2.setType(str.getClass());
        soapObject.addProperty(propertyInfo2);
        PropertyInfo propertyInfo3 = new PropertyInfo();
        propertyInfo3.setName("Password");
        propertyInfo3.setValue(str2);
        propertyInfo3.setType(str2.getClass());
        soapObject.addProperty(propertyInfo3);
        soapSerializationEnvelope.dotNet = true;
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        soapSerializationEnvelope.addMapping(NAMESPACE_GETCONFIGURATION, "ProcessMessageRequest", new ProcessMessageRequest().getClass());
        soapSerializationEnvelope.addMapping(NAMESPACE_GETCONFIGURATION, "RequestPayload", new RequestPayload().getClass());
        soapSerializationEnvelope.addMapping(NAMESPACE_GETCONFIGURATION, "SCRAConfigurationDeviceInfo", new SCRAConfigurationDeviceInfo().getClass());
        soapSerializationEnvelope.addMapping(NAMESPACE_GETCONFIGURATION, "SCRAConfigurationReaderType", new SCRAConfigurationReaderType().getClass());
        Uri uri = Uri.parse(str3);
        HttpsTLS12TransportSE httpsTLS12TransportSE = new HttpsTLS12TransportSE(uri.getHost(), uri.getPort(), uri.getPath(), i);
        try {
            httpsTLS12TransportSE.debug = true;
            httpsTLS12TransportSE.call(SOAP_ACTION_GETCONFIGURATION, soapSerializationEnvelope);
            SoapObject soapObject2 = (SoapObject) soapSerializationEnvelope.getResponse();
            String configurationResponse = processMessageResponse != null ? setConfigurationResponse((SoapObject) soapSerializationEnvelope.getResponse(), processMessageResponse) : "OK";
            return (configurationResponse.startsWith("Error:") || soapObject2 == null) ? configurationResponse : httpsTLS12TransportSE.responseDump;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    void FillDeviceInfo(SCRAConfigurationDeviceInfo sCRAConfigurationDeviceInfo, String str, String str2) {
        if (str.equalsIgnoreCase("Device")) {
            sCRAConfigurationDeviceInfo.setProperty(4, str2);
            return;
        }
        if (str.equalsIgnoreCase("Model")) {
            sCRAConfigurationDeviceInfo.setProperty(5, str2);
            return;
        }
        if (str.equalsIgnoreCase("Firmware")) {
            sCRAConfigurationDeviceInfo.setProperty(2, str2);
            return;
        }
        if (str.equalsIgnoreCase("Platform")) {
            sCRAConfigurationDeviceInfo.setProperty(3, str2);
            return;
        }
        if (str.equalsIgnoreCase("Product")) {
            sCRAConfigurationDeviceInfo.setProperty(6, str2);
            return;
        }
        if (str.equalsIgnoreCase("Release")) {
            sCRAConfigurationDeviceInfo.setProperty(1, str2);
        } else if (str.equalsIgnoreCase("SDK")) {
            sCRAConfigurationDeviceInfo.setProperty(0, str2);
        } else if (str.equalsIgnoreCase("Status")) {
            sCRAConfigurationDeviceInfo.setProperty(7, str2);
        }
    }

    void FillReaderType(SCRAConfigurationReaderType sCRAConfigurationReaderType, String str, String str2) {
        if (str.equalsIgnoreCase("Name")) {
            sCRAConfigurationReaderType.setProperty(2, str2);
            return;
        }
        if (str.equalsIgnoreCase("Type")) {
            sCRAConfigurationReaderType.setProperty(1, str2);
        } else if (str.equalsIgnoreCase("Version")) {
            sCRAConfigurationReaderType.setProperty(0, str2);
        } else if (str.equalsIgnoreCase("SDK")) {
            sCRAConfigurationReaderType.setProperty(3, str2);
        }
    }

    void FillStatusCode(StatusCode statusCode, String str, String str2) {
        if (str.equalsIgnoreCase("Name")) {
            statusCode.Description = str2;
            return;
        }
        if (str.equalsIgnoreCase("Type")) {
            try {
                statusCode.Number = Integer.parseInt(str2);
            } catch (Exception unused) {
            }
        } else if (str.equalsIgnoreCase("Version")) {
            statusCode.Version = str2;
        }
    }

    void FillConfigParam(ConfigParam configParam, String str, String str2) {
        if (str.equalsIgnoreCase("Name")) {
            configParam.Name = str2;
        } else if (str.equalsIgnoreCase("Type")) {
            configParam.Type = str2;
        } else if (str.equalsIgnoreCase("Value")) {
            configParam.Value = str2;
        }
    }

    private String setConfigurationObject(SCRAConfiguration sCRAConfiguration, Node node, int i) {
        NodeList elementsByTagName;
        Element element;
        try {
            Element element2 = (Element) node;
            ConfigParam configParam = null;
            if (i == 0) {
                elementsByTagName = element2.getElementsByTagName("DeviceInfo");
            } else if (i == 1) {
                elementsByTagName = element2.getElementsByTagName("StatusCode");
            } else if (i == 2) {
                elementsByTagName = element2.getElementsByTagName("ReaderType");
            } else {
                elementsByTagName = i != 3 ? null : element2.getElementsByTagName("ConfigParam");
            }
            if (elementsByTagName == null) {
                return "Error: Invalid XML Response:Could Not Set Configuration Object";
            }
            for (int i2 = 0; i2 < elementsByTagName.getLength(); i2++) {
                if (i == 3) {
                    configParam = new ConfigParam();
                    configParam.Name = "";
                    configParam.Type = "";
                    configParam.Value = "";
                }
                Node nodeItem = elementsByTagName.item(i2);
                if (nodeItem.getNodeType() == 1) {
                    NodeList childNodes = ((Element) nodeItem).getChildNodes();
                    for (int i3 = 0; i3 < childNodes.getLength(); i3++) {
                        if (childNodes.item(i3).getNodeType() == 1 && (element = (Element) childNodes.item(i3)) != null) {
                            String nodeName = element.getNodeName();
                            String nodeValue = element.getFirstChild() != null ? element.getFirstChild().getNodeValue() : "";
                            if (i == 0) {
                                FillDeviceInfo(sCRAConfiguration.DeviceInfo, nodeName, nodeValue);
                            } else if (i == 1) {
                                FillStatusCode(sCRAConfiguration.StatusCode, nodeName, nodeValue);
                            } else if (i == 2) {
                                FillReaderType(sCRAConfiguration.ReaderType, nodeName, nodeValue);
                            } else if (i == 3) {
                                FillConfigParam(configParam, nodeName, nodeValue);
                            }
                        }
                    }
                }
                if (i == 3) {
                    sCRAConfiguration.ConfigParams.addElement(configParam);
                }
            }
            return "OK";
        } catch (Exception e) {
            return "Error: Parsing XML Resonse To Object:" + e.getMessage();
        }
    }

    private String getConfigParams(Document document, ProcessMessageResponse processMessageResponse) {
        try {
            NodeList elementsByTagName = document.getElementsByTagName("Payload");
            if (elementsByTagName.getLength() <= 0) {
                return "OK";
            }
            String configurationObject = "OK";
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Node nodeItem = elementsByTagName.item(i);
                short s = 1;
                if (nodeItem.getNodeType() == 1) {
                    NodeList childNodes = ((Element) nodeItem).getChildNodes();
                    for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
                        debugMsg("Name=" + childNodes.item(i2).getNodeName() + ",Value=" + childNodes.item(i2).getFirstChild().getNodeValue());
                        if (childNodes.item(i2).getNodeName().equalsIgnoreCase("Version")) {
                            processMessageResponse.Payload.Version = childNodes.item(i2).getFirstChild().getNodeValue();
                        } else if (childNodes.item(i2).getNodeName().equalsIgnoreCase("SCRAConfigurations")) {
                            NodeList elementsByTagName2 = ((Element) childNodes.item(i2)).getElementsByTagName("SCRAConfiguration");
                            for (int i3 = 0; i3 < elementsByTagName2.getLength(); i3++) {
                                SCRAConfiguration sCRAConfiguration = new SCRAConfiguration();
                                sCRAConfiguration.ConfigParams = new ArrayOfConfigParam();
                                Node nodeItem2 = elementsByTagName2.item(i3);
                                if (nodeItem2.getNodeType() == s) {
                                    sCRAConfiguration.DeviceInfo = new SCRAConfigurationDeviceInfo();
                                    configurationObject = setConfigurationObject(sCRAConfiguration, nodeItem2, 0);
                                    if (configurationObject.equalsIgnoreCase("OK")) {
                                        sCRAConfiguration.ReaderType = new SCRAConfigurationReaderType();
                                        configurationObject = setConfigurationObject(sCRAConfiguration, nodeItem2, 2);
                                        if (configurationObject.equalsIgnoreCase("OK")) {
                                            sCRAConfiguration.StatusCode = new StatusCode();
                                            sCRAConfiguration.StatusCode.Description = "";
                                            sCRAConfiguration.StatusCode.Version = "";
                                            String configurationObject2 = setConfigurationObject(sCRAConfiguration, nodeItem2, s);
                                            if (configurationObject2.equalsIgnoreCase("OK")) {
                                                configurationObject2 = setConfigurationObject(sCRAConfiguration, nodeItem2, 3);
                                            }
                                            configurationObject = configurationObject2;
                                        }
                                    }
                                }
                                processMessageResponse.Payload.SCRAConfigurations.addElement(sCRAConfiguration);
                            }
                        } else if (childNodes.item(i2).getNodeName().equalsIgnoreCase("StatusCode")) {
                            NodeList childNodes2 = ((Element) childNodes.item(i2)).getChildNodes();
                            StatusCode statusCode = new StatusCode();
                            for (int i4 = 0; i4 < childNodes2.getLength(); i4++) {
                                String nodeName = childNodes2.item(i4).getNodeName();
                                String nodeValue = childNodes2.item(i4).getFirstChild().getNodeValue();
                                if (nodeName.equalsIgnoreCase("Number")) {
                                    statusCode.setProperty(0, nodeValue);
                                } else if (nodeName.equalsIgnoreCase("Description")) {
                                    statusCode.setProperty(1, nodeValue);
                                } else {
                                    if (nodeName.equalsIgnoreCase("Version")) {
                                        statusCode.setProperty(2, nodeValue);
                                    }
                                }
                            }
                            s = 1;
                            processMessageResponse.Payload.setProperty(1, statusCode);
                        }
                    }
                }
            }
            return configurationObject;
        } catch (Exception e) {
            return "Error: Parsing XML (" + e.getMessage() + ")";
        }
    }
}
