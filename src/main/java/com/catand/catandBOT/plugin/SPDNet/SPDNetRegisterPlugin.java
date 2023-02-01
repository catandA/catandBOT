package com.catand.catandBOT.plugin.SPDNet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

@Component
public class SPDNetRegisterPlugin extends BotPlugin {
	MsgUtils sendMsg;
	static BufferedWriter bufferedWriter;

	//TODO Sever
	static File file = new File("C:\\spd-server-ling\\server\\data\\config.json");

	//判断是否是中文
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	@Override
	public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
		String message = event.getRawMessage().replace("\n", "");
		if (message.contains("SPD注册")) {
			int userIndex = message.indexOf("用户名 ");
			Account account;
			//如果"用户名 "和"key "存在
			if (userIndex != -1) {

				//构建待加入Account对象
				String name;
				String key;
				name = message.substring(userIndex + 4);
				key = DigestUtils.md5DigestAsHex(("这是一个加盐前缀:QQ号码:" + event.getUserId()).getBytes()).substring(8, 24);
				account = new Account(false, false, key, name, false);


				if (name.contains("[CQ:")) {
					sendMsg = MsgUtils.builder().at(event.getUserId()).text("\n你这名字有你妈QQ表情包啊,拿表情糊弄人是吧，爬!!!");
					bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
					return MESSAGE_IGNORE;
				}

//                //检查用户名是不是放飞了自我
//                for (char c : name.toCharArray()) {
//                    //判断用户名是英文，或者是中文 如果都无法成功 则发送消息
//                    if (!CharUtil.isAsciiPrintable(c) && !isChinese(c)) {
//                        sendMsg = MsgUtils.builder().at(event.getUserId()).text(String.format("\n你这名字有你妈异体字符\"%c\"啊，快给我换!!!", c));
//                        bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
//                        return MESSAGE_IGNORE;
//                    }
//                }

				try {

					//按照SPDJSON作为模板类读取config.json的数据
					ObjectMapper objectMapper = new ObjectMapper();
					SPDJSON spdJSON;
					spdJSON = objectMapper.readValue(file, new TypeReference<SPDJSON>() {
					});

					//遍历寻找是否有相同用户名或者相同key
					for (Account account1 : spdJSON.getAccounts()) {
						//检查用户名
						if (account1.getNick().equals(name)) {
							sendMsg = MsgUtils.builder().at(event.getUserId()).text("\n有人抢先一步用了这个用户名了\n换一个用户名注册⑧");
							bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
							return MESSAGE_IGNORE;
						}

						//检查key
						else if (account1.getKey().equals(key)) {
							sendMsg = MsgUtils.builder().at(event.getUserId()).text("\n想同QQ注册多个是吧，不许！");
							bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
							return MESSAGE_IGNORE;
						}
					}

					//修改spdJSON并写回
					spdJSON.getAccounts().add(account);
					//TODO 链接常驻
					bufferedWriter = new BufferedWriter(new FileWriter(file, false));
					bufferedWriter.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(spdJSON));
					bufferedWriter.flush();
					bufferedWriter.close();

				} catch (Exception e) {
					e.printStackTrace();
					sendMsg = MsgUtils.builder().text("你妈,SPD注册功能出BUG了,快去控制台看看日志");
					bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
					return MESSAGE_BLOCK;
				}
				sendMsg = MsgUtils.builder().at(event.getUserId()).text("创建成功!\n用户名:" + name + "\n你的key请加我好友私聊发送key来查询");
			} else {
				sendMsg = MsgUtils.builder().at(event.getUserId()).text("语法无效!\n格式:\"SPD注册 用户名 XXX\"");
			}
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		return MESSAGE_IGNORE;
	}
}
