package iaik.utils;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public class Base64InputStream extends FilterInputStream {
    protected static final int ERROR = -1;
    protected static final int IGNORE = -2;
    protected static final int NOTIFY = -3;
    private static boolean a = false;
    private static final byte[] i = {marshall_t.status_vpos_processing_error, marshall_t.status_vpos_please_remove_card, 67, marshall_t.status_vpos_please_use_mag, marshall_t.status_vpos_try_again, 70, marshall_t.status_vpos_present_card, 72, 73, marshall_t.status_vpos_please_insert_or_swipe_card, marshall_t.status_vpos_please_present_one_card_only, 76, 77, marshall_t.status_vpos_try_another_card, marshall_t.status_vpos_please_insert_card, marshall_t.marshall_func_code_commodore_ext_commodore_tlv, 81, marshall_t.status_vpos_see_phone_for_instructions, marshall_t.status_vpos_present_card_again, marshall_t.status_vpos_insert_or_swipe_another_card, 85, 86, 87, 88, 89, 90, 97, 98, 99, marshall_t.marshalll_display_control_button_id_left_arrow, marshall_t.marshalll_display_control_button_id_menu, marshall_t.marshalll_display_control_button_id_right_arrow, marshall_t.marshalll_display_control_button_id_cancel, marshall_t.marshalll_display_control_button_id_back, 105, marshall_t.marshalll_display_control_button_id_touch, 107, 108, 109, 110, 111, 112, PPSCRADeviceValues.FUNCTION_KEY_LEFT, PPSCRADeviceValues.FUNCTION_KEY_MIDDLE, 115, PPSCRADeviceValues.FUNCTION_KEY_RIGHT, 117, 118, 119, PPSCRADeviceValues.FUNCTION_KEY_ENTER, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, marshall_t.status_vpos_card_error, 57, 43, PPSCRADeviceValues.RESPONSE_OPEN_HOST_CONNECTION, marshall_t.status_vpos_insert_card};
    private byte[] b;
    private byte[] c;
    private byte[] d;
    protected int[] decoding;
    private boolean e;
    private int f;
    private int g;
    private boolean h;

    public Base64InputStream(InputStream inputStream) {
        super(inputStream);
        this.b = new byte[]{0};
        this.c = new byte[4];
        this.d = new byte[4];
        this.e = false;
        this.f = 0;
        this.g = 0;
        int[] iArr = new int[256];
        this.decoding = iArr;
        this.h = a;
        a(iArr);
    }

    public Base64InputStream(InputStream inputStream, boolean z) {
        super(inputStream);
        this.b = new byte[]{0};
        this.c = new byte[4];
        this.d = new byte[4];
        this.e = false;
        this.f = 0;
        this.g = 0;
        int[] iArr = new int[256];
        this.decoding = iArr;
        this.h = z;
        a(iArr);
    }

    private static final int a(byte[] bArr, int i2) {
        int i3;
        int i4;
        if (i2 == 1) {
            i3 = (bArr[0] & 63) << 2;
            i4 = (bArr[1] & 48) >>> 4;
        } else if (i2 == 2) {
            i3 = (bArr[1] & 15) << 4;
            i4 = (bArr[2] & 60) >>> 2;
        } else {
            if (i2 != 3) {
                return 0;
            }
            i3 = (bArr[2] & 3) << 6;
            i4 = bArr[3] & 63;
        }
        return i4 | i3;
    }

    private static void a(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            iArr[i3] = -1;
        }
        while (true) {
            byte[] bArr = i;
            if (i2 >= bArr.length) {
                iArr[13] = -2;
                iArr[10] = -2;
                return;
            } else {
                iArr[bArr[i2]] = i2;
                i2++;
            }
        }
    }

    public static void setDefaultIgnoreInvalidCharacters(boolean z) {
        a = z;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    protected void notify(byte[] bArr) throws IOException {
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (read(this.b, 0, 1) < 0) {
            return -1;
        }
        return this.b[0] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (this.e) {
            return -1;
        }
        int i4 = 0;
        while (true) {
            int i5 = this.f;
            if (i5 < this.g) {
                int i6 = i4 + 1;
                byte[] bArr2 = this.c;
                int i7 = i5 + 1;
                this.f = i7;
                bArr[i4 + i2] = (byte) a(bArr2, i7);
                if (i6 >= i3) {
                    return i6;
                }
                i4 = i6;
            } else {
                this.f = 0;
                int i8 = 0;
                while (i8 < 4) {
                    int i9 = this.in.read(this.d, 0, 4 - i8);
                    if (i9 == -1) {
                        if (i8 != 0) {
                            throw new Base64Exception("Invalid length.");
                        }
                        this.e = true;
                        if (i4 == 0) {
                            return -1;
                        }
                        return i4;
                    }
                    int i10 = 0;
                    while (true) {
                        if (i10 < i9) {
                            byte[] bArr3 = this.d;
                            int i11 = bArr3[i10] & 255;
                            int i12 = this.decoding[i11];
                            if (i12 >= 0) {
                                this.c[i8] = (byte) i12;
                                i8++;
                            } else {
                                if (i12 == -1 && !this.h) {
                                    StringBuffer stringBuffer = new StringBuffer();
                                    stringBuffer.append("Invalid character '");
                                    stringBuffer.append(i11);
                                    stringBuffer.append("'!");
                                    throw new Base64Exception(stringBuffer.toString());
                                }
                                if (i12 == -3) {
                                    if (i8 != 0) {
                                        StringBuffer stringBuffer2 = new StringBuffer();
                                        stringBuffer2.append("Invalid character '");
                                        stringBuffer2.append(i11);
                                        stringBuffer2.append("'!");
                                        throw new Base64Exception(stringBuffer2.toString());
                                    }
                                    notify(bArr3);
                                }
                            }
                            i10++;
                        }
                    }
                }
                byte[] bArr4 = this.c;
                if (bArr4[2] == 64) {
                    if (bArr4[3] != 64) {
                        throw new Base64Exception("Invalid padding!");
                    }
                    this.g = 1;
                } else if (bArr4[3] == 64) {
                    this.g = 2;
                } else {
                    this.g = 3;
                }
            }
        }
    }

    public void setIgnoreInvalidCharacters(boolean z) {
        this.h = z;
    }
}
