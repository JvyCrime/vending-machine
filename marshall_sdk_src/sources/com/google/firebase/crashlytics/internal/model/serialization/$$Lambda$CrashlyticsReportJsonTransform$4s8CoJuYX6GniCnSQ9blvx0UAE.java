package com.google.firebase.crashlytics.internal.model.serialization;

import android.util.JsonReader;
import com.google.firebase.crashlytics.internal.model.serialization.CrashlyticsReportJsonTransform;

/* JADX INFO: renamed from: com.google.firebase.crashlytics.internal.model.serialization.-$$Lambda$CrashlyticsReportJsonTransform$4s8CoJuYX6GniCnSQ9blv-x0UAE, reason: invalid class name */
/* JADX INFO: compiled from: lambda */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$CrashlyticsReportJsonTransform$4s8CoJuYX6GniCnSQ9blvx0UAE implements CrashlyticsReportJsonTransform.ObjectParser {
    public static final /* synthetic */ $$Lambda$CrashlyticsReportJsonTransform$4s8CoJuYX6GniCnSQ9blvx0UAE INSTANCE = new $$Lambda$CrashlyticsReportJsonTransform$4s8CoJuYX6GniCnSQ9blvx0UAE();

    private /* synthetic */ $$Lambda$CrashlyticsReportJsonTransform$4s8CoJuYX6GniCnSQ9blvx0UAE() {
    }

    @Override // com.google.firebase.crashlytics.internal.model.serialization.CrashlyticsReportJsonTransform.ObjectParser
    public final Object parse(JsonReader jsonReader) {
        return CrashlyticsReportJsonTransform.parseEventFrame(jsonReader);
    }
}
