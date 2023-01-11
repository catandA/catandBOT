package com.catand.catandBOT.plugin;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class BeautifulPicturePlugin extends BotPlugin {
	MsgUtils sendMsg;

	@Override
	public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
//		if (event.getRawMessage().contains("美图")) {
//			sendMsg = MsgUtils.builder().img(String.format("https://iw233.cn/api/random.php/0/?rand=%d",System.currentTimeMillis()));
//			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
//			return MESSAGE_BLOCK;
//		}
		return MESSAGE_IGNORE;
	}
}
