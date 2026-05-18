package com.magtek.mobile.android.mtcms;

import android.util.Log;
import androidx.core.view.InputDeviceCompat;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
class MTHIDReportParser {
    private static final int HID_COLLECTION_APPLICATION = 1;
    private static final int HID_COLLECTION_LOGICAL = 2;
    private static final int HID_COLLECTION_PHYSICAL = 0;
    private static final int HID_FEATURE_REPORT = 2;
    private static final int HID_INPUT_REPORT = 0;
    private static final int HID_LONG_ITEM_PREFIX = 254;
    static final int HID_MAX_FIELDS = 256;
    private static final int HID_MAX_USAGES = 12288;
    private static final int HID_OUTPUT_REPORT = 1;
    public static final boolean TRACE = true;
    private int m_DelimiterDepth;
    private byte[] m_Descriptor;
    private int m_DescriptorLength;
    private Global m_Global;
    private LinkedList<Global> m_GlobalStack;
    private Local m_Local;
    private int m_ParseIndex;
    private LinkedList<MTHIDReportInfo> m_Reports;
    private MTHIDCollectionInfo m_RootCollection;
    private MTHIDCollectionInfo m_TopCollection;

    enum GlobalTag {
        USAGE_PAGE,
        LOGICAL_MINIMUM,
        LOGICAL_MAXIMUM,
        PHYSICAL_MINIMUM,
        PHYSICAL_MAXIMUM,
        UNIT_EXPONENT,
        UNIT,
        REPORT_SIZE,
        REPORT_ID,
        REPORT_COUNT,
        PUSH,
        POP
    }

    enum ItemType {
        MAIN,
        GLOBAL,
        LOCAL,
        RESERVED,
        LONG
    }

    enum LocalTag {
        USAGE,
        USAGE_MINIMUM,
        USAGE_MAXIMUM,
        DESIGNATOR_INDEX,
        DESIGNATOR_MINIMUM,
        DESIGNATOR_MAXIMUM,
        STRING_INDEX,
        STRING_MINIMUM,
        STRING_MAXIMUM,
        DELIMITER
    }

    enum MainTag {
        PADDING_0,
        PADDING_1,
        PADDING_2,
        PADDING_3,
        PADDING_4,
        PADDING_5,
        PADDING_6,
        PADDING_7,
        INPUT,
        OUTPUT,
        COLLECTION,
        FEATURE,
        ENDCOLLECTION
    }

    MTHIDReportParser() {
    }

    private static final class Local {
        public int[] m_CollectionIndex;
        public int m_DelimiterBranch;
        public int m_DelimiterDepth;
        public int m_UsageIndex;
        public int m_UsageMinimum;
        public int[] m_Usages;

        private Local() {
            this.m_Usages = new int[MTHIDReportParser.HID_MAX_USAGES];
            this.m_CollectionIndex = new int[MTHIDReportParser.HID_MAX_USAGES];
        }

        /* synthetic */ Local(AnonymousClass1 anonymousClass1) {
            this();
        }

        public void reset() {
            this.m_UsageIndex = 0;
            this.m_UsageMinimum = 0;
            this.m_DelimiterDepth = 0;
            this.m_DelimiterBranch = 0;
            int i = 0;
            while (true) {
                int[] iArr = this.m_Usages;
                if (i >= iArr.length) {
                    break;
                }
                iArr[i] = 0;
                i++;
            }
            int i2 = 0;
            while (true) {
                int[] iArr2 = this.m_CollectionIndex;
                if (i2 >= iArr2.length) {
                    return;
                }
                iArr2[i2] = 0;
                i2++;
            }
        }
    }

    public static final class Global {
        int m_LogicalMaximum;
        int m_LogicalMinimum;
        int m_PhysicalMaximum;
        int m_PhysicalMinimum;
        int m_ReportCount;
        int m_ReportId;
        int m_ReportSize;
        int m_Unit;
        int m_UnitExponent;
        int m_UsagePage;

        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public static final class Item {
        GlobalTag m_GTag;
        LocalTag m_LTag;
        MainTag m_MTag;
        int m_SValue;
        int m_Size;
        ItemType m_Type;
        int m_UValue;

        public Item(int i, ItemType itemType, int i2, int i3) {
            this.m_Size = i;
            this.m_Type = itemType;
            if (itemType == ItemType.MAIN) {
                this.m_MTag = MainTag.values()[i2];
            }
            if (this.m_Type == ItemType.GLOBAL) {
                if (i2 < 0 || i2 >= GlobalTag.values().length) {
                    throw new IllegalStateException(String.format("illegal/unsupported global tag %d", Integer.valueOf(i2)));
                }
                this.m_GTag = GlobalTag.values()[i2];
            }
            if (this.m_Type == ItemType.LOCAL) {
                if (i2 < 0 || i2 >= LocalTag.values().length) {
                    throw new IllegalStateException(String.format("illegal/unsupported local tag %d", Integer.valueOf(i2)));
                }
                this.m_LTag = LocalTag.values()[i2];
            }
            this.m_UValue = i3;
            this.m_SValue = i3;
            if (i == 1) {
                if ((i3 & (-128)) != 0) {
                    this.m_SValue = i3 | InputDeviceCompat.SOURCE_ANY;
                }
            } else if (i == 2 && (i3 & (-32768)) != 0) {
                this.m_SValue = (-65536) | i3;
            }
        }
    }

    private MTHIDReportInfo registerReport(int i, int i2) {
        for (MTHIDReportInfo mTHIDReportInfo : this.m_Reports) {
            if (mTHIDReportInfo.m_Type == i && mTHIDReportInfo.m_Id == i2) {
                return mTHIDReportInfo;
            }
        }
        MTHIDReportInfo mTHIDReportInfo2 = new MTHIDReportInfo(i, i2, this.m_TopCollection);
        this.m_Reports.add(mTHIDReportInfo2);
        return mTHIDReportInfo2;
    }

    private MTHIDFieldInfo registerField(MTHIDReportInfo mTHIDReportInfo, int i) {
        if (mTHIDReportInfo.m_MaxField == 256) {
            throw new IllegalStateException("too many fields in report");
        }
        MTHIDFieldInfo mTHIDFieldInfo = new MTHIDFieldInfo(this.m_TopCollection);
        MTHIDFieldInfo[] mTHIDFieldInfoArr = mTHIDReportInfo.m_Fields;
        int i2 = mTHIDReportInfo.m_MaxField;
        mTHIDReportInfo.m_MaxField = i2 + 1;
        mTHIDFieldInfoArr[i2] = mTHIDFieldInfo;
        mTHIDFieldInfo.m_Report = mTHIDReportInfo;
        return mTHIDFieldInfo;
    }

    private int lookUpCollection(int i) {
        for (MTHIDCollectionInfo mTHIDCollectionInfo = this.m_TopCollection; mTHIDCollectionInfo.m_Parent != null; mTHIDCollectionInfo = mTHIDCollectionInfo.m_Parent) {
            if (mTHIDCollectionInfo.m_Type == i) {
                return mTHIDCollectionInfo.m_Usage;
            }
        }
        return 0;
    }

    private void addUsage(int i) {
        if (this.m_Local.m_UsageIndex >= this.m_Local.m_Usages.length) {
            throw new IllegalStateException("usage index exceeded");
        }
        int[] iArr = this.m_Local.m_Usages;
        Local local = this.m_Local;
        int i2 = local.m_UsageIndex;
        local.m_UsageIndex = i2 + 1;
        iArr[i2] = i;
    }

    private void addField(int i, int i2) {
        MTHIDReportInfo mTHIDReportInfoRegisterReport = registerReport(i, this.m_Global.m_ReportId);
        if (mTHIDReportInfoRegisterReport == null) {
            throw new IllegalStateException("failed to register report");
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.m_Global.m_ReportCount; i4++) {
            if (i4 < this.m_Local.m_UsageIndex) {
                i3 = i4;
            }
            int i5 = mTHIDReportInfoRegisterReport.m_Size;
            mTHIDReportInfoRegisterReport.m_Size += this.m_Global.m_ReportSize;
            MTHIDFieldInfo mTHIDFieldInfoRegisterField = registerField(mTHIDReportInfoRegisterReport, this.m_Global.m_ReportCount);
            if (mTHIDFieldInfoRegisterField == null) {
                throw new IllegalStateException("failed to register field");
            }
            mTHIDFieldInfoRegisterField.m_Physical = lookUpCollection(0);
            mTHIDFieldInfoRegisterField.m_Logical = lookUpCollection(2);
            mTHIDFieldInfoRegisterField.m_Application = lookUpCollection(1);
            mTHIDFieldInfoRegisterField.m_Usage = this.m_Local.m_Usages[i3];
            mTHIDFieldInfoRegisterField.m_Flags = i2;
            mTHIDFieldInfoRegisterField.m_ReportOffset = i5;
            mTHIDFieldInfoRegisterField.m_ReportType = i;
            mTHIDFieldInfoRegisterField.m_ReportSize = this.m_Global.m_ReportSize;
            mTHIDFieldInfoRegisterField.m_LogicalMinimum = this.m_Global.m_LogicalMinimum;
            mTHIDFieldInfoRegisterField.m_LogicalMaximum = this.m_Global.m_LogicalMaximum;
            mTHIDFieldInfoRegisterField.m_PhysicalMinimum = this.m_Global.m_PhysicalMinimum;
            mTHIDFieldInfoRegisterField.m_PhysicalMaximum = this.m_Global.m_PhysicalMaximum;
            mTHIDFieldInfoRegisterField.m_UnitExponent = this.m_Global.m_UnitExponent;
            mTHIDFieldInfoRegisterField.m_Unit = this.m_Global.m_Unit;
        }
    }

    private void parseGlobalItem(Item item) {
        switch (AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[item.m_GTag.ordinal()]) {
            case 1:
                this.m_GlobalStack.push((Global) this.m_Global.clone());
                return;
            case 2:
                if (this.m_GlobalStack.isEmpty()) {
                    throw new IllegalStateException("global enviroment stack underflow");
                }
                this.m_Global = this.m_GlobalStack.pop();
                return;
            case 3:
                this.m_Global.m_UsagePage = item.m_UValue;
                return;
            case 4:
                this.m_Global.m_LogicalMinimum = item.m_SValue;
                return;
            case 5:
                this.m_Global.m_LogicalMaximum = item.m_SValue;
                return;
            case 6:
                this.m_Global.m_PhysicalMinimum = item.m_SValue;
                return;
            case 7:
                this.m_Global.m_PhysicalMaximum = item.m_SValue;
                System.out.println("m_Global.m_PhysicalMaximum " + this.m_Global.m_PhysicalMaximum);
                return;
            case 8:
                this.m_Global.m_UnitExponent = item.m_SValue;
                return;
            case 9:
                this.m_Global.m_Unit = item.m_UValue;
                return;
            case 10:
                if (item.m_UValue < 0 || item.m_UValue > 32) {
                    throw new IllegalStateException(String.format("invalid report size %d", Integer.valueOf(item.m_UValue)));
                }
                this.m_Global.m_ReportSize = item.m_UValue;
                return;
            case 11:
                if (item.m_UValue < 0 || item.m_UValue > HID_MAX_USAGES) {
                    throw new IllegalStateException(String.format("invalid report count %d", Integer.valueOf(item.m_UValue)));
                }
                this.m_Global.m_ReportCount = item.m_UValue;
                return;
            case 12:
                if (item.m_UValue == 0) {
                    throw new IllegalStateException("report_id 0 is invalid");
                }
                this.m_Global.m_ReportId = item.m_UValue;
                return;
            default:
                throw new IllegalStateException("unsupported global tag");
        }
    }

    private void parseMainItem(Item item) {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$MainTag[item.m_MTag.ordinal()];
        if (i == 1) {
            this.m_TopCollection = new MTHIDCollectionInfo(this.m_TopCollection, this.m_Local.m_Usages[0], item.m_UValue & 3);
            return;
        }
        if (i == 2) {
            if (this.m_TopCollection.m_Parent == null) {
                throw new IllegalStateException("collection stack underflow");
            }
            this.m_TopCollection = this.m_TopCollection.m_Parent;
            this.m_Local.reset();
            return;
        }
        if (i == 3) {
            this.m_Local.reset();
            return;
        }
        if (i == 4) {
            this.m_Local.reset();
        } else {
            if (i != 5) {
                return;
            }
            addField(2, item.m_UValue);
            this.m_Local.reset();
        }
    }

    private void parseLocalItem(Item item) {
        if (item.m_Size == 0) {
            throw new IllegalStateException("item data expected for local item");
        }
        int i = item.m_UValue;
        if (item.m_Size <= 2) {
            i += this.m_Global.m_UsagePage << 16;
        }
        switch (AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[item.m_LTag.ordinal()]) {
            case 1:
                if (item.m_UValue > 0) {
                    if (this.m_Local.m_DelimiterDepth != 0) {
                        throw new IllegalStateException("nested delimiters");
                    }
                    this.m_Local.m_DelimiterDepth++;
                    this.m_Local.m_DelimiterBranch++;
                    return;
                }
                if (this.m_Local.m_DelimiterDepth < 1) {
                    throw new IllegalStateException("extra delimiters");
                }
                this.m_Local.m_DelimiterDepth--;
                return;
            case 2:
                if (this.m_Local.m_DelimiterBranch > 1) {
                    return;
                }
                addUsage(i);
                return;
            case 3:
                if (this.m_Local.m_DelimiterDepth > 1) {
                    this.m_Local.m_UsageMinimum = i;
                    return;
                }
                return;
            case 4:
                if (this.m_Local.m_DelimiterBranch > 1) {
                    for (int i2 = this.m_Local.m_UsageMinimum; i2 <= item.m_UValue; i2++) {
                        addUsage(i2);
                    }
                    return;
                }
                return;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return;
            default:
                throw new IllegalStateException("unsupported local tag");
        }
    }

    private Item getNextItem() {
        int i;
        int i2 = this.m_ParseIndex;
        int i3 = this.m_DescriptorLength;
        if (i2 >= i3) {
            return null;
        }
        byte[] bArr = this.m_Descriptor;
        int i4 = i2 + 1;
        this.m_ParseIndex = i4;
        int i5 = bArr[i2] & 255;
        if (i5 == HID_LONG_ITEM_PREFIX) {
            if (i4 >= i3) {
                throw new IllegalStateException("unexpected end of data white fetching long item size");
            }
            int i6 = i4 + 1;
            this.m_ParseIndex = i6;
            int i7 = bArr[i4] & 255;
            if (i6 >= i3) {
                throw new IllegalStateException("unexpected end of data white fetching long item tag");
            }
            int i8 = i6 + 1;
            this.m_ParseIndex = i8;
            int i9 = bArr[i6] & 255;
            if ((i8 + i7) - 1 >= i3) {
                throw new IllegalStateException("unexpected end of data white fetching long item");
            }
            this.m_ParseIndex = i8 + i7;
            return new Item(i7, ItemType.LONG, i9, 0);
        }
        int i10 = (i5 >> 2) & 3;
        int i11 = (i5 >> 4) & 15;
        int i12 = i5 & 3;
        if (i12 != 1) {
            if (i12 != 2) {
                if (i12 != 3) {
                    i = 0;
                } else {
                    i12++;
                    if (i4 + 1 >= i3) {
                        throw new IllegalStateException("unexpected end of data white fetching item size==1");
                    }
                    int i13 = i4 + 1;
                    this.m_ParseIndex = i13;
                    int i14 = bArr[i4] & 255;
                    int i15 = i13 + 1;
                    this.m_ParseIndex = i15;
                    int i16 = ((bArr[i13] & 255) << 8) | i14;
                    int i17 = i15 + 1;
                    this.m_ParseIndex = i17;
                    int i18 = i16 | ((bArr[i15] & 255) << 16);
                    this.m_ParseIndex = i17 + 1;
                    i = i18 | (bArr[i17] << 24);
                }
            } else {
                if (i4 + 1 >= i3) {
                    throw new IllegalStateException("unexpected end of data white fetching item size==1");
                }
                int i19 = i4 + 1;
                this.m_ParseIndex = i19;
                int i20 = bArr[i4] & 255;
                this.m_ParseIndex = i19 + 1;
                i = ((bArr[i19] & 255) << 8) | i20;
            }
        } else {
            if (i4 >= i3) {
                throw new IllegalStateException("unexpected end of data white fetching item size==1");
            }
            this.m_ParseIndex = i4 + 1;
            i = bArr[i4] & 255;
        }
        if (i10 < 0 || i10 >= ItemType.values().length) {
            throw new IllegalStateException(String.format("illegal/unsupported type %d", Integer.valueOf(i10)));
        }
        return new Item(i12, ItemType.values()[i10], i11, i);
    }

    private void resetParser() {
        MTHIDCollectionInfo mTHIDCollectionInfo = new MTHIDCollectionInfo(null, 0, 0);
        this.m_RootCollection = mTHIDCollectionInfo;
        this.m_TopCollection = mTHIDCollectionInfo;
        this.m_GlobalStack = new LinkedList<>();
        this.m_DelimiterDepth = 0;
        this.m_ParseIndex = 0;
        this.m_Descriptor = null;
        this.m_DescriptorLength = 0;
        this.m_Local = new Local(null);
        this.m_Global = new Global();
        this.m_Reports = new LinkedList<>();
    }

    public void parse(byte[] bArr, int i) {
        resetParser();
        this.m_Descriptor = bArr;
        this.m_DescriptorLength = i;
        while (true) {
            Item nextItem = getNextItem();
            if (nextItem != null) {
                int i2 = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$ItemType[nextItem.m_Type.ordinal()];
                if (i2 == 1) {
                    parseMainItem(nextItem);
                } else if (i2 == 2) {
                    parseLocalItem(nextItem);
                } else {
                    if (i2 != 3) {
                        if (i2 == 4) {
                            throw new IllegalStateException("unexpected long global item");
                        }
                        throw new IllegalStateException("unknown global item type (bug)");
                    }
                    parseGlobalItem(nextItem);
                }
            } else {
                if (this.m_TopCollection.m_Parent != null) {
                    throw new IllegalStateException("unbalanced collection at end of report description");
                }
                if (this.m_DelimiterDepth > 0) {
                    throw new IllegalStateException("unbalanced delimiter at end of report description");
                }
                return;
            }
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtcms.MTHIDReportParser$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$ItemType;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$MainTag;

        static {
            int[] iArr = new int[ItemType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$ItemType = iArr;
            try {
                iArr[ItemType.MAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$ItemType[ItemType.LOCAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$ItemType[ItemType.GLOBAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$ItemType[ItemType.LONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[LocalTag.values().length];
            $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag = iArr2;
            try {
                iArr2[LocalTag.DELIMITER.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.USAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.USAGE_MINIMUM.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.USAGE_MAXIMUM.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.DESIGNATOR_INDEX.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.DESIGNATOR_MAXIMUM.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.DESIGNATOR_MINIMUM.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.STRING_INDEX.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.STRING_MAXIMUM.ordinal()] = 9;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$LocalTag[LocalTag.STRING_MINIMUM.ordinal()] = 10;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr3 = new int[MainTag.values().length];
            $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$MainTag = iArr3;
            try {
                iArr3[MainTag.COLLECTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$MainTag[MainTag.ENDCOLLECTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$MainTag[MainTag.INPUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$MainTag[MainTag.OUTPUT.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$MainTag[MainTag.FEATURE.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
            int[] iArr4 = new int[GlobalTag.values().length];
            $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag = iArr4;
            try {
                iArr4[GlobalTag.PUSH.ordinal()] = 1;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.POP.ordinal()] = 2;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.USAGE_PAGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.LOGICAL_MINIMUM.ordinal()] = 4;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.LOGICAL_MAXIMUM.ordinal()] = 5;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.PHYSICAL_MINIMUM.ordinal()] = 6;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.PHYSICAL_MAXIMUM.ordinal()] = 7;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.UNIT_EXPONENT.ordinal()] = 8;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.UNIT.ordinal()] = 9;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.REPORT_SIZE.ordinal()] = 10;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.REPORT_COUNT.ordinal()] = 11;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTHIDReportParser$GlobalTag[GlobalTag.REPORT_ID.ordinal()] = 12;
            } catch (NoSuchFieldError unused31) {
            }
        }
    }

    public static int getFeatureReportCount(byte[] bArr, int i) {
        try {
            MTHIDReportParser mTHIDReportParser = new MTHIDReportParser();
            mTHIDReportParser.parse(bArr, bArr.length);
            LinkedList<MTHIDReportInfo> linkedList = mTHIDReportParser.m_Reports;
            if (linkedList == null) {
                return -1;
            }
            for (MTHIDReportInfo mTHIDReportInfo : linkedList) {
                Log.i("HIDReportInfo", "ReportType=" + mTHIDReportInfo.m_Type);
                Log.i("HIDReportInfo", "ReportMaxField=" + mTHIDReportInfo.m_MaxField);
                Log.i("HIDReportInfo", "ReportSize=" + mTHIDReportInfo.m_Size);
                if (mTHIDReportInfo.m_Type == 2) {
                    return mTHIDReportInfo.m_MaxField;
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
