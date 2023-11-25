package com.spotifyinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyPaging<T> {
    private String href;
    private T[] items;
    private Integer limit;
    private String next;
    private Integer offset;
    private String previous;
    private Integer total;
}
