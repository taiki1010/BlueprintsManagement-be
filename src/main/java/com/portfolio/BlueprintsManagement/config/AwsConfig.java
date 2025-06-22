package com.portfolio.BlueprintsManagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Value("${s3.region.name}")
    private String region;

    /**
     * S3クライアントにリージョンを設定します。
     *
     * @return リージョンを付与したS3クライアント
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }
}
