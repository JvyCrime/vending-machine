package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzee extends zzf {
    private final zzed zza;
    private boolean zzb;

    zzee(zzfv zzfvVar) {
        super(zzfvVar);
        Context contextZzau = this.zzs.zzau();
        this.zzs.zzf();
        this.zza = new zzed(this, contextZzau, "google_app_measurement_local.db");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0117 A[PHI: r8
  0x0117: PHI (r8v3 android.database.sqlite.SQLiteDatabase) = (r8v2 android.database.sqlite.SQLiteDatabase), (r8v4 android.database.sqlite.SQLiteDatabase) binds: [B:55:0x00e7, B:70:0x0115] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x012a  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r10v4, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v5, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r8v6, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r8v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean zzq(int r17, byte[] r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 319
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzee.zzq(int, byte[]):boolean");
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    protected final boolean zzf() {
        return false;
    }

    final SQLiteDatabase zzh() throws SQLiteException {
        if (this.zzb) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zza.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzb = true;
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:148:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0236 A[PHI: r9 r15
  0x0236: PHI (r9v3 int) = (r9v1 int), (r9v1 int), (r9v4 int) binds: [B:149:0x0224, B:164:0x0251, B:157:0x0234] A[DONT_GENERATE, DONT_INLINE]
  0x0236: PHI (r15v7 android.database.sqlite.SQLiteDatabase) = 
  (r15v5 android.database.sqlite.SQLiteDatabase)
  (r15v6 android.database.sqlite.SQLiteDatabase)
  (r15v8 android.database.sqlite.SQLiteDatabase)
 binds: [B:149:0x0224, B:164:0x0251, B:157:0x0234] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0207 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x01e2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0254 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0254 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0254 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable> zzi(int r23) {
        /*
            Method dump skipped, instruction units count: 630
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzee.zzi(int):java.util.List");
    }

    public final void zzj() {
        int iDelete;
        zzg();
        try {
            SQLiteDatabase sQLiteDatabaseZzh = zzh();
            if (sQLiteDatabaseZzh == null || (iDelete = sQLiteDatabaseZzh.delete("messages", null, null)) <= 0) {
                return;
            }
            this.zzs.zzay().zzj().zzb("Reset local analytics data. records", Integer.valueOf(iDelete));
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzb("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zzk() {
        return zzq(3, new byte[0]);
    }

    final boolean zzl() {
        Context contextZzau = this.zzs.zzau();
        this.zzs.zzf();
        return contextZzau.getDatabasePath("google_app_measurement_local.db").exists();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006a A[PHI: r4
  0x006a: PHI (r4v3 int) = (r4v1 int), (r4v1 int), (r4v4 int) binds: [B:29:0x0061, B:35:0x007d, B:32:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzm() {
        /*
            r10 = this;
            java.lang.String r0 = "Error deleting app launch break from local database"
            r10.zzg()
            boolean r1 = r10.zzb
            r2 = 0
            if (r1 == 0) goto Lb
            return r2
        Lb:
            boolean r1 = r10.zzl()
            if (r1 == 0) goto L98
            r1 = 5
            r3 = 0
            r4 = 5
        L14:
            if (r3 >= r1) goto L89
            r5 = 0
            r6 = 1
            android.database.sqlite.SQLiteDatabase r5 = r10.zzh()     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            if (r5 != 0) goto L21
            r10.zzb = r6     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            return r2
        L21:
            r5.beginTransaction()     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            java.lang.String[] r7 = new java.lang.String[r6]     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            r8 = 3
            java.lang.String r8 = java.lang.Integer.toString(r8)     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            r7[r2] = r8     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            java.lang.String r8 = "messages"
            java.lang.String r9 = "type == ?"
            r5.delete(r8, r9, r7)     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            r5.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            r5.endTransaction()     // Catch: java.lang.Throwable -> L3f android.database.sqlite.SQLiteException -> L41 android.database.sqlite.SQLiteFullException -> L43 android.database.sqlite.SQLiteDatabaseLockedException -> L62
            r5.close()
            return r6
        L3f:
            r0 = move-exception
            goto L83
        L41:
            r7 = move-exception
            goto L45
        L43:
            r7 = move-exception
            goto L6e
        L45:
            if (r5 == 0) goto L50
            boolean r8 = r5.inTransaction()     // Catch: java.lang.Throwable -> L3f
            if (r8 == 0) goto L50
            r5.endTransaction()     // Catch: java.lang.Throwable -> L3f
        L50:
            com.google.android.gms.measurement.internal.zzfv r8 = r10.zzs     // Catch: java.lang.Throwable -> L3f
            com.google.android.gms.measurement.internal.zzel r8 = r8.zzay()     // Catch: java.lang.Throwable -> L3f
            com.google.android.gms.measurement.internal.zzej r8 = r8.zzd()     // Catch: java.lang.Throwable -> L3f
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L3f
            r10.zzb = r6     // Catch: java.lang.Throwable -> L3f
            if (r5 == 0) goto L80
            goto L6a
        L62:
            long r6 = (long) r4     // Catch: java.lang.Throwable -> L3f
            android.os.SystemClock.sleep(r6)     // Catch: java.lang.Throwable -> L3f
            int r4 = r4 + 20
            if (r5 == 0) goto L80
        L6a:
            r5.close()
            goto L80
        L6e:
            com.google.android.gms.measurement.internal.zzfv r8 = r10.zzs     // Catch: java.lang.Throwable -> L3f
            com.google.android.gms.measurement.internal.zzel r8 = r8.zzay()     // Catch: java.lang.Throwable -> L3f
            com.google.android.gms.measurement.internal.zzej r8 = r8.zzd()     // Catch: java.lang.Throwable -> L3f
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L3f
            r10.zzb = r6     // Catch: java.lang.Throwable -> L3f
            if (r5 == 0) goto L80
            goto L6a
        L80:
            int r3 = r3 + 1
            goto L14
        L83:
            if (r5 == 0) goto L88
            r5.close()
        L88:
            throw r0
        L89:
            com.google.android.gms.measurement.internal.zzfv r0 = r10.zzs
            com.google.android.gms.measurement.internal.zzel r0 = r0.zzay()
            com.google.android.gms.measurement.internal.zzej r0 = r0.zzk()
            java.lang.String r1 = "Error deleting app launch break from local database in reasonable time"
            r0.zza(r1)
        L98:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzee.zzm():boolean");
    }

    public final boolean zzn(zzab zzabVar) {
        byte[] bArrZzan = this.zzs.zzv().zzan(zzabVar);
        if (bArrZzan.length <= 131072) {
            return zzq(2, bArrZzan);
        }
        this.zzs.zzay().zzh().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzo(zzat zzatVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzau.zza(zzatVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzq(0, bArrMarshall);
        }
        this.zzs.zzay().zzh().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zzp(zzkv zzkvVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzkw.zza(zzkvVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzq(1, bArrMarshall);
        }
        this.zzs.zzay().zzh().zza("User property too long for local database. Sending directly to service");
        return false;
    }
}
