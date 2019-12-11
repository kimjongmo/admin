package com.example.study.ifs;

import com.example.study.model.network.Header;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudInterface<Req, Res> {
    public Header<Res> create(Header<Req> request);
    public Header<Res> read(Long id);
    public Header<Res> update(Header<Req> request);
    public Header delete(Long id);
    public Header<List<Res>> search(Pageable pageable);
}
