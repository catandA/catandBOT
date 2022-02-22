package com.catand.catandBOT.plugin.SPDNet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

@Component
public class SPDNetRegisterPlugin extends BotPlugin {
	MsgUtils sendMsg;
	static BufferedWriter bufferedWriter;
	//TODO
	static File file = new File("D:\\catandFTP\\spd\\server\\data\\config.json");
	@Override
	public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
		String message = event.getRawMessage().replace("\n", "");
		if (message.contains("SPD注册")) {
			int userIndex = message.indexOf("用户名 ");
			int keyIndex = message.indexOf("key ");
			Account account;

			//如果"用户名 "和"key "存在
			if (userIndex != -1 && keyIndex != -1) {

				//构建待加入Account对象
				String name;
				String key;
				name = message.substring(userIndex + 4, keyIndex);
				key = message.substring(keyIndex + 4);
				account = new Account(false, false, key, name);

				try {
					//按照SPDJSON作为模板类读取config.json的数据
					ObjectMapper objectMapper = new ObjectMapper();
					SPDJSON spdJSON;
					spdJSON = objectMapper.readValue(file, new TypeReference<SPDJSON>() {});

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
				sendMsg = MsgUtils.builder().at(event.getUserId()).text("创建成功!\n用户名:" + name + "\nkey:" + key);
			} else {
				sendMsg = MsgUtils.builder().at(event.getUserId()).text("语法无效!\n格式:\"SPD注册 用户名 XXX key XXX\"");
			}
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		if (message.startsWith("换种子 ")&&message.length()>4) {
			//TODO
			if (event.getUserId() == 3047354896L||event.getUserId() == 2735951230L) {
				String s = message.substring(4);
				long seed;
				if ("随机".equals(s)){
					seed = new Random().nextLong();
				}else{
					try {
						seed = Long.parseLong(s);
					}catch (Exception e){
						e.printStackTrace();
						sendMsg = MsgUtils.builder().text("有笨猪把种子打错了,我不说是谁");
						bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
						return MESSAGE_BLOCK;
					}
				}
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					SPDJSON spdJson;
					spdJson = objectMapper.readValue(file, SPDJSON.class);
					spdJson.setSeed(seed);
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
					bufferedWriter.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(spdJson));
					bufferedWriter.flush();
					bufferedWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
					sendMsg = MsgUtils.builder().text("你妈,换种子出BUG了,快去控制台看看日志");
					bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
					return MESSAGE_BLOCK;
				}
				sendMsg = MsgUtils.builder().text("已更换为 "+ seed);
			}else {
				sendMsg = MsgUtils.builder().at(event.getUserId()).text("爬,你没得权限");
			}
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		return MESSAGE_IGNORE;
	}
}
