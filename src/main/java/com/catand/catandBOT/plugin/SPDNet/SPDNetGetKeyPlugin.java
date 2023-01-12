package com.catand.catandBOT.plugin.SPDNet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;

@Component
public class SPDNetGetKeyPlugin extends BotPlugin {
	MsgUtils sendMsg;


	//TODO Sever
	static File file = new File("C:\\spd-server\\spd-server\\server\\data\\config.json");

	@Override
	public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
		String message = event.getRawMessage().replace("\n", "");
		//TODO 检查插件
		System.out.println("test go open!!!");
		if ("key".equals(message)) {

			String key;
			key = DigestUtils.md5DigestAsHex(("这是一个加盐前缀:QQ号码:" + event.getUserId()).getBytes()).substring(8, 24);

			try {
				//按照SPDJSON作为模板类读取config.json的数据
				ObjectMapper objectMapper = new ObjectMapper();
				SPDJSON spdJSON;
				spdJSON = objectMapper.readValue(file, new TypeReference<SPDJSON>() {
				});

				//遍历寻找是否有相同key
				for (Account account1 : spdJSON.getAccounts()) {
					//检查key
					if (account1.getKey().equals(key)) {
						sendMsg = MsgUtils.builder().text(String.format("你的key是: " + "\n%s", key));
						bot.sendPrivateMsg(event.getUserId(), sendMsg.build(), false);
						return MESSAGE_IGNORE;
					}
				}
				sendMsg = MsgUtils.builder().text("你好像还没注册");

			} catch (Exception e) {
				e.printStackTrace();
				sendMsg = MsgUtils.builder().text("你妈,出BUG了,快去控制台看看日志");
				bot.sendPrivateMsg(event.getUserId(), sendMsg.build(), false);
				return MESSAGE_BLOCK;
			}
			bot.sendPrivateMsg(event.getUserId(), sendMsg.build(), false);
		}
		return MESSAGE_IGNORE;
	}
}
