package com.wclwksn.mbtileimageserver.model;

import com.wclwksn.mbtileimageserver.MbtileHandle.MBTilesReader;

public class MbtilesObject {

	private MBTilesReader _mBTileReader;

	private String _imageFormat;

//	public MbtilesObject() {
//
//	}
	public MbtilesObject(MBTilesReader reader, String sformat) { 
		this._mBTileReader = reader;
		this._imageFormat = sformat;
	}

	public MBTilesReader get_mBTileReader() {
		return _mBTileReader;
	}

	public void set_mBTileReader(MBTilesReader _mBTileReader) {
		this._mBTileReader = _mBTileReader;
	}

	public String get_imageFormat() {
		return _imageFormat;
	}

	public void set_imageFormat(String _imageFormat) {
		this._imageFormat = _imageFormat;
	}

}
