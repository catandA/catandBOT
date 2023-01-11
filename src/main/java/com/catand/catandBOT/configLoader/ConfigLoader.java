package com.catand.catandBOT.configLoader;

import com.catand.catandBOT.CatandBotApplication;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class ConfigLoader {

	static File APPLICATION;

	static {
		//获取jar当前路径
		String path = System.getProperty("java.class.path");
		int lastIndex = path.lastIndexOf(File.separator) + 1;
		path = path.substring(0, lastIndex);

		//检验application.yml
		APPLICATION = new File(path + "application.yml");
		if (!APPLICATION.exists()) {
			System.out.println("\n未找到配置文件\"application.yml\"");
			boolean created = false;
			try {
				created = APPLICATION.createNewFile();
				Writer bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(APPLICATION), StandardCharsets.UTF_8));
				bos.write("server:\n" +
						"  port: 5000\n" +
						"#端口号\n" +
						"shiro:\n" +
						"  plugin-list:\n" +
						"    - com.catand.catandBOT.plugin.LogPlugin #控制台日志插件\n" +
						"    - com.catand.catandBOT.plugin.CatandPlugin #大部分基础功能插件\n" +
						"    - com.catand.catandBOT.plugin.SPDNet.SPDNetRegisterPlugin #SPDNet相关插件,一般情况下没用\n" +
						"    - com.catand.catandBOT.plugin.BeautifulPicturePlugin #美图插件\n" +
						"    - com.catand.catandBOT.plugin.ImagePlugin #查头像插件\n" +
						"    - com.catand.catandBOT.plugin.HelpPlugin #指令菜单插件\n" +
						"    - com.catand.catandBOT.plugin.SearchQQNumberPlugin #@查询QQ号插件\n"+
						"    - com.catand.catandBOT.plugin.TestPlugin #@测试功能插件\n"+
						"#在需要关闭的插件前面加上\"#\"");
				bos.flush();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
				APPLICATION.delete();
				System.out.println("配置文件生成失败");
				System.exit(0);
			}
			if (created) {
				System.out.println("配置文件\"application.yml\"已在当前目录生成");
				System.out.println("退出程序,请修改\"application.yml\"后重新启动");
			}
			CatandBotApplication.exitApplication();
		}

	}


}
