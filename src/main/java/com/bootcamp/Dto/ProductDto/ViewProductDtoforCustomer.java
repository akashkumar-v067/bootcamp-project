package com.bootcamp.Dto.ProductDto;

import java.util.List;

public class ViewProductDtoforCustomer extends ViewProductDto{
    List<String > links;

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
