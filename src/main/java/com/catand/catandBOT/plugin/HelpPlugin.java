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
			sendMsg = MsgUtils.builder().text("这是catand的机器人,当前版本：1.0.4-Release\n" +
					"-主要开发者：Catand\n-协同开发者：JDSALing\n" +
					"-基于 Shiro \n-目前白咕咕所拥有的功能：\n" +
					"==========常规功能==========\n" +
					"1.文字转语音(tts)(暂废)\n" +
					"2.无情的贴贴机器(暂废)\n" +
					"3.QQ头像戳一戳(暂废)\n" +
					"4.获取指定用户的QQ头像扔骰子(暂废)\n" +
					"5.通过@获取指定QQ号(好没用!)(暂废)\n" +
					"==========SPDNET相关操作==========\n" +
					"1.SPDNET相关操作(支持中英注册，表情包，特殊字符少来，爬！):\n" +
					"-格式:SPD注册 用户名 xxxx\n" +
					"2.注册好后加白咕咕好友，私聊发“key”(区分大小写)获取密码（请勿分享给其他人)");
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		return MESSAGE_IGNORE;
	}
}