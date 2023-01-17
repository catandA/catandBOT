package com.catand.catandBOT.plugin;

import com.mikuac.shiro.bo.ArrayMsg;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchQQNumberPlugin extends BotPlugin {
	MsgUtils sendMsg;
	@Override
	public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
		String rawMsg = event.getRawMessage();
		if (rawMsg.startsWith("查QQ")||rawMsg.startsWith("查qq")) {
			List<ArrayMsg> messageChain = (event.getArrayMsg());
			if (messageChain.size() > 1 && "at".equals(messageChain.get(1).getType())) {
				ArrayMsg message = messageChain.get(1);
				if ("at".equals(message.getType())) {
					sendMsg = MsgUtils.builder().text("QQ: " + message.getData().get("qq"));
					bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
				}
			}
		}
		return MESSAGE_IGNORE;
	}
}
