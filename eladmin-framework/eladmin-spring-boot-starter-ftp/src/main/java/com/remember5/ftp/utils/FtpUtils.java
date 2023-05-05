/**
 * Copyright [2022] [remember5]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.remember5.ftp.utils;

import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;

import java.io.IOException;
import java.util.List;

/**
 * ftp操作类
 *
 * @author wangjiahao
 * @date 2023/7/5 16:24
 */
public class FtpUtils {

    private Ftp ftp;

    public FtpUtils(Ftp ftp) {
        this.ftp = ftp;
    }

    public static void main(String[] args) throws IOException {
        Ftp ftp1 = new Ftp("192.168.161.23", 21, "username", "mypass");
        ftp1.setMode(FtpMode.Active);
        final List<String> ls = ftp1.ls("/");
        ls.stream().forEach(System.out::println);
        ftp1.close();
    }

}
