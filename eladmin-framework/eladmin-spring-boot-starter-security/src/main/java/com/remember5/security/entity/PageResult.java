package com.remember5.security.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PageResult<T> {

    private final List<T> content;

    private final long totalElements;
}
