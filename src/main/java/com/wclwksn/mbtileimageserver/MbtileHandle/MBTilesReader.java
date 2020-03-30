package com.wclwksn.mbtileimageserver.MbtileHandle;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MBTilesReader {

	private File f;
	private Connection connection;

	public MBTilesReader(File f) throws Exception {
		try {
			connection = SQLiteHelper.establishConnection(f);
		} catch (Exception e) {
			throw new Exception("Establish Connection to " + f.getAbsolutePath() + " failed", e);
		}
		this.f = f;
	}

	public File close() {
		try {
			connection.close();
		} catch (SQLException e) {
		}
		return f;
	}

	public MetadataEntry getMetadata() throws Exception {
		String sql = "SELECT * from metadata;";
		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			MetadataEntry ent = new MetadataEntry();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String value = resultSet.getString("value");
				ent.addKeyValue(name, value);
			}
			return ent;
		} catch (Exception e) {
			throw new Exception("Get Metadata failed", e);
		}
	}

	public TileIterator getTiles() throws Exception {
		String sql = "SELECT * from tiles;";
		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			return new TileIterator(resultSet);
		} catch (Exception e) {
			throw new Exception("Access Tiles failed", e);
		}
	}

	public Tile getTile(int zoom, int column, int row) throws Exception {
		String sql = String.format(
				"SELECT tile_data FROM tiles WHERE zoom_level = %d AND tile_column = %d AND tile_row = %d", zoom,
				column, row);

		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			if (resultSet.next()) {
				InputStream tileDataInputStream = null;
				tileDataInputStream = resultSet.getBinaryStream("tile_data");

				return new Tile(zoom, column, row, tileDataInputStream);
			} else {
				return new Tile(zoom, column, row, null);
			}
		} catch (Exception e) {
			throw new Exception(String.format("Could not get Tile for z:%d, column:%d, row:%d", zoom, column, row), e);
		}
	}

	public int getMaxZoom() throws Exception {
		String sql = "SELECT MAX(zoom_level) FROM tiles";

		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			return resultSet.getInt(1);
		} catch (Exception e) {
			throw new Exception("Could not get max zoom", e);
		}
	}

	public int getMinZoom() throws Exception {
		String sql = "SELECT MIN(zoom_level) FROM tiles";

		try {
			ResultSet resultSet = SQLiteHelper.executeQuery(connection, sql);
			return resultSet.getInt(1);
		} catch (Exception e) {
			throw new Exception("Could not get min zoom", e);
		}
	}
}
