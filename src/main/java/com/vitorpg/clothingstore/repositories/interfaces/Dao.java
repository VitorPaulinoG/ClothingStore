package com.vitorpg.clothingstore.repositories.interfaces;

public interface Dao<T> extends FinderDao<T>, SimpleSaverDao<T>, UpdaterDao<T>, RemovableDao {
}
