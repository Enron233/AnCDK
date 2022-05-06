package com.github.lileep.ancdk.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CSVUtil {

    /**
     * 读取CSV格式的文档数据
     *
     * @param filePath CSV格式的文件路径
     * @return CSV数据读取放入二维list中。
     */
    public static List<List<String>> readCSVFileData(File filePath) {
        List<List<String>> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataList.add(Arrays.asList(line.split(",")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 写入CSV格式的文档数据
     *
     * @param filePath CSV格式的文件路径
     * @param dataList 需要写入的数据
     */
    public static boolean writeCSVFileData(File filePath, List<List<String>> dataList) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (List<String> data : dataList) {
                for (int i = 0; i < data.size(); i++) {
                    bw.write(data.get(i));
                    if (i < data.size() - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 写入单列CSV格式的文档数据
     *
     * @param filePath CSV格式的文件路径
     * @param dataSet 需要写入的一列数据
     */
    public static boolean writeCSVFileData(File filePath, Set<String> dataSet) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (String data : dataSet) {
                bw.write(data);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
