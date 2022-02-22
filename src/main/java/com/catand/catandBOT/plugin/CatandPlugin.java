package com.catand.catandBOT.plugin;

import com.mikuac.shiro.bean.MsgChainBean;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class CatandPlugin extends BotPlugin {
	MsgUtils sendMsg;
	@Override
	public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
		String messageRaw = event.getRawMessage();
		if(messageRaw.startsWith("说 ")){
			if(messageRaw.contains("钢板")){
				sendMsg = MsgUtils.builder().tts("钢板，哪有钢板？让我透透");
				bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
			}else if(messageRaw.contains("咕咕")){
				sendMsg = MsgUtils.builder().tts("快来和我进行一个贴的贴");
				bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
			}else   {
				sendMsg = MsgUtils.builder().tts(messageRaw.substring(2));
				bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
			}
		}
		if (messageRaw.contains("戳")) {
			List<MsgChainBean> messageChain = (event.getArrayMsg());
			if (messageChain.size() > 1 && "at".equals(messageChain.get(1).getType())) {
				MsgChainBean message1 = messageChain.get(1);
				if ("at".equals(message1.getType())) {
					sendMsg = MsgUtils.builder().poke(Long.parseLong(message1.getData().get("qq")));
					bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
				}
			}
			else {
				sendMsg = MsgUtils.builder().poke(event.getUserId());
				bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
			}
		}
		if (messageRaw.startsWith("扔骰子")) {
			Random random = new Random();
			Calendar calendar = Calendar.getInstance();
			sendMsg = MsgUtils.builder().at(event.getUserId()).text("扔出了" + (random.nextInt(6) + 1) + "(" + calendar.get(Calendar.MINUTE) + "分" + calendar.get(Calendar.SECOND) + "秒");
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		if (messageRaw.contains("贴贴")) {
			sendMsg = MsgUtils.builder().at(event.getUserId()).text("贴贴").img("https://gchat.qpic.cn/gchatpic_new/3047354896/696421551-2283744490-F0F7B1AE168B0FDA40E9A27362C9462C/0?term=3");
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		if (messageRaw.contains("片片")) {
			sendMsg = MsgUtils.builder().video("https://vdse.bdstatic.com//192d9a98d782d9c74c96f09db9378d93.mp4", "https://gchat.qpic.cn/gchatpic_new/3047354896/696421551-2992631064-ECC25F593B968FAB2B1A056B98C48836/0?term=3");
			bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
		}
		List<MsgChainBean> messageChain = (event.getArrayMsg());
		if (messageChain.size() > 0) {
			MsgChainBean message = messageChain.get(0);
			if ("at".equals(message.getType())) {
				String qq = message.getData().get("qq");
				if (Objects.equals(qq, String.valueOf(bot.getSelfId()))) {
					long q;
					q = event.getUserId();
					sendMsg = MsgUtils.builder().at(q).text("贴贴").img("https://gchat.qpic.cn/gchatpic_new/3047354896/696421551-2283744490-F0F7B1AE168B0FDA40E9A27362C9462C/0?term=3");
					bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
				}
			}
		}
		return MESSAGE_IGNORE;
	}
}
