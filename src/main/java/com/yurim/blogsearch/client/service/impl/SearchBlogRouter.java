package com.yurim.blogsearch.client.service.impl;

import com.yurim.blogsearch.client.ClientType;
import com.yurim.blogsearch.client.service.SearchClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SearchBlogRouter {

    @Autowired
    Map<String, SearchClientService> searchClientServiceMap;

    public SearchClientService getServiceByType(ClientType type) {
        return searchClientServiceMap.get(type.getSearchClientService());
    }

}
