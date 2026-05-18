package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class CardDataInfo {
    public byte[] CBCMAC;
    public byte[] EncryptedPANExpDate;
    public byte EncryptedPANExpDateStatus;
    public byte[] EncryptedTrack1;
    public byte EncryptedTrack1Status;
    public byte[] EncryptedTrack2;
    public byte EncryptedTrack2Status;
    public byte[] EncryptedTrack3;
    public byte EncryptedTrack3Status;
    public byte[] KSN;
    public byte KSNMagnePrintStatus;
    public byte[] MagnePrint;
    public byte[] MagnePrintStatus;
    public byte[] PINPADSerialNumber;
    public String Track1;
    public byte Track1Status;
    public String Track2;
    public byte Track2Status;
    public String Track3;
    public byte Track3Status;
    private String a;
    private String b;
    private String c;
    private String d = "";

    CardDataInfo() {
        clearData();
    }

    public void clearData() {
        this.Track1 = "";
        this.Track2 = "";
        this.Track3 = "";
        this.EncryptedTrack1 = null;
        this.EncryptedTrack2 = null;
        this.EncryptedTrack3 = null;
        this.MagnePrint = null;
        this.EncryptedPANExpDate = null;
        this.PINPADSerialNumber = null;
        this.KSN = null;
        this.MagnePrintStatus = null;
        this.CBCMAC = null;
        this.Track1Status = (byte) 1;
        this.Track2Status = (byte) 1;
        this.Track3Status = (byte) 1;
        this.EncryptedTrack1Status = (byte) 1;
        this.EncryptedTrack2Status = (byte) 1;
        this.EncryptedTrack3Status = (byte) 1;
        this.KSNMagnePrintStatus = (byte) 1;
        this.EncryptedPANExpDateStatus = (byte) 1;
    }

    private String a() {
        try {
            if (this.Track1.length() <= 0) {
                return "";
            }
            int iIndexOf = this.Track1.indexOf("%");
            int iIndexOf2 = this.Track1.indexOf("^");
            return (iIndexOf == -1 || iIndexOf2 == -1) ? "" : this.Track1.substring(iIndexOf + 2, iIndexOf2);
        } catch (Exception unused) {
            return "";
        }
    }

    private String b() {
        try {
            if (this.Track2.length() <= 0) {
                return "";
            }
            int iIndexOf = this.Track2.indexOf(";");
            int iIndexOf2 = this.Track2.indexOf("=");
            return (iIndexOf == -1 || iIndexOf2 == -1) ? "" : this.Track2.substring(iIndexOf + 1, iIndexOf2);
        } catch (Exception unused) {
            return "";
        }
    }

    private String c() {
        String strE = e();
        return strE.trim().length() == 0 ? d() : strE;
    }

    private String d() {
        String[] strArrSplit;
        try {
            return (this.Track1.length() <= 0 || (strArrSplit = this.Track1.split("\\^")) == null || strArrSplit.length <= 2 || strArrSplit[2].length() <= 7) ? "" : strArrSplit[2].substring(0, 7);
        } catch (Exception unused) {
            return "";
        }
    }

    private String e() {
        int iIndexOf;
        try {
            return (this.Track2.length() <= 0 || (iIndexOf = this.Track2.indexOf("=")) == -1) ? "" : this.Track2.substring(iIndexOf + 1, iIndexOf + 8);
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void a(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = " "
            java.lang.String r1 = "^"
            java.lang.String r2 = "/"
            int r3 = r9.length()     // Catch: java.lang.Exception -> Lc1
            java.lang.String r4 = ""
            r5 = 1
            if (r3 <= 0) goto L28
            int r3 = r9.indexOf(r1)     // Catch: java.lang.Exception -> Lc1
            r6 = -1
            if (r3 == r6) goto L1d
            int r7 = r3 + 1
            int r1 = r9.indexOf(r1, r7)     // Catch: java.lang.Exception -> Lc1
            goto L1e
        L1d:
            r1 = -1
        L1e:
            if (r3 == r6) goto L28
            if (r1 == r6) goto L28
            int r3 = r3 + r5
            java.lang.String r9 = r9.substring(r3, r1)     // Catch: java.lang.Exception -> Lc1
            goto L29
        L28:
            r9 = r4
        L29:
            java.lang.String[] r9 = r9.split(r2)     // Catch: java.lang.Exception -> Lc1
            int r1 = r9.length     // Catch: java.lang.Exception -> Lc1
            r3 = 2
            r6 = 0
            if (r1 <= r3) goto L72
            r0 = r9[r6]     // Catch: java.lang.Exception -> Lc1
            r8.c = r0     // Catch: java.lang.Exception -> Lc1
        L36:
            int r0 = r9.length     // Catch: java.lang.Exception -> Lc1
            if (r5 >= r0) goto Lc1
            java.lang.String r0 = r8.a     // Catch: java.lang.Exception -> Lc1
            int r0 = r0.length()     // Catch: java.lang.Exception -> Lc1
            if (r0 != 0) goto L57
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc1
            r0.<init>()     // Catch: java.lang.Exception -> Lc1
            java.lang.String r1 = r8.a     // Catch: java.lang.Exception -> Lc1
            r0.append(r1)     // Catch: java.lang.Exception -> Lc1
            r1 = r9[r5]     // Catch: java.lang.Exception -> Lc1
            r0.append(r1)     // Catch: java.lang.Exception -> Lc1
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> Lc1
            r8.a = r0     // Catch: java.lang.Exception -> Lc1
            goto L6f
        L57:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc1
            r0.<init>()     // Catch: java.lang.Exception -> Lc1
            java.lang.String r1 = r8.a     // Catch: java.lang.Exception -> Lc1
            r0.append(r1)     // Catch: java.lang.Exception -> Lc1
            r0.append(r2)     // Catch: java.lang.Exception -> Lc1
            r1 = r9[r5]     // Catch: java.lang.Exception -> Lc1
            r0.append(r1)     // Catch: java.lang.Exception -> Lc1
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> Lc1
            r8.a = r0     // Catch: java.lang.Exception -> Lc1
        L6f:
            int r5 = r5 + 1
            goto L36
        L72:
            int r1 = r9.length     // Catch: java.lang.Exception -> Lc1
            if (r1 <= r5) goto Lb6
            r1 = r9[r6]     // Catch: java.lang.Exception -> Lc1
            r8.c = r1     // Catch: java.lang.Exception -> Lc1
            r9 = r9[r5]     // Catch: java.lang.Exception -> Lc1
            java.lang.String[] r1 = r9.split(r0)     // Catch: java.lang.Exception -> Lc1
            int r2 = r1.length     // Catch: java.lang.Exception -> Lc1
            if (r2 <= r5) goto Lb3
            r9 = r1[r6]     // Catch: java.lang.Exception -> Lc1
            r8.a = r9     // Catch: java.lang.Exception -> Lc1
            r8.b = r4     // Catch: java.lang.Exception -> Lc1
        L88:
            int r9 = r1.length     // Catch: java.lang.Exception -> Lc1
            if (r5 >= r9) goto Lc1
            java.lang.String r9 = r8.b     // Catch: java.lang.Exception -> Lc1
            int r9 = r9.length()     // Catch: java.lang.Exception -> Lc1
            if (r9 != 0) goto L98
            r9 = r1[r5]     // Catch: java.lang.Exception -> Lc1
            r8.b = r9     // Catch: java.lang.Exception -> Lc1
            goto Lb0
        L98:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc1
            r9.<init>()     // Catch: java.lang.Exception -> Lc1
            java.lang.String r2 = r8.b     // Catch: java.lang.Exception -> Lc1
            r9.append(r2)     // Catch: java.lang.Exception -> Lc1
            r9.append(r0)     // Catch: java.lang.Exception -> Lc1
            r2 = r1[r5]     // Catch: java.lang.Exception -> Lc1
            r9.append(r2)     // Catch: java.lang.Exception -> Lc1
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> Lc1
            r8.b = r9     // Catch: java.lang.Exception -> Lc1
        Lb0:
            int r5 = r5 + 1
            goto L88
        Lb3:
            r8.a = r9     // Catch: java.lang.Exception -> Lc1
            goto Lc1
        Lb6:
            int r0 = r9.length     // Catch: java.lang.Exception -> Lc1
            if (r0 <= 0) goto Lc1
            r9 = r9[r6]     // Catch: java.lang.Exception -> Lc1
            r8.c = r9     // Catch: java.lang.Exception -> Lc1
            r8.b = r4     // Catch: java.lang.Exception -> Lc1
            r8.a = r4     // Catch: java.lang.Exception -> Lc1
        Lc1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.ppscra.CardDataInfo.a(java.lang.String):void");
    }

    private void b(String str) {
        String str2;
        try {
            String str3 = "";
            if (str.length() <= 0 || !str.startsWith("%")) {
                str2 = "";
            } else {
                int iIndexOf = str.indexOf("^");
                int i = iIndexOf < 16 ? iIndexOf : 15;
                str2 = "";
                int i2 = 0;
                while (i < str.length() && i2 <= 34) {
                    int i3 = i + 1;
                    String strSubstring = str.substring(i3, i + 2);
                    if (strSubstring.equalsIgnoreCase("^")) {
                        break;
                    }
                    i2++;
                    str2 = str2 + strSubstring;
                    i = i3;
                }
            }
            String[] strArrSplit = str2.split("\\$");
            if (strArrSplit.length > 2) {
                this.c = strArrSplit[0];
                this.a = strArrSplit[1];
                for (int i4 = 2; i4 < strArrSplit.length; i4++) {
                    String str4 = str3 + strArrSplit[i4];
                    if (i4 < strArrSplit.length - 1) {
                        str4 = str4 + " ";
                    }
                    str3 = str4;
                }
                this.b = str3;
                return;
            }
            if (strArrSplit.length > 1) {
                this.c = strArrSplit[0];
                this.a = strArrSplit[1];
            } else if (strArrSplit.length > 0) {
                this.c = strArrSplit[0];
            }
        } catch (Exception unused) {
        }
    }

    private void f() {
        try {
            String track1Masked = getTrack1Masked();
            this.a = "";
            this.b = "";
            this.c = "";
            a(track1Masked);
            if (this.a.trim().length() == 0 && this.c.trim().length() == 0) {
                b(track1Masked);
            }
            this.d = track1Masked;
        } catch (Exception unused) {
        }
    }

    public String getTrack1() {
        return PPSCRACommon.getHexString(this.EncryptedTrack1, 0, null);
    }

    public String getTrack2() {
        return PPSCRACommon.getHexString(this.EncryptedTrack2, 0, null);
    }

    public String getTrack3() {
        return PPSCRACommon.getHexString(this.EncryptedTrack3, 0, null);
    }

    public String getTrack1Masked() {
        return this.Track1;
    }

    public String getTrack2Masked() {
        return this.Track2;
    }

    public String getTrack3Masked() {
        return this.Track3;
    }

    public String getMaskedTracks() {
        return getTrack1Masked() + getTrack2Masked() + getTrack3Masked();
    }

    public String getMagnePrint() {
        return PPSCRACommon.getHexString(this.MagnePrint, 0, null);
    }

    public byte getKSNMagnePrintStatus() {
        return this.KSNMagnePrintStatus;
    }

    public byte getTrack1DecodeStatus() {
        return this.Track1Status;
    }

    public byte getTrack2DecodeStatus() {
        return this.Track2Status;
    }

    public byte getTrack3DecodeStatus() {
        return this.Track3Status;
    }

    public String getLastName() {
        f();
        String str = this.c;
        return str == null ? "" : str;
    }

    public String getFirstName() {
        f();
        String str = this.a;
        return str == null ? "" : str;
    }

    public String getMiddleName() {
        f();
        String str = this.b;
        return str == null ? "" : str;
    }

    public String getName() {
        try {
            String firstName = getFirstName();
            try {
                String middleName = getMiddleName();
                if (middleName.length() > 0) {
                    if (firstName.length() > 0) {
                        firstName = firstName + " ";
                    }
                    firstName = firstName + middleName;
                }
                String lastName = getLastName();
                if (lastName.length() <= 0) {
                    return firstName;
                }
                if (firstName.length() > 0) {
                    firstName = firstName + " ";
                }
                return firstName + lastName;
            } catch (Exception unused) {
                return firstName;
            }
        } catch (Exception unused2) {
            return "";
        }
    }

    public String getExpDate() {
        try {
            String strC = c();
            return strC.length() > 6 ? strC.substring(0, 4) : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public String getLast4() {
        try {
            String maskedPAN = getMaskedPAN();
            return maskedPAN.length() > 4 ? maskedPAN.substring(maskedPAN.length() - 4) : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public String getServiceCode() {
        try {
            String strC = c();
            return strC.length() > 6 ? strC.substring(4) : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public String getCardIIN() {
        try {
            String maskedPAN = getMaskedPAN();
            return maskedPAN.length() >= 6 ? maskedPAN.substring(0, 6) : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public String getMaskedPAN() {
        String strB = b();
        return strB.trim().length() == 0 ? a() : strB;
    }
}
