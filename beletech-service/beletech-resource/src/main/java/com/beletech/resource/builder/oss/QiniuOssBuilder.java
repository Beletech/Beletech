package com.beletech.resource.builder.oss;

import com.beletech.core.oss.OssTemplate;
import com.beletech.core.oss.QiniuTemplate;
import com.beletech.core.oss.props.OssProperties;
import com.beletech.core.oss.rule.OssRule;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.SneakyThrows;
import com.beletech.resource.entity.Oss;

/**
 * 七牛云存储构建类
 *
 * @author XueBing
 */
public class QiniuOssBuilder {

	@SneakyThrows
	public static OssTemplate template(Oss oss, OssRule ossRule) {
		Configuration cfg = new Configuration(Region.autoRegion());
		Auth auth = Auth.create(oss.getAccessKey(), oss.getSecretKey());
		UploadManager uploadManager = new UploadManager(cfg);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		OssProperties ossProperties = new OssProperties();
		ossProperties.setEndpoint(oss.getEndpoint());
		ossProperties.setAccessKey(oss.getAccessKey());
		ossProperties.setSecretKey(oss.getSecretKey());
		ossProperties.setBucketName(oss.getBucketName());
		return new QiniuTemplate(auth, uploadManager, bucketManager, ossProperties, ossRule);
	}

}
