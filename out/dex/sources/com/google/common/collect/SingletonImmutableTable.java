package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@GwtCompatible
class SingletonImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
    final C singleColumnKey;
    final R singleRowKey;
    final V singleValue;

    SingletonImmutableTable(Table.Cell<R, C, V> cell) {
        this(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
    }

    SingletonImmutableTable(R r, C c, V v) {
        this.singleRowKey = (R) Preconditions.checkNotNull(r);
        this.singleColumnKey = (C) Preconditions.checkNotNull(c);
        this.singleValue = (V) Preconditions.checkNotNull(v);
    }

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.Table
    public ImmutableMap<R, V> column(C c) {
        Preconditions.checkNotNull(c);
        return containsColumn(c) ? ImmutableMap.m511of(this.singleRowKey, (Object) this.singleValue) : ImmutableMap.m510of();
    }

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.Table
    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.m511of(this.singleColumnKey, ImmutableMap.m511of(this.singleRowKey, (Object) this.singleValue));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.AbstractTable
    public ImmutableSet<Table.Cell<R, C, V>> createCellSet() {
        return ImmutableSet.m534of(cellOf(this.singleRowKey, this.singleColumnKey, this.singleValue));
    }

    @Override // com.google.common.collect.ImmutableTable
    ImmutableTable.SerializedForm createSerializedForm() {
        return ImmutableTable.SerializedForm.create(this, new int[]{0}, new int[]{0});
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.AbstractTable
    public ImmutableCollection<V> createValues() {
        return ImmutableSet.m534of(this.singleValue);
    }

    @Override // com.google.common.collect.ImmutableTable, com.google.common.collect.Table
    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.m511of(this.singleRowKey, ImmutableMap.m511of(this.singleColumnKey, (Object) this.singleValue));
    }

    @Override // com.google.common.collect.Table
    public int size() {
        return 1;
    }
}
