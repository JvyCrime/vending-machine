package iaik.security.cipher;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import com.magtek.mobile.android.mtlib.MTEMVEvent;
import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import com.magtek.mobile.android.mtusdk.cms.EMVL2CommandID;
import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import org.kxml2.wap.Wbxml;

/* JADX INFO: loaded from: classes.dex */
public class RC2Parameters extends IvParameters {
    static final int[] b = {189, 86, 234, 242, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL2_REG, 241, 172, 42, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_DESC_STRING, 147, 209, 156, 27, 51, 253, 208, 48, 4, 182, 220, 125, 223, 50, 75, 247, MTEMVEvent.OnARQCReceived, 69, 155, 49, 187, 33, 90, 65, 159, 225, 217, 74, 77, 158, 218, 160, 104, 44, Wbxml.OPAQUE, 39, 95, 128, 54, 62, 238, 251, 149, 26, 254, MTEMVEvent.OnDeviceExtendedResponse, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_OSC_TRIM1_REG, 52, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_PHY_TXCTL_REG, 19, 240, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_CLK30K_TRIM_REG, 63, 216, 12, 120, 36, 175, 35, 82, Wbxml.EXT_1, 103, 23, FT_4222_Defines.I2C_CMD.I2C_MASTER_STATUS, 102, 144, 231, 232, 7, 184, 96, 72, 230, 30, 83, 243, 146, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_SR_CTL1_REG, 114, 140, 8, 21, 110, 134, 0, 132, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 244, 127, 138, 66, 25, 246, 219, MTEMVEvent.OnEMVCommandResult, 20, 141, 80, 18, 186, 60, 6, 78, 236, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_OTP_REG, 53, 17, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL1_REG, 136, 142, 43, 148, 153, 183, 113, 116, 211, 228, 191, 58, 222, 150, 14, 188, 10, 237, 119, 252, 55, 107, 3, 121, 137, 98, 198, 215, 192, 210, 124, 106, 139, 34, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_SR_CTL0_REG, 91, 5, 93, 2, 117, 213, 97, 227, 24, 143, 85, 81, 173, 31, 11, 94, 133, 229, Wbxml.EXT_2, 87, 99, MTEMVEvent.OnUserSelectionRequest, 61, 108, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_SFR, 197, MTEMVEvent.OnTransactionResult, 112, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_USB_REG, 145, 89, 13, 71, 32, 200, 79, 88, DataTypeTag.CONSTRUCTIVE, 1, 226, 22, 56, 196, 111, 59, 15, 101, 70, 190, 126, 45, 123, 130, 249, 64, 181, 29, 115, 248, 235, 38, 199, EMVL2CommandID.EMV_L2_PIN_ENTRY_SHOW_PROMPT, 151, 37, 84, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_CHIPTOP_REG, 40, 170, 152, 157, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_REG_TRIM_REG, 100, 109, 122, 212, 16, 129, 68, 239, 73, 214, 174, 46, 221, 118, 92, 47, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_OSC_TRIM0_REG, 28, MTEMVEvent.OnDisplayMessageRequest, 9, 105, 154, 131, 207, 41, 57, 185, 233, 76, 255, 67, 171};
    static final int[] c = new int[256];
    int d;

    static {
        for (int i = 0; i < 256; i++) {
            c[b[i]] = i;
        }
    }

    static int a(int i) {
        return i >= 256 ? i : b[i];
    }

    static int b(int i) {
        if (i <= 0) {
            return 128;
        }
        return i >= 256 ? i : c[i];
    }

    @Override // iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        ASN1Object octet_string;
        try {
            if (this.d > 0) {
                octet_string = new SEQUENCE();
                octet_string.addComponent(new INTEGER(this.d));
                octet_string.addComponent(new OCTET_STRING(this.a));
            } else {
                octet_string = new OCTET_STRING(this.a);
            }
            return DerCoder.encode(octet_string);
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Cannot encode RC2 parameters: ");
            stringBuffer.append(e.getMessage());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        return engineGetEncoded();
    }

    @Override // iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        RC2ParameterSpec rC2ParameterSpec = new RC2ParameterSpec(b(this.d), this.a);
        return !rC2ParameterSpec.getClass().isAssignableFrom(cls) ? super.engineGetParameterSpec(cls) : rC2ParameterSpec;
    }

    @Override // iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof RC2ParameterSpec) {
            RC2ParameterSpec rC2ParameterSpec = (RC2ParameterSpec) algorithmParameterSpec;
            this.d = a(rC2ParameterSpec.getEffectiveKeyBits());
            this.a = rC2ParameterSpec.getIV();
        } else {
            if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
                throw new InvalidParameterSpecException("Parameter must be IvParameterSpec or RC2ParameterSpec.");
            }
            this.a = ((IvParameterSpec) algorithmParameterSpec).getIV();
            this.d = -1;
        }
        if (this.a == null) {
            this.a = new byte[8];
        }
        if (this.a.length != 8) {
            throw new InvalidParameterSpecException("IV must be 8 octets long!");
        }
    }

    @Override // iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        try {
            ASN1Object aSN1ObjectDecode = DerCoder.decode(bArr);
            if (aSN1ObjectDecode.isA(ASN.SEQUENCE)) {
                this.d = ((BigInteger) aSN1ObjectDecode.getComponentAt(0).getValue()).intValue();
                this.a = (byte[]) aSN1ObjectDecode.getComponentAt(1).getValue();
            } else {
                if (!aSN1ObjectDecode.isA(ASN.OCTET_STRING)) {
                    throw new IOException("Cannot decode RC2 params. Invalid ASN.1 type!");
                }
                this.d = -1;
                this.a = (byte[]) aSN1ObjectDecode.getValue();
            }
            if (this.a == null || this.a.length != 8) {
                throw new IOException("Cannot init RC2 params. Invalid IV; must be 8 octets long.");
            }
        } catch (CodingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("DER decoding error. ");
            stringBuffer.append(e.toString());
            throw new IOException(stringBuffer.toString());
        }
    }

    @Override // iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, String str) throws IOException {
        engineInit(bArr);
    }

    @Override // iaik.security.cipher.IvParameters, java.security.AlgorithmParametersSpi
    protected String engineToString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.d > 0) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("rc2ParameterVersion: ");
            stringBuffer2.append(this.d);
            stringBuffer2.append("\n");
            stringBuffer.append(stringBuffer2.toString());
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("IV: ");
        stringBuffer3.append(Util.toString(this.a));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        return stringBuffer.toString();
    }
}
