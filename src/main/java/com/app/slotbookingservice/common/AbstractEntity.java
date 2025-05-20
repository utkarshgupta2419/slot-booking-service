package com.app.slotbookingservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    @Field("_id")
    private ObjectId id;

    @Field("sys_create_time")
    @CreatedDate
    private LocalDateTime sysCreateTime;

    @Field("sys_update_time")
    @LastModifiedDate
    private LocalDateTime sysUpdateTime;

}
