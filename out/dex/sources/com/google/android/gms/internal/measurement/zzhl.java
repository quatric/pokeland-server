package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
final class zzhl {
    static String zzd(zzdp zzdpVar) {
        zzho zzhoVar = new zzho(zzdpVar);
        StringBuilder sb = new StringBuilder(zzhoVar.size());
        for (int i = 0; i < zzhoVar.size(); i++) {
            byte bZzaq = zzhoVar.zzaq(i);
            if (bZzaq == 34) {
                sb.append("\\\"");
            } else if (bZzaq == 39) {
                sb.append("\\'");
            } else if (bZzaq != 92) {
                switch (bZzaq) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (bZzaq < 32 || bZzaq > 126) {
                            sb.append('\\');
                            sb.append((char) (((bZzaq >>> 6) & 3) + 48));
                            sb.append((char) (((bZzaq >>> 3) & 7) + 48));
                            sb.append((char) ((bZzaq & 7) + 48));
                        } else {
                            sb.append((char) bZzaq);
                        }
                        break;
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
