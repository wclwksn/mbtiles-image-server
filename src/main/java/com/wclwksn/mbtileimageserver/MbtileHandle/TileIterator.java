package com.wclwksn.mbtileimageserver.MbtileHandle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A streaming iterator to extract tiles
 */
public class TileIterator {
    private ResultSet rs;

    public TileIterator(ResultSet s) {
        rs = s;
    }

    public boolean hasNext() {
        try {
            boolean hasNext = rs.next();
//            if (!hasNext) {
//                close();
//            }
            return hasNext;
        } catch (SQLException e) {
            return false;
        }
    }

    public Tile next() throws Exception {
        try {
            int zoom = rs.getInt("zoom_level");
            int column = rs.getInt("tile_column");
            int row = rs.getInt("tile_row");
            InputStream tile_data;
            if (rs.getBytes(4) != null) {
                tile_data = new ByteArrayInputStream(rs.getBytes(4));
            } else {
                tile_data = new ByteArrayInputStream(new byte[]{});
            }
            return new Tile(zoom, column, row, tile_data);
        } catch (SQLException e) {
            throw new Exception("Read next tile", e);
        }
    }

    public void close() {
        try {
            rs.close();
        } catch (SQLException e) {
        }
    }
}

