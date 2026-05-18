package org.kobjects.pim;

/* JADX INFO: loaded from: classes2.dex */
public class VCard extends PimItem {
    @Override // org.kobjects.pim.PimItem
    public String getType() {
        return "vcard";
    }

    public VCard() {
    }

    public VCard(VCard vCard) {
        super(vCard);
    }

    @Override // org.kobjects.pim.PimItem
    public int getArraySize(String str) {
        if (str.equals("n")) {
            return 5;
        }
        return str.equals("adr") ? 6 : -1;
    }
}
