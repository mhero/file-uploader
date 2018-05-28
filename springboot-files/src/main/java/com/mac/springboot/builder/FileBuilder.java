package com.mac.springboot.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.mac.springboot.domain.Document;
import com.mac.springboot.dto.out.DocumentOut;

public class FileBuilder {

	public enum Attribute {
		creationDate, lastAccess, lastModify;
	}

	public static Document of(MultipartFile uploadedFile) {
		Document document = new Document();
		Map<Attribute, Date> fileBasicAttributesFile = fileBasicAttributes(convert(uploadedFile));
		try {
			document.setTitle(uploadedFile.getOriginalFilename());
			document.setDescription(uploadedFile.getContentType());
			document.setCreationDate(fileBasicAttributesFile.get(Attribute.lastAccess));
			document.setUploadDate(new Date());
			document.setFile(uploadedFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return document;
	}

	public static DocumentOut of(Document document) {
		DocumentOut documentOut = new DocumentOut();
		documentOut.setId(document.getId());
		documentOut.setTitle(document.getTitle());
		documentOut.setDescription(document.getDescription());
		return documentOut;
	}

	public static Map<Attribute, Date> fileBasicAttributes(File file) {
		Map<Attribute, Date> attributes = new HashMap<>();
		if (file == null)
			return attributes;
		try {
			BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			attributes.put(Attribute.creationDate, new Date(attr.creationTime().toMillis()));
			attributes.put(Attribute.lastAccess, new Date(attr.lastAccessTime().toMillis()));
			attributes.put(Attribute.lastModify, new Date(attr.lastModifiedTime().toMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return attributes;
	}

	public static File convert(MultipartFile file) {
		File convFile = null;
		try {
			convFile = new File(file.getOriginalFilename());
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convFile;
	}

}
