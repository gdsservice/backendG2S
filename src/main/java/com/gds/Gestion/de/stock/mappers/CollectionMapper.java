package com.gds.Gestion.de.stock.mappers;

import com.gds.Gestion.de.stock.Input.CollectionINPUT;
import com.gds.Gestion.de.stock.entites.Collections;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CollectionMapper {

    public Collections collectionINPUTACollection(CollectionINPUT collectionINPUT){
        Collections collection = new Collections();
        BeanUtils.copyProperties(collectionINPUT, collection);
        return collection;
    }
}
