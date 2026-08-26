package com.nintendo.npf.sdk.user;

import android.graphics.Color;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Mii {
    private byte[] coreData;
    private String etag;
    private MiiColor favoriteColor;
    private String imageOrigin;
    private String miiId;
    private byte[] storeData;
    private String urlTemplate;
    private Format format = Format.PNG;
    private Type type = Type.FACE_96;
    private Expression expression = Expression.NORMAL;
    private int bgColor = Color.argb(0, 255, 255, 255);
    private MiiColor clothesColor = MiiColor.DEFAULT;
    private int cameraXRotate = 0;
    private int cameraYRotate = 0;
    private int cameraZRotate = 0;
    private int characterXRotate = 0;
    private int characterYRotate = 0;
    private int characterZRotate = 0;

    public enum Expression {
        NORMAL(0),
        SMILE(1),
        ANGER(2),
        SORROW(3),
        SURPRISE(4),
        BLINK(5),
        NORMAL_OPEN_MOUTH(6),
        SMILE_OPEN_MOUTH(7),
        ANGER_OPEN_MOUTH(8),
        SURPRISE_OPEN_MOUTH(9),
        SORROW_OPEN_MOUTH(10),
        BLINK_OPEN_MOUTH(11),
        WINK_LEFT(12),
        WINK_RIGHT(13),
        WINK_LEFT_OPEN_MOUTH(14),
        WINK_RIGHT_OPEN_MOUTH(15),
        LIKE_WINK_LEFT(16),
        LIKE_WINK_RIGHT(17),
        FRUSTRATED(18);


        /* JADX INFO: renamed from: a */
        private final int f1805a;

        Expression(int i) {
            this.f1805a = i;
        }

        public int getInt() {
            return this.f1805a;
        }
    }

    public enum Format {
        PNG(0),
        TGA(1);


        /* JADX INFO: renamed from: a */
        private final int f1807a;

        Format(int i) {
            this.f1807a = i;
        }

        public int getInt() {
            return this.f1807a;
        }
    }

    public enum MiiColor {
        DEFAULT(0),
        RED(1),
        ORANGE(2),
        YELLOW(3),
        YELLOWGREEN(4),
        GREEN(5),
        BLUE(6),
        SKYBLUE(7),
        PINK(8),
        PURPLE(9),
        BROWN(10),
        WHITE(11),
        BLACK(12);


        /* JADX INFO: renamed from: a */
        private final int f1809a;

        MiiColor(int i) {
            this.f1809a = i;
        }

        public int getInt() {
            return this.f1809a;
        }
    }

    public enum Type {
        FACE_96(0),
        FACE_128(1),
        FACE_270(2),
        FACE_512(3),
        FACE_ONLY_96(4),
        FACE_ONLY_128(5),
        FACE_ONLY_270(6),
        FACE_ONLY_512(7),
        ALL_BODY_96(8),
        ALL_BODY_270(9);


        /* JADX INFO: renamed from: a */
        private final int f1811a;

        Type(int i) {
            this.f1811a = i;
        }

        public int getInt() {
            return this.f1811a;
        }
    }

    protected Mii(String str, String str2, byte[] bArr, byte[] bArr2, String str3, String str4, String str5) {
        this.urlTemplate = str;
        this.miiId = str2;
        this.coreData = bArr;
        this.storeData = bArr2;
        this.imageOrigin = str3;
        this.etag = str4;
        if (str5 == null) {
            this.favoriteColor = MiiColor.DEFAULT;
        } else {
            this.favoriteColor = MiiColor.valueOf(str5.toUpperCase());
        }
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public int getCameraXRotate() {
        return this.cameraXRotate;
    }

    public int getCameraYRotate() {
        return this.cameraYRotate;
    }

    public int getCameraZRotate() {
        return this.cameraZRotate;
    }

    public int getCharacterXRotate() {
        return this.characterXRotate;
    }

    public int getCharacterYRotate() {
        return this.characterYRotate;
    }

    public int getCharacterZRotate() {
        return this.characterZRotate;
    }

    public MiiColor getClothesColor() {
        return this.clothesColor;
    }

    public byte[] getCoreData() {
        return this.coreData;
    }

    public String getEtag() {
        return this.etag;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public MiiColor getFavoriteColor() {
        return this.favoriteColor;
    }

    public Format getFormat() {
        return this.format;
    }

    public String getImageOrigin() {
        return this.imageOrigin;
    }

    public String getImageUrl() {
        if (this.imageOrigin == null || this.etag == null) {
            return null;
        }
        String str = this.urlTemplate;
        String str2 = str.substring(0, str.indexOf("?")).replaceAll("\\{", "").replace("imageOrigin}", this.imageOrigin).replace("id}", this.miiId).replace("etag}", this.etag).replace("format}", this.format.toString().toLowerCase()) + "?expression=" + this.expression.toString().toLowerCase();
        switch (this.type) {
            case FACE_96:
            case FACE_128:
            case FACE_270:
            case FACE_512:
                str2 = str2 + "&type=face";
                break;
            case FACE_ONLY_96:
            case FACE_ONLY_128:
            case FACE_ONLY_270:
            case FACE_ONLY_512:
                str2 = str2 + "&type=face_only";
                break;
            case ALL_BODY_96:
            case ALL_BODY_270:
                str2 = str2 + "&type=all_body";
                break;
        }
        switch (this.type) {
            case FACE_96:
            case FACE_ONLY_96:
            case ALL_BODY_96:
                str2 = str2 + "&width=96";
                break;
            case FACE_128:
            case FACE_ONLY_128:
                str2 = str2 + "&width=128";
                break;
            case FACE_270:
            case FACE_ONLY_270:
            case ALL_BODY_270:
                str2 = str2 + "&width=270";
                break;
            case FACE_512:
            case FACE_ONLY_512:
                str2 = str2 + "&width=512";
                break;
        }
        String str3 = String.format("%08X", Integer.valueOf(this.bgColor));
        return (((((((str2 + "&bgColor=" + (str3.substring(2) + str3.substring(0, 2))) + "&clothesColor=" + this.clothesColor.toString().toLowerCase()) + "&cameraXRotate=" + this.cameraXRotate) + "&cameraYRotate=" + this.cameraYRotate) + "&cameraZRotate=" + this.cameraZRotate) + "&characterXRotate=" + this.characterXRotate) + "&characterYRotate=" + this.characterYRotate) + "&characterZRotate=" + this.characterZRotate;
    }

    public String getMiiId() {
        return this.miiId;
    }

    public byte[] getStoreData() {
        return this.storeData;
    }

    public Type getType() {
        return this.type;
    }

    public String getUrlTemplate() {
        return this.urlTemplate;
    }

    public void setBgColor(int i) {
        this.bgColor = i;
    }

    public void setCameraXRotate(int i) {
        this.cameraXRotate = i;
    }

    public void setCameraYRotate(int i) {
        this.cameraYRotate = i;
    }

    public void setCameraZRotate(int i) {
        this.cameraZRotate = i;
    }

    public void setCharacterXRotate(int i) {
        this.characterXRotate = i;
    }

    public void setCharacterYRotate(int i) {
        this.characterYRotate = i;
    }

    public void setCharacterZRotate(int i) {
        this.characterZRotate = i;
    }

    public void setClothesColor(MiiColor miiColor) {
        this.clothesColor = miiColor;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
