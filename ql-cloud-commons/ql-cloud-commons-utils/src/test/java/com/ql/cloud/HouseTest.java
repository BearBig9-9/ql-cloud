/**
 * HouseTest.java 2016年12月01日 9:25
 * <p>
 * Copyright (c) 2010-2016 yinghezhong Inc. All rights reserved.
 *
 * @Description
 * @version 1.0
 */
package com.ql.cloud;

import com.ql.cloud.commons.utils.HttpClientUtils;

import javax.naming.SizeLimitExceededException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询合同编号
 *
 * @author 齐龙
 * @date 2016年12月01日 9:25
 *
 */
public class HouseTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientUtils httpUtils = HttpClientUtils.getInstance();
        String url = "http://60.10.45.218:8080/ContractSearch.aspx";
        String codePre = "201608";//08:22,20,21,23,24,19,18,17,16,15,12,11,10;09:10,11,12,13,14,15,16,09,08,07,06,05,04
        String cardNo = "131125198312141865";
        String password = "141865";
        Map<String, String> params = new HashMap<>();
        params.put("ctl00$Center$ScriptManager1", "ctl00$Center$UpdatePanel1|ctl00$Center$Button1");
        params.put("__EVENTTARGET", "");
        params.put("__EVENTARGUMENT", "");
        params.put("select", "1");
        params.put("ctl00$Center$card", cardNo);
        params.put("ctl00$Center$password", password);
        params.put("ctl00$Center$searchtype", "0");
        params.put(
                "__VIEWSTATE",
                "/wEPDwUJMTI4MTIyODU2D2QWAmYPZBYCAgMPZBYEAgEPZBYGAgEPZBYMAgEPDxYCHgRUZXh0BQUzMTMzOGRkAgMPDxYCHwAFCjI2MzE2OTAuNzZkZAIFDw8WAh8ABQUxODYzNGRkAgcPDxYCHwAFCjE5MzcwMjEuNDVkZAIJDw8WAh8ABQUxMjcwNGRkAgsPDxYCHwAFCTY5NDY2OS4zMWRkAgMPZBYCZg88KwAJAQAPFgQeCERhdGFLZXlzFgAeC18hSXRlbUNvdW50ZmRkAgcPZBYCZg9kFgQCCw8WAh4Fc3R5bGUFDmRpc3BsYXk6YmxvY2s7FgICAQ88KwARAgAPFgQeC18hRGF0YUJvdW5kZx8CZmQBEBYAFgAWAGQCDQ8WAh8DBQ1kaXNwbGF5Om5vbmU7FgICAQ88KwARAQEQFgAWABYAZAIDDzwrAAkBAA8WBB8BFgAfAmZkZBgCBR9jdGwwMCRDZW50ZXIkZ3Zfc2Vjb25kX2NvbnRyYWN0D2dkBRhjdGwwMCRDZW50ZXIkZ3ZfY29udHJhY3QPPCsADAEIZmR+mVnJ/8z+7S3uJB9+f3ZRZcziintcgG5usBG07vmpMA==");
        params.put("__EVENTVALIDATION", "/wEWBgKevcvFAwL1v4qKAwLrpZC2DgLUyL+ODALS2MHfBQKFqLaeAThN2fplQiVqhASL5wBggaMoUrUN6qcbRfnwtLfr+Z9k");
        params.put("__ASYNCPOST", "true");
        params.put("ctl00$Center$Button1", "搜索");
        params.put("ctl00$Center$code", "20160912359");
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        for (int start = 30; start < 31; start++){

            for (int i = 1; i < 1000; i++) {
                StringBuffer stringBuffer = new StringBuffer(codePre);
                if (start < 10){
                    stringBuffer.append("0");
                }
                stringBuffer.append(start);
                if (i < 10) {
                    stringBuffer.append("00");
                } else if (i < 100) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(i);
                params.put("ctl00$Center$code", stringBuffer.toString());
                String result = httpUtils.post(url, header, params, 0);
                System.out.println("[result]:" + result);
                if (result.contains("大运河")) {
                    System.out.println("[合同编号]:" + stringBuffer.toString());
                    break;
                } else {
                    System.out.println("[失败]:" + stringBuffer.toString());
                }
                Thread.sleep(500);

            }
        }



    }


}
