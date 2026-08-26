package kotlin.p025io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: FileTreeWalk.kt */
/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Metadata(m1982bv = {1, 0, 3}, m1983d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0007"}, m1984d2 = {"walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib"}, m1985k = 5, m1986mv = {1, 1, 15}, m1988xi = 1, m1989xs = "kotlin/io/FilesKt")
class FilesKt__FileTreeWalkKt extends FilesKt__FileReadWriteKt {
    @NotNull
    public static final FileTreeWalk walk(@NotNull File walk, @NotNull FileWalkDirection direction) {
        Intrinsics.checkParameterIsNotNull(walk, "$this$walk");
        Intrinsics.checkParameterIsNotNull(direction, "direction");
        return new FileTreeWalk(walk, direction);
    }

    public static /* synthetic */ FileTreeWalk walk$default(File file, FileWalkDirection fileWalkDirection, int i, Object obj) {
        if ((i & 1) != 0) {
            fileWalkDirection = FileWalkDirection.TOP_DOWN;
        }
        return FilesKt.walk(file, fileWalkDirection);
    }

    @NotNull
    public static final FileTreeWalk walkBottomUp(@NotNull File walkBottomUp) {
        Intrinsics.checkParameterIsNotNull(walkBottomUp, "$this$walkBottomUp");
        return FilesKt.walk(walkBottomUp, FileWalkDirection.BOTTOM_UP);
    }

    @NotNull
    public static final FileTreeWalk walkTopDown(@NotNull File walkTopDown) {
        Intrinsics.checkParameterIsNotNull(walkTopDown, "$this$walkTopDown");
        return FilesKt.walk(walkTopDown, FileWalkDirection.TOP_DOWN);
    }
}
