package com.catand.catandBOT.plugin;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class HelpPlugin extends BotPlugin {
	MsgUtils sendMsg;
	@Override
	public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
		if(event.getRawMessage().contains("帮助")||event.getRawMessage().contains("help")){
			sendMsg = MsgUtils.builder().text("这是catand的机器人,当前版本：1.0.1-DEV\n" +
					"-主要开发者：Catand\n-协同开发：JDSALing\n" +
					"-基于 Shiro \n-目前白咕咕所拥有的功能：\n" +
					"1.文字转语音(tts)\n" +
					"2.无情的贴贴机器\n" +
					"3.QQ头像戳一戳\n" +
					"4.获取指定用户的QQ头像扔骰子\n" +
					"5.通过@获取指定QQ号(好没用!)\n" +
					"6.SPDNET相关操作（由于技术问题，暂不支持中文注册）:\n-格式:SPD注册 用户名 xxxx key xxxx\n" +
					"7.用户注册，用户注册唯一凭据\n" +
					"8.更换服务器种子(仅最高管理员可用)\n");
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		return MESSAGE_IGNORE;
	}
}