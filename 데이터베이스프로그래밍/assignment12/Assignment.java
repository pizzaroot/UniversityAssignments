package org.dfpl.lecture.database.assignment.assignment21011793;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class CSVRecord {
    private String rawRecord;
    private String[] separatedValues;

    public String[] getSeparatedValues() {
        return separatedValues;
    }

    CSVRecord(String line) {
        rawRecord = line;
        parseRecord();
    }

    void parseRecord() {
        boolean out = true;
        int degree = 1;

        for (int i = 0; i < rawRecord.length(); i++) {
            switch (rawRecord.charAt(i)) {
                case '"':
                    out = !out;
                    break;
                case ',':
                    if (out) degree++;
                    break;
            }
        }

        separatedValues = new String[degree];
        for (int i = 0; i < degree; i++) separatedValues[i] = "";

        int index = 0;

        for (int i = 0; i < rawRecord.length(); i++) {
            char ch = rawRecord.charAt(i);
            switch (ch) {
                case '"':
                    out = !out;
                    break;
                case ',':
                    if (out) {
                        index++;
                        break;
                    }
                default:
                    separatedValues[index] += ch;
            }
        }
    }
}

class SQLPrinter {
    private ResultSet rs;

    SQLPrinter(ResultSet rs) {
        this.rs = rs;
    }

    void print() throws SQLException {
        int describeStmtColumnCnt = rs.getMetaData().getColumnCount();
        String[] describeStmtMetaData = new String[describeStmtColumnCnt];
        int[] describeStmtMaxLength = new int[describeStmtColumnCnt];
        for (int i = 0; i < describeStmtColumnCnt; i++) {
            String attrName = rs.getMetaData().getColumnName(i + 1);
            describeStmtMaxLength[i] = Math.max(describeStmtMaxLength[i], attrName.length());
            describeStmtMetaData[i] = attrName;
        }

        while (rs.next()) {
            for (int i = 0; i < describeStmtColumnCnt; i++) {
                String value = rs.getString(i + 1);
                if (value == null) value = "NULL";
                describeStmtMaxLength[i] = Math.max(describeStmtMaxLength[i], value.length());
            }
        }

        System.out.print("+");
        for (int i = 0; i < describeStmtColumnCnt; i++) {
            for (int j = 0; j < describeStmtMaxLength[i] + 2; j++) System.out.print('-');
            System.out.print('+');
        }
        System.out.println();

        System.out.print('|');
        for (int i = 0; i < describeStmtColumnCnt; i++) {
            System.out.print(" " + describeStmtMetaData[i]);
            for (int j = describeStmtMetaData[i].length() + 1; j < describeStmtMaxLength[i] + 2; j++) System.out.print(' ');
            System.out.print('|');
        }
        System.out.println();

        System.out.print("+");
        for (int i = 0; i < describeStmtColumnCnt; i++) {
            for (int j = 0; j < describeStmtMaxLength[i] + 2; j++) System.out.print('-');
            System.out.print('+');
        }
        System.out.println();

        rs.beforeFirst();

        while (rs.next()) {
            System.out.print('|');
            for (int i = 0; i < describeStmtColumnCnt; i++) {
                String value = rs.getString(i + 1);
                if (value == null) value = "NULL";
                System.out.print(" " + value);
                for (int j = value.length() + 1; j < describeStmtMaxLength[i] + 2; j++) System.out.print(' ');
                System.out.print('|');
            }
            System.out.println();
        }

        System.out.print("+");
        for (int i = 0; i < describeStmtColumnCnt; i++) {
            for (int j = 0; j < describeStmtMaxLength[i] + 2; j++) System.out.print('-');
            System.out.print('+');
        }
        System.out.println();
    }
}

public class Assignment {
    static void printResultSet(ResultSet rs) throws SQLException {
        SQLPrinter sp = new SQLPrinter(rs);
        sp.print();
    }

    static void execSQL(Statement stmt, String query) throws SQLException {
        System.out.println("SQL> " + query + ";");
        printResultSet(stmt.executeQuery(query));
    }

    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new FileReader("d:\\assignment.csv", Charset.forName("euc-kr")));

        List<GameFacility> gameFacilityList = new ArrayList<>();

        br.readLine();
        while (true) {
            String line = br.readLine();
            if (line == null) break;

            CSVRecord record = new CSVRecord(line);
            String[] values = record.getSeparatedValues();

            gameFacilityList.add(new GameFacility(Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], Double.parseDouble(values[5]), Double.parseDouble(values[6])));
        }
        br.close();

        for (var facility : gameFacilityList) {
            System.out.println(facility);
        }

        String id = "root";
        String password = "1234";

        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", id, password);

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS dbp");
        stmt.executeUpdate("USE dbp");
        stmt.executeUpdate("CREATE OR REPLACE TABLE game_facility (id INT PRIMARY KEY, reg_date DATE, name VARCHAR(200), type VARCHAR(100), address VARCHAR(500), coordinate POINT) CHARACTER SET 'euckr'");

        execSQL(stmt, "DESCRIBE game_facility");

        for (var facility : gameFacilityList) {
            stmt.executeUpdate("INSERT INTO game_facility VALUES (" + facility.getSerialNumber() + ", '" + facility.getRegistrationDate() + "', '" + facility.getBusinessName() + "', '" + facility.getIndustryType() + "', '" + facility.getAddress() + "', POINT(" + facility.getLatitude() + ", " + facility.getLongitude() +  "))");
        }

        execSQL(stmt, "SELECT id, reg_date, name, type, address, X(coordinate) AS latitude, Y(coordinate) AS longitude FROM game_facility");
        execSQL(stmt, "SELECT COUNT(*) FROM game_facility WHERE type='복합유통게임제공업'");
    }
}
