package org.social.core.entity.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.social.core.util.UtilDateTime;

@Entity
@Table(name = "LEARNING_DATA")
public class LearningData {
    private long learningId;
    private String classificationTypeId;
    private String learningData;
    private String classificationId;
    private Timestamp createdTs;
    private Timestamp lastUpdatedTs;

    public LearningData() {
        createdTs = UtilDateTime.nowTimestamp();
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "LEARNING_ID")
    public long getLearningId() {
        return learningId;
    }

    public void setLearningId(long learningId) {
        this.learningId = learningId;
    }

    @Column(name = "CLASSIFICATION_TYPE_ID")
    public String getClassificationTypeId() {
        return classificationTypeId;
    }

    public void setClassificationTypeId(String learningTypeId) {
        classificationTypeId = learningTypeId;
    }

    @Column(name = "LEARNING_DATA")
    public String getLearningData() {
        return learningData;
    }

    public void setLearningData(String learningData) {
        this.learningData = learningData;
    }

    @Column(name = "CLASSIFICATION_ID")
    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    @Column(name = "CREATED_TS")
    public Timestamp getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Timestamp createdTs) {
        this.createdTs = createdTs;
    }

    @Column(name = "LAST_UPDATED_TS")
    public Timestamp getLastUpdatedTs() {
        return lastUpdatedTs;
    }

    public void setLastUpdatedTs(Timestamp lastUpdatedTs) {
        this.lastUpdatedTs = lastUpdatedTs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        builder.append("learningId=");
        builder.append(learningId);
        builder.append(", learningTypeId=");
        builder.append(classificationTypeId);
        builder.append(", learningData=");
        builder.append(learningData);
        builder.append(", classificationId=");
        builder.append(classificationId);
        builder.append(", createdTs=");
        builder.append(createdTs);
        builder.append(", lastUpdatedTs=");
        builder.append(lastUpdatedTs);
        builder.append("]");

        return builder.toString();
    }

}
