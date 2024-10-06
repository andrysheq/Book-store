package com.example.library.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindCollectionResponse<T> {

    @Builder.Default
    private List<T> data = new ArrayList<>();
}
