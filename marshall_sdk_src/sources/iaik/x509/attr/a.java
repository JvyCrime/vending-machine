package iaik.x509.attr;

import iaik.asn1.structures.GeneralName;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
final class a implements TargetChecker {
    a() {
    }

    private static String a(String str) {
        int iIndexOf = str.indexOf("//");
        if (iIndexOf != -1) {
            str = str.substring(iIndexOf + 2);
        }
        int iIndexOf2 = str.indexOf(58);
        int iIndexOf3 = str.indexOf(47);
        return (iIndexOf2 > 0 || iIndexOf3 > 0) ? iIndexOf2 > 0 ? str.substring(0, iIndexOf2) : iIndexOf3 != -1 ? str.substring(0, iIndexOf3) : str : str;
    }

    @Override // iaik.x509.attr.TargetChecker
    public boolean isTargetFor(Target target, Object obj) throws TargetException {
        boolean zEquals;
        String str;
        Objects.requireNonNull(target, "Target must not be null");
        Objects.requireNonNull(obj, "Server must not be null.");
        int type = target.getType();
        if (type < 0 || type > 2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unkown Target type ");
            stringBuffer.append(target.getType());
            stringBuffer.append(" (");
            stringBuffer.append(target.getTypeAsString());
            stringBuffer.append(").");
            throw new TargetException(stringBuffer.toString());
        }
        if (obj instanceof Target) {
            zEquals = target.equals(obj);
        } else {
            if (type == 2) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Cannot handle server object ");
                stringBuffer2.append(obj.getClass());
                throw new TargetException(stringBuffer2.toString());
            }
            if (!(obj instanceof GeneralName)) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Cannot handle server object ");
                stringBuffer3.append(obj.getClass());
                throw new TargetException(stringBuffer3.toString());
            }
            zEquals = ((GeneralName) obj).equals(type == 0 ? ((TargetName) target).getName() : ((TargetGroup) target).getGroup());
        }
        if (zEquals || type != 1) {
            return zEquals;
        }
        GeneralName group = ((TargetGroup) target).getGroup();
        if (group.getType() != 2 || (str = (String) group.getName()) == null) {
            return zEquals;
        }
        String strA = null;
        GeneralName group2 = obj instanceof TargetGroup ? ((TargetGroup) obj).getGroup() : obj instanceof GeneralName ? (GeneralName) obj : null;
        if (group2 == null) {
            return zEquals;
        }
        int type2 = group2.getType();
        if (type2 == 1 || type2 == 2) {
            strA = (String) group2.getName();
        } else if (type2 == 6) {
            strA = a((String) group2.getName());
        }
        return strA != null ? strA.toLowerCase().endsWith(str.toLowerCase()) : zEquals;
    }
}
