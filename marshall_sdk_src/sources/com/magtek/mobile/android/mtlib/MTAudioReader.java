package com.magtek.mobile.android.mtlib;

import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.Equalizer;
import android.media.audiofx.NoiseSuppressor;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.mtlib.MTAudioReaderConfiguration;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.util.Arrays;
import kotlin.jvm.internal.ShortCompanionObject;

/* JADX INFO: loaded from: classes.dex */
class MTAudioReader {
    public static final String AUDIO_READER_PROTOCOL_VERSION = "1.4.0";
    public static final int COMMUNICATION_DEBUG = 10;
    public static final int COMMUNICATION_ERROR = 13;
    public static final int COMMUNICATION_STARTED = 7;
    public static final int COMMUNICATION_STOPPED = 8;
    public static final int COMMUNICATION_TIMEOUT = 9;
    static String DEBUG_TAG = "magtek";
    public static final int INTER_PACKET_TIMEOUT = 2;
    public static final int INTER_TRANSACTION_TIMEOUT = 3;
    public static final int INTRA_PACKET_TIMEOUT = 1;
    public static final byte PACKET_ID_ACK_NACK = -87;
    public static final byte PACKET_ID_STANDARD = 105;
    private static final int RECORDING_BUFFERS = 16;
    public static final int RECORDING_MINMAX_SAMPLE_UPDATE = 12;
    public static final int RECORDING_SAMPLE_UPDATE = 11;
    private static final int RX_DATA_MAX_SIZE = 2048;
    public static final int TRANSACTION_CHECKSUM_ERROR = 6;
    public static final int TRANSACTION_COMPLETE = 3;
    public static final int TRANSACTION_MISSED_PACKET_ERROR = 4;
    public static final int TRANSACTION_PACKET_ERROR = 5;
    public static final int TRANSMISSION_STARTED = 2;
    private Handler appHandler;
    private AudioRecord ar;
    private int bitPos;
    private byte calcChecksum;
    private boolean commEnabled;
    private Thread communicationsThread;
    private short currentAudioSample;
    private short currentLevel;
    private byte currentPacketId;
    private byte currentPacketNum;
    private short dataByte;
    private byte expectedPacketNum;
    private boolean firstPacketReceived;
    private boolean firstPacketReceivedAcknowledged;
    private boolean interPacketTimeoutStarted;
    private boolean interTransactionTimeoutStarted;
    private boolean intraPacketTimeoutStarted;
    private short lastHighestAudioSample;
    private short lastLevel;
    private short lastLowestAudioSample;
    private boolean lastPacketReceived;
    private boolean mBothLeftRight;
    private String mCommand;
    private MTAudioReaderConfiguration mConfiguration;
    private int mMinBufferOutSize;
    private int mMinMaxSampleUpdates;
    private short[] mOutgoingBuffer;
    private int mOutgoingIndex;
    private int mOutgoingSize;
    private Thread mOutputThread;
    private boolean mPlay;
    private boolean mReceivedWholePacket;
    private int mSampleUpdates;
    private boolean mSwapLeftRight;
    private int minBufferSizeInBytes;
    private boolean needToDetermineIfInverted;
    private int numShortsRead;
    private short[] outBuffer;
    private int packetBytesSoFar;
    private int packetLength;
    private byte rcvdChecksum;
    private byte[] rxData;
    private int rxDataIndex;
    private int sampleCount;
    private short[] softAudioBuffer;
    private AudioTrack track;
    public static final byte PACKET_ID_LAST = -103;
    private static final byte[] MANCHESTER_BYTE = {85, 86, 89, 90, marshall_t.marshalll_display_control_button_id_menu, marshall_t.marshalll_display_control_button_id_right_arrow, 105, marshall_t.marshalll_display_control_button_id_touch, PPSCRADeviceValues.ACK_STATUS_CERT_EXISTS, PPSCRADeviceValues.ACK_STATUS_DUPLICATE_KSN_KEY, PACKET_ID_LAST, -102, -91, -90, -87, PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY};
    private static final int[] RECORD_SAMPLE_RATE = {44100, 48000, 32000, 22050, 16000, 11025};
    private static final int[] PLAYBACK_SAMPLE_RATE = {44100, 48000, 32000, 22050, 16000, 11025};
    private PACKET_STATES packetState = PACKET_STATES.PACKET_ID;
    private L0_STATES l0_State = L0_STATES.SYNC1;
    Handler mHandler = new Handler() { // from class: com.magtek.mobile.android.mtlib.MTAudioReader.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                MTAudioReader.this.intraPacketTimeoutStarted = false;
                MTAudioReader.this.appHandler.obtainMessage(9, 1, MTAudioReader.this.mConfiguration.getIntraPacketTimeoutInMs()).sendToTarget();
                MTAudioReader.this.resetEverything();
            } else if (i == 2) {
                MTAudioReader.this.interPacketTimeoutStarted = false;
                MTAudioReader.this.appHandler.obtainMessage(9, 2, MTAudioReader.this.mConfiguration.getInterPacketTimeoutInMs()).sendToTarget();
                MTAudioReader.this.resetEverything();
            } else {
                if (i != 3) {
                    return;
                }
                MTAudioReader.this.interTransactionTimeoutStarted = false;
                MTAudioReader.this.appHandler.obtainMessage(9, 3, MTAudioReader.this.mConfiguration.getInterTransactionTimeoutInMs()).sendToTarget();
                MTAudioReader.this.resetEverything();
            }
        }
    };

    private enum L0_STATES {
        SYNC1,
        SYNC2,
        SYNC3,
        DATA,
        BYTE_READY,
        ERROR_TOO_MANY_SAMPLES
    }

    private enum PACKET_STATES {
        PACKET_ID,
        PACKET_NUM,
        PACKET_LENGTH_A,
        PACKET_LENGTH_B,
        MSG_LSB,
        MSG_MSB,
        CHECKSUM_LSB,
        CHECKSUM_MSB
    }

    private static byte decodeManchesterByte(byte b) {
        byte b2 = 0;
        for (byte b3 = 1; b3 <= 8; b3 = (byte) (b3 << 1)) {
            byte b4 = (byte) (b & 3);
            if (b4 == 0 || b4 == 3) {
                return (byte) -1;
            }
            if ((b4 & 2) == 2) {
                b2 = (byte) (b2 | b3);
            }
            b = (byte) (b >> 2);
        }
        return b2;
    }

    static /* synthetic */ int access$1008(MTAudioReader mTAudioReader) {
        int i = mTAudioReader.mOutgoingIndex;
        mTAudioReader.mOutgoingIndex = i + 1;
        return i;
    }

    public MTAudioReader(Handler handler) {
        this.appHandler = handler;
        setConfiguration(MTAudioReaderConfiguration.getDefaultConfiguration());
        this.needToDetermineIfInverted = true;
        this.rxData = new byte[2048];
        this.rxDataIndex = 0;
        this.commEnabled = false;
        this.mCommand = "";
        this.mOutgoingSize = 0;
        this.mOutgoingIndex = 0;
        this.mSampleUpdates = 0;
    }

    public static MTAudioReader createAudioReader(Handler handler) {
        return new MTAudioReader(handler);
    }

    private boolean isRecordingHardwareIsAvailable() {
        if (this.mConfiguration == null) {
            this.mConfiguration = MTAudioReaderConfiguration.getDefaultConfiguration();
        }
        int inputSampleRateInHz = this.mConfiguration.getInputSampleRateInHz();
        int inputChannelConfig = this.mConfiguration.getInputChannelConfig();
        int inputAudioFormat = this.mConfiguration.getInputAudioFormat();
        AudioRecord audioRecord = new AudioRecord(this.mConfiguration.getInputAudioSource(), inputSampleRateInHz, inputChannelConfig, inputAudioFormat, AudioRecord.getMinBufferSize(inputSampleRateInHz, inputChannelConfig, inputAudioFormat));
        boolean z = 1 == audioRecord.getState();
        audioRecord.release();
        return z;
    }

    public void setSampleUpdates(int i) {
        this.mSampleUpdates = i;
    }

    public void setMinMaxSampleUpdates(int i) {
        this.mMinMaxSampleUpdates = i;
    }

    public void sendCommand(String str) {
        this.mCommand = str;
        if (this.mConfiguration.getCommandOutputLogic() == MTAudioReaderConfiguration.COMMAND_OUTPUT_LOGIC.NON_INVERTED) {
            composeMessage(false);
            return;
        }
        if (this.mConfiguration.getCommandOutputLogic() == MTAudioReaderConfiguration.COMMAND_OUTPUT_LOGIC.INVERTED) {
            composeMessage(true);
        } else if (this.mConfiguration.getCommandOutputLogic() == MTAudioReaderConfiguration.COMMAND_OUTPUT_LOGIC.NON_INVERTED_AND_INVERTED) {
            composeNonInvertedAndInvertedMessage();
        } else {
            composeMessage(false);
        }
    }

    public void setConfiguration(MTAudioReaderConfiguration mTAudioReaderConfiguration) {
        if (mTAudioReaderConfiguration != null) {
            this.mConfiguration = mTAudioReaderConfiguration;
        } else {
            this.mConfiguration = MTAudioReaderConfiguration.getDefaultConfiguration();
        }
    }

    public void startCommunications() {
        clearAudio();
        this.mSwapLeftRight = this.mConfiguration.getSwapLeftRight();
        this.mBothLeftRight = this.mConfiguration.getBothLeftRight();
        Thread thread = new Thread(new Runnable() { // from class: com.magtek.mobile.android.mtlib.MTAudioReader.1
            @Override // java.lang.Runnable
            public void run() {
                Process.setThreadPriority(-19);
                MTAudioReader.this.commLoop();
            }
        }, "Magtek Audio Reader Communications Thread");
        this.communicationsThread = thread;
        this.commEnabled = true;
        thread.start();
    }

    public void stopCommunications() {
        this.commEnabled = false;
        while (this.communicationsThread.isAlive()) {
        }
        Log.d("AudioReader", "thread finished");
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        clearAudio();
        this.communicationsThread = null;
    }

    private void clearAudio() {
        AudioRecord audioRecord = this.ar;
        if (audioRecord != null) {
            try {
                audioRecord.stop();
            } catch (IllegalStateException unused) {
            } catch (Throwable th) {
                this.ar.release();
                throw th;
            }
            this.ar.release();
        }
        AudioTrack audioTrack = this.track;
        if (audioTrack != null) {
            try {
                audioTrack.stop();
            } catch (IllegalStateException unused2) {
            } catch (Throwable th2) {
                this.track.release();
                throw th2;
            }
            this.track.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commLoop() {
        setupAudioRecordBuffers();
        AudioRecord audioRecord = new AudioRecord(this.mConfiguration.getInputAudioSource(), this.mConfiguration.getInputSampleRateInHz(), this.mConfiguration.getInputChannelConfig(), this.mConfiguration.getInputAudioFormat(), this.minBufferSizeInBytes * 16);
        this.ar = audioRecord;
        if (audioRecord == null) {
            devDebug("** AudioRecord object is NULL");
            this.appHandler.obtainMessage(13).sendToTarget();
            return;
        }
        setInitValues();
        devDebug("** Sample Rate: " + this.ar.getSampleRate() + " Hz");
        AutomaticGainControl automaticGainControlCreate = AutomaticGainControl.create(this.ar.getAudioSessionId());
        if (automaticGainControlCreate != null) {
            devDebug("** AGC Enabled=" + automaticGainControlCreate.getEnabled());
        } else {
            devDebug("** AGC is null");
        }
        AcousticEchoCanceler acousticEchoCancelerCreate = AcousticEchoCanceler.create(this.ar.getAudioSessionId());
        if (acousticEchoCancelerCreate != null) {
            devDebug("** AEC Enabled=" + acousticEchoCancelerCreate.getEnabled());
        } else {
            devDebug("** AEC is null");
        }
        if (NoiseSuppressor.create(this.ar.getAudioSessionId()) != null) {
            devDebug("** NSP Enabled=" + acousticEchoCancelerCreate.getEnabled());
        } else {
            devDebug("** NSP is null");
        }
        try {
            this.ar.startRecording();
            generateWaveformData(this.mConfiguration.getWaveFormFrequency(), this.mConfiguration.getWaveFormSignalType());
            play();
            this.mPlay = true;
            this.appHandler.obtainMessage(10, ("Input Sample Rate: " + this.ar.getSampleRate() + " Hz\n") + "Input Source: " + this.ar.getAudioSource()).sendToTarget();
            startTransactionTimeout();
            int inputSampleRateInHz = this.mSampleUpdates > 0 ? this.mConfiguration.getInputSampleRateInHz() / this.mSampleUpdates : 0;
            int inputSampleRateInHz2 = this.mMinMaxSampleUpdates > 0 ? this.mConfiguration.getInputSampleRateInHz() / this.mMinMaxSampleUpdates : 0;
            this.appHandler.obtainMessage(7).sendToTarget();
            int i = 0;
            int i2 = 0;
            while (this.commEnabled) {
                AudioRecord audioRecord2 = this.ar;
                short[] sArr = this.softAudioBuffer;
                int i3 = audioRecord2.read(sArr, 0, sArr.length);
                this.numShortsRead = i3;
                if (i3 == -2) {
                    devDebug(String.format("There was a problem reading a chunk of samples! ERROR_BAD_VALUE", new Object[0]));
                } else {
                    for (int i4 = 0; i4 < this.numShortsRead; i4++) {
                        short s = this.softAudioBuffer[i4];
                        this.currentAudioSample = s;
                        this.currentLevel = comparator(s);
                        processSample();
                        if (inputSampleRateInHz > 0 && (i = i + 1) >= inputSampleRateInHz) {
                            this.appHandler.obtainMessage(11, Short.toString(this.currentAudioSample)).sendToTarget();
                            i = 0;
                        }
                        if (inputSampleRateInHz2 > 0) {
                            if (i2 == 0) {
                                short s2 = this.currentAudioSample;
                                this.lastHighestAudioSample = s2;
                                this.lastLowestAudioSample = s2;
                            } else {
                                short s3 = this.currentAudioSample;
                                if (s3 > this.lastHighestAudioSample) {
                                    this.lastHighestAudioSample = s3;
                                } else if (s3 < this.lastLowestAudioSample) {
                                    this.lastLowestAudioSample = s3;
                                }
                            }
                            i2++;
                            if (i2 >= inputSampleRateInHz2) {
                                this.appHandler.obtainMessage(12, new String[]{Short.toString(this.lastLowestAudioSample), Short.toString(this.lastHighestAudioSample)}).sendToTarget();
                                i2 = 0;
                            }
                        }
                    }
                }
            }
            this.mPlay = false;
            clearAudio();
            this.appHandler.obtainMessage(8).sendToTarget();
        } catch (Exception unused) {
            devDebug("** Unable to start recording");
            this.appHandler.obtainMessage(13).sendToTarget();
        }
    }

    private void processSample() {
        int samplesPerBit;
        int i = this.sampleCount + 1;
        this.sampleCount = i;
        if (i == 1000) {
            this.sampleCount = 0;
        }
        if (this.currentLevel != this.lastLevel) {
            int i2 = AnonymousClass4.$SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$L0_STATES[this.l0_State.ordinal()];
            if (i2 == 1) {
                if (!this.interTransactionTimeoutStarted) {
                    startTransactionTimeout();
                }
                if (this.currentLevel == this.mConfiguration.getLogicLow()) {
                    this.l0_State = L0_STATES.SYNC2;
                    this.sampleCount = 1;
                }
            } else if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 == 4) {
                        if (this.sampleCount - 1 > this.mConfiguration.getLower3BitLimit() && this.sampleCount - 1 < this.mConfiguration.getUpper3BitLimit()) {
                            if (this.needToDetermineIfInverted) {
                                this.needToDetermineIfInverted = false;
                                if (this.currentLevel == this.mConfiguration.getLogicLow()) {
                                    this.mConfiguration.setLogicInverted(!r0.isLogicInverted());
                                    this.currentLevel = this.mConfiguration.getLogicHigh();
                                }
                            }
                            int i3 = 0;
                            do {
                                if (this.currentLevel == this.mConfiguration.getLogicLow()) {
                                    this.dataByte = (short) (this.dataByte | this.bitPos);
                                }
                                this.bitPos <<= 1;
                                i3++;
                                samplesPerBit = (int) (((double) this.sampleCount) - ((double) (this.mConfiguration.getSamplesPerBit() * i3)));
                                this.sampleCount = samplesPerBit;
                            } while (samplesPerBit > this.mConfiguration.getHalfBitLimit());
                            if (this.bitPos > this.mConfiguration.getMaxBitPosition()) {
                                devDebug("OOS 1");
                                reset_L0();
                            } else if (this.bitPos == this.mConfiguration.getMaxBitPosition()) {
                                short s = this.dataByte;
                                if ((s & 513) == 512) {
                                    this.sampleCount = 1;
                                    this.bitPos = 1;
                                    processBytes((byte) ((s >> 1) & 255));
                                    this.dataByte = (short) 0;
                                } else {
                                    devDebug(String.format("OOS 2: dataByte=%#x", Short.valueOf(s)));
                                    reset_L0();
                                }
                            } else {
                                this.sampleCount = 1;
                            }
                        } else if (this.sampleCount - 1 > this.mConfiguration.getLowerSyncSampleLimit() && this.sampleCount - 1 < this.mConfiguration.getUpperSyncSampleLimit()) {
                            this.l0_State = L0_STATES.DATA;
                            this.sampleCount = 1;
                            this.bitPos = 1;
                            this.dataByte = (short) 0;
                            initPacket();
                        } else {
                            reset_L0();
                        }
                    }
                } else if (this.sampleCount - 1 > this.mConfiguration.getLowerSyncSampleLimit() && this.sampleCount - 1 < this.mConfiguration.getUpperSyncSampleLimit()) {
                    this.l0_State = L0_STATES.DATA;
                    this.sampleCount = 1;
                    this.bitPos = 1;
                    this.dataByte = (short) 0;
                    initPacket();
                } else {
                    reset_L0();
                }
            } else if (this.sampleCount - 1 > this.mConfiguration.getLowerSyncSampleLimit() && this.sampleCount - 1 < this.mConfiguration.getUpperSyncSampleLimit()) {
                this.l0_State = L0_STATES.SYNC3;
                this.sampleCount = 1;
                if (this.interTransactionTimeoutStarted) {
                    cancelTransactionTimeout();
                    startTransactionTimeout();
                }
            } else {
                reset_L0();
            }
            this.lastLevel = this.currentLevel;
        }
    }

    private void processBytes(byte b) {
        byte bDecodeManchesterByte = decodeManchesterByte(b);
        if (bDecodeManchesterByte == -1) {
            reset_L0();
            return;
        }
        switch (AnonymousClass4.$SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[this.packetState.ordinal()]) {
            case 1:
                this.currentPacketId = b;
                if (b == 105 || b == -103 || b == -87) {
                    this.packetState = PACKET_STATES.PACKET_NUM;
                    byte b2 = this.calcChecksum;
                    byte b3 = this.currentPacketId;
                    this.calcChecksum = (byte) (b2 - b3);
                    if (b3 == -103) {
                        this.lastPacketReceived = true;
                    }
                    if (this.firstPacketReceived) {
                        this.firstPacketReceived = false;
                        this.rxDataIndex = 0;
                        if (!this.firstPacketReceivedAcknowledged) {
                            if (this.mConfiguration.getTransmissionAlertMode() == MTAudioReaderConfiguration.TRANSMISSION_ALERT_MODE.FIRST_REAL_BYTE) {
                                this.appHandler.obtainMessage(2).sendToTarget();
                                this.firstPacketReceivedAcknowledged = true;
                            } else if (this.mConfiguration.getTransmissionAlertMode() == MTAudioReaderConfiguration.TRANSMISSION_ALERT_MODE.NEVER) {
                                this.firstPacketReceivedAcknowledged = true;
                            }
                        }
                    } else if (!this.mReceivedWholePacket) {
                        this.appHandler.obtainMessage(5).sendToTarget();
                        resetEverything();
                    } else {
                        if (this.interPacketTimeoutStarted) {
                            cancelTimeout(2);
                        }
                        this.mReceivedWholePacket = false;
                    }
                } else {
                    reset_L0();
                }
                break;
            case 2:
                this.currentPacketNum = bDecodeManchesterByte;
                byte b4 = this.expectedPacketNum;
                if (bDecodeManchesterByte != b4) {
                    this.appHandler.obtainMessage(4, bDecodeManchesterByte & 255, b4 & 255).sendToTarget();
                    resetEverything();
                } else {
                    byte b5 = this.currentPacketId;
                    if (b5 == 105 || b5 == -103) {
                        startTimeout(1, bDecodeManchesterByte);
                    }
                    this.packetState = PACKET_STATES.PACKET_LENGTH_A;
                    this.calcChecksum = (byte) (this.calcChecksum - this.currentPacketNum);
                }
                break;
            case 3:
                this.packetLength = bDecodeManchesterByte;
                this.packetState = PACKET_STATES.PACKET_LENGTH_B;
                break;
            case 4:
                int i = this.packetLength + (bDecodeManchesterByte * 16);
                this.packetLength = i;
                this.calcChecksum = (byte) (this.calcChecksum - i);
                if (i < 1) {
                    reset_L0();
                } else if (i == 1) {
                    this.packetState = PACKET_STATES.CHECKSUM_LSB;
                } else {
                    this.packetState = PACKET_STATES.MSG_LSB;
                }
                break;
            case 5:
                this.rxData[this.rxDataIndex] = bDecodeManchesterByte;
                this.packetState = PACKET_STATES.MSG_MSB;
                break;
            case 6:
                byte[] bArr = this.rxData;
                int i2 = this.rxDataIndex;
                bArr[i2] = (byte) (bArr[i2] + ((byte) (bDecodeManchesterByte * 16)));
                this.calcChecksum = (byte) (this.calcChecksum - bArr[i2]);
                int i3 = this.packetBytesSoFar + 1;
                this.packetBytesSoFar = i3;
                this.rxDataIndex = i2 + 1;
                if (i3 == this.packetLength - 1) {
                    this.packetState = PACKET_STATES.CHECKSUM_LSB;
                } else {
                    this.packetState = PACKET_STATES.MSG_LSB;
                }
                break;
            case 7:
                this.rcvdChecksum = bDecodeManchesterByte;
                this.packetState = PACKET_STATES.CHECKSUM_MSB;
                break;
            case 8:
                this.rcvdChecksum = (byte) (this.rcvdChecksum + ((byte) (bDecodeManchesterByte * 16)));
                this.mReceivedWholePacket = true;
                if (this.intraPacketTimeoutStarted) {
                    cancelTimeout(1);
                }
                processPackets();
                reset_L0();
                if (this.currentPacketId == 105) {
                    startTimeout(2, this.currentPacketNum);
                }
                break;
            default:
                reset_L0();
                break;
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtlib.MTAudioReader$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$L0_STATES;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES;

        static {
            int[] iArr = new int[PACKET_STATES.values().length];
            $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES = iArr;
            try {
                iArr[PACKET_STATES.PACKET_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[PACKET_STATES.PACKET_NUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[PACKET_STATES.PACKET_LENGTH_A.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[PACKET_STATES.PACKET_LENGTH_B.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[PACKET_STATES.MSG_LSB.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[PACKET_STATES.MSG_MSB.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[PACKET_STATES.CHECKSUM_LSB.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$PACKET_STATES[PACKET_STATES.CHECKSUM_MSB.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[L0_STATES.values().length];
            $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$L0_STATES = iArr2;
            try {
                iArr2[L0_STATES.SYNC1.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$L0_STATES[L0_STATES.SYNC2.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$L0_STATES[L0_STATES.SYNC3.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTAudioReader$L0_STATES[L0_STATES.DATA.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private void processPackets() {
        if (!this.firstPacketReceivedAcknowledged && this.mConfiguration.getTransmissionAlertMode() == MTAudioReaderConfiguration.TRANSMISSION_ALERT_MODE.FIRST_REAL_PACKET) {
            this.appHandler.obtainMessage(2).sendToTarget();
            this.firstPacketReceivedAcknowledged = true;
        }
        if (this.calcChecksum != this.rcvdChecksum) {
            this.appHandler.obtainMessage(6, this.currentPacketId, this.currentPacketNum).sendToTarget();
            resetEverything();
            return;
        }
        incrementExpectedPacketNumber();
        if (this.lastPacketReceived) {
            this.appHandler.obtainMessage(3, Arrays.copyOfRange(this.rxData, 0, this.rxDataIndex)).sendToTarget();
            resetEverything();
        }
    }

    private void incrementExpectedPacketNumber() {
        byte b = (byte) (this.expectedPacketNum + 1);
        this.expectedPacketNum = b;
        if (b == 16) {
            this.expectedPacketNum = (byte) 0;
        }
    }

    private short comparator(short s) {
        int i;
        short logicHigh = this.mConfiguration.getLogicHigh();
        short logicLow = this.mConfiguration.getLogicLow();
        boolean zIsLogicInverted = this.mConfiguration.isLogicInverted();
        if (s > this.mConfiguration.getHighComparatorThreshold()) {
            if (!zIsLogicInverted) {
                return logicHigh;
            }
            i = logicHigh - logicHigh;
        } else {
            if (s >= this.mConfiguration.getLowComparatorThreshold()) {
                return this.lastLevel;
            }
            if (!zIsLogicInverted) {
                return logicLow;
            }
            i = logicHigh - logicLow;
        }
        return (short) i;
    }

    public void play() {
        Thread thread = new Thread(new Runnable() { // from class: com.magtek.mobile.android.mtlib.MTAudioReader.2
            @Override // java.lang.Runnable
            public void run() {
                Process.setThreadPriority(-19);
                try {
                    MTAudioReader mTAudioReader = MTAudioReader.this;
                    mTAudioReader.mMinBufferOutSize = mTAudioReader.getPlaybackBufferMinSize();
                    AudioTrack.getMinBufferSize(MTAudioReader.this.mConfiguration.getOutputSampleRateInHz(), 12, MTAudioReader.this.mConfiguration.getOutputAudioFormat());
                    MTAudioReader.this.track = new AudioTrack(MTAudioReader.this.mConfiguration.getOutputStreamType(), MTAudioReader.this.mConfiguration.getOutputSampleRateInHz(), 12, MTAudioReader.this.mConfiguration.getOutputAudioFormat(), MTAudioReader.this.mMinBufferOutSize, MTAudioReader.this.mConfiguration.getOutputMode());
                    float maxVolume = AudioTrack.getMaxVolume();
                    MTAudioReader.devDebug("** Playback Rate: " + MTAudioReader.this.track.getPlaybackRate());
                    long increasingOutputInMs = (long) MTAudioReader.this.mConfiguration.getIncreasingOutputInMs();
                    boolean boostUsingEqualizer = MTAudioReader.this.mConfiguration.getBoostUsingEqualizer();
                    if (MTAudioReader.this.mConfiguration.getCommandOutputLogic() == MTAudioReaderConfiguration.COMMAND_OUTPUT_LOGIC.NON_INVERTED) {
                        MTAudioReader.devDebug("AudioReader", " *** Non-Invert command logic");
                    } else if (MTAudioReader.this.mConfiguration.getCommandOutputLogic() == MTAudioReaderConfiguration.COMMAND_OUTPUT_LOGIC.INVERTED) {
                        MTAudioReader.devDebug("AudioReader", " *** Invert command logic");
                    } else if (MTAudioReader.this.mConfiguration.getCommandOutputLogic() == MTAudioReaderConfiguration.COMMAND_OUTPUT_LOGIC.NON_INVERTED_AND_INVERTED) {
                        MTAudioReader.devDebug("AudioReader", " *** Non-Invert and Inverted command logic");
                    }
                    long j = 0;
                    if (increasingOutputInMs > 0) {
                        MTAudioReader.devDebug("AudioReader", " *** increasing output over: " + MTAudioReader.this.mConfiguration.getIncreasingOutputInMs() + "ms");
                    }
                    if (MTAudioReader.this.mConfiguration.getSwapLeftRight()) {
                        MTAudioReader.devDebug("AudioReader", " *** swap L/R channels");
                    }
                    if (MTAudioReader.this.mConfiguration.getBothLeftRight()) {
                        MTAudioReader.devDebug("AudioReader", " *** power both channels");
                    }
                    if (boostUsingEqualizer) {
                        MTAudioReader.devDebug("AudioReader", " *** boost using EQ");
                        Equalizer equalizer = new Equalizer(1, MTAudioReader.this.track.getAudioSessionId());
                        equalizer.setEnabled(true);
                        short numberOfBands = equalizer.getNumberOfBands();
                        short[] bandLevelRange = equalizer.getBandLevelRange();
                        for (short s = 0; s < numberOfBands; s = (short) (s + 1)) {
                            try {
                                short bandLevel = equalizer.getBandLevel(s);
                                equalizer.setBandLevel(s, bandLevelRange[1]);
                                MTAudioReader.devDebug("** Band: " + ((int) s) + ", Level: " + ((int) bandLevel) + " NewLevel: " + ((int) equalizer.getBandLevel(s)));
                            } catch (Exception e) {
                                MTAudioReader.devDebug("*** Exception: " + e.getMessage());
                            }
                        }
                        if (equalizer.getEnabled()) {
                            MTAudioReader.devDebug("** Equalizer is enabled.");
                        } else {
                            MTAudioReader.devDebug("** Equalizer is disabled.");
                        }
                    }
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    int i = 10;
                    long j2 = increasingOutputInMs / ((long) 10);
                    float f = increasingOutputInMs > 0 ? (1 * maxVolume) / 10 : maxVolume;
                    MTAudioReader.this.track.setStereoVolume(f, f);
                    MTAudioReader.devDebug("** Setting Volume: " + f);
                    MTAudioReader.this.track.play();
                    int i2 = 1;
                    while (MTAudioReader.this.mPlay) {
                        if (increasingOutputInMs > j) {
                            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                            if (jCurrentTimeMillis2 >= increasingOutputInMs) {
                                MTAudioReader.this.track.setStereoVolume(maxVolume, maxVolume);
                                MTAudioReader.devDebug("** Setting Volume: " + maxVolume);
                                increasingOutputInMs = j;
                            } else {
                                int i3 = ((int) (jCurrentTimeMillis2 / j2)) + 1;
                                if (i3 != i2) {
                                    float f2 = (i3 * maxVolume) / i;
                                    MTAudioReader.this.track.setStereoVolume(f2, f2);
                                    MTAudioReader.devDebug("** Setting Volume: " + f2);
                                    i2 = i3;
                                }
                            }
                        }
                        for (int i4 = 0; i4 < MTAudioReader.this.outBuffer.length; i4 += 2) {
                            if (MTAudioReader.this.mOutgoingSize <= 0 || MTAudioReader.this.mOutgoingIndex >= MTAudioReader.this.mOutgoingSize) {
                                if (MTAudioReader.this.mSwapLeftRight) {
                                    if (MTAudioReader.this.mBothLeftRight) {
                                        MTAudioReader.this.outBuffer[i4 + 1] = MTAudioReader.this.outBuffer[i4];
                                    } else {
                                        MTAudioReader.this.outBuffer[i4 + 1] = 0;
                                    }
                                } else if (MTAudioReader.this.mBothLeftRight) {
                                    MTAudioReader.this.outBuffer[i4] = MTAudioReader.this.outBuffer[i4 + 1];
                                } else {
                                    MTAudioReader.this.outBuffer[i4] = 0;
                                }
                            } else {
                                if (MTAudioReader.this.mSwapLeftRight) {
                                    MTAudioReader.this.outBuffer[i4 + 1] = MTAudioReader.this.mOutgoingBuffer[MTAudioReader.access$1008(MTAudioReader.this)];
                                } else {
                                    MTAudioReader.this.outBuffer[i4] = MTAudioReader.this.mOutgoingBuffer[MTAudioReader.access$1008(MTAudioReader.this)];
                                }
                                if (MTAudioReader.this.mOutgoingIndex == MTAudioReader.this.mOutgoingSize) {
                                    MTAudioReader.this.mOutgoingSize = 0;
                                }
                            }
                        }
                        MTAudioReader.this.track.write(MTAudioReader.this.outBuffer, 0, MTAudioReader.this.outBuffer.length);
                        i = 10;
                        j = 0;
                    }
                } catch (IllegalStateException e2) {
                    MTAudioReader.devDebug(e2.toString());
                }
            }
        });
        this.mOutputThread = thread;
        this.mPlay = true;
        thread.start();
    }

    private void setupAudioRecordBuffers() {
        int minBufferSize;
        int minBufferSize2 = AudioRecord.getMinBufferSize(this.mConfiguration.getInputSampleRateInHz(), this.mConfiguration.getInputChannelConfig(), this.mConfiguration.getInputAudioFormat());
        this.minBufferSizeInBytes = minBufferSize2;
        if (minBufferSize2 < 1) {
            int i = 0;
            while (true) {
                int[] iArr = RECORD_SAMPLE_RATE;
                if (i < iArr.length) {
                    int i2 = iArr[i];
                    if (i2 != this.mConfiguration.getInputSampleRateInHz() && (minBufferSize = AudioRecord.getMinBufferSize(i2, this.mConfiguration.getInputChannelConfig(), this.mConfiguration.getInputAudioFormat())) > 0) {
                        this.minBufferSizeInBytes = minBufferSize;
                        this.mConfiguration.setInputSampleRateInHz(i2);
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
        }
        int i3 = this.minBufferSizeInBytes;
        if (i3 > 0) {
            this.softAudioBuffer = new short[i3 / 2];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPlaybackBufferMinSize() {
        int minBufferSize;
        int minBufferSize2 = AudioTrack.getMinBufferSize(this.mConfiguration.getOutputSampleRateInHz(), this.mConfiguration.getOutputChannelConfig(), this.mConfiguration.getOutputAudioFormat());
        if (minBufferSize2 >= 1) {
            return minBufferSize2;
        }
        int i = 0;
        while (true) {
            int[] iArr = PLAYBACK_SAMPLE_RATE;
            if (i >= iArr.length) {
                return minBufferSize2;
            }
            int i2 = iArr[i];
            if (i2 != this.mConfiguration.getOutputSampleRateInHz() && (minBufferSize = AudioTrack.getMinBufferSize(i2, this.mConfiguration.getOutputChannelConfig(), this.mConfiguration.getOutputAudioFormat())) > 0) {
                this.mConfiguration.setOutputSampleRateInHz(i2);
                return minBufferSize;
            }
            i++;
        }
    }

    private void setInitValues() {
        resetEverything();
        byte[] bArr = this.rxData;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        } else {
            this.rxData = new byte[2048];
        }
        this.rxDataIndex = 0;
        this.numShortsRead = 0;
    }

    public void resetEverything() {
        initPacket();
        this.lastLevel = this.mConfiguration.getLogicHigh();
        reset_L0();
        this.sampleCount = 0;
        this.firstPacketReceived = true;
        this.firstPacketReceivedAcknowledged = false;
        this.lastPacketReceived = false;
        this.expectedPacketNum = (byte) 0;
        this.currentPacketId = (byte) 0;
        this.currentPacketNum = (byte) 0;
        this.rxDataIndex = 0;
        this.intraPacketTimeoutStarted = false;
        this.interPacketTimeoutStarted = false;
        cancelAllTimeouts();
        this.mReceivedWholePacket = false;
    }

    private void initPacket() {
        this.packetState = PACKET_STATES.PACKET_ID;
        this.packetBytesSoFar = 0;
        this.packetLength = 0;
        this.calcChecksum = (byte) 0;
        this.rcvdChecksum = (byte) 0;
    }

    private void reset_L0() {
        this.l0_State = L0_STATES.SYNC1;
        this.lastPacketReceived = false;
    }

    private void cancelAllTimeouts() {
        cancelTimeout(1);
        cancelTimeout(2);
        cancelTimeout(3);
    }

    private void cancelTimeout(int i) {
        if (i == 1) {
            this.intraPacketTimeoutStarted = false;
        } else if (i == 2) {
            this.interPacketTimeoutStarted = false;
        } else if (i == 3) {
            this.interTransactionTimeoutStarted = false;
        }
        this.mHandler.removeMessages(i);
    }

    private void startTransactionTimeout() {
        startTimeout(3, (byte) 0);
    }

    private void cancelTransactionTimeout() {
        cancelTimeout(3);
    }

    private void startTimeout(int i, byte b) {
        if (i == 1) {
            long intraPacketTimeoutInMs = this.mConfiguration.getIntraPacketTimeoutInMs();
            if (intraPacketTimeoutInMs != -1) {
                Handler handler = this.mHandler;
                handler.sendMessageDelayed(handler.obtainMessage(i, this.currentPacketId & 255, b & 255), intraPacketTimeoutInMs);
                this.intraPacketTimeoutStarted = true;
                return;
            }
            return;
        }
        if (i == 2) {
            long interPacketTimeoutInMs = this.mConfiguration.getInterPacketTimeoutInMs();
            if (interPacketTimeoutInMs != -1) {
                Handler handler2 = this.mHandler;
                handler2.sendMessageDelayed(handler2.obtainMessage(i, this.currentPacketId & 255, b & 255), interPacketTimeoutInMs);
                this.interPacketTimeoutStarted = true;
                return;
            }
            return;
        }
        if (i != 3) {
            return;
        }
        long interTransactionTimeoutInMs = this.mConfiguration.getInterTransactionTimeoutInMs();
        if (interTransactionTimeoutInMs != -1) {
            Handler handler3 = this.mHandler;
            handler3.sendMessageDelayed(handler3.obtainMessage(i, this.currentPacketId & 255, b & 255), interTransactionTimeoutInMs);
            this.interTransactionTimeoutStarted = true;
        }
    }

    private void generateWaveformData(double d, MTAudioReaderConfiguration.WAVE_FORM_SIGNAL_TYPE wave_form_signal_type) {
        int outputSampleRateInHz = (int) (((double) this.mConfiguration.getOutputSampleRateInHz()) / d);
        this.outBuffer = new short[outputSampleRateInHz * 2];
        if (this.mSwapLeftRight) {
            devDebug("AudioReader", " *** Swap L/R");
        }
        devDebug("AudioReader", " *** Wave Frequency: " + d);
        if (wave_form_signal_type == MTAudioReaderConfiguration.WAVE_FORM_SIGNAL_TYPE.SQUARE) {
            devDebug("AudioReader", " *** Square Wave");
            int i = outputSampleRateInHz / 2;
            boolean z = true;
            short s = ShortCompanionObject.MIN_VALUE;
            for (int i2 = 0; i2 < outputSampleRateInHz; i2++) {
                if (i2 % i == 0) {
                    z = !z;
                    s = z ? ShortCompanionObject.MIN_VALUE : ShortCompanionObject.MAX_VALUE;
                }
                if (this.mSwapLeftRight) {
                    short[] sArr = this.outBuffer;
                    int i3 = i2 * 2;
                    sArr[i3] = s;
                    if (this.mBothLeftRight) {
                        sArr[i3 + 1] = s;
                    } else {
                        sArr[i3 + 1] = 0;
                    }
                } else {
                    if (this.mBothLeftRight) {
                        this.outBuffer[i2 * 2] = s;
                    } else {
                        this.outBuffer[i2 * 2] = 0;
                    }
                    this.outBuffer[(i2 * 2) + 1] = s;
                }
            }
            return;
        }
        if (wave_form_signal_type == MTAudioReaderConfiguration.WAVE_FORM_SIGNAL_TYPE.SINE) {
            devDebug("AudioReader", " *** Sine Wave");
            double d2 = 0.0d;
            double d3 = 6.283185307179586d / ((double) outputSampleRateInHz);
            for (int i4 = 0; i4 < outputSampleRateInHz; i4++) {
                short sSin = (short) (Math.sin(d2) * 32767.0d);
                d2 += d3;
                if (d2 > 6.283185307179586d) {
                    d2 -= 6.283185307179586d;
                }
                if (this.mSwapLeftRight) {
                    if (this.mBothLeftRight) {
                        this.outBuffer[i4 * 2] = sSin;
                    } else {
                        this.outBuffer[i4 * 2] = 0;
                    }
                    this.outBuffer[(i4 * 2) + 1] = 0;
                } else {
                    if (this.mBothLeftRight) {
                        this.outBuffer[i4 * 2] = sSin;
                    } else {
                        this.outBuffer[i4 * 2] = 0;
                    }
                    this.outBuffer[(i4 * 2) + 1] = sSin;
                }
            }
        }
    }

    private static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    private void composeMessage(boolean z) {
        if (z) {
            devDebug("AudioReader", " *** inverting command output");
        }
        if (this.mCommand.length() > 0) {
            byte[] bArrHexStringToByteArray = hexStringToByteArray(this.mCommand);
            if (bArrHexStringToByteArray.length > 0) {
                int length = (bArrHexStringToByteArray.length * 2) + 12 + 2;
                buildMessage(new byte[length], 0, bArrHexStringToByteArray, bArrHexStringToByteArray.length);
                short s = ShortCompanionObject.MIN_VALUE;
                short s2 = ShortCompanionObject.MAX_VALUE;
                if (!z) {
                    s = ShortCompanionObject.MAX_VALUE;
                    s2 = ShortCompanionObject.MIN_VALUE;
                }
                int i = 10;
                int i2 = length * 10 * 6;
                this.mOutgoingBuffer = new short[i2];
                int i3 = 0;
                int i4 = 0;
                while (i3 < length) {
                    int i5 = 1;
                    short s3 = (short) (((short) (r3[i3] << 1)) | 512);
                    int i6 = 0;
                    while (i6 < i) {
                        short s4 = (s3 & i5) == i5 ? s : s2;
                        int i7 = 0;
                        while (i7 < 6) {
                            this.mOutgoingBuffer[i4] = s4;
                            i7++;
                            i4++;
                        }
                        i5 <<= 1;
                        i6++;
                        i = 10;
                    }
                    i3++;
                    i = 10;
                }
                this.mOutgoingSize = i2;
                this.mOutgoingIndex = 0;
            }
            this.mCommand = "";
        }
    }

    private void composeNonInvertedAndInvertedMessage() {
        if (this.mCommand.length() > 0) {
            byte[] bArrHexStringToByteArray = hexStringToByteArray(this.mCommand);
            if (bArrHexStringToByteArray.length > 0) {
                int length = (bArrHexStringToByteArray.length * 2) + 12 + 2;
                buildMessage(new byte[length], 0, bArrHexStringToByteArray, bArrHexStringToByteArray.length);
                int i = 10;
                int i2 = length * 10 * 6 * 2;
                this.mOutgoingBuffer = new short[i2];
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    int i5 = 1;
                    if (i3 >= length) {
                        break;
                    }
                    short s = (short) (((short) (r3[i3] << 1)) | 512);
                    for (int i6 = 0; i6 < 10; i6++) {
                        short s2 = (s & i5) == i5 ? ShortCompanionObject.MAX_VALUE : ShortCompanionObject.MIN_VALUE;
                        int i7 = 0;
                        while (i7 < 6) {
                            this.mOutgoingBuffer[i4] = s2;
                            i7++;
                            i4++;
                        }
                        i5 <<= 1;
                    }
                    i3++;
                }
                int i8 = 0;
                while (i8 < length) {
                    short s3 = (short) (((short) (r3[i8] << 1)) | 512);
                    int i9 = 0;
                    int i10 = 1;
                    while (i9 < i) {
                        short s4 = (s3 & i10) == i10 ? ShortCompanionObject.MIN_VALUE : ShortCompanionObject.MAX_VALUE;
                        int i11 = 0;
                        while (i11 < 6) {
                            this.mOutgoingBuffer[i4] = s4;
                            i11++;
                            i4++;
                        }
                        i10 <<= 1;
                        i9++;
                        i = 10;
                    }
                    i8++;
                    i = 10;
                }
                this.mOutgoingSize = i2;
                this.mOutgoingIndex = 0;
            }
            this.mCommand = "";
        }
    }

    void buildMessage(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3;
        int i4 = 0;
        while (true) {
            if (i4 >= 4) {
                break;
            }
            bArr[i4] = 85;
            i4++;
        }
        for (i3 = 4; i3 < 6; i3++) {
            bArr[i3] = marshall_t.marshall_packet_option_rfu_mask;
        }
        bArr[6] = PACKET_ID_LAST;
        bArr[7] = MANCHESTER_BYTE[i & 15];
        byte b = (byte) (i2 + 1);
        buildMessageDataLength(bArr, 8, 9, b);
        buildMessageData(bArr, 10, bArr2, i2);
        int i5 = i2 * 2;
        calcResponseChksum(bArr, 10 + i5, (byte) i, (byte) 105, b, bArr2, i2);
        int i6 = 12 + i5;
        bArr[i6] = marshall_t.marshall_packet_option_rfu_mask;
        bArr[i6 + 1] = marshall_t.marshall_packet_option_rfu_mask;
    }

    static void buildMessageDataLength(byte[] bArr, int i, int i2, byte b) {
        byte[] bArr2 = MANCHESTER_BYTE;
        bArr[i] = bArr2[(byte) (b & 15)];
        bArr[i2] = bArr2[(byte) ((b >> 4) & 15)];
    }

    static void buildMessageData(byte[] bArr, int i, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = (i3 * 2) + i;
            byte[] bArr3 = MANCHESTER_BYTE;
            bArr[i4] = bArr3[bArr2[i3] & 15];
            bArr[i4 + 1] = bArr3[(bArr2[i3] >> 4) & 15];
        }
    }

    static void calcResponseChksum(byte[] bArr, int i, byte b, byte b2, byte b3, byte[] bArr2, int i2) {
        byte b4 = (byte) (b2 + b + b3);
        for (int i3 = 0; i3 < i2; i3++) {
            b4 = (byte) (b4 + bArr2[i3]);
        }
        byte b5 = (byte) (0 - b4);
        byte[] bArr3 = MANCHESTER_BYTE;
        bArr[i + 1] = bArr3[(byte) ((b5 >> 4) & 15)];
        bArr[i] = bArr3[(byte) (b5 & 15)];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void devDebug(String str) {
        devDebug(DEBUG_TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void devDebug(String str, String str2) {
        Log.d(str, str2);
    }

    private void debugMsg(String str) {
        Log.i("MTSCRA.Lib", str);
    }
}
