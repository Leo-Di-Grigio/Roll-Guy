function execute(dialog, player, npc)
	local text = dialog:textEnd();

	if(player:strength() > 6) then
		text = text .. "Если займешь второго, то можем попытаться.";
	end

	-- edit text
	dialog:setTextEnd(text);
end