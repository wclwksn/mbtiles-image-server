package com.wclwksn.mbtileimageserver.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wclwksn.mbtileimageserver.MblistBean;
import com.wclwksn.mbtileimageserver.MbtileHandle.MBTilesReader;
import com.wclwksn.mbtileimageserver.model.*;

@RestController
@RequestMapping("/mbtiles")
public class MbtileController {

	private static final Logger log = LoggerFactory.getLogger(MbtileController.class);
	@Autowired
	MblistBean _mbtileBean;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public List<MbtileInfo> MbtileIndex() {
		return _mbtileBean.getAllmbfiles();
	}

	@ResponseBody
	@RequestMapping(path = "refresh", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public UpdateStatus refreshFolder() {
		UpdateStatus _upstatus = new UpdateStatus();
		try {
			_mbtileBean.setmbtilesValue();
			_upstatus.isSuc = true;
		} catch (Exception e) {
			_upstatus.errorMsg = e.getMessage();
		}

		return _upstatus;
	}

	@GetMapping("{layer}/{zoom}/{x}/{y}")
	public ResponseEntity getTile(@PathVariable int x, @PathVariable int y, @PathVariable int zoom,
			@PathVariable String layer) {
		try {

			MBTilesReader mbTilesReader = _mbtileBean.getMBReader(layer);
			if (mbTilesReader != null) {
				com.wclwksn.mbtileimageserver.MbtileHandle.Tile tile = mbTilesReader.getTile(zoom, x, y);
				
				if ( tile.getData() != null) {
					int size = tile.getData().available();
					byte[] bytes = new byte[size];
					tile.getData().read(bytes);
					HttpHeaders headers = new HttpHeaders();
					headers.add("Content-Type", "image/png");
					return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
				}
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到对象");
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(String.format("The tile with the parameters x=%d, y=%d & zoom=%d not found", x, y, zoom));
		}
	}

}
