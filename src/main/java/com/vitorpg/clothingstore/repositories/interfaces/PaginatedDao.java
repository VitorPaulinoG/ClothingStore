package com.vitorpg.clothingstore.repositories.interfaces;

import java.util.List;

public interface PaginatedDao<T> {
    List<T> findPaginated(Long maxCount, Long offset);
}
