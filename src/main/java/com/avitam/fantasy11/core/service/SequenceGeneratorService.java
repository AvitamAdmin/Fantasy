package com.avitam.fantasy11.core.service;

import com.avitam.fantasy11.model.BaseEntity;
import com.jayway.jsonpath.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.jayway.jsonpath.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private static MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public int generateSequence(String seqName) {

        BaseEntity counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                BaseEntity.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }

    private Query query(Criteria id) {
        return null;
    }
}
