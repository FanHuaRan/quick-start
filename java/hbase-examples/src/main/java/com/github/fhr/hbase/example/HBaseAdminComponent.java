package com.github.fhr.hbase.example;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Arrays;

/**
 * @author huaran
 * @since 2020/12/19
 **/
public class HBaseAdminComponent {
    private HBaseConnectionFactory hBaseConnectionFactory;

    public HBaseAdminComponent(HBaseConnectionFactory hBaseConnectionFactory) {
        this.hBaseConnectionFactory = hBaseConnectionFactory;
    }

    public void createTable(String tableName, String[] columnFamilies, int maxVersion) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             HBaseAdmin admin = (HBaseAdmin) connection.getAdmin()) {
            TableName table = TableName.valueOf(tableName);
            if (admin.tableExists(table)) {
                throw new IllegalArgumentException(String.format("%s already exist", tableName));
            }
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(table);
            Arrays.stream(columnFamilies).forEach(columnFamily -> {
                tableDescriptorBuilder.addColumnFamily(new HColumnDescriptor(columnFamily).setMaxVersions(maxVersion));
            });
            admin.createTable(tableDescriptorBuilder.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTable(String tableName) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             HBaseAdmin admin = (HBaseAdmin) connection.getAdmin()) {
            TableName table = TableName.valueOf(tableName);
            if (!admin.tableExists(table)) {
                return;
            }
            admin.disableTable(table);
            admin.deleteTable(table);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addColumnFamily(String tableName, String columnFamily, int maxVersion) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             HBaseAdmin admin = (HBaseAdmin) connection.getAdmin()) {
            admin.addColumnFamily(TableName.valueOf(tableName), new HColumnDescriptor(columnFamily).setMaxVersions(maxVersion));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteColumnFamily(String tableName, String columnFamily) {
        try (Connection connection = hBaseConnectionFactory.getConnection();
             HBaseAdmin admin = (HBaseAdmin) connection.getAdmin()) {
            admin.deleteColumnFamily(TableName.valueOf(tableName), Bytes.toBytes(columnFamily));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
