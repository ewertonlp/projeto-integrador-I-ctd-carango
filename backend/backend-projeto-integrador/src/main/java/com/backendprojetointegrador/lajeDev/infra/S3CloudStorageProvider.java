package com.backendprojetointegrador.lajeDev.infra;

import com.backendprojetointegrador.lajeDev.core.StorageProperties;
import com.backendprojetointegrador.lajeDev.domain.model.Imagem;
import com.backendprojetointegrador.lajeDev.domain.service.imagem.CloudStorageProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Component
@AllArgsConstructor
public class S3CloudStorageProvider implements CloudStorageProvider {

    private final S3Presigner s3Presigner;
    private final StorageProperties storageProperties;

    @Override
    public URL generatePresignerUploadUrl(Imagem imagem) {
        AwsRequestOverrideConfiguration.Builder builder = AwsRequestOverrideConfiguration.builder();

        builder.putRawQueryParameter("x-amz-acl", "public-read");

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(storageProperties.getS3().getBucket())
                .key(imagem.getName())
                .contentType(imagem.getContentType())
                .contentLength(imagem.getContentLength())
                .overrideConfiguration(builder.build())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(20))
                .putObjectRequest(objectRequest)
                .build();

        return s3Presigner.presignPutObject(presignRequest).url();
    }

}
