package com.beletech.resource.builder.oss;

import com.beletech.core.oss.MinioTemplate;
import com.beletech.core.oss.OssTemplate;
import com.beletech.core.oss.props.OssProperties;
import com.beletech.core.oss.rule.OssRule;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import com.beletech.resource.entity.Oss;

/**
 * Minio云存储构建类
 *
 * @author XueBing
 */
public class MinioOssBuilder {

	@SneakyThrows
	public static OssTemplate template(Oss oss, OssRule ossRule) {
		MinioClient minioClient = MinioClient.builder()
			.endpoint(oss.getEndpoint())
			.credentials(oss.getAccessKey(), oss.getSecretKey())
			.build();
		OssProperties ossProperties = new OssProperties();
		ossProperties.setEndpoint(oss.getEndpoint());
		ossProperties.setAccessKey(oss.getAccessKey());
		ossProperties.setSecretKey(oss.getSecretKey());
		ossProperties.setBucketName(oss.getBucketName());
		return new MinioTemplate(minioClient, ossRule, ossProperties);
	}

}
