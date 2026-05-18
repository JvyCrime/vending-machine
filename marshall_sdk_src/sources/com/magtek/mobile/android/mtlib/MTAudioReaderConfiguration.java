package com.magtek.mobile.android.mtlib;

import android.os.Build;
import com.felhr.usbserial.FTDISerialDevice;
import kotlin.jvm.internal.ShortCompanionObject;

/* JADX INFO: loaded from: classes.dex */
class MTAudioReaderConfiguration {
    public static final String AUDIO_READER_CONFIGURATION_VERSION = "1.0.2";
    private static final float HALF_BYTE_MARGIN_PERCENTAGE = 0.16666667f;
    private boolean boostUsingEqualizer;
    private boolean bothLeftRight;
    private COMMAND_OUTPUT_LOGIC commandOutputLogic;
    private float halfBitLimit;
    private short highComparatorThreshold;
    private short highSample;
    private int increasingOutputInMs;
    private int inputAudioFormat;
    private int inputAudioSource;
    private int inputChannelConfig;
    private int inputSampleRateInHz;
    private int interPacketTimeoutInMs;
    private int interTransactionTimeoutInMs;
    private int intraPacketTimeoutInMs;
    private short logicHigh;
    private boolean logicInverted;
    private short logicLow;
    private short lowComparatorThreshold;
    private short lowSample;
    private float lower3BitLimit;
    private int lowerSyncSampleLimit;
    private int maxBitPosition;
    private int outputAudioFormat;
    private int outputChannelConfig;
    private int outputMode;
    private int outputSampleRateInHz;
    private int outputStreamType;
    private float readerBaud;
    private float samplesPerBit;
    private boolean swapLeftRight;
    private TRANSMISSION_ALERT_MODE transmissionAlertMode;
    private float upper3BitLimit;
    private int upperSyncSampleLimit;
    private int waveFormFrequency;
    private WAVE_FORM_SIGNAL_TYPE waveFormSignalType;

    public enum COMMAND_OUTPUT_LOGIC {
        NON_INVERTED,
        INVERTED,
        NON_INVERTED_AND_INVERTED
    }

    public enum TRANSMISSION_ALERT_MODE {
        NEVER,
        FIRST_REAL_BYTE,
        FIRST_REAL_PACKET
    }

    public enum WAVE_FORM_SIGNAL_TYPE {
        SINE,
        SQUARE
    }

    private MTAudioReaderConfiguration() {
    }

    public static MTAudioReaderConfiguration getDefaultConfiguration() {
        MTAudioReaderConfiguration mTAudioReaderConfiguration = new MTAudioReaderConfiguration();
        mTAudioReaderConfiguration.setReaderBaud(7350.0f);
        mTAudioReaderConfiguration.setOutputSampleRateInHz(44100);
        mTAudioReaderConfiguration.setOutputChannelConfig(12);
        mTAudioReaderConfiguration.setOutputAudioFormat(2);
        mTAudioReaderConfiguration.setOutputStreamType(3);
        mTAudioReaderConfiguration.setOutputMode(1);
        mTAudioReaderConfiguration.setSamplesPerBit(6.0f);
        mTAudioReaderConfiguration.setHalfBitLimit(3.0f);
        mTAudioReaderConfiguration.setUpper3BitLimit(21.0f);
        mTAudioReaderConfiguration.setLower3BitLimit(3.0f);
        mTAudioReaderConfiguration.setUpperSyncSampleLimit(35);
        mTAudioReaderConfiguration.setLowerSyncSampleLimit(25);
        mTAudioReaderConfiguration.setMaxBitPosition(1024);
        if (Build.VERSION.SDK_INT >= 14) {
            mTAudioReaderConfiguration.setInputAudioSource(6);
        } else {
            mTAudioReaderConfiguration.setInputAudioSource(1);
        }
        mTAudioReaderConfiguration.setInputSampleRateInHz(44100);
        mTAudioReaderConfiguration.setInputChannelConfig(16);
        mTAudioReaderConfiguration.setInputAudioFormat(2);
        mTAudioReaderConfiguration.setHighComparatorThreshold((short) 1000);
        mTAudioReaderConfiguration.setLowComparatorThreshold((short) -1000);
        mTAudioReaderConfiguration.setHighSample(ShortCompanionObject.MAX_VALUE);
        mTAudioReaderConfiguration.setLowSample(ShortCompanionObject.MIN_VALUE);
        mTAudioReaderConfiguration.setLogicHigh((short) 1);
        mTAudioReaderConfiguration.setLogicLow((short) 0);
        mTAudioReaderConfiguration.setLogicInverted(false);
        mTAudioReaderConfiguration.setTransmissionAlertMode(TRANSMISSION_ALERT_MODE.FIRST_REAL_BYTE);
        mTAudioReaderConfiguration.setIntraPacketTimeoutInMs(2000);
        mTAudioReaderConfiguration.setInterPacketTimeoutInMs(5000);
        mTAudioReaderConfiguration.setInterTransactionTimeoutInMs(FTDISerialDevice.FTDI_BAUDRATE_300);
        mTAudioReaderConfiguration.setWaveFormSignalType(WAVE_FORM_SIGNAL_TYPE.SQUARE);
        mTAudioReaderConfiguration.setWaveFormFrequency(1000);
        mTAudioReaderConfiguration.setIncreasingOutputInMs(500);
        mTAudioReaderConfiguration.setBothLeftRight(true);
        mTAudioReaderConfiguration.setSwapLeftRight(false);
        mTAudioReaderConfiguration.setBoostUsingEqualizer(false);
        mTAudioReaderConfiguration.setCommandOutputLogic(COMMAND_OUTPUT_LOGIC.NON_INVERTED_AND_INVERTED);
        return mTAudioReaderConfiguration;
    }

    public static void setDerivedFields(MTAudioReaderConfiguration mTAudioReaderConfiguration) {
        if (mTAudioReaderConfiguration.getReaderBaud() == 7350.0f && mTAudioReaderConfiguration.getInputSampleRateInHz() == 44100) {
            mTAudioReaderConfiguration.setSamplesPerBit(6.0f);
            mTAudioReaderConfiguration.setHalfBitLimit(3.0f);
            mTAudioReaderConfiguration.setLowerSyncSampleLimit(25);
            mTAudioReaderConfiguration.setUpperSyncSampleLimit(35);
            mTAudioReaderConfiguration.setLower3BitLimit(3.0f);
            mTAudioReaderConfiguration.setUpper3BitLimit(21.0f);
            return;
        }
        if (mTAudioReaderConfiguration.getReaderBaud() == 7350.0f && mTAudioReaderConfiguration.getInputSampleRateInHz() == 48000) {
            mTAudioReaderConfiguration.setSamplesPerBit(6.531f);
            mTAudioReaderConfiguration.setHalfBitLimit(3.265f);
            mTAudioReaderConfiguration.setLowerSyncSampleLimit(25);
            mTAudioReaderConfiguration.setUpperSyncSampleLimit(35);
            mTAudioReaderConfiguration.setLower3BitLimit(3.265f);
            mTAudioReaderConfiguration.setUpper3BitLimit(21.0f);
            return;
        }
        if (mTAudioReaderConfiguration.getReaderBaud() == 7350.0f && mTAudioReaderConfiguration.getInputSampleRateInHz() == 32000) {
            mTAudioReaderConfiguration.setSamplesPerBit(4.352f);
            mTAudioReaderConfiguration.setHalfBitLimit(2.176f);
            mTAudioReaderConfiguration.setLowerSyncSampleLimit(18);
            mTAudioReaderConfiguration.setUpperSyncSampleLimit(26);
            mTAudioReaderConfiguration.setLower3BitLimit(2.176f);
            mTAudioReaderConfiguration.setUpper3BitLimit(15.0f);
            return;
        }
        if (mTAudioReaderConfiguration.getReaderBaud() == 7350.0f && mTAudioReaderConfiguration.getInputSampleRateInHz() == 22050) {
            mTAudioReaderConfiguration.setSamplesPerBit(3.0f);
            mTAudioReaderConfiguration.setHalfBitLimit(1.5f);
            mTAudioReaderConfiguration.setLowerSyncSampleLimit(12);
            mTAudioReaderConfiguration.setUpperSyncSampleLimit(18);
            mTAudioReaderConfiguration.setLower3BitLimit(1.5f);
            mTAudioReaderConfiguration.setUpper3BitLimit(10.0f);
            return;
        }
        float inputSampleRateInHz = mTAudioReaderConfiguration.getInputSampleRateInHz() / mTAudioReaderConfiguration.getReaderBaud();
        mTAudioReaderConfiguration.setSamplesPerBit(inputSampleRateInHz);
        mTAudioReaderConfiguration.setHalfBitLimit(mTAudioReaderConfiguration.getSamplesPerBit() / 2.0f);
        float f = 5.0f * inputSampleRateInHz;
        double d = f;
        double d2 = HALF_BYTE_MARGIN_PERCENTAGE * f;
        mTAudioReaderConfiguration.setLowerSyncSampleLimit((int) (Math.floor(d) - Math.floor(d2)));
        mTAudioReaderConfiguration.setUpperSyncSampleLimit((int) (Math.ceil(d) + Math.ceil(d2)));
        mTAudioReaderConfiguration.setLower3BitLimit(inputSampleRateInHz / 2.0f);
        mTAudioReaderConfiguration.setUpper3BitLimit((float) (((double) inputSampleRateInHz) * 3.5d));
    }

    public float getReaderBaud() {
        return this.readerBaud;
    }

    public void setReaderBaud(float f) {
        this.readerBaud = f;
    }

    public int getWaveFormFrequency() {
        return this.waveFormFrequency;
    }

    public void setWaveFormFrequency(int i) {
        this.waveFormFrequency = i;
    }

    public WAVE_FORM_SIGNAL_TYPE getWaveFormSignalType() {
        return this.waveFormSignalType;
    }

    public void setWaveFormSignalType(WAVE_FORM_SIGNAL_TYPE wave_form_signal_type) {
        this.waveFormSignalType = wave_form_signal_type;
    }

    public int getInputAudioSource() {
        return this.inputAudioSource;
    }

    public void setInputAudioSource(int i) {
        this.inputAudioSource = i;
    }

    public int getInputSampleRateInHz() {
        return this.inputSampleRateInHz;
    }

    public void setInputSampleRateInHz(int i) {
        this.inputSampleRateInHz = i;
        setDerivedFields(this);
    }

    public int getInputChannelConfig() {
        return this.inputChannelConfig;
    }

    public void setInputChannelConfig(int i) {
        this.inputChannelConfig = i;
    }

    public int getInputAudioFormat() {
        return this.inputAudioFormat;
    }

    public void setInputAudioFormat(int i) {
        this.inputAudioFormat = i;
    }

    public int getOutputSampleRateInHz() {
        return this.outputSampleRateInHz;
    }

    public void setOutputSampleRateInHz(int i) {
        this.outputSampleRateInHz = i;
    }

    public int getOutputChannelConfig() {
        return this.outputChannelConfig;
    }

    public void setOutputChannelConfig(int i) {
        this.outputChannelConfig = i;
    }

    public int getOutputAudioFormat() {
        return this.outputAudioFormat;
    }

    public void setOutputAudioFormat(int i) {
        this.outputAudioFormat = i;
    }

    public int getOutputStreamType() {
        return this.outputStreamType;
    }

    public void setOutputStreamType(int i) {
        this.outputStreamType = i;
    }

    public int getOutputMode() {
        return this.outputMode;
    }

    public void setOutputMode(int i) {
        this.outputMode = i;
    }

    public float getSamplesPerBit() {
        return this.samplesPerBit;
    }

    public void setSamplesPerBit(float f) {
        this.samplesPerBit = f;
    }

    public float getHalfBitLimit() {
        return this.halfBitLimit;
    }

    public void setHalfBitLimit(float f) {
        this.halfBitLimit = f;
    }

    public float getUpper3BitLimit() {
        return this.upper3BitLimit;
    }

    public void setUpper3BitLimit(float f) {
        this.upper3BitLimit = f;
    }

    public float getLower3BitLimit() {
        return this.lower3BitLimit;
    }

    public void setLower3BitLimit(float f) {
        this.lower3BitLimit = f;
    }

    public int getUpperSyncSampleLimit() {
        return this.upperSyncSampleLimit;
    }

    public void setUpperSyncSampleLimit(int i) {
        this.upperSyncSampleLimit = i;
    }

    public int getLowerSyncSampleLimit() {
        return this.lowerSyncSampleLimit;
    }

    public void setLowerSyncSampleLimit(int i) {
        this.lowerSyncSampleLimit = i;
    }

    public int getMaxBitPosition() {
        return this.maxBitPosition;
    }

    public void setMaxBitPosition(int i) {
        this.maxBitPosition = i;
    }

    public short getHighComparatorThreshold() {
        return this.highComparatorThreshold;
    }

    public void setHighComparatorThreshold(short s) {
        this.highComparatorThreshold = s;
    }

    public short getLowComparatorThreshold() {
        return this.lowComparatorThreshold;
    }

    public void setLowComparatorThreshold(short s) {
        this.lowComparatorThreshold = s;
    }

    public short getHighSample() {
        return this.highSample;
    }

    public void setHighSample(short s) {
        this.highSample = s;
    }

    public short getLowSample() {
        return this.lowSample;
    }

    public void setLowSample(short s) {
        this.lowSample = s;
    }

    public boolean isLogicInverted() {
        return this.logicInverted;
    }

    public short getLogicHigh() {
        return this.logicHigh;
    }

    public void setLogicHigh(short s) {
        this.logicHigh = s;
    }

    public short getLogicLow() {
        return this.logicLow;
    }

    public void setLogicLow(short s) {
        this.logicLow = s;
    }

    public void setLogicInverted(boolean z) {
        this.logicInverted = z;
    }

    public TRANSMISSION_ALERT_MODE getTransmissionAlertMode() {
        return this.transmissionAlertMode;
    }

    public void setTransmissionAlertMode(TRANSMISSION_ALERT_MODE transmission_alert_mode) {
        this.transmissionAlertMode = transmission_alert_mode;
    }

    public int getIntraPacketTimeoutInMs() {
        return this.intraPacketTimeoutInMs;
    }

    public void setIntraPacketTimeoutInMs(int i) {
        this.intraPacketTimeoutInMs = i;
    }

    public int getInterPacketTimeoutInMs() {
        return this.interPacketTimeoutInMs;
    }

    public void setInterPacketTimeoutInMs(int i) {
        this.interPacketTimeoutInMs = i;
    }

    public int getInterTransactionTimeoutInMs() {
        return this.interTransactionTimeoutInMs;
    }

    public void setInterTransactionTimeoutInMs(int i) {
        this.interTransactionTimeoutInMs = i;
    }

    public COMMAND_OUTPUT_LOGIC getCommandOutputLogic() {
        return this.commandOutputLogic;
    }

    public void setCommandOutputLogic(COMMAND_OUTPUT_LOGIC command_output_logic) {
        this.commandOutputLogic = command_output_logic;
    }

    public int getIncreasingOutputInMs() {
        return this.increasingOutputInMs;
    }

    public void setIncreasingOutputInMs(int i) {
        this.increasingOutputInMs = i;
    }

    public boolean getBoostUsingEqualizer() {
        return this.boostUsingEqualizer;
    }

    public void setBoostUsingEqualizer(boolean z) {
        this.boostUsingEqualizer = z;
    }

    public boolean getSwapLeftRight() {
        return this.swapLeftRight;
    }

    public void setSwapLeftRight(boolean z) {
        this.swapLeftRight = z;
    }

    public boolean getBothLeftRight() {
        return this.bothLeftRight;
    }

    public void setBothLeftRight(boolean z) {
        this.bothLeftRight = z;
    }

    public String toString() {
        return ((((((((((((((((((((((((((((((((("" + String.format("readerBaud\t= %f\n", Float.valueOf(this.readerBaud))) + String.format("inputAudioSource\t= %d\n", Integer.valueOf(this.inputAudioSource))) + String.format("inputSampleRateInHz\t= %d\n", Integer.valueOf(this.inputSampleRateInHz))) + String.format("inputChannelConfig\t= %d\n", Integer.valueOf(this.inputChannelConfig))) + String.format("inputAudioFormat\t= %d\n", Integer.valueOf(this.inputAudioFormat))) + String.format("outputSampleRateInHz\t= %d\n", Integer.valueOf(this.outputSampleRateInHz))) + String.format("outputChannelConfig\t= %d\n", Integer.valueOf(this.outputChannelConfig))) + String.format("outputAudioFormat\t= %d\n", Integer.valueOf(this.outputAudioFormat))) + String.format("outputStreamType\t= %d\n", Integer.valueOf(this.outputStreamType))) + String.format("outputMode\t= %d\n", Integer.valueOf(this.outputMode))) + String.format("samplePerBit\t= %d\n", Float.valueOf(this.samplesPerBit))) + String.format("halfBitLimit\t= %d\n", Float.valueOf(this.halfBitLimit))) + String.format("upper3BitLimit\t= %d\n", Float.valueOf(this.upper3BitLimit))) + String.format("lower3BitLimit\t= %d\n", Float.valueOf(this.lower3BitLimit))) + String.format("upperSyncSampleLimit\t= %d\n", Integer.valueOf(this.upperSyncSampleLimit))) + String.format("lowerSyncSampleLimit\t= %d\n", Integer.valueOf(this.lowerSyncSampleLimit))) + String.format("maxBitPosition\t= %d\n", Integer.valueOf(this.maxBitPosition))) + String.format("highComparatorThreshold\t= %d\n", Short.valueOf(this.highComparatorThreshold))) + String.format("lowComparatorThreshold\t= %d\n", Short.valueOf(this.lowComparatorThreshold))) + String.format("highSample\t= %d\n", Short.valueOf(this.highSample))) + String.format("lowSample\t= %d\n", Short.valueOf(this.lowSample))) + String.format("logicHigh\t= %d\n", Short.valueOf(this.logicHigh))) + String.format("logicLow\t= %d\n", Short.valueOf(this.logicLow))) + String.format("logicInverted\t= %b\n", Boolean.valueOf(this.logicInverted))) + String.format("intraPacketTimeoutInMs\t= %d\n", Integer.valueOf(this.intraPacketTimeoutInMs))) + String.format("interPacketTimeoutInMs\t= %d\n", Integer.valueOf(this.interPacketTimeoutInMs))) + String.format("interTransactionTimeoutInMs\t= %d\n", Integer.valueOf(this.interTransactionTimeoutInMs))) + String.format("transmissionAlertMode\t= %s\n", this.transmissionAlertMode.toString())) + String.format("signalType\t= %s\n", this.waveFormSignalType.toString())) + String.format("commandOutputLogic\t= %d\n", this.commandOutputLogic)) + String.format("increasingOutputInMs\t= %d\n", Integer.valueOf(this.increasingOutputInMs))) + String.format("boostUsingEqualizer\t= %b\n", Boolean.valueOf(this.boostUsingEqualizer))) + String.format("swapLeftRight\t= %b\n", Boolean.valueOf(this.swapLeftRight))) + String.format("bothLeftRight\t= %b\n", Boolean.valueOf(this.bothLeftRight));
    }

    public void setConfiguration(String str, String str2) throws MTSCRAException {
        try {
            if (str.equalsIgnoreCase("INPUT_SAMPLE_RATE_IN_HZ")) {
                try {
                    setInputSampleRateInHz(Integer.parseInt(str2));
                    return;
                } catch (Exception unused) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            boolean z = true;
            if (str.equalsIgnoreCase("INPUT_WAVE_FORM")) {
                try {
                    int i = Integer.parseInt(str2);
                    if (i == 0) {
                        setWaveFormSignalType(WAVE_FORM_SIGNAL_TYPE.SINE);
                    } else if (i == 1) {
                        setWaveFormSignalType(WAVE_FORM_SIGNAL_TYPE.SQUARE);
                    } else {
                        setWaveFormSignalType(WAVE_FORM_SIGNAL_TYPE.SQUARE);
                    }
                    return;
                } catch (Exception unused2) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("WAVE_FORM_FREQUENCY_IN_HZ")) {
                try {
                    setWaveFormFrequency(Integer.parseInt(str2));
                    return;
                } catch (Exception unused3) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("HALF_BIT_LIMIT")) {
                try {
                    setHalfBitLimit(Float.parseFloat(str2));
                    return;
                } catch (Exception unused4) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INPUT_AUDIO_FORMAT")) {
                try {
                    setInputAudioFormat(Integer.parseInt(str2));
                    return;
                } catch (Exception unused5) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("OUTPUT_AUDIO_FORMAT")) {
                try {
                    setOutputAudioFormat(Integer.parseInt(str2));
                    return;
                } catch (Exception unused6) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INPUT_CHANNEL_CONFIG")) {
                try {
                    setInputChannelConfig(Integer.parseInt(str2));
                    return;
                } catch (Exception unused7) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INPUT_AUDIO_SOURCE")) {
                try {
                    if (str2.equalsIgnoreCase("VRECOG")) {
                        setInputAudioSource(6);
                        return;
                    }
                    if (str2.equalsIgnoreCase("VCALL")) {
                        setInputAudioSource(4);
                        return;
                    }
                    if (str2.equalsIgnoreCase("MIC")) {
                        setInputAudioSource(1);
                        return;
                    }
                    if (str2.equalsIgnoreCase("DEFAULT")) {
                        setInputAudioSource(0);
                        return;
                    }
                    if (str2.equalsIgnoreCase("CAMC")) {
                        setInputAudioSource(5);
                        return;
                    }
                    if (str2.equalsIgnoreCase("VCOMM")) {
                        throw new MTSCRAException("Invalid Configuration Value:" + str2);
                    }
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                } catch (Exception unused8) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INPUT_AUDIO_SOURCE_VRECOG")) {
                try {
                    setInputAudioSource(6);
                    return;
                } catch (Exception unused9) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INPUT_AUDIO_SOURCE_VDNLINK")) {
                try {
                    setInputAudioSource(3);
                    return;
                } catch (Exception unused10) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INPUT_AUDIO_SOURCE_VUPLINK")) {
                try {
                    setInputAudioSource(2);
                    return;
                } catch (Exception unused11) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INTER_PACKET_TIMEOUT_MS")) {
                try {
                    setInterPacketTimeoutInMs(Integer.parseInt(str2));
                    return;
                } catch (Exception unused12) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INTER_TRANSACTION_TIMEOUT_MS")) {
                try {
                    setInterTransactionTimeoutInMs(Integer.parseInt(str2));
                    return;
                } catch (Exception unused13) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INTRA_PACKET_TIMEOUT_MS")) {
                try {
                    setIntraPacketTimeoutInMs(Integer.parseInt(str2));
                    return;
                } catch (Exception unused14) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("LOWER_3BIT_LIMIT")) {
                try {
                    setLower3BitLimit(Float.parseFloat(str2));
                    return;
                } catch (Exception unused15) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("LOWER_SYNC_SAMPLE_LIMIT")) {
                try {
                    setLowerSyncSampleLimit(Integer.parseInt(str2));
                    return;
                } catch (Exception unused16) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("MAX_BIT_POSITION")) {
                try {
                    setMaxBitPosition(Integer.parseInt(str2));
                    return;
                } catch (Exception unused17) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("READER_BAUD")) {
                try {
                    setReaderBaud(Integer.parseInt(str2));
                    return;
                } catch (Exception unused18) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("SAMPLES_PER_BIT")) {
                try {
                    setSamplesPerBit(Float.parseFloat(str2));
                    return;
                } catch (Exception unused19) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("UPPER_3BIT_LIMIT")) {
                try {
                    setUpper3BitLimit(Float.parseFloat(str2));
                    return;
                } catch (Exception unused20) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("UPPER_SYNC_SAMPLE_LIMIT")) {
                try {
                    setUpperSyncSampleLimit(Integer.parseInt(str2));
                    return;
                } catch (Exception unused21) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("OUTPUT_MODE")) {
                try {
                    setOutputMode(Integer.parseInt(str2));
                    return;
                } catch (Exception unused22) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("OUTPUT_SAMPLE_RATE_IN_HZ")) {
                try {
                    setOutputSampleRateInHz(Integer.parseInt(str2));
                    return;
                } catch (Exception unused23) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("OUTPUT_STREAM_TYPE")) {
                try {
                    setOutputStreamType(Integer.parseInt(str2));
                    return;
                } catch (Exception unused24) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("LOGIC_INVERTED")) {
                try {
                    setLogicInverted(Boolean.parseBoolean(str2));
                    return;
                } catch (Exception unused25) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("HIGH_COMPARATOR_THRESHOLD")) {
                try {
                    setHighComparatorThreshold(Short.parseShort(str2));
                    return;
                } catch (Exception unused26) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("LOW_COMPARATOR_THRESHOLD")) {
                try {
                    setLowComparatorThreshold(Short.parseShort(str2));
                    return;
                } catch (Exception unused27) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("HIGH_SAMPLE")) {
                try {
                    setHighSample(Short.parseShort(str2));
                    return;
                } catch (Exception unused28) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("LOW_SAMPLE")) {
                try {
                    setLowSample(Short.parseShort(str2));
                    return;
                } catch (Exception unused29) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("LOGIC_HIGH")) {
                try {
                    setLogicHigh(Short.parseShort(str2));
                    return;
                } catch (Exception unused30) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("LOGIC_LOW")) {
                try {
                    setLogicHigh(Short.parseShort(str2));
                    return;
                } catch (Exception unused31) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("COMMAND_OUTPUT_LOGIC")) {
                try {
                    short s = Short.parseShort(str2);
                    if (s == 0) {
                        setCommandOutputLogic(COMMAND_OUTPUT_LOGIC.NON_INVERTED);
                    } else if (s == 1) {
                        setCommandOutputLogic(COMMAND_OUTPUT_LOGIC.INVERTED);
                    } else if (s == 2) {
                        setCommandOutputLogic(COMMAND_OUTPUT_LOGIC.NON_INVERTED_AND_INVERTED);
                    } else {
                        setCommandOutputLogic(COMMAND_OUTPUT_LOGIC.NON_INVERTED);
                    }
                    return;
                } catch (Exception unused32) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("INCREASING_OUTPUT_IN_MS")) {
                try {
                    setIncreasingOutputInMs(Short.parseShort(str2));
                    return;
                } catch (Exception unused33) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("BOOST_USING_EQUALIZER")) {
                try {
                    if (Short.parseShort(str2) <= 0) {
                        z = false;
                    }
                    setBoostUsingEqualizer(z);
                    return;
                } catch (Exception unused34) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("SWAP_LEFT_RIGHT")) {
                try {
                    if (Short.parseShort(str2) <= 0) {
                        z = false;
                    }
                    setSwapLeftRight(z);
                    return;
                } catch (Exception unused35) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            if (str.equalsIgnoreCase("BOTH_LEFT_RIGHT")) {
                try {
                    if (Short.parseShort(str2) <= 0) {
                        z = false;
                    }
                    setBothLeftRight(z);
                    return;
                } catch (Exception unused36) {
                    throw new MTSCRAException("Invalid Configuration Value:" + str2);
                }
            }
            throw new MTSCRAException("Invalid Configuration:" + str);
        } catch (Exception e) {
            throw new MTSCRAException(e.getMessage());
        }
        throw new MTSCRAException(e.getMessage());
    }

    public void setConfigurationParams(String str) throws MTSCRAException {
        String[] strArrSplit = str.split(",");
        if (strArrSplit.length > 0) {
            for (String str2 : strArrSplit) {
                String[] strArrSplit2 = str2.split("=");
                if (strArrSplit2.length == 2) {
                    setConfiguration(strArrSplit2[0].toUpperCase().trim(), strArrSplit2[1].toUpperCase().trim());
                }
            }
        }
    }
}
