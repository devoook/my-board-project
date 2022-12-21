package com.myproject.boardproject.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);  // Math.max를 활용하여 음수 값이 나오는 것을 방지
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages) ;

        return IntStream.range(startNumber,endNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
