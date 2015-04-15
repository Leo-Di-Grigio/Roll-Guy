function execute(dialog, player, npc)
	local text = dialog:textEnd();

	if(player:strength() > 6) then
		text = text .. "Если займешь второго, то можем попытаться.";

		-- add hiden topic
		npc:addDialogTopic(8);
		ui:updateDialogTopics();
	end

	-- edit text
	dialog:setTextEnd(text);
end