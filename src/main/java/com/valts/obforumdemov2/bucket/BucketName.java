package com.valts.obforumdemov2.bucket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    PROFILE_IMAGE("ob-forum-demo-image-upload");

    private final String bucketName;
}
