package com.magtek.mobile.android.mtlib.config;

import com.magtek.mobile.android.mtlib.MTSCRAException;

/* JADX INFO: loaded from: classes.dex */
public class MTSCRAConfig {
    private String SDK_VERSION;
    private ConfigurationWS mConfigurationWS = new ConfigurationWS();

    public MTSCRAConfig(String str) {
        this.SDK_VERSION = "";
        this.SDK_VERSION = str;
    }

    public String getConfigurationXML(String str, String str2, int i, SCRAConfigurationDeviceInfo sCRAConfigurationDeviceInfo, String str3, int i2) throws MTSCRAException {
        ProcessMessageRequest processMessageRequest = new ProcessMessageRequest();
        processMessageRequest.Payload = new RequestPayload();
        processMessageRequest.Payload.ReaderType = new SCRAConfigurationReaderType();
        processMessageRequest.Payload.ReaderType.setProperty(3, this.SDK_VERSION);
        processMessageRequest.Payload.ReaderType.setProperty(0, "1");
        processMessageRequest.Payload.ReaderType.setProperty(1, String.valueOf(i));
        if (sCRAConfigurationDeviceInfo != null) {
            processMessageRequest.Payload.DeviceInfo = sCRAConfigurationDeviceInfo;
        }
        processMessageRequest.Payload.Version = "1";
        String configurationXML = this.mConfigurationWS.getConfigurationXML(str, str2, str3, processMessageRequest, null, i2);
        if (configurationXML.startsWith("Error:")) {
            throw new MTSCRAException(configurationXML);
        }
        return configurationXML;
    }

    public ProcessMessageResponse getConfigurationResponse(String str) throws MTSCRAException {
        ProcessMessageResponse processMessageResponse = new ProcessMessageResponse();
        processMessageResponse.Payload = new ResponsePayload();
        processMessageResponse.Payload.SCRAConfigurations = new ArrayOfSCRAConfiguration();
        processMessageResponse.Payload.StatusCode = new StatusCode();
        processMessageResponse.Payload.Version = "";
        String configurationResponse = this.mConfigurationWS.setConfigurationResponse(str, processMessageResponse);
        if (configurationResponse.equalsIgnoreCase("OK")) {
            return processMessageResponse;
        }
        throw new MTSCRAException(configurationResponse);
    }

    public String getConfigurationParams(String str, ProcessMessageResponse processMessageResponse) throws MTSCRAException {
        String configurationParams = "";
        if (processMessageResponse != null && processMessageResponse.Payload.SCRAConfigurations.size() > 0) {
            boolean z = false;
            boolean z2 = false;
            for (int i = 0; i < processMessageResponse.Payload.SCRAConfigurations.size(); i++) {
                SCRAConfiguration sCRAConfiguration = (SCRAConfiguration) processMessageResponse.Payload.SCRAConfigurations.elementAt(i);
                if (sCRAConfiguration.ConfigParams.size() > 0) {
                    String string = sCRAConfiguration.DeviceInfo.getProperty(5).toString();
                    if (string != null) {
                        if (string.equalsIgnoreCase(str)) {
                            configurationParams = getConfigurationParams(sCRAConfiguration);
                            z = true;
                        } else if (processMessageResponse.Payload.SCRAConfigurations.size() == 1) {
                            throw new MTSCRAException("Configuration does not match the device");
                        }
                    }
                    z2 = true;
                }
            }
            if (!z && z2) {
                throw new MTSCRAException("No matching configuration found for this device");
            }
        }
        return configurationParams;
    }

    private String getConfigurationParams(SCRAConfiguration sCRAConfiguration) throws MTSCRAException {
        String str = "";
        if (sCRAConfiguration != null && sCRAConfiguration.ConfigParams.size() > 0) {
            String str2 = (String) sCRAConfiguration.ReaderType.getProperty(3);
            if (str2.length() > 0 && str2.compareTo(this.SDK_VERSION) > 0) {
                throw new MTSCRAException("Device not supported on current SDK. Device requires SDK version " + str2 + " or greater");
            }
            for (int i = 0; i < sCRAConfiguration.ConfigParams.size(); i++) {
                str = (str + (((ConfigParam) sCRAConfiguration.ConfigParams.elementAt(i)).Name + "=" + ((ConfigParam) sCRAConfiguration.ConfigParams.elementAt(i)).Value)) + ",";
            }
        }
        return str;
    }
}
