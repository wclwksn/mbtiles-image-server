package com.wclwksn.mbtileimageserver.model;

import java.io.File;

public class MbtileInfo {
	public String mname;
	public String fullPath;
	public File mbFile;

	public File getMbFile() {
		return mbFile;
	}

	public void setMbFile(File mbFile) {
		this.mbFile = mbFile;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

}
