package com.beletech.resource.endpoint;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.beletech.core.oss.model.BeletechFile;
import com.beletech.core.oss.model.OssFile;
import com.beletech.core.secure.annotation.PreAuth;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.constant.RoleConstant;
import com.beletech.core.tool.utils.FileUtil;
import com.beletech.core.tool.utils.Func;
import com.beletech.resource.builder.oss.OssBuilder;
import com.beletech.resource.entity.Attach;
import com.beletech.resource.service.IAttachService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储端点
 *
 * @author XueBing
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/oss/endpoint")
@Api(value = "对象存储端点", tags = "对象存储端点")
public class OssEndpoint {

	/**
	 * 对象存储构建类
	 */
	private final OssBuilder ossBuilder;

	/**
	 * 附件表服务
	 */
	private final IAttachService attachService;

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return Bucket
	 */
	@SneakyThrows
	@PostMapping("/make-bucket")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result makeBucket(@RequestParam String bucketName) {
		ossBuilder.template().makeBucket(bucketName);
		return Result.success("创建成功");
	}

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-bucket")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result removeBucket(@RequestParam String bucketName) {
		ossBuilder.template().removeBucket(bucketName);
		return Result.success("删除成功");
	}

	/**
	 * 拷贝文件
	 *
	 * @param fileName       存储桶对象名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/copy-file")
	public Result copyFile(@RequestParam String fileName, @RequestParam String destBucketName, String destFileName) {
		ossBuilder.template().copyFile(fileName, destBucketName, destFileName);
		return Result.success("操作成功");
	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/stat-file")
	public Result<OssFile> statFile(@RequestParam String fileName) {
		return Result.data(ossBuilder.template().statFile(fileName));
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-path")
	public Result<String> filePath(@RequestParam String fileName) {
		return Result.data(ossBuilder.template().filePath(fileName));
	}


	/**
	 * 获取文件外链
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-link")
	public Result<String> fileLink(@RequestParam String fileName) {
		return Result.data(ossBuilder.template().fileLink(fileName));
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file")
	public Result<BeletechFile> putFile(@RequestParam MultipartFile file) {
		BeletechFile beletechFile = ossBuilder.template().putFile(file.getOriginalFilename(), file.getInputStream());
		return Result.data(beletechFile);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-by-name")
	public Result<BeletechFile> putFile(@RequestParam String fileName, @RequestParam MultipartFile file) {
		BeletechFile beletechFile = ossBuilder.template().putFile(fileName, file.getInputStream());
		return Result.data(beletechFile);
	}

	/**
	 * 上传文件并保存至附件表
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-attach")
	public Result<BeletechFile> putFileAttach(@RequestParam MultipartFile file) {
		String fileName = file.getOriginalFilename();
		BeletechFile beletechFile = ossBuilder.template().putFile(fileName, file.getInputStream());
		Long attachId = buildAttach(fileName, file.getSize(), beletechFile);
		beletechFile.setAttachId(attachId);
		return Result.data(beletechFile);
	}

	/**
	 * 上传文件并保存至附件表
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-attach-by-name")
	public Result<BeletechFile> putFileAttach(@RequestParam String fileName, @RequestParam MultipartFile file) {
		BeletechFile beletechFile = ossBuilder.template().putFile(fileName, file.getInputStream());
		Long attachId = buildAttach(fileName, file.getSize(), beletechFile);
		beletechFile.setAttachId(attachId);
		return Result.data(beletechFile);
	}

	/**
	 * 构建附件表
	 *
	 * @param fileName  文件名
	 * @param fileSize  文件大小
	 * @param beletechFile 对象存储文件
	 * @return attachId
	 */
	private Long buildAttach(String fileName, Long fileSize, BeletechFile beletechFile) {
		String fileExtension = FileUtil.getFileExtension(fileName);
		Attach attach = new Attach();
		attach.setDomain(beletechFile.getDomain());
		attach.setLink(beletechFile.getLink());
		attach.setName(beletechFile.getName());
		attach.setOriginalName(beletechFile.getOriginalName());
		attach.setAttachSize(fileSize);
		attach.setExtension(fileExtension);
		attachService.save(attach);
		return attach.getId();
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-file")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result removeFile(@RequestParam String fileName) {
		ossBuilder.template().removeFile(fileName);
		return Result.success("操作成功");
	}

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-files")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public Result removeFiles(@RequestParam String fileNames) {
		ossBuilder.template().removeFiles(Func.toStrList(fileNames));
		return Result.success("操作成功");
	}

}
