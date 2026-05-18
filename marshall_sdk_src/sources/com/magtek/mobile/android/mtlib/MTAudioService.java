package com.magtek.mobile.android.mtlib;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MTAudioService extends MTBaseService {
    private static final String TAG = "MTAudioService";
    MTAudioReader m_audioReader;
    MTAudioReaderConfiguration m_audioReaderConfiguration;
    private Handler m_audioReaderDataHandler = new Handler(new AudioReaderHandlerCallback());

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDevicePMValue() {
        return "PM4";
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setAddress(String str) {
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionRetry(boolean z) {
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionTimeout(int i) {
    }

    private class AudioReaderHandlerCallback implements Handler.Callback {
        private AudioReaderHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            byte[] bArr;
            try {
                int i = message.what;
                if (i == 3) {
                    if (message.obj != null && (bArr = (byte[]) message.obj) != null && bArr.length > 0) {
                        byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
                        Log.i(MTAudioService.TAG, "Data Copy Length=" + bArrCopyOf.length);
                        boolean z = false;
                        if (bArr.length >= 2) {
                            Log.i(MTAudioService.TAG, "Data[0] = " + ((int) bArrCopyOf[0]));
                            Log.i(MTAudioService.TAG, "Data[1] = " + ((int) bArrCopyOf[1]));
                            if (bArr[0] == -63 && (bArr[1] == 1 || bArr[1] == 6)) {
                                Log.i(MTAudioService.TAG, "bCardData = true");
                                z = true;
                            }
                        }
                        if (MTAudioService.this.m_serviceAdapter != null) {
                            if (z) {
                                MTAudioService.this.m_serviceAdapter.OnCardData(bArrCopyOf);
                            } else {
                                MTAudioService.this.m_serviceAdapter.OnCommandData(bArrCopyOf);
                            }
                        }
                    }
                } else if (i == 13 && MTAudioService.this.m_serviceAdapter != null) {
                    MTAudioService.this.m_serviceAdapter.OnDeviceError();
                }
            } catch (Exception unused) {
            }
            return true;
        }
    }

    private void openDevice() {
        MTAudioReader mTAudioReader = this.m_audioReader;
        if (mTAudioReader != null) {
            MTAudioReaderConfiguration mTAudioReaderConfiguration = this.m_audioReaderConfiguration;
            if (mTAudioReaderConfiguration != null) {
                mTAudioReader.setConfiguration(mTAudioReaderConfiguration);
            }
            this.m_audioReader.startCommunications();
        }
    }

    private void closeDevice() {
        MTAudioReader mTAudioReader = this.m_audioReader;
        if (mTAudioReader != null) {
            mTAudioReader.stopCommunications();
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public MTDeviceFeatures getDeviceFeatures() {
        MTDeviceFeatures mTDeviceFeatures = new MTDeviceFeatures();
        mTDeviceFeatures.MSR = true;
        return mTDeviceFeatures;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void connect() {
        if (this.m_audioReader == null) {
            try {
                this.m_audioReader = MTAudioReader.createAudioReader(this.m_audioReaderDataHandler);
            } catch (Exception unused) {
            }
        }
        if (this.m_audioReaderConfiguration == null) {
            this.m_audioReaderConfiguration = MTAudioReaderConfiguration.getDefaultConfiguration();
        }
        try {
            this.m_audioReaderConfiguration.setConfigurationParams(this.m_configuration);
        } catch (Exception unused2) {
        }
        setState(MTServiceState.Connecting);
        openDevice();
        setState(MTServiceState.Connected);
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void disconnect() {
        setState(MTServiceState.Disconnecting);
        closeDevice();
        setState(MTServiceState.Disconnected);
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean sendData(byte[] bArr) {
        MTAudioReader mTAudioReader = this.m_audioReader;
        if (mTAudioReader == null) {
            return true;
        }
        mTAudioReader.sendCommand(MTParser.getHexString(bArr));
        return true;
    }
}
