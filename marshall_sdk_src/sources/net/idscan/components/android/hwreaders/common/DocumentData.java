package net.idscan.components.android.hwreaders.common;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DocumentData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lnet/idscan/components/android/hwreaders/common/DocumentData;", "", "data", "", "types", "", "Lnet/idscan/components/android/hwreaders/common/DocumentType;", "([BLjava/util/Set;)V", "getData", "()[B", "getTypes", "()Ljava/util/Set;", "component-common_release"}, k = 1, mv = {1, 4, 2})
public final class DocumentData {
    private final byte[] data;
    private final Set<DocumentType> types;

    /* JADX WARN: Multi-variable type inference failed */
    public DocumentData(byte[] data, Set<? extends DocumentType> types) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(types, "types");
        this.data = data;
        this.types = types;
    }

    public final byte[] getData() {
        return this.data;
    }

    public final Set<DocumentType> getTypes() {
        return this.types;
    }
}
