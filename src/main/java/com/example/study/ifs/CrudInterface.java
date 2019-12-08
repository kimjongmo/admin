package com.example.study.ifs;

import com.example.study.model.network.Header;

public interface CrudInterface<Req, Res> {
    public Header<Res> create(Header<Req> request);
    public Header<Res> read(Long id);
    public Header<Res> update(Header<Req> request);
    public Header delete(Long id);
}
