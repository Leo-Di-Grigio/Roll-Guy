function execute(dialog, player, npc)
	local text = dialog:textEnd();

	if(player:stamina() > 6) then
		text = text .. " ...Хотя, может, ты и сможешь...э, надсмотрщик на нас пялится, копай.";

		-- add hiden topic
		npc:addDialogTopic(3);
		ui:updateDialogTopics();
	end

	-- edit text
	dialog:setTextEnd(text);
end