package com.template.app.mongoEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 用户对象
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
@Data
@Document(collection = "adminUser")
@ApiModel(value = "User object", description = "User info")
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "unique id")
    @Field("_id")
    @Id
    private ObjectId id;

    @ApiModelProperty(value = "email")
    @Field("email")
    private String email;

    @ApiModelProperty(value = "match maker id")
    @Field("matchmakerId")
    private ObjectId matchmakerId;

    @ApiModelProperty(value = "block users")
    @Field("blockUsers")
    private List<ObjectId> blockUsers;

    /**
     * Personal information
     */
    @ApiModelProperty(value = "first name")
    @Field("firstName")
    private String firstName;

    @ApiModelProperty(value = "last name")
    @Field("lastName")
    private String lastName;

    @ApiModelProperty(value = "birth year")
    @Field("birthYear")
    private Integer birthYear;

    @ApiModelProperty(value = "my answers")
    @Field("myAnswers")
    private List<MyAnswer> myAnswers;

    @Data
    @ApiModel(value = "MyAnswer object", description = "MyAnswer info")
    public static class MyAnswer {

        @ApiModelProperty(value = "question id")
        @Field("questionId")
        private ObjectId questionId;

        @ApiModelProperty(value = "answer")
        @Field("answer")
        private String answer;

    }

    @ApiModelProperty(value = "created date")
    @Field("createdDate")
    private Date createdDate;

    @ApiModelProperty(value = "update date")
    @Field("updateDate")
    private Date updateDate;

    @ApiModelProperty(value = "active date")
    @Field("activeDate")
    private Date activeDate;

}
