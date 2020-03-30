package com.wclwksn.mbtileimageserver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wclwksn.mbtileimageserver.MbtileHandle.MBTilesReader;
import com.wclwksn.mbtileimageserver.model.*;

@Component
public class MblistBean implements InitializingBean {
	@Value("${mbdata.path}")
	private String mbpath;
	private static final Logger log = LoggerFactory.getLogger(MblistBean.class);

	private List<MbtileInfo> _allmbfiles = new ArrayList<MbtileInfo>();
	private HashMap<String, MBTilesReader> _mbReaderHM = new HashMap<String, MBTilesReader>();

	public List<MbtileInfo> getAllmbfiles() {
		return _allmbfiles;
	}

	public HashMap<String, MBTilesReader> getMBReaderHM() {
		return _mbReaderHM;
	}

	public MBTilesReader getMBReader(String _keyvalue) {
		if (_mbReaderHM.containsKey(_keyvalue)) {
			return _mbReaderHM.get(_keyvalue);
		}
		return null;
	}

	public void setmbtilesValue() throws Exception {
		_mbReaderHM.clear();
		this._allmbfiles.clear();
		this.readAllFile(mbpath);
		log.info("readfinish!");
	}

	private void readAllFile(String filePath) throws Exception {
		File f = new File(filePath);
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				readAllFile(file.getAbsolutePath());
			} else {
				String _fileName = file.getName();
				String suffix = _fileName.substring(_fileName.lastIndexOf("."));
				if (suffix.equals(".mbtiles")) {
					MbtileInfo _mbInfo = new MbtileInfo();
					_mbInfo.mname = _fileName;
					_mbInfo.fullPath = file.getAbsolutePath();
					_mbInfo.mbFile = file;
					if (!_mbReaderHM.containsKey(_fileName)) {
						_mbReaderHM.put(_fileName, new MBTilesReader(file));
					}
					_allmbfiles.add(_mbInfo);
				}
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.readAllFile(mbpath);
	}

}
