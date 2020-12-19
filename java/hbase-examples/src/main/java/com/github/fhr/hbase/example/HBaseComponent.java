package com.github.fhr.hbase.example;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;

/**
 * @author huaran
 * @since 2020/12/19
 **/
public class HBaseComponent {
    private HBaseConnectionFactory hBaseConnectionFactory;

    public HBaseComponent(HBaseConnectionFactory hBaseConnectionFactory) {
        this.hBaseConnectionFactory = hBaseConnectionFactory;
    }

    public void putRow(String tableName, String rowKey, String columnFamily, String qualifier, String data) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), Bytes.toBytes(data));
            table.put(put);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void putRows(String tableName, List<Put> puts) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            table.put(puts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRow(String tableName, String rowKey) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteQualifier(String tableName, String rowKey, String columnFamily, String qualifierName) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifierName));
            table.delete(delete);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Result getRow(String tableName, String rowKey) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Result getRow(String tableName, String rowKey, FilterList filterList) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setFilter(filterList);
            return table.get(get);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultScanner getScanner(String tableName, int cachingSize) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Scan scan = new Scan();
            scan.setCaching(cachingSize);
            return table.getScanner(scan);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultScanner getScanner(String tableName, int cachingSize, String startKey, String stopKey) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Scan scan = new Scan();
            scan.withStartRow(Bytes.toBytes(startKey));
            scan.withStopRow(Bytes.toBytes(stopKey));
            scan.setCaching(cachingSize);
            return table.getScanner(scan);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultScanner getScanner(String tableName, int cachingSize, String startKey, String stopKey, FilterList filterList) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             Table table = connection.getTable(TableName.valueOf(tableName))) {
            Scan scan = new Scan();
            scan.setFilter(filterList);
            scan.withStartRow(Bytes.toBytes(startKey));
            scan.withStopRow(Bytes.toBytes(stopKey));
            scan.setCaching(cachingSize);
            return table.getScanner(scan);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
