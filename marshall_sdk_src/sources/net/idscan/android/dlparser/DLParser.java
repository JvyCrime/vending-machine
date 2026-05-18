package net.idscan.android.dlparser;

import android.content.Context;
import android.util.Base64;
import java.nio.ByteBuffer;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class DLParser {
    private static final int NATIVE_ERR_INVALID_LICENSE_KEY = -28;
    private static final int NATIVE_ERR_INVALID_LICENSE_KEY_EXPIRED = -30;
    private static final int NATIVE_ERR_INVALID_LICENSE_KEY_PACKAGE_NAME = -29;
    private static final int NATIVE_NO_ERROR = 0;
    private long _handle = createNativeInstance();
    private boolean _isClosed = false;

    public static class DLResult {
        public static final int FLAG_FALSE = 0;
        public static final int FLAG_TRUE = 1;
        public static final int FLAG_UNDEFINED = 2;
        public static final int STATUS_SUSPICIOUS = 2;
        public static final int STATUS_UNDEFINED = 0;
        public static final int STATUS_VALID = 1;
        public String documentType = "";
        public String countryCode = "";
        public String country = "";
        public String jurisdictionCode = "";
        public String iin = "";
        public String firstName = "";
        public String middleName = "";
        public String lastName = "";
        public String nameSuffix = "";
        public String namePrefix = "";
        public String fullName = "";
        public String address1 = "";
        public String address2 = "";
        public String city = "";
        public String licenseNumber = "";
        public String classificationCode = "";
        public String restrictionCode = "";
        public String restrictionCodeDescription = "";
        public String endorsementsCode = "";
        public String endorsementCodeDescription = "";
        public String gender = "";
        public String issuedBy = "";
        public String postalCode = "";
        public String eyeColor = "";
        public String race = "";
        public String hairColor = "";
        public String height = "";
        public String weightKG = "";
        public String weightLBS = "";
        public String complianceType = "";
        public String vehicleClassCode = "";
        public String vehicleClassCodeDescription = "";
        public int isLimitedDurationDocument = 2;
        public int isOrganDonor = 2;
        public int isVeteran = 2;
        public String expirationDate = "";
        public String birthdate = "";
        public String issueDate = "";
        public String HAZMATExpDate = "";
        public String cardRevisionDate = "";
        public int documentIdValidationStatus = 0;
        public int barcodeValidationStatus = 0;
        public int jurisdictionCodeValidationStatus = 0;
    }

    public enum ELicenseKeyStatus {
        empty,
        invalid,
        valid,
        expired
    }

    private native long createNativeInstance();

    private native void destroyNativeInstance(long j);

    private native int nativeCheckLicenseKey(Context context, long j, ByteBuffer byteBuffer, int i, long j2);

    private native int nativeGetBuildVersion(long j);

    private native int nativeGetMajorVersion(long j);

    private native int nativeGetMinorVersion(long j);

    private native String nativeGetVersion(long j);

    private native int nativeParse(long j, DLResult dLResult, ByteBuffer byteBuffer, int i, DocumentParameters documentParameters);

    private native int nativeSetupKey(Context context, long j, ByteBuffer byteBuffer, int i);

    public static class DocumentParameters {
        public final int columns;
        public final int ecl;
        public final int rows;

        public DocumentParameters(int i, int i2, int i3) {
            this.columns = i;
            this.rows = i2;
            this.ecl = i3;
        }
    }

    public static class DLParserException extends Exception {
        private static final long serialVersionUID = 6041509378972224384L;

        public DLParserException() {
        }

        public DLParserException(String str, Throwable th) {
            super(str, th);
        }

        public DLParserException(String str) {
            super(str);
        }

        public DLParserException(Throwable th) {
            super(th);
        }
    }

    protected void finalize() {
        if (this._isClosed) {
            return;
        }
        this._isClosed = true;
        destroyNativeInstance(this._handle);
        this._handle = 0L;
    }

    public final DLResult parse(byte[] bArr) throws DLParserException {
        return parse(bArr, null);
    }

    public final DLResult parse(byte[] bArr, DocumentParameters documentParameters) throws DLParserException {
        if (this._isClosed) {
            throw new IllegalStateException("Native instance not initialized.");
        }
        Objects.requireNonNull(bArr, "Parameter 'data' should not be null.");
        if (bArr.length == 0) {
            return new DLResult();
        }
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(bArr.length);
        byteBufferAllocateDirect.put(bArr);
        DLResult dLResult = new DLResult();
        int iNativeParse = nativeParse(this._handle, dLResult, byteBufferAllocateDirect, bArr.length, documentParameters);
        if (iNativeParse == 0) {
            return dLResult;
        }
        throw new DLParserException("Can't parse the data: " + _decodeError(iNativeParse));
    }

    public final void setup(Context context, String str) throws DLParserException {
        Objects.requireNonNull(context, "Parameter 'context' can't be null.");
        Objects.requireNonNull(str, "Parameter 'key' can't be null.");
        byte[] bArrDecode = Base64.decode(str, 0);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(bArrDecode.length);
        byteBufferAllocateDirect.put(bArrDecode);
        int iNativeSetupKey = nativeSetupKey(context, this._handle, byteBufferAllocateDirect, bArrDecode.length);
        if (iNativeSetupKey == 0) {
            return;
        }
        throw new DLParserException("Can't setup the parser: " + _decodeError(iNativeSetupKey));
    }

    public final ELicenseKeyStatus checkLicenseKey(Context context, String str, long j) {
        Objects.requireNonNull(context, "Parameter 'context' can't be null.");
        Objects.requireNonNull(str, "Parameter 'key' can't be null.");
        if (str.length() == 0) {
            return ELicenseKeyStatus.empty;
        }
        try {
            byte[] bArrDecode = Base64.decode(str, 0);
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(bArrDecode.length);
            byteBufferAllocateDirect.put(bArrDecode);
            int iNativeCheckLicenseKey = nativeCheckLicenseKey(context, this._handle, byteBufferAllocateDirect, bArrDecode.length, j);
            if (iNativeCheckLicenseKey == 0) {
                return ELicenseKeyStatus.valid;
            }
            switch (iNativeCheckLicenseKey) {
                case NATIVE_ERR_INVALID_LICENSE_KEY_EXPIRED /* -30 */:
                    return ELicenseKeyStatus.expired;
                case NATIVE_ERR_INVALID_LICENSE_KEY_PACKAGE_NAME /* -29 */:
                case NATIVE_ERR_INVALID_LICENSE_KEY /* -28 */:
                    return ELicenseKeyStatus.invalid;
                default:
                    return ELicenseKeyStatus.invalid;
            }
        } catch (IllegalArgumentException unused) {
            return ELicenseKeyStatus.invalid;
        }
    }

    public final int getMajorVersion() {
        return nativeGetMajorVersion(this._handle);
    }

    public final int getMinorVersion() {
        return nativeGetMinorVersion(this._handle);
    }

    public final int getBuildVersion() {
        return nativeGetBuildVersion(this._handle);
    }

    public final String getVersion() {
        return nativeGetVersion(this._handle);
    }

    static {
        System.loadLibrary("idscan_parser");
    }

    private String _decodeError(int i) {
        switch (i) {
            case NATIVE_ERR_INVALID_LICENSE_KEY_EXPIRED /* -30 */:
                return "License key is expired.";
            case NATIVE_ERR_INVALID_LICENSE_KEY_PACKAGE_NAME /* -29 */:
                return "License key is not valid for this package.";
            case NATIVE_ERR_INVALID_LICENSE_KEY /* -28 */:
                return "License key is not valid.";
            case -27:
                return "Invalid JNI call.";
            case -26:
            default:
                return "code " + i;
            case -25:
                return "DL is not parsed.";
        }
    }
}
