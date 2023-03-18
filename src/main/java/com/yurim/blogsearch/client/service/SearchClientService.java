package com.yurim.blogsearch.client.service;

import java.util.List;

public interface SearchClientService<I,O> {

    O search(I searchRequest);
}
